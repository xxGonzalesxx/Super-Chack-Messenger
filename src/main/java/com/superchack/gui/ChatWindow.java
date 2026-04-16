package com.superchack.gui;

import com.superchack.network.P2PClient;
import com.superchack.network.P2PServer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public  class ChatWindow {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField messageField;

    private P2PServer server;
    private P2PClient client;
    private boolean isServer;

    public void init(String ip, int port, boolean isServer) {
        this.isServer = isServer;

        if (isServer) {
            server = new P2PServer(this);
            new Thread(() -> server.start(port)).start();
            chatArea.appendText("📡 Сервер запущен на порту " + port + "\n");
        } else {
            client = new P2PClient(this);
            new Thread(() -> client.connect(ip, port)).start();
            chatArea.appendText("🔌 Подключение к " + ip + ":" + port + "...\n");
        }
    }

    @FXML
    public void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            chatArea.appendText("Вы: " + message + "\n");
            messageField.clear();
            // TODO: отправка через P2P
        }
    }
    public void addMessage(String message) {
        Platform.runLater(() -> chatArea.appendText("Друг: " + message + "\n"));
    }
}