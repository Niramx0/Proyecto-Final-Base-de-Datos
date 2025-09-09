
package parte1;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author USUARIO
 */
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
public class conexion {
    String bd="";
    String url="jdbc:mysql://localhost:3306/";
    String user= "root";
    String password= "admin1234";
    String driver= "com.mysql.cj.jdbc.Driver";     
    Connection cx;
    
    
    public conexion(String bd){
        this.bd=bd;
        
    }
    
    public Connection conectar(){
        try {
            Class.forName(driver);
            cx= DriverManager.getConnection(url+bd, user, password);
            System.out.println("se conecto a base de datos"+bd);
        } catch (ClassNotFoundException |SQLException ex) {
            System.out.println("no se conecto a base de datos"+bd);
            Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return cx;
      }
    public static void main(String[] args) {
        conexion Conexion=new conexion(" bases_proyecto");
        Conexion.conectar();
        
    }
    
    

    
    
    }
 