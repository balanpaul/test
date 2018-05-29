package agentie.persistence;

import agentie.model.Agent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private Jdbcutils dbUtils;

    public UserRepository(Jdbcutils dbUtils) {
        this.dbUtils = dbUtils;
    }

    public Agent findBy(String username, String pass) {
        System.out.println("JDBC findBy 2 params");
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select name from users where username=? and password=?")){
            preStmt.setString(1,username);
            preStmt.setString(2,pass);
            ResultSet result=preStmt.executeQuery();
            boolean resOk=result.next();
            System.out.println("findBy user, pass "+resOk);
            if (resOk) {
                Agent user=new Agent(username,pass);
                user.setNume(result.getString("name"));
                //user.setUsername(result.getString("username"));
                //user.setPassword(result.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return null;
    }
    public List<Agent> getAll() {
        List<Agent> rez = new ArrayList<>();
        try(Connection con = dbUtils.getConnection(); PreparedStatement preStmt = con.prepareStatement("SELECT * From users")){
            try(ResultSet result = preStmt.executeQuery()) {
                while (result.next()){
                    int id=result.getInt(1);
                    String nume = result.getString(2);
                    String username = result.getString(3);
                    String password = result.getString(4);
                    Agent copil = new Agent( nume, username, password);
                    rez.add(copil);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error getAll Organizator" + e.getMessage());
        }
        return rez;
    }
}
