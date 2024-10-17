package com.example

import org.apache.camel.Exchange
import java.util.logging.Logger
import org.apache.camel.Processor
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.nio.file.Files
import java.nio.file.Paths
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.MultipartEntityBuilder

def createTempFileWithCustomName(filename) {
    def tempDir = System.getProperty("java.io.tmpdir")
    def tempFilePath = Paths.get(tempDir, filename)
    def fileContent = "dummy content response file"
    Files.write(tempFilePath, fileContent.getBytes()) // Create the file and write content
    return tempFilePath.toFile()
}

def processFormData(Exchange exchange, Logger logger, File file) throws Exception {
    def builder = MultipartEntityBuilder.create()
    builder.addBinaryBody("file", file) // Adding the file parameter

    String comment = "File from Camel route " + exchange.getExchangeId() // Adding the comments parameter
    builder.addTextBody("comments", comment)

    def entity = builder.build()
    exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "multipart/form-data")
    exchange.getIn().setBody(entity)

    def url = "https://lfe.test.development.ing/api/v1/folders/{{folderid}}/files"
    exchange.getIn().setHeader("CamelHttpMethod", "POST")
}


def identifyParentFolderid(Exchange exchange) throws Exception{
    String folderid = exchange.getProperty("folderid")
    return folderid
}

def process(Exchange exchange, Logger logger) throws Exception{
    String body = exchange.getIn().getBody(String.class)

    try{
        def resp = new JsonSlurper().parseText(body)
        String folderID = resp.folderID
        String uploadIP = resp.uploadIP

        exchange.setProperty("folderid", folderID)

        String filename = exchange.getProperty("filename")
        String country = exchange.getProperty("country")

        def currentDateTime = LocalDateTime.now()

        def formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss")
        def formattedDateTime = currentDateTime.format(formatter)
        String exchangeId = exchange.getExchangeId()

        String respFilename = "processed-" + exchangeId + "_" + filename

        def tempFile = createTempFileWithCustomName(respFilename)

        processFormData(exchange, logger, tempFile)
    }catch(Exception e){
        logger.info("lfe-multipart-processor JSON parsing exception:" + e.message)
        throw e
    }
}

Logger logger = Logger.getLogger("")

process(exchange, logger)
