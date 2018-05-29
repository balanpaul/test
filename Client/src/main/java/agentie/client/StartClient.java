package agentie.client;

import agentie.client.gui.LoginViewController;
import agentie.service.IServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartClient extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:spring-client.xml");
            IServer server = (IServer) factory.getBean("service");
            System.out.println("Obtained a reference to remote server");

            /*FXMLLoader loader = new FXMLLoader(
                    getClass().getClassLoader().getResource("Login.fxml"));
            Parent root=loader.load();
            FXMLLoader loader =new FXMLLoader(getClass().getClassLoader().getResource("loginpage.fxml"));

            //loader.setLocation(ClassLoader.getSystemResource("loginpage.fxml"));
            //BorderPane root = new BorderPane();
            AnchorPane root = (AnchorPane) FXMLLoader.load(StartClient.class.getResource("/packagename/LoginGUI.fxml"));

            LoginViewController loginViewController = loader.getController();
            loginViewController.setService(server);
            */
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ClassLoader.getSystemResource("loginpage.fxml"));
            BorderPane root;
            root = loader.load();
            LoginViewController loginViewController = loader.getController();
            loginViewController.setService(server);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Autentificare");
            primaryStage.show();
        } catch (Exception e){
            System.err.println("Initialization  exception:"+e);
            e.printStackTrace();
        }
    }
}
