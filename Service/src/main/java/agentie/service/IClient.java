package agentie.service;

import agentie.model.Zbor;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote{
    void cumparareBilet(Zbor zbor[])throws AgentException,RemoteException;
}
