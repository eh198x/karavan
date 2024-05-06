import groovy.xml.XmlParser

import org.apache.camel.Exchange
import java.util.logging.Logger


Logger LOGGER = Logger.getLogger("")
LOGGER.info("=====SQL Create-Received a new file upload...\n")

process(exchange, LOGGER)

def process(Exchange exchange, Logger LOGGER ) throws Exception {
    //def xmlFile = exchange.getIn(File.class)
    
    //LOGGER.info("===== Before printing xml file")

    //Object xmlFile = exchange.getIn().getBody(); //Works

    def xmlStream = exchange.getIn().getBody(File.class); //Works
  
    LOGGER.info("===== Printing xml file:" + xmlStream )
 
    // Parse the XML content using XmlParser
    def articles = new XmlParser().parse(xmlStream)

    def sqlStatements = []  // List to store generated SQL statements

 
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
  
  
    }

    // Set the list of SQL statements as an exchange property
    exchange.setProperty("sqlStatements", sqlStatements)

    println "Finished processing articles."
   
}

 

/*
def xmlFile = getClass().getResourceAsStream("articles.xml")

// Parse the XML content using XmlParser
def articles = new XmlParser().parse(xmlFile)
 
articles.article.each { article ->
def firstName = article.author.firstname.text()
def title = article.title.text()
def releaseDate = article.children().get(2).text()

//println "Articles: '$article' "
//println "Release Date: '$releaseDate'"

def sql

if (firstName == "Jonas") {
sql = "INSERT INTO articles (title, author, release_date) VALUES ('$title', '${firstName} ${article.author.lastname.text()}', '$releaseDate')"
} else if (firstName == "Elias") {
sql = "UPDATE articles SET title = '$title', release_date = '$releaseDate' WHERE firstname = '$firstName'"
} else if (firstName == "Peter") {
sql = "DELETE FROM articles WHERE firstname = '$firstName'"
} else {
// Handle cases where firstname doesn't match any condition (optional: log or skip)
println "Skipping article: First name is not Jonas, Elias, or Peter."
}

if (sql) {
println sql  // Print the generated SQL statement for each article
}
  
  
}


println "Finished processing articles."
 */
