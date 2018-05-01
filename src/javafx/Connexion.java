
package javafx;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.*;




public class Connexion {
    Connection conn=null;
    public static Connection ConnecrDB()
    {
        try{
          Class.forName("com.mysql.jdbc.Driver") ;
          Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/GestionCabinetM", "root","");
          return conn;
        } catch (Exception e) {
           
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}