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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import teamgarbo.github.com.badbotapp.message.*;
import teamgarbo.github.com.badbotapp.model.Properties;

public class MainActivity extends AppCompatActivity {

    private IntentIntegrator qrScan;

    String playerID; //This can be anything that identifies the user
    String playerName;
    String clubID;
    Socket socket;
    ObjectOutputStream os;
    ObjectInputStream is;
    boolean socketInitalised = false;

    boolean loggedIn = false;

    int court = -2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getActionBar().setTitle(R.string.app_name);
        startSocket();
        initPlayerID();
        updatePlayerDetails("Not logged in");
        qrScan = new IntentIntegrator(this);
        //qrScan.initiateScan();
        FloatingActionButton fbt = (FloatingActionButton) findViewById(R.id.floatingQRButton);
        fbt.setImageDrawable(getResources().getDrawable(R.drawable.qrcode));
        enabled(false);
        updateCourtDetails(-2);
    }

    private void changeFBT()
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FloatingActionButton fbt = findViewById(R.id.floatingQRButton);
                if(loggedIn)
                    fbt.setImageDrawable(getResources().getDrawable(R.drawable.logout));
                else
                    fbt.setImageDrawable(getResources().getDrawable(R.drawable.qrcode));
            }
        });

    }

    private void updatePlayerDetails(final String name)
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView name_text = findViewById(R.id.txt_Player);
                name_text.setText(name);
            }
        });

    }

    private void updateCourtDetails(final int courtNo)
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView court_text = findViewById(R.id.txt_court);
                if(courtNo == -1)
                    court_text.setText("In queue...");
                else if (courtNo == -2)
                    court_text.setText("Waiting...");
                else
                    court_text.setText("Court " + courtNo);
            }
        });
    }

    public void enabled(final boolean e)
    {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout ll = findViewById(R.id.outcome_container);
                if(e)
                    ll.setVisibility(View.VISIBLE);
                else
                {
                    ll.setVisibility(View.GONE);
                    updateCourtDetails(-1);
                }
            }
        });
    }

    public void gameWon(View view)
    {
        try {
            sendMessage(new GameEndMessage(clubID,playerID, Properties.WIN));
            enabled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void gameLost(View view)
    {
        try {
            sendMessage(new GameEndMessage(clubID,playerID, Properties.LOSS));
            enabled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void gameDNF(View view)
    {
        try {
            sendMessage(new GameEndMessage(clubID,playerID, Properties.DRAW));
            enabled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void test_button(View view)
    {
        updateCourtDetails(10);
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeFBT();
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
        Message test = new InitialMessage(clubID, playerID);
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

       // if(message instanceof BadMessage){

        //}
        if(message instanceof GameStartMessage){
            //TODO create intent with "i won/lost" buttons
            //TODO set textview thing to court number
            court = ((GameStartMessage) message).getCourtNumber();
            updateCourtDetails(court);
            enabled(true);
        }

        if(message instanceof ExistingPlayerMessage){
            playerName = ((ExistingPlayerMessage) message).getName();
            updatePlayerDetails(playerName);
            loggedIn = true;
            changeFBT();
        }

        if(message instanceof RequestPlayerMessage){
            startActivityForResult(new Intent(getBaseContext(), NewUserFormActivity.class), 444);
        }

        if(message instanceof RequestLogout){
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public void logout() {

        try {
            if(court >= 0)
                gameDNF(null);
            sendMessage(new LogoutMessage(clubID, playerID));
            loggedIn = false;
            changeFBT();
            updatePlayerDetails("Not logged in");
            enabled(false);
            updateCourtDetails(-2);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void scanQRButton(View view){
        //initiating the qr code scan
        if(loggedIn)
            logout();
        else
            qrScan.initiateScan();

    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 444) {
            if (resultCode == RESULT_OK) {
                String player_name = data.getStringExtra("player_name");
                String player_band = data.getStringExtra("player_band");

                try {
                    sendMessage(new CreatePlayerMessage(clubID,playerID, player_name, Integer.parseInt(player_band)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

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
