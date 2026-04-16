package com.superchack.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConnectionDialog {

    public static ConnectionData showAndWait() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Подключение");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label ipLabel = new Label("IP адрес друга:");
        TextField ipField = new TextField("127.0.0.1");

        Label portLabel = new Label("Порт:");
        TextField portField = new TextField("45000");

        Button connectButton = new Button("Подключиться");
        Button cancelButton = new Button("Отмена");

        connectButton.setOnAction(e -> {
            dialog.close();
        });
        cancelButton.setOnAction(e -> {
            ipField.setText(null);
            portField.setText(null);
            dialog.close();
        });

        root.getChildren().addAll(ipLabel, ipField, portLabel, portField, connectButton, cancelButton);
        Scene scene = new Scene(root, 300, 250);
        dialog.setScene(scene);
        dialog.showAndWait();

        if (ipField.getText() != null && portField.getText() != null) {
            try {
                int port = Integer.parseInt(portField.getText());
                return new ConnectionData(ipField.getText(), port);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public static class ConnectionData {
        public final String ip;
        public final int port;

        public ConnectionData(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }
    }
}