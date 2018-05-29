package agentie.persistence;

import agentie.model.Zbor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Component
public class ZborRepository {
    private SessionFactory sessionFactory;

    public ZborRepository() {
       // final StandardServiceRegistry registry=new StandardServiceRegistryBuilder().configure().build();
       // SessionFactory sessionFactory = new Configuration().configure("F:\\Rest\\Server\\src\\main\\resources\\hibernate.cfg.xml").buildSessionFactory();
        try{
            System.out.println("\n\nprint\n\n");
            initialize();
            System.out.println(sessionFactory);

        }catch (Exception e){
            System.out.println("\n\neroare?\n\n"+e.getMessage());
            e.printStackTrace();
            close();

        }
    }


    public void save(Zbor entity) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                session.save(entity);
                tx.commit();
            }catch (RuntimeException ex){
                if(tx!=null)
                    tx.rollback();
            }
            finally {
                session.close();
            }
        }
    }


    public void delete(Zbor zbor) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                //session.delete("Zbor",zbor);
                session.createQuery("delete from Zbor where idBilet= "+zbor.getId()).executeUpdate();

                //session.delete(zbor);
                tx.commit();

            }catch (Exception e){
                if(tx!=null)
                    tx.rollback();
            }
            finally {
                session.close();
            }
        }
    }


    public void update( Zbor entity) {
        Session session=sessionFactory.openSession();
        Transaction tx=null;
        try{
            tx=session.beginTransaction();
            session.update(entity);
            tx.commit();
        }catch (Exception e){
            if(tx!=null)
                tx.rollback();;
        }finally {
            session.close();
        }
    }


    public Zbor findOne(Integer integer) {
        Session session =sessionFactory.openSession();
        Transaction tx=null;
        try{
            tx=session.beginTransaction();
            Zbor z=session.get(Zbor.class,integer);
            tx.commit();
            return z;
        }catch (RuntimeException ex){
            if(tx!=null)
                tx.rollback();
        }finally {
            session.close();
        }

        return null;
    }


    public List<Zbor> findAll() {
        System.out.println("asdasd");

        List<Zbor> zborList = new ArrayList<>();
        Transaction tx = null;
       try
       {
           Session session = sessionFactory.openSession();


               tx = session.beginTransaction();
               /*
               //Query query = session.createQuery("from Zbor "); //You will get Weayher object
               CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
               CriteriaQuery<Zbor> criteriaQuery = criteriaBuilder.createQuery(Zbor.class);
               Root<Zbor> root = criteriaQuery.from(Zbor.class);
              criteriaQuery.select(root);
              Query<Zbor> query = session.createQuery(criteriaQuery);
               zborList = query.getResultList();
              // list.add(new Zbor(1,"dsa","sda","sda",2));
               // /list = query.list();
               */

               Query query = session.createQuery("FROM Zbor");
               zborList = (ArrayList<Zbor>) query.getResultList();

               tx.commit();
       }
       catch (Exception ex)
       {
           if (tx != null)
               tx.rollback();
           ex.printStackTrace();
       }
        return zborList;
    }


    public List<Zbor> filterBy(String dataPlecarii) {
        return null;
    }


   /* public List<Zbor> filetrBy(String destinatie, String plecare) {
        Connection con=dbUtils.getConnection();
        List<Zbor> zbors=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("SELECT * from Zbor WHERE Destinatie=? and DataPlecari=? ")){
            preStmt.setString(1,destinatie);
            preStmt.setString(2,plecare);
            try(ResultSet result=preStmt.executeQuery()){
                while (result.next()) {
                    int id = result.getInt("idBilet");
                    String destinaties = result.getString("Destinatie");
                    String plecares = result.getString("DataPlecari");
                    String aeroport = result.getString("Aeroport");
                    int nrLocuri = result.getInt("LocuriDisponibile");
                    // String s = String.valueOf(plecare);
                    Zbor zb = new Zbor(id, destinaties, plecares, aeroport, nrLocuri);
                    zbors.add(zb);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zbors;
    }*/


    private void initialize() {
        /*final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();*/
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            //StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
        }

    }
    private void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
