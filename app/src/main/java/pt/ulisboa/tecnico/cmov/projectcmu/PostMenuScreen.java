package pt.ulisboa.tecnico.cmov.projectcmu;

<<<<<<< HEAD
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.projectcmu.database.DatabaseHelper;
import pt.ulisboa.tecnico.cmov.projectcmu.util.DatabaseConstants;

public class PostMenuScreen extends AppCompatActivity {

    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    private DatabaseHelper mHelper;

=======
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PostMenuScreen extends AppCompatActivity {

>>>>>>> origin/master
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_menu_screen);
<<<<<<< HEAD
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostMenuScreen.this ,addPostScreen.class);
                startActivity(intent);
            }
        });

        mTaskListView = (ListView) findViewById(R.id.posts_list_view);
        postsLoader();
    }

    private void postsLoader(){
        ArrayList<String> postsList = new ArrayList<>();
        mHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseConstants.PublicationsEntry.TABLE,
                new String[]{DatabaseConstants.PublicationsEntry._ID, DatabaseConstants.PublicationsEntry.COL_TASK_TITLE},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(DatabaseConstants.PublicationsEntry.COL_TASK_TITLE);
            postsList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.posts_list,
                    R.id.post_text_view,
                    postsList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(postsList);
            mAdapter.notifyDataSetChanged();
            mTaskListView.setAdapter(mAdapter);
        }

        if(mTaskListView.getCount() == 0 && postsList.size()==0){
            TextView nullListView = (TextView) findViewById(R.id.null_post_list_view);
            nullListView.setVisibility(TextView.VISIBLE);
            mTaskListView.setVisibility(ListView.INVISIBLE);
        }

        cursor.close();
        db.close();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        postsLoader();
        super.onResume();
    }

    private void deletePost(String title){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(DatabaseConstants.PublicationsEntry.TABLE,
                DatabaseConstants.PublicationsEntry.COL_TASK_TITLE + " = ?",
                new String[]{title});
        db.close();
    }

    public void seePost(View view){

        TextView titleText = (TextView) view.findViewById(R.id.post_text_view);
        final String titleString = String.valueOf(titleText.getText());

        String body = "empty";

        String[] title = new String[]{titleString};
        mHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseConstants.PublicationsEntry.TABLE,
                new String[]{DatabaseConstants.PublicationsEntry.COL_TASK_CONTENT},
                "title =? ", title, null, null, null);

        if (cursor != null && cursor.moveToFirst()){
            body = cursor.getString(0);
        }



        // Creating alert Dialog with one Button
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle(titleString);

        // Setting Dialog Message
        alertDialog.setMessage(body);




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
                        deletePost(titleString);
                        postsLoader();
                        dialog.dismiss();
                    }
                });



        alertDialog.show();

    }

=======
    }
>>>>>>> origin/master
}
