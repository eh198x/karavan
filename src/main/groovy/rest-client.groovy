import java.util.logging.Logger
import org.apache.camel.Exchange

import org.apache.hc.client5.http.classic.methods.HttpPost
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse
import org.apache.hc.client5.http.impl.classic.HttpClients


import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def sendPostRequest(Logger logger, String url, Map < String, Object > payload) {
    CloseableHttpClient httpClient = HttpClients.createDefault()
    try {
        HttpPost postRequest = new HttpPost(url)
        String jsonPayload = JsonOutput.toJson(payload)
        StringEntity entity = new StringEntity(jsonPayload)
        postRequest.setEntity(entity)
        postRequest.setHeader("Content-Type", "application/json")
        CloseableHttpResponse response = httpClient.execute(postRequest)
        try {
            def respStatus = response.getCode()
            def respBody = response.getEntity().getContent().text
            logger.info("Response Status: ${respStatus}")
            logger.info("Response Body: ${respBody}")

            // Parse the JSON response
            def jsonSlurper = new JsonSlurper()
            def respMap = jsonSlurper.parseText(respBody)
            
            def quantity = respMap.quantity
            def status = respMap.status
            logger.info("Response Body.quantity: ${quantity}")
            logger.info("Response Body.status: ${status}")

            exchange.getIn().setHeader("RespStatus", String.valueOf(respStatus));
            exchange.getIn().setBody(respBody)

        } finally {
            response.close()
        }
    } finally {
        httpClient.close()
    }
}

Logger logger = Logger.getLogger("")

//exchange.getIn().setBody("Hello Rest client from Karavan rest-client!")
//println 'Until here all good'

def shipDate = '2024-09-26T12:12:12.120Z'

def payload = [id: 0, petId: 0, quantity: 0, shipDate: shipDate, status: 'placed', complete: true]

def url = 'https://petstore.swagger.io/v2/store/order'

sendPostRequest(logger, url, payload)

//println 'REST call ended!'