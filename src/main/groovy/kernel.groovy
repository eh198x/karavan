import java.util.logging.Logger
import org.apache.camel.Exchange
//import io.netty.handler.codec.http.multipart.*
import org.apache.camel.component.netty.http.NettyHttpMessage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.io.File

Logger logger = Logger.getLogger('')
logger.info('Received a new file upload...\n')
process(exchange, logger)

def process(Exchange exchange, Logger logger) throws Exception {
    NettyHttpMessage nettyHttpMessage = exchange.getIn(NettyHttpMessage.class)
    //logger.info("Inside process 1\n")
    HttpPostRequestDecoder postRequest = new HttpPostRequestDecoder(nettyHttpMessage.getHttpRequest())
    //logger.info("Inside process 2\n")
    getHttpDataAttributes(postRequest, logger)
    //logger.info("Inside process 3\n")
   
}

def getHttpDataAttributes(HttpPostRequestDecoder request, Logger logger) {
    try {
        for (InterfaceHttpData part : request.getBodyHttpDatas()) {
            if (part instanceof MixedAttribute) {
                Attribute attribute = (MixedAttribute) part
                logger.info(String.format("Found part with key: %s and value: %s ", attribute.getName(), attribute.getValue()))
            } else if (part instanceof MixedFileUpload) {
                MixedFileUpload attribute = (MixedFileUpload) part
                logger.info(String.format("Found part with key: %s and value: %s ", attribute.getName(), attribute.getFilename()))
                saveFile(attribute)
               
            }
        }
    } catch (IOException e) {
        String errorMsg = String.format("Cannot parse request")
        logger.error(errorMsg,e)
    } finally {        
    }  
}

def saveFile (MixedFileUpload fileUpload) {
    try {
        Path uploadsPath = Paths.get("Fileshare/1-uploads");
        Files.createDirectories(uploadsPath);
        File file = new File(uploadsPath.toFile(), fileUpload.getFilename());
        byte[] fileContent = fileUpload.get();
        Files.write(file.toPath(), fileContent);      
    } catch (IOException e) {
        e.printStackTrace();
        throw new IOException("Failed to save file", e);
    }
}
request.body="File uploaded succesfully!"
