package cfl.eua;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class CustomPropertyLoader {

    public static void load() {
        Logger logger = Logger.getLogger(CustomPropertyLoader.class.getName());
        
        Properties props = new Properties();
        
        try (FileInputStream fis = new FileInputStream("application.properties")) {
            props.load(fis);
        } catch (IOException e) {
            logger.severe("Error loading properties file: " + e.getMessage());
            return;
        }
        
        // Fetch the specific property
        String schema = props.getProperty("custom.Cyprus.schema");
        
        // Log the schema using string concatenation
        logger.info("JAVA code Schema: " + schema);
    }
}
