import java.util.logging.Logger
import org.apache.camel.Exchange
import groovy.json.JsonOutput

def process(Exchange exchange, Logger logger) throws Exception {
    String body = exchange.getIn().getBody(String.class)

    String country = exchange.getProperty("country")
    String fileId = exchange.getProperty("fileId")

    String filename = exchange.getProperty("filename")
    def exchangeId = exchange.getExchangeId()

    logger.info("rest-api-move-resp-life Country: [${country}], filename:[${filename}], exchangeId:[${exchangeId}]")
    String responseFileName = "response-${country}-${exchangeId}_${filename}.xml"
    logger.info("rest-api-move-resp-life responseFileName: [${responseFileName}]")

    String uri = "https://test-v4.test.development.int/api/v1/files/${fileId}"
    logger.info("rest-api-move-resp-life uri: [${uri}]")
    exchange.getIn().setHeader("CamelHttpUri", uri)

    def payload = JsonOutput.toJson(responseFileName)
    exchange.getIn().setBody(payload)
    exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json")

    exchange.getIn().setBody(json)
}

Logger logger = Logger.getLogger("")

logger.info("REST API call to move file starts!")

process(exchange, logger)
logger.info("REST API call to move file ended!")
