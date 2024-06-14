
//@Grab('org.apache.activemq:activemq-client:6.1.2')
import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory

@Grab('jakarta.jms:jakarta.jms-api:3.1.0')

//import javax.jms.ConnectionFactory

import org.apache.activemq.activemq-jms-pool.ConnectionFactory
import javax.jms.Connection
import javax.jms.Session
import javax.jms.MessageProducer
import javax.jms.TextMessage

def amqpHost = "192.168.49.2" // Replace with your Artemis AMQP host
def amqpPort = 5672 // Replace with the correct port (default is 5672)
def username = "elias" // Replace with your AMQ_USERNAME
def password = "elias" // Replace with your AMQ_PASSWORD
def address = "myAddress0" // Specify the address name
def queue = "myQueue0" // Specify the queue name

//def connectionFactory = new JmsConnectionFactory("amqp://$amqpHost:$amqpPort")
def connectionFactory = new ActiveMQJMSConnectionFactory("amqp://$amqpHost:$amqpPort")
connectionFactory.username = username
connectionFactory.password = password

try {
    def connection = connectionFactory.createConnection()
    def session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE)
    def producer = session.createProducer(session.createQueue(address))

    connection.start()

    def message = session.createTextMessage("Hello, Artemis!")
    producer.send(message)

    println("Message sent successfully to $address")

    producer.close()
    session.close()
    connection.close()
} catch (Exception e) {
    println("Error sending message: ${e.message}")
}
