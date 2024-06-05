GroovyShell shell = new GroovyShell()
//def tools = shell.parse(new File('mytools.groovy'))

//import groovy.xml.XmlParser
//import mytools


//import java.util.logging.Logger 
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.LogManager

//private static final Logger logger = LogManager.getLogger(Log4j2Example.class);


try{
    System.setProperty("log4j.configurationFile", "Configuration/log4j2.yaml") // Load the Log4j2 configuration from the XML file

    System.setProperty("log4j2.debug", "true")

    //Logger LOGGER = LogManager.getRootLogger();
    Logger LOGGER = LogManager.getLogger("org.apache.logging.log4j.test2")

    // Try logging a debug message
    if (LOGGER.getName() != "") {
         System.out.println("Logger ["+ LOGGER.getName()+"] is available and configured for debug level");
    } else {
        // Logger might not be configured for debug level, or it might not exist
        System.out.println("**WARNING:** Logger existence or debug level configuration unclear");
    }

    LOGGER.info("just a msg")
    LOGGER.warn("just a msg")
    LOGGER.error("just a msg")
    //LOGGER.info("Configuration File Defined To Be :: "+System.getProperty("log4j.configurationFile"));
  
    //LOGGER.info("2 Configuration File Defined To Be :: "+System.getProperty("log4j.configurationFile"));


    //def LOGGER = LogManager.getLogger("Syslog") 
 
    //tools.process(exchange, LOGGER)

}catch(Exception e){
    e.printStackTrace();
}

/*
def process(Exchange exchange, Logger LOGGER ) throws Exception {
     
def xmlStream = exchange.getIn().getBody(File.class);  
  
LOGGER.info("===== Identified file: " + xmlStream )
  
  
}
 */
