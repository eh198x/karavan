package cfl.eua.karavan;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArtemisConfig {
    @Bean
    public AMQPConnectionDetails securedAmqpConnection() {
        String username;
        return new AMQPConnectionDetails("amqp://127.0.0.1:61616", "{{camel.amqp.username}}", "{{camel.amqp.password}}");
    }
}