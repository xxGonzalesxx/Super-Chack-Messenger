package com.superchack.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindow extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("/LoginWindow.fxml"));

        primaryStage.setTitle("Super Chack Messenger");
        primaryStage.setScene(new Scene(root, 350, 250));
        primaryStage.show();
    }

    @FXML
    public void openServer() {
        // Запуск сервера
        ConnectionDialog.ConnectionData data = ConnectionDialog.showAndWait();
        if (data != null) {
            openChatWindow(data.ip, data.port, true);
        }
    }

    @FXML
    public void openClient() {
        // Подключение как клиент
        ConnectionDialog.ConnectionData data = ConnectionDialog.showAndWait();
        if (data != null) {
            openChatWindow(data.ip, data.port, false);
        }
    }

    private void openChatWindow(String ip, int port, boolean isServer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChatWindow.fxml"));
            Parent root = loader.load();

            ChatWindow controller = loader.getController();
            controller.init(ip, port, isServer);

            Stage chatStage = new Stage();
            chatStage.setTitle("Super Chack Messenger — Чат");
            chatStage.setScene(new Scene(root, 500, 400));
            chatStage.show();

            if (primaryStage != null) {
                primaryStage.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}