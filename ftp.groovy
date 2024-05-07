GroovyShell shell = new GroovyShell()
def tools = shell.parse(new File('mytools.groovy'))

//import groovy.xml.XmlParser
//import mytools


import java.util.logging.Logger


//import groovy.json.JsonSlurper


Logger LOGGER = Logger.getLogger("")
//LOGGER.info("=====FTP-Received a new file upload...\n")

 
tools.process(exchange, LOGGER)

/*
def process(Exchange exchange, Logger LOGGER ) throws Exception {
     
    def xmlStream = exchange.getIn().getBody(File.class);  
  
    LOGGER.info("===== Identified file: " + xmlStream )
  
  
}
*/
