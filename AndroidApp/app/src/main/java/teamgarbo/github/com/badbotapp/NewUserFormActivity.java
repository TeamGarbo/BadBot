package teamgarbo.github.com.badbotapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class NewUserFormActivity extends AppCompatActivity {

    String colorBands[] = {
            "Blue",
            "Green",
            "Yellow",
            "Orange",
            "Red",
            "Colors"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_form);
        //getActionBar().setTitle(R.string.new_user);

        Spinner spinner = findViewById(R.id.color_spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        colorBands); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    public void endActivity(View view)
    {
        finish();
    }

    public void submitNewUser(View view)
    {
        EditText playerNameEditText = findViewById(R.id.player_name);
        String playerName = playerNameEditText.getText().toString();

        Spinner spinner = findViewById(R.id.color_spinner);
        String playerBand = (String) spinner.getSelectedItem();

        int elo = 0;
        for(int i = 0; i < colorBands.length; ++i)
            if(colorBands[i].equals(playerBand))
                elo = 5*i;

        Intent data = new Intent();
        data.putExtra("player_name", playerName);
        data.putExtra("player_band", elo + "");

        setResult(RESULT_OK,data);

        finish();
    }
}
