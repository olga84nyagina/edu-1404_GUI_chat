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

public class HelloController {//подключение к элементам в окне
    static ArrayList<String> usersName = new ArrayList<>();//список пользователей
    DataOutputStream out;
    @FXML
    TextField textField;// поле текста
   @FXML
    TextArea textArea;//область текста
   @FXML
    Button connectBtn;//кнопка соединения
   @FXML
   Button sendBtn;//  кнопка ссылки
   @FXML
    public void handlerSend() {//отправление сообщения
       String text = textField.getText();//функция ввода поле текста
       textArea.appendText(text + "\n");//добавление переноса строки
       textField.clear();// очистить текст
       textField.requestFocus();//фокусировка курсора
       try {//исключение ошибок при подключении
           out.writeUTF(text); // отправка на сервер
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
    @FXML
    public void connect(){//кнопка подключиться
    try {
        Socket socket = new Socket("localhost", 8179);//подключение к серверному сокету
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());//поток ввода обьекта
        out = new DataOutputStream(socket.getOutputStream());//поток вывода данных

        Thread thread = new Thread(new Runnable() {//новый поток
            @Override
            public void run() {
                try {//исключение ошибки потери соединения
                    while (true){//проверка условия
                        Object object = in.readObject();
                        if(object.getClass().equals(usersName.getClass())){//список пользователей
                            usersName = (ArrayList<String>) object;
                            textArea.appendText(usersName.toString()+"\n");//
                        }else{
                            String response = (String) object;
                            textArea.appendText(response+"\n");
                        }
                    }
                }catch (Exception exception){//исключение ошибок
                    System.out.println("Потеряно соединение с сервером");
                }
            }
        });
        thread.start();
        sendBtn.setDisable(false);//заблокировать кнопку
        connectBtn.setDisable(true);
    } catch (IOException e){//исключение ошибок
        e.printStackTrace();
}
    }
     }
