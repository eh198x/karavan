import groovy.xml.XmlParser

//import org.apache.camel.Exchange
import java.util.logging.Logger


Logger LOGGER = Logger.getLogger("")
LOGGER.info("SQL Create-Received a new file upload...\n")

process(  LOGGER)

def process(Logger LOGGER) throws Exception {
    //def xmlFile = exchange.getIn(File.class)
    
    LOGGER.info("Before printing xml file")

    def xmlFile = getClass().getResourceAsStream("articles.xml")
 
    LOGGER.info("Printing xml file:" + xmlFile )

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
