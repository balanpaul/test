package agentie.service;

import agentie.model.Agent;
import agentie.model.Bilet;
import agentie.model.Zbor;

import java.rmi.RemoteException;

public interface IServer {
    Agent login(Agent user, IClient client) throws AgentException;
    void logout(Agent user) throws AgentException;
    Zbor[] getZbor()throws AgentException;
    Zbor[] cautare(String dest, String data) throws AgentException;
    void updateZbor(Bilet bilet) throws AgentException,RemoteException;
    Agent getAgent(String username, String password);

}
