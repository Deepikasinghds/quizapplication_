package quiz;



    import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class connection {
    static Connection Connection;
    public Connection Connection(){
        
    String url="jdbc:mysql://localhost:3306//quiz";
        Connection con=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con  = DriverManager.getConnection("jdbc:mysql://localhost:3306//quiz","root","Vivo123");
            
        }      catch(SQLException |ClassNotFoundException ex){
        
       System.out.println("error in connectivity:"+ex);
        Logger.getLogger(connection.class.getName()).log(Level.SEVERE,null , ex);
    }  
        return con;
    }
}
