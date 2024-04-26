import java.util.logging.Logger
import org.apache.camel.Exchange
import io.netty.handler.codec.http.multipart.*
import org.apache.camel.component.netty.http.NettyHttpMessage
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.io.File

Logger LOGGER = Logger.getLogger("")
LOGGER.info("Received a new file upload...\n")
process(exchange, LOGGER)

def process(Exchange exchange, Logger LOGGER) throws Exception {
    NettyHttpMessage nettyHttpMessage = exchange.getIn(NettyHttpMessage.class)
    HttpPostRequestDecoder postRequest = new HttpPostRequestDecoder(nettyHttpMessage.getHttpRequest())
    getHttpDataAttributes(postRequest, LOGGER)
   
}

def getHttpDataAttributes(HttpPostRequestDecoder request, Logger LOGGER) {
    try {
        for (InterfaceHttpData part : request.getBodyHttpDatas()) {
            if (part instanceof MixedAttribute) {
                Attribute attribute = (MixedAttribute) part
                LOGGER.info(String.format("Found part with key: %s and value: %s ", attribute.getName(), attribute.getValue()))
            } else if (part instanceof MixedFileUpload) {
                MixedFileUpload attribute = (MixedFileUpload) part
                LOGGER.info(String.format("Found part with key: %s and value: %s ", attribute.getName(), attribute.getFilename()))
                saveFile(attribute)
               
            }
        }
    } catch (IOException e) {
        String errorMsg = String.format("Cannot parse request")
        LOGGER.error(errorMsg,e)
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
