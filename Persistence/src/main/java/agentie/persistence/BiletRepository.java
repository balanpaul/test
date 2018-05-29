package agentie.persistence;

import agentie.model.Bilet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BiletRepository {

    private Jdbcutils dbUtils;

    public BiletRepository(Jdbcutils dbUtils) {
        this.dbUtils = dbUtils;
    }

    public void save(Bilet entity) {
        Connection con =dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("insert into Bilet (idBilet,Nume,NumeTuristi,Adresa,NrLocuri) VALUES (?,?,?,?,?)")) {
            preStmt.setInt(1,entity.getId());
            preStmt.setString(2,entity.getNume());
            preStmt.setString(3,  entity.getTurist());
            preStmt.setString(4,entity.getAdresa());
            preStmt.setInt(5,entity.getLocuri());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.print("Eror DB" + e);
        } ;

    }
    public Bilet findOne(Integer integer) {
        Connection con=dbUtils.getConnection();
        try (PreparedStatement preStmt =con.prepareStatement("select * from Bilet where IdBilet=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()){
                if(result.next()){
                    int id=result.getInt("idBilet");
                    String nume=result.getString("Nume");
                    String turist=result.getString("NumeTuristi");
                    String adresa=result.getString("Adresa");
                    int nrLocuri=result.getInt("NrLocuri");

                    Bilet zb=new Bilet(id,nume,turist, adresa, nrLocuri);
                    return zb;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }

        return null;
    }
}
