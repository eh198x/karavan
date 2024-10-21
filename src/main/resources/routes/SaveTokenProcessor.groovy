import org.apache.camel.Exchange
import org.apache.camel.Processor

class SaveTokenProcessor implements Processor {
    @Override
    @Bean
    void process(Exchange exchange) throws Exception {
        def tokenStore = exchange.getContext().getRegistry().lookupByName("tokenStore")
        def tokenCyprus = exchange.getProperty("tokenCyprus", String.class)
        tokenStore.put("tokenCyprus", tokenCyprus)
    }
}
