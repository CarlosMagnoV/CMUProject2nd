package pt.ulisboa.tecnico.cmov.projectcmu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuScreen extends AppCompatActivity {

    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_screen);

        setUsername();
    }

    private void setUsername(){


        Intent intent = getIntent();
        String usernameString = intent.getExtras().getString("Username");

        //Creation of a new session with the given username
        //This happens only once. After, the argument is irrelevant

        session = Session.getInstance(usernameString);



        TextView username = (TextView) findViewById(R.id.profile_button);
        username.setText(session.username);
    }

    //TODO implement goToMessages
    public void goToMessages(View view){
        Intent intent = new Intent(this, MyMessagesScreen.class);
        startActivity(intent);
    }

    //TODO implement goToLocations
    public void goToLocations(View view){
        Intent intent = new Intent(this, LocationScreen.class);
        startActivity(intent);
    }

    //TODO implement goToPosts
    public void goToPosts(View view){
        Intent intent = new Intent(this, PostMenuScreen.class);
        startActivity(intent);
    }

    //TODO implement goToProfile
    public void goToProfile(View view){}

    public void goToReceived(View view){
        Intent intent = new Intent(this, ReceivePosts.class);
        startActivity(intent);
    }
}
