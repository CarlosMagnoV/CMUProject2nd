package pt.ulisboa.tecnico.cmov.projectcmu;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.projectcmu.database.DatabaseHelper;
import pt.ulisboa.tecnico.cmov.projectcmu.util.DatabaseConstants;

public class ReceivePosts extends AppCompatActivity {

    //TODO receber posts do servidor

    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    private DatabaseHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_posts);

        mTaskListView = (ListView) findViewById(R.id.receive_list_view);
        receiveLoader();
    }

    public void showReceiveContent(View view){
        TextView titleText = (TextView) view.findViewById(R.id.receive_text_view);
        final String titleString = String.valueOf(titleText.getText());

        final String body = null; //TODO pedir body de post ao server

        // Creating alert Dialog with one Button
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle(titleString);

        // Setting Dialog Message
        alertDialog.setMessage(body);




        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        savePostOnDatabase(titleString, body);
                        receiveLoader();
                        dialog.dismiss();
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });



        alertDialog.show();


    }

    private void savePostOnDatabase(String title, String body){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.PublicationsEntry.COL_TASK_TITLE, title);
        values.put(DatabaseConstants.PublicationsEntry.COL_TASK_CONTENT, body);

        db.insertWithOnConflict(DatabaseConstants.PublicationsEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        Toast.makeText(this, "Publication Stored!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void receiveLoader(){


        ArrayList<String> receiveListTitles = new ArrayList();
        //TODO  make receiveListTitles receive titles' list from server
        //TODO  remove already Received messages from receiveListTitles

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.posts_list,
                    R.id.post_text_view,
                    receiveListTitles);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(receiveListTitles);
            mAdapter.notifyDataSetChanged();
            mTaskListView.setAdapter(mAdapter);
        }

        if(mTaskListView.getCount() == 0 && receiveListTitles.size()==0){
            TextView nullListView = (TextView) findViewById(R.id.null_receive_list_label);
            nullListView.setVisibility(TextView.VISIBLE);
            mTaskListView.setVisibility(ListView.INVISIBLE);
        }
    }


}
