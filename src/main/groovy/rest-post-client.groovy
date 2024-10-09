import java.util.logging.Logger
import org.apache.camel.Exchange

import org.apache.hc.client5.http.classic.methods.HttpPost
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse
import org.apache.hc.client5.http.impl.classic.HttpClients

import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder
import org.apache.hc.client5.http.entity.mime.FileBody
import org.apache.hc.client5.http.entity.mime.StringBody

import org.apache.hc.core5.http.ContentType

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def sendPostMultipartRequest(Logger logger, String url, String id, String name, String status, File file) {
    CloseableHttpClient httpClient = HttpClients.createDefault()
    try {
        url += "/" + id
        HttpPost postRequest = new HttpPost(url)

        def entity = MultipartEntityBuilder.create()            
            .addPart("file", new FileBody(file))
            .addPart("comment", new StringBody("uploaded via Groovy code", ContentType.TEXT_PLAIN))
            .build()

        postRequest.setEntity(entity)
        postRequest.setHeader("Content-Type", "multipart/form-data")
        CloseableHttpResponse response = httpClient.execute(postRequest)
        try {
            def respStatus = response.getCode()
            def respBody = response.getEntity().getContent().text
            logger.info("Response Status: ${respStatus}")
            logger.info("Response Body: ${respBody}")

            // Parse the JSON response
            def jsonSlurper = new JsonSlurper()
            def respMap = jsonSlurper.parseText(respBody)
            
            def code = respMap.code
            def message = respMap.message
            logger.info("Response Body.code: [${code}]")
            logger.info("Response Body.message: [${message}]")

            //exchange.getIn().setHeader("RespStatus", String.valueOf(respStatus));
            exchange.getIn().setBody(respBody)

        } finally {
            response.close()
        }
    } finally {
        httpClient.close()
    }
}

Logger logger = Logger.getLogger("")

logger.info("About to call sendPostMultipartRequest!!!!")

def url = 'https://petstore.swagger.io/v2/pet'

String id ='9223372016900031597'
String name = 'Kotenios'
String status = 'placed'
File file = new File("/home/elias/Downloads/cv_typst_ELIAS/me.jpeg") // Replace with the actual file path

sendPostMultipartRequest(logger, url, id, name, status, file)
