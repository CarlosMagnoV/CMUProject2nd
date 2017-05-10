package pt.ulisboa.tecnico.cmov.projectcmu;

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
import android.widget.Toast;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.projectcmu.database.DatabaseHelper;
import pt.ulisboa.tecnico.cmov.projectcmu.util.DatabaseConstants;

public class LocationScreen extends AppCompatActivity {

    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;
    private DatabaseHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationScreen.this, NewLocationScreen.class);
                startActivity(intent);

            }
        });
        mTaskListView = (ListView) findViewById(R.id.locations_list_view);
        locationsLoader();



    }

    private void locationsLoader(){

        ArrayList<String> locationsList = new ArrayList<>();
        mHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseConstants.LocationEntry.TABLE,
                new String[]{DatabaseConstants.LocationEntry._ID, DatabaseConstants.LocationEntry.COL_TASK_TITLE},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(DatabaseConstants.LocationEntry.COL_TASK_TITLE);
            locationsList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.locations_list,
                    R.id.location_title,
                    locationsList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(locationsList);
            mAdapter.notifyDataSetChanged();
            mTaskListView.setAdapter(mAdapter);
        }

        if(mTaskListView.getCount() == 0 && locationsList.size()==0){
            TextView nullListView = (TextView) findViewById(R.id.null_location_list_label);
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
        locationsLoader();
        super.onResume();
    }

    private Cursor getLocationInfo(String title){
        String[] location = new String[]{title};
        mHelper = DatabaseHelper.getInstance(this);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseConstants.LocationEntry.TABLE,
                new String[]{DatabaseConstants.LocationEntry.COL_TASK_X_COORDINATE,
                        DatabaseConstants.LocationEntry.COL_TASK_X_COORDINATE,
                         DatabaseConstants.LocationEntry.COL_TASK_RANGE},
                "title =? ", location, null, null, null);

        return cursor;
    }

    private void deleteLocation(String title){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(DatabaseConstants.LocationEntry.TABLE,
                DatabaseConstants.LocationEntry.COL_TASK_TITLE + " = ?",
                new String[]{title});
        db.close();
    }

    public void seeLocations(View view){

        //View parent = (View) view.getParent();
        TextView locationText = (TextView) view.findViewById(R.id.location_title);
        final String locationString = String.valueOf(locationText.getText());

        int coordinateX=0;
        int coordinateY=0;
        int range=0;

        Cursor cursor = getLocationInfo(locationString);

        if (cursor != null && cursor.moveToFirst()){
            coordinateX = cursor.getInt(0);
            coordinateY = cursor.getInt(1);
            range = cursor.getInt(2);
        }



        // Creating alert Dialog with one Button
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        //AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle(locationString);

        // Setting Dialog Message
        alertDialog.setMessage("[" + coordinateX + ", " + coordinateY + ", " + range + "m]");




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
                        deleteLocation(locationString);
                        locationsLoader();
                        dialog.dismiss();
                    }
                });



        alertDialog.show();

    }

}
