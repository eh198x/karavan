import java.util.UUID;

import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Message;

@Configuration
@BindToRegistry("CustomProcessFile")
public class CustomProcessFile implements Processor {
 
    public void process(Exchange exchange) throws Exception {
        String uuid = UUID.randomUUID().toString();
        exchange.getIn().setBody("File uploaded! UUID: " + uuid);
    }

}