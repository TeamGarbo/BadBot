package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import teamgarbo.github.com.badbotapp.message.Message;
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
    	boolean runningFine = true;
        while(runningFine) {
            try {
                Message message =(Message)inputStream.readObject();
                Controller.getInstance().processMessage(message);
            }catch(ClassNotFoundException e){
            	runningFine = false;
            	closeSocket();
               // e.printStackTrace();
            }catch(IOException e){
            	runningFine = false;
            	closeSocket();
                //e.printStackTrace();
            }
        }
        //Receive data from client
    }

    public void closeSocket() {
    	try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void sendMessage(Message message){
        try {
            outputStream.writeObject(message);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}