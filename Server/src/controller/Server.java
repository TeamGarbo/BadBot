import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
package controller;
public class Server {

    private ArrayList<Socket> connectedPlayers = new ArrayList();

    public static void main(String[] args){
        new Server();
    }

    public Server(){
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4444);

            while(true) {
                Socket client = serverSocket.accept();
                executorService.execute(new ConnectionHandler(client));
            }

        }catch(IOException e){
            e.printStackTrace();
        }


    }
}
