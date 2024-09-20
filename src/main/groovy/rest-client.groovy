import java.util.logging.Logger
import org.apache.camel.Exchange

import org.apache.hc.client5.http.classic.methods.HttpPost
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.http.io.entity.StringEntity
import groovy.json.JsonOutput

def sendPostRequest(String url, Map < String, Object > payload) {
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
            println("Response Status: ${respStatus}")
            println("Response Body: ${respBody}")

            exchange.getIn().setHeader("RespStatus", String.valueOf(respStatus));
            exchange.getIn().setBody(respBody)
        } finally {
            response.close()
        }
    } finally {
        httpClient.close()
    }
}

//exchange.getIn().setBody("Hello Rest client from Karavan rest-client!")
//println 'Until here all good'

def payload = [id: 0, petId: 0, quantity: 0, shipDate: '2024-09-20T12:18:35.352Z', status: 'placed', complete: true]

def url = 'https://petstore.swagger.io/v2/store/order'

sendPostRequest(url, payload)

//println 'REST call ended!'