package pt.ulisboa.tecnico.cmov.projectcmu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddPostScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_screen);
    }

    private void cancelPost(View v){
        finish();
    }

    private void goHome(View v){
        finish();
    }

    private void okPost(View v){
        EditText edBody = (EditText) findViewById(R.id.editText2);
        EditText edTitle = (EditText) findViewById(R.id.editText);
        String body = edBody.getText().toString();
        String title = edTitle.getText().toString();
        Bundle extras = new Bundle();
        extras.putString("body",body);
        extras.putString("title",title);

    }
}
