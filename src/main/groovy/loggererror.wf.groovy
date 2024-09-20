import java.util.logging.Logger
import org.apache.camel.Exchange

Logger logger = Logger.getLogger("")

//logger.info("[ERROR WF] groovy script\n")

process(exchange, logger)

//def process(Exception exception, Exchange exchange, Logger logger) throws Exception {
def process(Exchange exchange, Logger logger) throws Exception {
    try {
        def exchangeId = exchange.getExchangeId()     

        String exceptionMessage = exchange.getIn().getHeader("ExceptionMessage", String.class)

        logger.info("EXCHANGE ID: ${exchangeId} - Exception message: ${exceptionMessage}\n")

    } catch (Exception e) {
        // Log the exception details
        logger.info("EXCHANGE ID: ${exchangeId} - Exception occurred: ${e.message}")
        e.printStackTrace() // Print the stack trace (optional)

        // Rethrow the exception if needed
        throw e
    }
}
