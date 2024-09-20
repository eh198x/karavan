import groovy.xml.XmlParser

import org.apache.camel.Exchange
import java.util.logging.Logger

//import groovy.json.JsonSlurper


Logger logger = Logger.getLogger("")
logger.info("=====SQL Create-Received a new file upload...\n")

//processRest(  logger)
process(exchange, logger)

/*
def processRest( Logger logger ) throws Exception {

 
def restEndpoint = "https://petstore.swagger.io/oauth/token"  // OAuth2 token endpoint
def formData = [
grant_type: "password",
username: "test",
password: "abc123"
]

// Send a POST request to the token endpoint
def response = new URL(restEndpoint).openConnection().with {
requestMethod = "POST"
doOutput = true
outputStream.withWriter { writer ->
writer << formData.collect { k, v -> "$k=$v" }.join("&")
}
inputStream.text
}

// Parse the response to extract the access token
def jsonSlurper = new JsonSlurper()
def accessToken = jsonSlurper.parseText(response)?.access_token

println "Access Token: $accessToken"


}
 */


def process(Exchange exchange, Logger logger ) throws Exception {
     
    def xmlStream = exchange.getIn().getBody(File.class);  
  
    logger.info("===== Printing xml file:" + xmlStream )
  
    def articles = new XmlParser().parse(xmlStream)

    def sqlStatements = []  // List to store generated SQL statements
 
    def i = 0

    articles.article.each { article ->
        def author = article.author.text()
        def title = article.title.text()
        def releaseDate = article.children().get(2).text()

        //println "Articles: '$article' "
        //println "Release Date: '$releaseDate'"

        def sql


        if (author.contains("Jonas")) { 
            sql = "INSERT INTO articles (title, author, release_date) VALUES ('$title', '${author} ${article.author.text()}', '$releaseDate')"
        } else if (author.contains("Elias")) {
            sql = "UPDATE articles SET title = '$title', release_date = '$releaseDate' WHERE author = '$author'"
        } else if (author.contains("Peter")) {
            sql = "DELETE FROM articles WHERE author = '$author'"
        } else { 
            println "Author is '$author'. Skipping: Author is not Jonas, Elias, or Peter."
        }

        if (sql) {  
            println "=== Generated SQL: " + sql  // Print the generated SQL statement for each article
            sqlStatements.add(sql)  // Add generated SQL to the list
        }
  
  
        //} //closing for each article

        println "Finished processing articles.\n"

        // Set the list of SQL statements as an exchange property
        exchange.setProperty("sqlStatements", sqlStatements)
 
        // Add REST API call here
        def restEndpoint = "https://petstore.swagger.io/v2/pet"  // Replace with your actual API endpoint
        def sampleBody = [
            id: i,
            category: [
                id: i,
                name: author
            ],
            name: author + "'s pet",
            photoUrls: ["string"],
            tags: [
                [
                    id: i,
                    name: "Just a tag"
                ]
            ],
            status: "available"
        ]

        /* -- not working
        exchange.getIn().setHeader("Content-Type", "application/json")
        exchange.getIn().setBody(sampleBody)
        exchange.getIn().setHeader("CamelHttpMethod", "POST")

        // Add OIDC authentication headers
        exchange.getIn().setHeader("Authorization", "Basic " + Base64.encodeBase64String("test:abc123".getBytes()))
         */
        // Log the REST API request
        println "Calling REST API: POST $restEndpoint with body:\n${sampleBody}\n"

        // You can add the REST component here if needed
        // Example: .to("rest:{{restEndpoint}}")

        // Process the API response
        def apiResponse = exchange.getIn().getBody(String)  // Assuming the response is a String
        println "Received API response:\n$apiResponse\n"

        // Additional processing or transformation as needed
        // ...

        // Set the processed response (if applicable)
        exchange.setProperty("processedApiResponse", apiResponse)
   
        i++
    } //closing for each article

}
