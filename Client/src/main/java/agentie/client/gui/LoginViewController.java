package agentie.client.gui;

import agentie.model.Agent;
import agentie.model.Zbor;
import agentie.service.AgentException;
import agentie.service.IClient;
import agentie.service.IServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginViewController extends UnicastRemoteObject implements IClient,Serializable{
    private IServer service;
    private Agent userLogat;
    private AppViewController appViewController;
    @FXML
    TextField textfieldUsername;
    @FXML
    TextField textfieldParola;
    @FXML
    Button buttonAutentificare;
    Parent mainAgentie;
    public LoginViewController() throws RemoteException {
    super();
    }

    public  void setService(IServer service){
        this.service=service;
    }

    //Alert for error
    private void showErrorMessage(String msg){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Whoops");
        message.setContentText(msg);
        message.showAndWait();
    }

    //deschiderea unei noi ferestre, loader = fisierul fxml, title = titlul ferestrei
    private void openMainPage(ActionEvent e, String title, Agent userLogat) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/mainpage.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.sizeToScene();

        appViewController = fxmlLoader.getController();
        appViewController.setService(service, userLogat);

        stage.show();
        ((Node) (e.getSource())).getScene().getWindow().hide();
    }

    @FXML
    public void handleButtonAutentificare(ActionEvent actionEvent) {
        try {
            String username = textfieldUsername.getText();
            String password = textfieldParola.getText();
            // if (username.equals("") || password.equals(""))
            //  throw new NullPointerException();

            Agent user = new Agent(username, password);
            service.login(user, this);
            Agent fullUser = service.getAgent(username, password);
            userLogat = fullUser;
            openMainPage(actionEvent, "Talent Show System - " + user.getUsername(), userLogat);
        } catch (NullPointerException e1) {
            showErrorMessage(e1.getMessage());
            e1.printStackTrace();
        } catch ( AgentException e1) {
            showErrorMessage("Invalid username or password.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        /*
        //Parent root;
        String nume = textfieldUsername.getText();
        String passwd = textfieldParola.getText();
        agent = new Agent(nume, passwd);


        try {
            service.login(agent, this);
            // Util.writeLog("User succesfully logged in "+crtUser.getId());
            Stage stage = new Stage();
            stage.setTitle(" Window for " + agent.getId());
            stage.setScene(new Scene(mainAgentie));

            /*stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    agentiCtrl.handleButtonLogout();
                    System.exit(0);
                }
            });

            stage.show();
            agentiCtrl.setUser(agent);
            //ch.setLoggedFriends();
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

        } catch (AgentException e) {
            e.printStackTrace();
        } */


        @Override
    public void cumparareBilet(Zbor[] zbor) throws AgentException, RemoteException {
        appViewController.cumparareBilet(zbor);
    }
}
