import groovy.xml.XmlParser

import org.apache.camel.Exchange
import java.util.logging.Logger


Logger logger = Logger.getLogger("")
logger.info("=====SQL Create-Received a new file upload...\n")

process(exchange, logger)

def process(Exchange exchange, Logger logger ) throws Exception {
    //def xmlFile = exchange.getIn(File.class)
    
    logger.info("===== Before printing xml file")

    //Object xmlFile = exchange.getIn().getBody(); //Works

    def xmlStream = exchange.getIn().getBody(File.class); //Works
  
    logger.info("===== Printing xml file:" + xmlStream )
 
    // Parse the XML content using XmlParser
    def articles = new XmlParser().parse(xmlStream)
 
    articles.article.each { article ->
        def firstName = article.author.firstname.text()
        def title = article.title.text()
        def releaseDate = article.children().get(2).text()

        println("firstName: '$firstName', title: '$title', releaseDate: 'releaseDate'")
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
