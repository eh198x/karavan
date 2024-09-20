GroovyShell shell = new GroovyShell()
Script tools = shell.parse(new File('mytools.groovy'))

//import groovy.xml.XmlParser
//import mytools
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.LogManager

//private static final Logger logger = LogManager.getLogger(Log4j2Example.class);

try{
    System.setProperty('log4j.configurationFile', 'Configuration/log4j2.yaml')

    System.setProperty('log4j2.debug', 'true')

    //Logger logger = LogManager.getRootLogger();
    Logger logger = LogManager.getLogger("")

    logger.info('Calling tools.jsonProcess...!')

    tools.jsonProcess(exchange, logger)

} catch(Exception e){ 
    logger.info('An error occured:' + e.message)
    //e.printStackTrace();
}