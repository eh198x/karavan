import java.util.logging.Logger
import org.apache.camel.Exchange

import groovy.xml.*
import groovy.json.JsonSlurper

Logger LOGGER = Logger.getLogger("")
//LOGGER.info("Received a new XML...\n")

//def result = 
process(exchange, LOGGER)

/*
String title = result[0];
String author = result[1];

LOGGER.info("before setVariables:: title: $title, author: $author")

setVariables(title, author, exchange, LOGGER)
*/

def process(Exchange exchange, Logger LOGGER) throws Exception {
    String body = exchange.getIn().getBody(String.class);  
    String httpMethod = exchange.getIn().getHeader(Exchange.HTTP_METHOD, String.class);
    
    //LOGGER.info("HTTP method:" + httpMethod + "\nBody:\n" + body + "\n")
    
    LOGGER.info("Body $body");

    def parsedResponse = new JsonSlurper().parseText(body)

    LOGGER.info("parsedResponse $parsedResponse");

    String id = parsedResponse.id
    String exchangeId = parsedResponse.exchange_id
    String message = parsedResponse.message
    String status = parsedResponse.status
    
    def recid = id.toInteger()
    LOGGER.info("internal2rest.groovy recid: $recid exchangeID: $exchangeId, message: $message")
    
    exchange.setProperty("recid", recid)
    //LOGGER.info("Setting recid: $recid")

    exchange.getIn().setBody(message)
}

/*
def setVariables(String title, String author, Exchange exchange, Logger LOGGER) throws Exception {
    String exchangeId = exchange.getExchangeId()  
    exchange.setProperty("title", title)
    exchange.setProperty("author", author)    
    LOGGER.info("EXCHANGE ID: ${exchangeId} setVariables: title: $title, author: $author")
}
*/