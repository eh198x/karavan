import org.apache.camel.Exchange
import org.apache.camel.Processor

class RetrieveTokenProcessor implements Processor {
    @Override
    @Bean
    void process(Exchange exchange) throws Exception {
        def tokenStore = exchange.getContext().getRegistry().lookupByName("tokenStore")
        def tokenCyprus = tokenStore.get("tokenCyprus")
        exchange.setProperty("tokenCyprus", tokenCyprus)
    }
}
