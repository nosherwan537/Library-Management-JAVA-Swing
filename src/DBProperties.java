//file:DBProperties.java
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
/**
 * A class to read database properties from a file database.properties.
 */
public class DBProperties {
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            // Load properties from file
            properties.load(new FileInputStream("database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getURL() {
        return properties.getProperty("db.url");
    }

    public static String getUser() {
        return properties.getProperty("db.user");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}