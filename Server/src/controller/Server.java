package controller;

import controller.ConnectionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Server {

    private ArrayList<Socket> connectedPlayers = new ArrayList();

    public Server(){
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4444);
            System.out.println("Server: started!");

            while(true) {
                Socket client = serverSocket.accept();
                executorService.execute(new ConnectionHandler(client));
            }

        }catch(IOException e){
            e.printStackTrace();
        }


    }
}
