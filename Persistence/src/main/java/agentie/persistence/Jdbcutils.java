package agentie.persistence;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class Jdbcutils {
    public Jdbcutils() {
    }
    private Connection instance=null;

    private Connection getNewConnection(){
        String driver="com.mysql.cj.jdbc.Driver";
        String url="jdbc:mysql://127.0.0.1:3306/mpp";
        String user="root";
        String pass="";
        Connection con=null;
        try{
            Class.forName(driver);
            if(user!=null&&pass!=null)
                con= DriverManager.getConnection(url,user,pass);
            else
                con=DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
    public Connection getConnection(){
        try{
            if(instance==null||instance.isClosed())
                instance=getNewConnection();
        } catch (SQLException e) {
            System.out.println("error DB"+e);
        }
        return instance;
    }
}
