import java.util.logging.Logger
import org.apache.camel.Exchange
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

/* --UNUSED
// Database connection parameters
def url = "jdbc:postgresql://192.168.49.2:32150/postgres"
def user = "postgres"
def password = "password"
 */

Logger LOGGER = Logger.getLogger("")
//LOGGER.info("database.groovy about to connect to db...\n")

Connection connection = connect(exchange, LOGGER);

String response = runProcedure(connection, exchange, LOGGER)

request.body= response

def connect(Exchange exchange, Logger LOGGER) throws Exception {
    try {
        String title = exchange.getProperty("title")
        String author = exchange.getProperty("author") 

        String url = "jdbc:postgresql://192.168.49.2:32150/postgres"
        String user = "postgres"
        String password = "password"

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("ssl", "false");
        Connection connection = DriverManager.getConnection(url, props);

        return connection
    } catch (Exception e) {
        LOGGER.severe("An error occurred: ${e.message}")
        //String response = "<response>${e.message}</response>"
        String response = "<response>${e.message}</response>"
        request.body = response
        //e.printStackTrace();
    } 
}

def runProcedure(Connection connection, Exchange exchange, Logger LOGGER) throws Exception {
    try {

        String title = exchange.getProperty("title")
        String author = exchange.getProperty("author") 

        // Create a CallableStatement for the function call
        CallableStatement call = connection.prepareCall("{? = call public.create_article(?, ?)}")
        
        call.registerOutParameter(1, Types.OTHER) // Register the output parameter
        call.setString(2, title)
        call.setString(3, author)
    
        call.execute()

        // Retrieve the result (assuming your function returns a single value)
        String result = call.getObject(1).toString()
        LOGGER.info("Called DB Function result: $result")

        //String response = "<response>$result</response>"
        return result
                
        // Close the connection
        if (connection != null) {
            connection.close()
        }
    } catch (Exception e) {
        LOGGER.severe("An error occurred: ${e.message}")
        //String response = "<response>${e.message}</response>"
        String response = "${e.message}"
        return response
        //e.printStackTrace();
    } 
}