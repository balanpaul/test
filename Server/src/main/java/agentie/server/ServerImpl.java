package agentie.server;

import agentie.model.Agent;
import agentie.model.Bilet;
import agentie.model.Zbor;
import agentie.persistence.BiletRepository;
import agentie.persistence.UserRepository;
import agentie.persistence.ZborRepository;
import agentie.service.AgentException;
import agentie.service.IClient;
import agentie.service.IServer;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImpl implements IServer {

    private Map<String, IClient> loggedClients;
    private UserRepository userRepositoryJdbc;
    private ZborRepository zborJdbcRepository;
    private BiletRepository biletJdbcRepository;
    private String username;
    private Agent userLogat;

    public ServerImpl(UserRepository userRepositoryJdbc, ZborRepository zborJdbcRepository, BiletRepository biletJdbcRepository) {
        this.userRepositoryJdbc = userRepositoryJdbc;
        this.zborJdbcRepository = zborJdbcRepository;
        this.biletJdbcRepository = biletJdbcRepository;
        loggedClients=new ConcurrentHashMap<>();
    }


    @Override
    public Agent login(Agent user, IClient client) throws AgentException {
        //List<Agent> users=userRepositoryJdbc.findAll();
        Agent agent=userRepositoryJdbc.findBy(user.getUsername(), user.getPassword());
        Agent agent1 = userRepositoryJdbc.findBy(user.getUsername(), user.getPassword());
        if (agent1 != null) {
            if (loggedClients.get(user.getUsername()) != null)
                throw new AgentException("User already logged in.");
            loggedClients.put(user.getUsername(), client);
            //notifyFriendsLoggedIn(user);
            return user;
        } else {
            throw new AgentException("Authentication failed.");
        }

    }

    @Override
    public void logout(Agent user) throws AgentException {
        loggedClients.remove(user.getUsername());
    }

    @Override
    public Zbor[] getZbor() throws AgentException {
        return zborJdbcRepository.findAll().toArray(new Zbor[1]);
    }

    @Override
    public Zbor[] cautare(String dest, String data) throws AgentException {
        return new Zbor[0];
    }

    @Override
    public void updateZbor(Bilet bilet)throws AgentException,RemoteException {
        biletJdbcRepository.save(bilet);
        Zbor zbor = (Zbor) zborJdbcRepository.findOne(bilet.getId());
        if (zbor != null) {
            Zbor z=new Zbor(zbor.getId(),zbor.getDestinatie(),zbor.getAeroport(),zbor.getPlecare(),zbor.getNrLocuri()-bilet.getLocuri());
            zborJdbcRepository.update( z);

        }

        notifyO();
    }

    private void notifyO()throws AgentException,RemoteException {
        for (String usernamePers:loggedClients.keySet()){
            this.loggedClients.get(usernamePers).cumparareBilet(getZbor());
        }
    }

    @Override
    public Agent getAgent(String username, String password) {
        return  userRepositoryJdbc.findBy(username,password);

    }
}
