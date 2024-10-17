import java.util.logging.Logger
import org.apache.camel.Exchange

import java.net.URLEncoder

def encodeParam(p, v) {
    param.collect { k, v -> "${URLEncoder.encode(k, 'UTF-8')}=${URLEncoder.encode(v, 'UTF-8')}" }.join('&')
}

Logger logger = Logger.getLogger("")

String lfeUrl = exchange.getVariable("lfeUrl", String.class)
String lfeUsername = exchange.getVariable("lfeUsername", String.class)
String lfePassword = exchange.getVariable("lfePassword", String.class)

// not working
// def lfeUrlPublic = exchange.context.resolvePropertyPlaceholders("{{lfeUrl}}")

logger.info("body-auth-api lfeUrlPublic:[${lfeUrlPublic}]")

logger.info("body-auth-api The value of LFE Url is: ${lfeUrl} and username/password: ${lfeUsername}/${lfePassword}")

def payload = [grant_type: 'password', username: lfeUsername, password: lfePassword]
String uri = encodeParam(payload)

def url = "${lfeUrl}/api/v1/token"

exchange.getIn().setHeader("CamelHttpUri", url)
exchange.getIn().setHeader("CamelHttpMethod", "POST")
exchange.getIn().setBody(payload)

