import java.util.logging.Logger
import org.apache.camel.Exchange 
import java.io.File

Logger LOGGER = Logger.getLogger("")
//LOGGER.info("Received a new file upload...\n")
process(exchange, LOGGER)

def process(Exchange exchange, Logger LOGGER) throws Exception {
   
    def myFile = exchange.getIn().getBody(File.class); //Works
    
    LOGGER.info("Workflow started for file:" + myFile )
 
   
}

