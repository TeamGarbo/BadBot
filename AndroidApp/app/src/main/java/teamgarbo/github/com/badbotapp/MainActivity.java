package teamgarbo.github.com.badbotapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import teamgarbo.github.com.badbotapp.message.BadMessage;

public class MainActivity extends AppCompatActivity {

    String ID; //This can be anything that identifies the user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setTitle(R.string.app_name);
    }


    public void sendTestMessage() throws IOException {
        String ip = "10.9.133.81";
        InetAddress adr =  InetAddress.getByName(ip);

        Socket socket = new Socket(adr, 4444);
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

        // Send first message
        BadMessage test = new BadMessage("i", "l");
        os.writeObject(test);
        os.flush(); // Send off the data
    }

    public void buttonClick(View view){
        new Thread()
        {
            public void run() {
                try {
                    sendTestMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void loadNewUser(View view)
    {
        Intent myIntent = new Intent(this, NewUserFormActivity.class);
        this.startActivity(myIntent);
    }
}
