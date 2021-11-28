package com.example.chat_gui_1404;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class HelloController {
    static ArrayList<String> usersName = new ArrayList<>();
    @FXML
    TextField textField;
   @FXML
    TextArea textArea;
   @FXML
    Button connectBtn;
   @FXML
   Button sendBtn;
   @FXML
    public void handlerSend(){
       String text = textField.getText();
       textArea.appendText(text+"\n");
       textField.clear();
       textField.requestFocus();
       try {
           out.writeUTF(text);
           catch(IOException e){
               e.printStackTrace();
           }
       }
    @FXML
    public void connect(){
    try {
        Socket socket = new Socket("localhost", 8179);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        Object object = in.readObject();
                        if(object.getClass().equals(usersName.getClass())){
                            usersName = (ArrayList<String>) object;
                            textArea.appendText(usersName.toString()+"\n");
                        }else{
                            String response = (String) object;
                            textArea.appendText(response+"\n");
                        }
                    }
                }catch (Exception exception){
                    System.out.println("Потеряно соединение с сервером");
                }
            }
        });
        thread.start();
        sendBtn.setDisable(false);
connectBtn.setDisable(true);
        while (true){
            String request = scanner.nextLine();
            out.writeUTF(request);
        }
    } catch (IOException e){
        e.printStackTrace();
}
    }
     }
