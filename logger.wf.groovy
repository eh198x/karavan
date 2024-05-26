import java.util.logging.Logger
import org.apache.camel.Exchange 
import java.io.File


Logger LOGGER = Logger.getLogger("")
//LOGGER.info("Received a new file upload...\n")
process(exchange, LOGGER)

def process(Exchange exchange, Logger LOGGER) throws Exception {    

    def exchangeId = exchange.getExchangeId() // Access the Exchange ID

    def myFile = exchange.getIn().getBody(File.class); //Works
    
      exchange.setProperty("myFile", myFile.absolutePath) // Use absolute path for clarity


    LOGGER.info("EXCHANGE ID: ${exchangeId} - Workflow started for file: ${myFile}")


}

