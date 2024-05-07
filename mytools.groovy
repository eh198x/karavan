
import org.apache.camel.Exchange
import java.util.logging.Logger

def process(Exchange exchange, Logger LOGGER ) throws Exception {
     
    def xmlStream = exchange.getIn().getBody(File.class);  
  
    LOGGER.info("===== Identified file: " + xmlStream )
}