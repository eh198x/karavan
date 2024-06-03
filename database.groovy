GroovyShell shell = new GroovyShell()
def tools = shell.parse(new File('mytools.groovy'))

//import groovy.xml.XmlParser
//import mytools
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.LogManager

//private static final Logger logger = LogManager.getLogger(Log4j2Example.class);


try{
    System.setProperty("log4j.configurationFile", "Configuration/log4j2.yaml") // Load the Log4j2 configuration from the XML file

    System.setProperty("log4j2.debug", "true")

    //Logger LOGGER = LogManager.getRootLogger();
    Logger LOGGER = LogManager.getLogger("")

    LOGGER.info("Calling tools.jsonProcess...!")

    tools.jsonProcess(exchange, LOGGER)

}catch(Exception e){
    e.printStackTrace();
}

/*
def process(Exchange exchange, Logger LOGGER ) throws Exception {
     
def xmlStream = exchange.getIn().getBody(File.class);  
  
LOGGER.info("===== Identified file: " + xmlStream )
  
  
}
 */
