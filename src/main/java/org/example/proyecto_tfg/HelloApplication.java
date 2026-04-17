package org.example.proyecto_tfg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/main-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1300, 700);

        mainStage = stage;
        stage.setTitle("WORKSHOP+");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        stage.centerOnScreen();

        // Cargar la vista de login al inicio
        cargarVistaLogin();
    }

    public static void cargarVistaLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/hello-view.fxml"));
        Parent loginView = fxmlLoader.load();

        StackPane stackPrincipal = (StackPane) mainStage.getScene().getRoot();
        stackPrincipal.getChildren().clear();
        stackPrincipal.getChildren().add(loginView);
    }

    public static void cargarVistaPrincipal() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/principalWindows-view.fxml"));
        Parent mainView = fxmlLoader.load();

        StackPane stackPrincipal = (StackPane) mainStage.getScene().getRoot();
        stackPrincipal.getChildren().clear();
        stackPrincipal.getChildren().add(mainView);
    }
    public static void cargarVistaRegister() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/view/proyecto_tfg/register-view.fxml"));
        Parent registerView = fxmlLoader.load();

        StackPane stackPrincipal = (StackPane) mainStage.getScene().getRoot();
        stackPrincipal.getChildren().clear();
        stackPrincipal.getChildren().add(registerView);
    }

}
