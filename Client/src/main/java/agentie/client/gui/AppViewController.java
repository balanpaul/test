package agentie.client.gui;

import agentie.model.Agent;
import agentie.model.Bilet;
import agentie.model.Zbor;
import agentie.service.AgentException;
import agentie.service.IClient;
import agentie.service.IServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppViewController extends UnicastRemoteObject implements IClient,Serializable {
    private IServer service;
    private Agent userlogat;

    @FXML
    TableColumn<Zbor, String> columnDestinatie,columnId,columnAeroport,columnPlecare;
    @FXML
    TableColumn<Zbor,Integer>columnLocuri;
    private Agent user;
    @FXML
    private TableView<Zbor> tableview;
    @FXML
    private TextField cautDest,cautData,numeClient,numeTuristi, adresa,locuri,idZbor;

    private ObservableList<Zbor> model;

    public AppViewController() throws RemoteException {
    super();
    }

    @FXML
    public void initialize(){

    }
    public void setUser(Agent a){
        this.userlogat=a;
    }
    public void setService(IServer service, Agent organizator) {
        this.service = service;
        this.userlogat = organizator;
        loadTable();
    }

    //Alert for error
    private void showErrorMessage(String msg){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Whoops");
        message.setContentText(msg);
        message.showAndWait();
    }

    //deschiderea unei noi ferestre, loader = fisierul fxml, title = titlul ferestrei
    private void openLoginPage(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loginpage.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = new Stage();
        ((Node) (e.getSource())).getScene().getWindow().hide();
        LoginViewController loginViewController = fxmlLoader.getController();
        loginViewController.setService(service);
        stage.setTitle("Autentificare");
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    public void handleButtonLogout(ActionEvent actionEvent) {
        try {
            service.logout(userlogat);
            //deschide LOGIN PAGE
            openLoginPage(actionEvent);
        } catch (AgentException e1) {
            showErrorMessage("Logout Error.");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void handleCumparare(ActionEvent actionEvent){
        Zbor z=tableview.getSelectionModel().getSelectedItem();
        if(z==null)showErrorMessage("Alegeti o destinatie din tabel");
        int id=z.getId();
        String client=numeClient.getText();
        String turist=numeTuristi.getText();
        String adres=adresa.getText();
        int loc=Integer.valueOf(locuri.getText());
        try {
            update(id,client,turist,adres,loc);
        }catch (AgentException e){
            showErrorMessage("Your server probably closed connection");
        } catch (RemoteException e) {
            showErrorMessage(e.getMessage());
            e.printStackTrace();
        }
    }

    private void update(int id, String client, String turist, String adres, int loc) throws AgentException, RemoteException {
        Bilet b=new Bilet(id,client,turist,adres,loc);
        service.updateZbor(b);
        //server.getZbor();
    }

    private void loadTable(){
        try {
            this.model = FXCollections.observableArrayList(service.getZbor());
            tableview.setItems(model);
            tableview.getSelectionModel().selectFirst();
        } catch (AgentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cumparareBilet(Zbor[] zbor) throws AgentException, RemoteException {
        List<Zbor> z=new ArrayList<>();
        Collections.addAll(z,zbor);
        this.model=FXCollections.observableArrayList(z);
        this.tableview.setItems(model);
    }
}
