import java.util.logging.Logger
import org.apache.camel.Exchange 
import java.io.File


Logger logger = Logger.getLogger("")
//logger.info("Received a new file upload...\n")
process(exchange, logger)

def process(Exchange exchange, Logger logger) throws Exception {    

    def exchangeId = exchange.getExchangeId() // Access the Exchange ID

    def myFile = exchange.getIn().getBody(File.class); //Works
    
      exchange.setProperty("myFile", myFile.absolutePath) // Use absolute path for clarity


    logger.info("EXCHANGE ID: ${exchangeId} - Workflow started for file: ${myFile}")


}

