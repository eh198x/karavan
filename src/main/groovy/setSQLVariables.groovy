import java.util.logging.Logger
import org.apache.camel.Exchange

Logger LOGGER = Logger.getLogger("")

process(exchange, LOGGER)

def process(Exchange exchange, Logger LOGGER) throws Exception {
    try {
        setVariables(exchange,LOGGER)
    } catch (Exception e) {
        // Log the exception details
        LOGGER.info("EXCHANGE ID: ${exchangeId} - Exception occurred: ${e.message}")
        e.printStackTrace() // Print the stack trace (optional)

        // Rethrow the exception if needed
        throw e
    }
}

def setVariables(Exchange exchange, Logger LOGGER) throws Exception {
    def exchangeId = exchange.getExchangeId()     

    /*
    def myFile = exchange.getIn().getBody(File.class);

    def filePath = myFile.absolutePath

    def normalizedPath = filePath.replaceAll("[/\\\\]+", "/") // Normalize path separators
    def country = normalizedPath.tokenize("/")[-2] // Get the second-to-last token

    println "Last folder: $country" // This will output the country name
    */
    //String exceptionMessage = exchange.getIn().getHeader("ExceptionMessage", String.class)
    
    String title = exchangeId
    String author = 'Custom Author from setSQLVariables.groovy'
    //def recid = id.toInteger()
    exchange.setProperty("title", title)
    exchange.setProperty("author", author)
    //exchange.setProperty("update_workflow_status", "UPDATE public.workflow SET status='AA' WHERE id = $recid");
    LOGGER.info("EXCHANGE ID: ${exchangeId} Setting title: $title, author: $author")
}