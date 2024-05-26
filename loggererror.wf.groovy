import java.util.logging.Logger
import org.apache.camel.Exchange

Logger LOGGER = Logger.getLogger("")

LOGGER.info("[ERROR WF] groovy script\n")

process(exchange, LOGGER)

//def process(Exception exception, Exchange exchange, Logger LOGGER) throws Exception {
def process(Exchange exchange, Logger LOGGER) throws Exception {
    try {
        def exchangeId = exchange.getExchangeId()     

        String exceptionMessage = exchange.getIn().getHeader("ExceptionMessage", String.class)

        LOGGER.info("[ERROR WF] EXCHANGE ID: ${exchangeId}. Exception message: ${exceptionMessage}\n")

    } catch (Exception e) {
        // Log the exception details
        LOGGER.info("[ERROR WF] Exception occurred: ${e.message}")
        e.printStackTrace() // Print the stack trace (optional)

        // Rethrow the exception if needed
        throw e
    }
}
