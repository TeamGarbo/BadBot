package controller;
import message.Message;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
public class ConnectionHandler implements Runnable {
    Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    public ConnectionHandler(Socket socket){
        this.socket = socket;
        try {
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void run(){
        while(socket.isConnected()) {
            try {
                Message message =(Message)inputStream.readObject();
                Controller.getInstance().HANDLEMESSAGE(object);
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        //Receive data from client
    }

    public void sendMessage(Message message){
        try {
            outputStream.writeObject(object);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}