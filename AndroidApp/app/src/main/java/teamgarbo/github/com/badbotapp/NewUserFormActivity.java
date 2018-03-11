package teamgarbo.github.com.badbotapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewUserFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_form);
        //getActionBar().setTitle(R.string.new_user);
    }

    public void endActivity(View view)
    {
        finish();
    }

    public void submitNewUser(View view)
    {

    }
}
