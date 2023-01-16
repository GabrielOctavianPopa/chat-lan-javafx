package com.example.chatlanjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginController {

    @FXML
    private Button botonLogin;

    @FXML
    private Button botonRegistro;

    @FXML
    private TextField nombreUsuario;

    @FXML
    private TextField contraseña;

    @FXML
    private Label labelError;

    private Socket socket;

    PrintWriter printWrite;
    BufferedReader bufferedRead;

    public void connect() throws IOException {
        socket = new Socket("localhost", 6000);
        printWrite = new PrintWriter(socket.getOutputStream(), true);
        bufferedRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    private void checkLogin() throws IOException {
        LoginApplication login = new LoginApplication();
        try{
            String username = nombreUsuario.getText();
            char[] password = contraseña.getText().toCharArray();
            String passwordString = contraseña.getText();

            // mandar al servidor el nombre de usuario y la contraseña
            connect();

            printWrite.println("LOGIN," + username + "," + passwordString);
            String response = bufferedRead.readLine();
            if (response.equals("OK")){
                //login.changeScene("Hello-view.fxml");
                //login.changeScene("ChatCliente-view.fxml");
                System.out.println("Nice");
            } else if (response.equals("ERROR")) {
                labelError.setText("Error en el login");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void userLogIn(ActionEvent event) throws IOException {
        String username = nombreUsuario.getText();
        String password = contraseña.getText();

        if(username.isEmpty()){
            labelError.setText("Introduce un nombre de usuario");
        } else if (password.isEmpty()){
            labelError.setText("Introduce una contraseña");
        } else {
            try {
                connect();
                printWrite.println("LOGIN," + username + "," + password);
                String response = bufferedRead.readLine();

                if (response.equals("OK")) {
                    labelError.setText("Login correcto");
                } else if (response.equals("ERROR")) {
                    labelError.setText("Error en el login");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public LoginController() {

    }
}