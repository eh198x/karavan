package cfl.eua.karavan;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArtemisConfig {

    @Bean
    public JmsConnectionFactory jmsConnectionFactory() {
        // Specify the broker URL for AMQP
        String brokerURL = "amqp://127.0.0.1:61616";

        // Specify the username and password -- NOT NEEDED
        //String username;// = "elias";
        //String password;// = "elias";

        // Create a connection factory for AMQP
        //JmsConnectionFactory connectionFactory = new JmsConnectionFactory(username, password, brokerURL);
        JmsConnectionFactory connectionFactory = new JmsConnectionFactory("", "", brokerURL);
        
        return connectionFactory;
    }