GroovyShell shell = new GroovyShell()
//def tools = shell.parse(new File('mytools.groovy'))

//import groovy.xml.XmlParser
//import mytools


//import java.util.logging.Logger 
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.LogManager

//private static final Logger logger = LogManager.getLogger(Log4j2Example.class);



try{
    System.setProperty("log4j.configurationFile", "Configuration/log4j2.yaml") 

    System.setProperty("log4j2.debug", "true")

    //Logger logger = LogManager.getRootLogger();
    Logger logger = LogManager.getLogger("org.apache.logging.log4j.test2")

    // Try logging a debug message
    if (logger.getName() != "") {
        logger.info("Logger ["+ logger.getName()+"] is available and configured for debug level");
    } else {
        // Logger might not be configured for debug level, or it might not exist
        logger.info("**WARNING:** Logger existence or debug level configuration unclear");
    }

    logger.info("just a msg")
    logger.warn("just a msg")
    logger.error("just a msg")
    //logger.info("Configuration File Defined To Be :: "+System.getProperty("log4j.configurationFile"));
  
    //logger.info("2 Configuration File Defined To Be :: "+System.getProperty("log4j.configurationFile"));


    //def logger = LogManager.getLogger("Syslog") 
 
    //tools.process(exchange, logger)

}catch(Exception e){
    e.printStackTrace();
}

/*
def process(Exchange exchange, Logger logger ) throws Exception {
     
def xmlStream = exchange.getIn().getBody(File.class);  
  
logger.info("===== Identified file: " + xmlStream )
  
  
}
 */
