import java.util.logging.Logger
import org.apache.camel.Exchange

import groovy.xml.*

Logger LOGGER = Logger.getLogger("")
//LOGGER.info("Received a new XML...\n")

def result = process(exchange, LOGGER)
String title = result['arg1'];
String author = result['arg2'];

setVariables(title, author, exchange, LOGGER)

def process(Exchange exchange, Logger LOGGER) throws Exception {
    //String content = exchange.getIn(String.class)
    ////NettyHttpMessage nettyHttpMessage = exchange.getIn(File.class)
    String body = exchange.getIn().getBody(String.class);
    
    String httpMethod = exchange.getIn().getHeader(Exchange.HTTP_METHOD, String.class);
    
    //LOGGER.info("HTTP method:" + httpMethod + "\nBody:\n" + body + "\n")

        // Parse XML using Groovy Slurper
    def xml = new XmlSlurper().parseText(body)

    // Loop through articles
    xml.articles.article.each { article ->
        String title = article.title().text()
        String authorFirstName = article.author.firstname().text()
        String authorLastName = article.author.lastname().text()
        String author = authorFirstName + " " + authorLastName;
        // Process extracted data
        LOGGER.info("Title: $title, Author: $author\n")
        //return [title, author]
        return [arg1: title, arg2: author]
        // ... (Optional: store data in exchange properties)
    }
   
   //request.body="XML was processed succesfully!"
}

def setVariables(String title, String author, Exchange exchange, Logger LOGGER) throws Exception {
    String exchangeId = exchange.getExchangeId()  
    exchange.setProperty("title", title)
    exchange.setProperty("author", author)    
    LOGGER.info("EXCHANGE ID: ${exchangeId} setVariables: title: $title, author: $author")
}
