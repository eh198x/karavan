import java.util.logging.Logger
import org.apache.camel.Exchange
import groovy.sql.Sql
import java.sql.*;
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
LOGGER.info("database.groovy about to connect to db...\n")

process1(exchange, LOGGER)

def process1(Exchange exchange, Logger LOGGER) throws Exception {
    try {

        String title = exchange.getProperty("title")
        String author = exchange.getProperty("author") 

        String url = "jdbc:postgresql://192.168.49.2:32150/postgres"
        def user = "postgres"
        def password = "password"

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        props.setProperty("ssl", "false");
        Connection connection = DriverManager.getConnection(url, props);

        //    def connection = DriverManager.getConnection(url, user, password)

        // Create a CallableStatement for the function call
        def call = connection.prepareCall("{? = call public.create_article(?, ?)}")
        
        call.registerOutParameter(1, Types.OTHER) // Register the output parameter
        call.setString(2, title)
        call.setString(3, author)
    
        call.execute()

        // Retrieve the result (assuming your function returns a single value)
        String result = call.getObject(1).toString()
        LOGGER.info("Function result: $result")
    } catch (Exception e) {
        println "An error occurred: ${e.message}"
    } finally {
        // Close the connection
        if (connection != null) {
            connection.close()
        }
    }
}

def process(Exchange exchange, Logger LOGGER) throws Exception {
    try {
        // Establish a connection to the PostgreSQL database

        // Database connection parameters
        def url = "jdbc:postgresql://192.168.49.2:32150/postgres"
        def user = "postgres"
        def password = "password"

        def connection = DriverManager.getConnection(url, user, password)
        // Create a Sql object to interact with the database
        def sql = new Sql(connection)
        // Call a PostgreSQL function and process its result
        // Replace 'my_function' with your actual function name and adjust the parameters as needed
        def functionName = "public.create_article"
        String title = exchange.getProperty("title")
        String author = exchange.getProperty("author")
        
        def parameterValues = [title, author]
        LOGGER.info("Before sql call of function $functionName with arguments [$title, $author]")
        
        //String result = sql.call(functionName, parameterValues).first()

        String result = sql.call("{call public.create_article(?, ?)}", parameterValues).first()

        LOGGER.info("After sql call in database.groovy. Result: $result")
        
         
    } catch (Exception e) {
        println "An error occurred: ${e.message}"
    } finally {
        // Close the connection
        if (connection != null) {
            connection.close()
        }
    }
}
