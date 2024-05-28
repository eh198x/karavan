
import org.apache.camel.Exchange
//import java.util.logging.Logger
 
import org.apache.logging.log4j.Logger

import groovy.json.JsonSlurper

def process(Exchange exchange, Logger LOGGER ) throws Exception {
     
    def xmlStream = exchange.getIn().getBody(File.class);  
  
    LOGGER.info("===== Identified file: " + xmlStream )
    LOGGER.warn("===== just a warning" )
    LOGGER.error("===== an error!" )
}


def jsonProcess(Exchange exchange, Logger LOGGER ) throws Exception {
     
    def jsonResponse = exchange.getIn().getBody(String.class);  
  
    def parsedResponse = new JsonSlurper().parseText(jsonResponse)
    
    // Extract relevant fields (e.g., "id")
    def id = parsedResponse.id
    def exchangeId = parsedResponse.exchange_id
    def message = parsedResponse.message
    def status = parsedResponse.status

    // Log the extracted values
    LOGGER.info("ID: $id")
    LOGGER.info("Exchange ID: $exchangeId")
    LOGGER.info("Message: $message")
    LOGGER.info("Status: $status")

}