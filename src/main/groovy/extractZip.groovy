import java.util.zip.*
import java.util.logging.Logger
import org.apache.camel.Exchange

import java.util.Properties

Logger LOGGER = Logger.getLogger("")

Properties props = new Properties()

//String zipFileName = myFile.absolutePath
//String inputDir = "Fileshare/1-INPUT"

try {
    // Load properties from the application.properties file
    FileInputStream fis = new FileInputStream("src/main/resources/application.properties")
    props.load(fis)
    fis.close()
} catch (IOException e) {
    LOGGER.severe("Error loading properties file: " + e.getMessage())
}


//LOGGER.info("Received a new file upload...\n")

def outputDir = props.getProperty("custom.directory.extracts")
LOGGER.info("outputDir:" + outputDir)

if (outputDir?.isEmpty() || outputDir == null) {
    outputDir = "Fileshare/2-Extracts-MANUAL"
} else {
    // Handle the case when outputDir is not empty
    // Your code here...
}


process(outputDir, exchange, LOGGER)

//UnZip archive
def process(String outputDir, Exchange exchange, Logger LOGGER) throws Exception {
    def myFile = exchange.getIn().getBody(File. class)
    def filePath = myFile.absolutePath
    //Relative path 
    //String pathWithoutFilename = myFile.getParentFile().getPath();
    //Absolute Path
    String pathWithoutFilename = myFile.getParentFile().getAbsolutePath();
    String parentFilename = myFile.getParentFile().getName();
    outputDir = outputDir + File.separator + parentFilename
    LOGGER.info("Printing parent file name:" + parentFilename)
    LOGGER.info("Printing outputDir:" + outputDir)
    def exchangeId = exchange.getExchangeId()
    byte [] buffer = new byte [1024]
    ZipInputStream zis = new ZipInputStream(new FileInputStream(filePath))
    ZipEntry zipEntry = zis.getNextEntry()
    while (zipEntry != null) {
        File newFile = new File(outputDir + File.separator + exchangeId + File.separator, zipEntry.name)
        //File newFile = new File(outputDir+ File.separator + exchangeId + File.separator, pathWithoutFilename)
        if (zipEntry.isDirectory()) {
            if (!newFile.isDirectory() && !newFile.mkdirs()) {
                throw new IOException("Failed to create directory " + newFile)
            }
        } else {
            // fix for Windows-created archives
            File parent = newFile.parentFile
            if (!parent.isDirectory() && !parent.mkdirs()) {
                throw new IOException("Failed to create directory " + parent)
            }
            // write file content
            FileOutputStream fos = new FileOutputStream(newFile)
            int len = 0
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len)
            }
            fos.close()
        }
        zipEntry = zis.getNextEntry()
    }
    zis.closeEntry()
    zis.close()
}

//LOGGER.info("Printing file path without filename:" + pathWithoutFilename)
//process(exchange, LOGGER)
//extract(exchange, LOGGER)

/*
//Zip files  
ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream(zipFileName))  
new File(inputDir).eachFile() { file -> 
  //check if file
  if (file.isFile()){
    zipFile.putNextEntry(new ZipEntry(file.name))
    def buffer = new byte[file.size()]  
    file.withInputStream { 
      zipFile.write(buffer, 0, it.read(buffer))  
    }  
    zipFile.closeEntry()
  }
}  
zipFile.close()  
*/

