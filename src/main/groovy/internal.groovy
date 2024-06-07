import java.util.logging.Logger
import org.apache.camel.Exchange
//import io.netty.handler.codec.http.multipart.*

Logger LOGGER = Logger.getLogger("")
//LOGGER.info("Received a new XML...\n")
process(exchange, LOGGER)

setVariables(exchange, LOGGER)

def process(Exchange exchange, Logger LOGGER) throws Exception {
    //String content = exchange.getIn(String.class)
    ////NettyHttpMessage nettyHttpMessage = exchange.getIn(File.class)
    String body = exchange.getIn().getBody(String.class);
    
    String httpMethod = exchange.getIn().getHeader(Exchange.HTTP_METHOD, String.class);
    
    LOGGER.info("HTTP method:" + httpMethod + "\nBody:\n" + body + "\n")

    //HttpPostRequestDecoder postRequest = new HttpPostRequestDecoder(nettyHttpMessage.getHttpRequest())
    //getHttpDataAttributes(postRequest, LOGGER)
   
   //request.body="XML was processed succesfully!"
}

def setVariables(Exchange exchange, Logger LOGGER) throws Exception {

    String exchangeId = exchange.getExchangeId()  
    String title="internal.groovy.title"
    String author="internal.groovy.author"

    
    exchange.setProperty("title", title)
    exchange.setProperty("author", author)    
    LOGGER.info("EXCHANGE ID: ${exchangeId} setVariables: title: $title, author: $author")

    /*
    def jsonBody = [
        "articles": [
            [
                "author": "oscerd",
                "title": "Rome"
            ]
        ]
    ]

    // Convert the map to a JSON string
    def jsonString = new groovy.json.JsonBuilder(jsonBody).toPrettyString()

    println jsonString
    */
}
