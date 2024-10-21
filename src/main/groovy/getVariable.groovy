
import java.util.zip.*
import java.util.logging.Logger
import org.apache.camel.Exchange
import org.apache.camel.language.simple.SimpleLanguage

Logger logger = Logger.getLogger("")
logger.info("getVariable")

process(exchange, logger)

def process(Exchange exchange, Logger logger) throws Exception {
    def body = exchange.getIn().getBody(String)

    //def camelContext = exchange.getContext()
    //def tokenCyprus = camelContext.resolvePropertyPlaceholders("{{variable.global:tokenCyprus}}")

    def tokenCyprus = exchange.getProperty("tokenCyprus")

    logger.info("Global Variable tokenCyprus: ${tokenCyprus}")

    def uri = "${tokenCyprus}"

    logger.info("PRINTING uri: [${uri}]")

    exchange.getIn().setHeader("CamelHttpUri", uri)

}
