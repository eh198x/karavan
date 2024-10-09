import java.util.logging.Logger
import org.apache.camel.Exchange

import org.apache.hc.client5.http.classic.methods.HttpGet
//import org.apache.hc.client5.http.classic.methods.HttpPost
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse
import org.apache.hc.client5.http.impl.classic.HttpClients

import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder
import org.apache.hc.client5.http.entity.mime.FileBody
import org.apache.hc.client5.http.entity.mime.StringBody

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def sendGetRequest(Logger logger, String url) {
    CloseableHttpClient httpClient = HttpClients.createDefault()
    try {
        HttpGet req = new HttpGet(url)
                
        req.setHeader("Content-Type", "application/json")
        CloseableHttpResponse response = httpClient.execute(req)
        try {
            def respStatus = response.getCode()
            def respBody = response.getEntity().getContent().text
            logger.info("Response Status: ${respStatus}")
            logger.info("Response Body: ${respBody}")

            // Parse the JSON response
            
            def jsonSlurper = new JsonSlurper()
            def items = jsonSlurper.parseText(respBody)

            logger.info("Printing items")

            def newItems = items.collect { item ->
                def newItem = [id: item.id, name: item.name, status: item.status]
                //logger.info("ID: ${newItem.id}, Name: ${newItem.name}, Status: ${newItem.status}")
                return newItem
            }

            
            /*
            respMap.each { item ->
                //def id = (item.id % 1000)
               logger.info("ID: ${item.id}, Name: ${item.name}, Status: ${item.status}")
            }
            */

            // Create JSON from the new items
            def json = JsonOutput.toJson(newItems)
            logger.info("Generated JSON: ${json}")

            // Assign JSON to exchange object
            exchange.getIn().setBody(json)

            /*
            def quantity = respMap.quantity
            def status = respMap.status
            logger.info("Response Body.quantity: ${quantity}")
            logger.info("Response Body.status: ${status}")

            exchange.getIn().setHeader("RespStatus", String.valueOf(respStatus));
            */
            //exchange.getIn().setBody(respBody)

        } finally {
            response.close()
        }
    } finally {
        httpClient.close()
    }
}

Logger logger = Logger.getLogger("")

def url = 'https://petstore.swagger.io/v2/pet/findByStatus?status=available'

sendGetRequest(logger, url)

//println 'REST call ended!'