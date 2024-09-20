import java.util.logging.Logger
import org.apache.camel.Exchange

import groovy.xml.*

Logger logger = Logger.getLogger("")
//logger.info("Received a new XML...\n")

def result = process(exchange, logger)

String title = result[0];
String author = result[1];

logger.info("before setVariables:: title: $title, author: $author")

setVariables(title, author, exchange, logger)

def process(Exchange exchange, Logger logger) throws Exception {
    //String content = exchange.getIn(String.class)
    ////NettyHttpMessage nettyHttpMessage = exchange.getIn(File.class)
    String body = exchange.getIn().getBody(String.class);
    
    String httpMethod = exchange.getIn().getHeader(Exchange.HTTP_METHOD, String.class);
    
    //logger.info("HTTP method:" + httpMethod + "\nBody:\n" + body + "\n")

    // Parse XML using Groovy Slurper
    def xml = new XmlSlurper().parseText(body)

    String firstArticleTitle = xml.article[0].title.text()
    String authorFullName = "${xml.article[0].author.firstname.text()} ${xml.article[0].author.lastname.text()}"
    
    logger.info("First Article Title: $firstArticleTitle")
    logger.info("Author Full Name: $authorFullName")

    return [firstArticleTitle, authorFullName]
}

def setVariables(String title, String author, Exchange exchange, Logger logger) throws Exception {
    String exchangeId = exchange.getExchangeId()  
    exchange.setProperty("title", title)
    exchange.setProperty("author", author)    
    logger.info("EXCHANGE ID: ${exchangeId} setVariables: title: $title, author: $author")
}
