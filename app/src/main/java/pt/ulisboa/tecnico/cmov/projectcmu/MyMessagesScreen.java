package pt.ulisboa.tecnico.cmov.projectcmu;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.projectcmu.database.DatabaseHelper;
import pt.ulisboa.tecnico.cmov.projectcmu.util.DatabaseConstants;

public class MyMessagesScreen extends AppCompatActivity {

    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    private DatabaseHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messages_screen);



        addForTesting("Message One", "I'm message one!");
        addForTesting("Message Two", "I'm message number two!");

        mTaskListView = (ListView) findViewById(R.id.messages_list_view);
        messagesLoader();

    }

    private void addForTesting(String title, String content){

        mHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.MessagesEntry.COL_TASK_TITLE, title);
        values.put(DatabaseConstants.MessagesEntry.COL_TASK_CONTENT, content);

        db.insertWithOnConflict(DatabaseConstants.MessagesEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);

        db.close();
    }

    private void messagesLoader(){

        ArrayList<String> messagesList = new ArrayList<>();
        mHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseConstants.MessagesEntry.TABLE,
                new String[]{DatabaseConstants.MessagesEntry.COL_TASK_TITLE},
                null, null, null, null, null);

        while (cursor.moveToNext()){
            messagesList.add(cursor.getString(0));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.messages_list,
                    R.id.message_view,
                    messagesList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(messagesList);
            mAdapter.notifyDataSetChanged();
            mTaskListView.setAdapter(mAdapter);
        }

        if(mTaskListView.getCount() == 0 && messagesList.size()==0){
            TextView nullListView = (TextView) findViewById(R.id.null_list_label);
            nullListView.setVisibility(TextView.VISIBLE);
            mTaskListView.setVisibility(ListView.INVISIBLE);
        }

        cursor.close();
        db.close();
    }

    private void deleteMessage(String messageTitle){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(DatabaseConstants.MessagesEntry.TABLE,
                DatabaseConstants.MessagesEntry.COL_TASK_TITLE + " = ?",
                new String[]{messageTitle});
        db.close();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public void viewMessage(View view){
        TextView messageTitleText = (TextView) view.findViewById(R.id.message_view);
        final String messageTitleString = String.valueOf(messageTitleText.getText());

        String message = "empty";

        String[] location = new String[]{messageTitleString};
        mHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseConstants.MessagesEntry.TABLE,
                new String[]{DatabaseConstants.MessagesEntry.COL_TASK_CONTENT},
                "title =? ", location, null, null, null);

        if (cursor != null && cursor.moveToFirst()){
            message = cursor.getString(0);
        }



        // Creating alert Dialog with one Button
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle(messageTitleString);

        // Setting Dialog Message
        alertDialog.setMessage(message);




        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.setNegativeButton("Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        deleteMessage(messageTitleString);
                        messagesLoader();
                        dialog.dismiss();
                    }
                });



        alertDialog.show();
    }
}
