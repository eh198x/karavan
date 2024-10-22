package cfl.eua.karavan;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArtemisConfig {
    @Bean
    public AMQPConnectionDetails securedAmqpConnection() {
        String username;
        return new AMQPConnectionDetails("{{camel.amqp.uri}}", "{{camel.amqp.username}}", "{{camel.amqp.password}}");
    }
}