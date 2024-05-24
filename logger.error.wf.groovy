import java.util.logging.Logger
import org.apache.camel.Exchange 
import java.io.File

Logger LOGGER = Logger.getLogger("")

LOGGER.info("[ERROR WF] EXCHANGE ID: ${exchangeId} before process call")

//process(exchange, LOGGER)

def process(Exchange exchange, Logger LOGGER) throws Exception {
    // Extract error details
    def exchangeId = exchange.getExchangeId()
   

    try {
        def errorMessage = e.getMessage()

        LOGGER.info("[ERROR WF] EXCHANGE ID: ${exchangeId} ERROR: ${errorMessage}")
        def myFile = exchange.getIn().getBody(File.class)
        File originalFile = new File(myFile.getAbsolutePath())

        
        def newFilePath = "Fileshare/2-failed/${myFile.getName()}" // Adjust the path as needed
        File newFile = new File(newFilePath)

    
        originalFile.renameTo(newFile)
         
    } catch (Exception ex) {
        // Handle the exception from moving the file
        LOGGER.warning("Failed to move file: ${ex.getMessage()}")
        // Optionally, you can decide what to do next, such as logging the failure or taking corrective action
    }
}
