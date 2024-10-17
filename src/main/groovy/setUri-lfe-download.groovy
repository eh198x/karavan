
import java.util.zip.*
import java.util.logging.Logger
import org.apache.camel.Exchange

Logger logger = Logger.getLogger("")
logger.info("setUri-life-download")

process(exchange, logger)

def process(Exchange exchange, Logger logger) throws Exception {
    def body = exchange.getIn().getBody(String)

    logger.info("setUri-life-download body is:[${body}]")

    body = body.replaceAll("\r", "")

    logger.info("setUri-life-download body after replace:[${body}]")

    def bodyMap = body.split("\n").collectEntries { entry ->
        def pair = entry.split(":")
        [(pair[0].trim()): pair[1].trim()]
    }

    def itemId = bodyMap['id']
    def uri = "http://life.test.development.int/api/v1/files/${itemId}/download"
    exchange.getIn().setHeader("CamelHttpUri", uri)

    def itemName = bodyMap['name']
    def itemCountry = bodyMap['country']
    logger.info("setUri-life-download Extracted id:[${itemId}], filename: [${itemName}] and country:[${itemCountry}]")

    exchange.setProperty("fileName", itemName)
    exchange.setProperty("fileId", itemId)
    exchange.setProperty("country", itemCountry)

    //set the body to null
    //exchange.getIn().setBody(null)
}
