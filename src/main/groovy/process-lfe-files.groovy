import java.util.logging.Logger
import org.apache.camel.Exchange
import java.net.URLEncoder
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

def encodeParams(params) {
    params.collect { k, v -> "${URLEncoder.encode(k, 'UTF-8')}=${URLEncoder.encode(v, 'UTF-8')}" }.join('&')
}

def extractCountry(String path) {
    def matcher = (path =~ /\/V1\/S\/V1\/(.*)/)
    return matcher ? matcher[0][1] : null
}

def process(Exchange exchange, Logger logger) throws Exception {
    String body = exchange.getIn().getBody(String.class)
    logger.info("Received body: ${body}")

    try {
        def map = new JsonSlurper().parseText(body)
        def params = [
            path: map.path,
            id: map.id,
            name: map.name,
            country: extractCountry(map.path)
        ]

        if (params.country == null) {
            logger.info("Path does not start with /V1/S/: ${params.path}")
            return
        }

        def jsonContext = [
            path: params.path,
            id: params.id,
            name: params.name,
            country: params.country
        ]

        exchange.getIn().setBody(JsonOutput.toJson(jsonContext))
    } catch (Exception e) {
        logger.info("Failed to parse JSON: ${e.message}")
    }
}

Logger logger = Logger.getLogger("")
process(exchange, logger)
