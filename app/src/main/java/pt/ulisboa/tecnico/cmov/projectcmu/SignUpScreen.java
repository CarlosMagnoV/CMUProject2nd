package pt.ulisboa.tecnico.cmov.projectcmu;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pt.ulisboa.tecnico.cmov.projectcmu.util.StatusTracker;

/**
 * Created by cmagn on 08/05/2017.
 */

public class SignUpScreen extends Activity {
    private String mActivityName;
    private StatusTracker mStatusTracker = StatusTracker.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_screen);
        mActivityName = "sign_up_screen";
        mStatusTracker.setStatus(mActivityName, "onCreate");
        Button p1_button = (Button)findViewById(R.id.SUbackButton);
        p1_button.setText("Back to Login");
        //Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mStatusTracker.setStatus(mActivityName, "onStart");
        //Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mStatusTracker.setStatus(mActivityName, "onRestart");
        //Utils.printStatus(mStatusView, mStatusAllView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStatusTracker.setStatus(mActivityName, "onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        mStatusTracker.setStatus(mActivityName, "onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        mStatusTracker.setStatus(mActivityName, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStatusTracker.setStatus(mActivityName, "onDestroy");
        mStatusTracker.clear();
    }


    public void goBack(View v) {
        //Intent intent = new Intent(SignUpScreen.this, LoginScreen.class);
        //startActivity(intent);
        finish();
    }

    public void goToMainMenu(View view){

        String username = null;
        String password = null;
        String passwordConfirm = null;
        try {
            username = String.valueOf(((TextView) this.findViewById(R.id.SUusername)).getText());
            password = String.valueOf(((TextView) this.findViewById(R.id.SUpassword)).getText());
            passwordConfirm = String.valueOf(((TextView) this.findViewById(R.id.SUconfirmPassword)).getText());
        }
        catch(NullPointerException e){
            Toast.makeText(this, "Username or password empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!username.isEmpty() && !password.isEmpty() && !passwordConfirm.isEmpty()) {
            if(passwordConfirm.equals(password)) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainMenuScreen.class);


                Bundle b = new Bundle();
                b.putString("Username", username);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    intent.addFlags(0x8000);    // equal to Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                intent.putExtras(b);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Username or password empty", Toast.LENGTH_SHORT).show();
        }
    }




}
