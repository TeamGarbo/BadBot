package teamgarbo.github.com.badbotapp;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import teamgarbo.github.com.badbotapp.message.BadMessage;

public class MainActivity extends AppCompatActivity {

    private IntentIntegrator qrScan;

    String playerID; //This can be anything that identifies the user
    String clubID;
    Socket socket;
    ObjectOutputStream os;
    boolean socketInitalised = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getActionBar().setTitle(R.string.app_name);
        qrScan = new IntentIntegrator(this);
        initPlayerID();
    }



    public void initSocket() throws IOException {
        String ip = "10.9.133.81";
        InetAddress adr = InetAddress.getByName(ip);

        socket = new Socket(adr, 4444);
        os = new ObjectOutputStream(socket.getOutputStream());
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
        BadMessage test = new BadMessage(clubID, playerID);
        os.writeObject(test);
        os.flush(); // Send off the data
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
                System.out.println(clubID);
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
}
