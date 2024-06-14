import org.apache.camel.builder.RouteBuilder
import org.apache.camel.component.jms.JmsComponent
import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.impl.SimpleRegistry
import org.apache.qpid.jms.JmsConnectionFactory
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory

/*
@GrabResolver(name='redhat', root='https://maven.repository.redhat.com/ga/', m2Compatible=true)
@Grab(group = 'org.apache.camel', module = 'camel-core', version = '2.21.0.fuse-720050-redhat-00001')
@Grab(group = 'org.apache.camel', module = 'camel-jms', version = '2.21.0.fuse-720050-redhat-00001')
@Grab(group = 'org.apache.qpid', module = 'qpid-jms-client', version = '0.40.0.redhat-00001')
@Grab(group = 'org.messaginghub', module = 'pooled-jms', version = '1.0.4.redhat-00001')
@Grab(group = 'org.slf4j', module = 'slf4j-log4j12', version = '1.7.26')
@Grab(group = 'log4j', module = 'log4j', version = '1.2.17')
*/

def host = "192.168.49.2"
def port = 5672
def username = "elias" // Replace with your AMQ_USERNAME
def password = "elias" // Replace with your AMQ_PASSWORD
def address = "myAddress0" // Specify the address name
def queue = "myQueue0" // Specify the queue name

def static jmsProduceConsume() {
    /*
    String jmsUrl = "amqps://$host:$port?transport.trustStoreLocation=./client_ts.p12&" +
            "transport.trustStorePassword=password&transport.verifyHost=false&jms.sendTimeout=5000"
            */
    String jmsUrl = "amqp://$host:$port"

    def jms = new JmsComponent(connectionFactory:
            new JmsPoolConnectionFactory
                    (useAnonymousProducers: false,
                            maxConnections: 5,
                            connectionFactory: new JmsConnectionFactory($username, $password, jmsUrl)))

    def camelCtx = new DefaultCamelContext(new SimpleRegistry(['jms':jms]))
    camelCtx.addRoutes(new RouteBuilder() {
        def void configure() {
            from('timer:in?period=5000&repeatCount=5')
                    .setBody(simple('hello world - ${exchangeProperty[CamelTimerCounter]}'))
                    .log('Sending ${body} to queue1')
                    .to('jms:queue:queue1')

            from('jms:queue:queue1').log('${body}')
        }
    })
    camelCtx.start()
    Runtime.runtime.addShutdownHook({ ->
        camelCtx.stop()
    })
    synchronized(this){ this.wait() }
}

jmsProduceConsume()