import java.util.logging.Logger
import org.apache.camel.Exchange
import io.netty.handler.codec.http.multipart.*
import org.apache.camel.component.netty.http.NettyHttpMessage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.io.File

//import org.apache.camel.support.ExchangeHelper

Logger LOGGER = Logger.getLogger("")
//LOGGER.info("Received a new XML...\n")
process(exchange, LOGGER)

def process(Exchange exchange, Logger LOGGER) throws Exception {
    //String content = exchange.getIn(String.class)
    ////NettyHttpMessage nettyHttpMessage = exchange.getIn(File.class)
    String body = exchange.getIn().getBody(String.class);
    
    String httpMethod = exchange.getIn().getHeader(Exchange.HTTP_METHOD, String.class);
    
    // + "\nBody:\n" + body + "\n")

    //HttpPostRequestDecoder postRequest = new HttpPostRequestDecoder(nettyHttpMessage.getHttpRequest())
    //getHttpDataAttributes(postRequest, LOGGER)
   //exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "application/xml")

   String response = "<response>Error!</response>"
   //exchange.getOut().setBody(response)
   LOGGER.info("HTTP method:" + httpMethod + ", Error Response:" + response)
   request.body= response
}

