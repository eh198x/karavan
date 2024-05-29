
import org.apache.camel.Exchange
//import java.util.logging.Logger
 
import org.apache.logging.log4j.Logger
//import org.apache.logging.log4j.LogManager

import groovy.json.JsonSlurper

def process(Exchange exchange, Logger LOGGER ) throws Exception {
     
    def xmlStream = exchange.getIn().getBody(File.class);  
  
    LOGGER.info("===== Identified file: " + xmlStream )
    LOGGER.warn("===== just a warning" )
    LOGGER.error("===== an error!" )
}


def jsonProcess(Exchange exchange, Logger LOGGER ) throws Exception {
     

    def jsonResponse = exchange.getIn().getBody(String.class);  
  
    LOGGER.info("Into tools.jsonProcess... jsonResponse:"+ jsonResponse)

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

    try {
        def recid = id.toInteger()
        exchange.setProperty("recid", recid)
        exchange.setProperty("update_workflow_status", "UPDATE public.workflow SET status='AA' WHERE id = $recid");
        LOGGER.info("Setting recid: $recid")
        exchange.setProperty("recmessage", message)
    } catch (NumberFormatException e) {
        LOGGER.error("Error converting 'id' to integer: ${e.message}")
        // Handle the error (e.g., set a default value or log an error message)
    }
    

}