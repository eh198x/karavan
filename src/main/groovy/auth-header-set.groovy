 
import java.util.logging.Logger
import org.apache.camel.Exchange
import java.net.URLEncoder
import groovy.json.JsonSlurper

def encodeParams(params) {
    params.collect { k, v -> "${URLEncoder.encode(k, 'UTF-8')}=${URLEncoder.encode(v, 'UTF-8')}" }.join('&')
}

Logger logger = Logger.getLogger("")

def process(Exchange exchange, Logger logger) throws Exception {
    String body = exchange.getIn().getBody(String.class)

    try {
        def parsedResponse = new JsonSlurper().parseText(body)
        String accessToken = parsedResponse.access_token
        String refreshToken = parsedResponse.refresh_token
        accessToken = "Bearer ${accessToken}"

        logger.info("set-header-rest-api Retrieved values: accessToken:[${accessToken}]")
        logger.info("set-header-rest-api Retrieved values: refreshToken:[${refreshToken}]")

        exchange.getIn().setHeader('Authorization', accessToken)
    } catch(Exception e) {
        logger.info("set-header-rest JSON Parsing Exception: " + e.message)
        throw e
    }
}

process(exchange, logger)
def payload = [sortField: 'uploadStamp', perPage: '200', sortDirection: 'desc']
String url = encodeParams(payload)
exchange.getIn().setHeader('CamelHttpUri', url)
