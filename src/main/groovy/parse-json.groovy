import java.util.logging.Logger
import org.apache.camel.Exchange

//import groovy.xml.*
import groovy.json.JsonSlurper

Logger logger = Logger.getLogger('')
//logger.info("Received a new XML...\n")

//def result =
process(exchange, logger)

/*
String title = result[0];
String author = result[1];

logger.info("before setVariables:: title: $title, author: $author")

setVariables(title, author, exchange, logger)
*/

def process(Exchange exchange, Logger logger) throws Exception {
    String body = exchange.getIn().getBody(String.class)
    //String httpMethod = exchange.getIn().getHeader(Exchange.HTTP_METHOD, String.class)

    //logger.info("HTTP method:" + httpMethod + "\nBody:\n" + body + "\n")

    logger.info("Body: [${body}]")

    try{
        def parsedResponse = new JsonSlurper().parseText(body)

        logger.info("parsedResponse: [${parsedResponse}]")

        /*
        String id = parsedResponse.id
        String exchangeId = parsedResponse.exchange_id
        String message = parsedResponse.message
        String status = parsedResponse.status

        def recid = id.toInteger()
        logger.info("internal2rest.groovy recid: $recid exchangeID: $exchangeId, message: $message, status: $status")
        */

        String fruit = parsedResponse.fruit
        String size = parsedResponse.size
        String color = parsedResponse.color
        logger.info("Retrieved values:: fruit:${fruit}, size:${size}, color:${color}")

        Integer recid = 10
        exchange.setProperty('recid', recid) //recid is used by the postgres component
        exchange.setProperty('fruit', fruit)
        //logger.info("Setting recid: $recid")

        String message = "This is my message and it includes fruit:${fruit} and recid:${recid}"

        exchange.getIn().setBody(message)

        logger.info("Body was set, now will call postgres")

    } catch(Exception e){
        logger.info("JSON Parsing Exception:" + e.message)
        throw e
    }
}

/*
def setVariables(String title, String author, Exchange exchange, Logger logger) throws Exception {
    String exchangeId = exchange.getExchangeId()
    exchange.setProperty("title", title)
    exchange.setProperty("author", author)
    logger.info("EXCHANGE ID: ${exchangeId} setVariables: title: $title, author: $author")
}
*/
