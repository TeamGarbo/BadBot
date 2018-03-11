package teamgarbo.github.com.badbotapp;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import teamgarbo.github.com.badbotapp.message.*;

public class MainActivity extends AppCompatActivity {

    private IntentIntegrator qrScan;

    String playerID; //This can be anything that identifies the user
    String clubID;
    Socket socket;
    ObjectOutputStream os;
    ObjectInputStream is;
    boolean socketInitalised = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getActionBar().setTitle(R.string.app_name);
        startSocket();
        initPlayerID();
        qrScan = new IntentIntegrator(this);
        qrScan.initiateScan();
        FloatingActionButton fbt = (FloatingActionButton) findViewById(R.id.floatingQRButton);
        fbt.setImageDrawable(getResources().getDrawable(R.drawable.qrcode));
    }

            

    public void initSocket() throws IOException {
        //String ip = "10.9.133.81";
        String ip = "46.101.53.119";
        InetAddress adr = InetAddress.getByName(ip);

        socket = new Socket(adr, 4444);
        os = new ObjectOutputStream(socket.getOutputStream());
        is = new ObjectInputStream(socket.getInputStream());
        socketInitalised = true;
    }

    public void initPlayerID() {

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        // get IMEI Permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
            initPlayerID();
            return;
        }

        playerID = tm.getDeviceId();
    }

    public void sendTestMessage() throws IOException {
        // Send first message
        Message test = new BadMessage(clubID, playerID);
        os.writeObject(test);
        os.flush(); // Send off the data
    }

    public void sendMessage(final Message message) throws IOException {
        new Thread()
        {
            public void run() {
                try {
                    if(!socketInitalised) initSocket();
                    System.out.println(message.getPlayerID());
                    os.writeObject(message);
                    os.flush(); // Send off the data
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void startSocket(){
        new Thread()
        {
            public void run() {
                try {
                    if(!socketInitalised) initSocket();
                    listener();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void listener(){
        new Thread()
        {
            public void run() {
                try {
                    if(socketInitalised)
                        while(true){
                           Message message = (Message) is.readObject();
                           processMessage(message);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //TODO: process message based on type
    public void processMessage(Message message){
        System.out.println("Message bounceback: " + message.getPlayerID());

        if(message instanceof BadMessage){

        }


    }

    public void buttonClick(View view){
        System.out.println(this.playerID);
        new Thread()
        {
            public void run() {
                try {
                    if(!socketInitalised) initSocket();
                    sendTestMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public void scanQRButton(View view){
        //initiating the qr code scan
        qrScan.initiateScan();

    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode is empty
            if (result.getContents() != null) {
                //If qr code has data, set clubid as qrcode
                clubID = result.getContents();

                try {

                    sendMessage(new InitialMessage(clubID, playerID));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(clubID);

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                v.vibrate(100);
            }
            else {
                //if qr code is null
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void loadNewUser(View view)
    {
        Intent myIntent = new Intent(this, NewUserFormActivity.class);
        this.startActivity(myIntent);
    }

    @Override
    protected void onDestroy() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }
}
