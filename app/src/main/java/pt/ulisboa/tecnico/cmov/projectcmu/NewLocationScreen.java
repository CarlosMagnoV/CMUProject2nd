package pt.ulisboa.tecnico.cmov.projectcmu;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pt.ulisboa.tecnico.cmov.projectcmu.database.DatabaseHelper;
import pt.ulisboa.tecnico.cmov.projectcmu.util.DatabaseConstants;

public class NewLocationScreen extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location_screen);

        this.dbHelper = DatabaseHelper.getInstance(this);

    }

    public void finishNewLocation(View view){
        EditText title = (EditText) findViewById(R.id.new_note_title);
        EditText xCoorContent = (EditText) findViewById(R.id.x_coordinate);
        EditText yCoorContent = (EditText) findViewById(R.id.y_coordinate);
        EditText rangeContent = (EditText) findViewById(R.id.range_field);

        String strTitle = null;
        int xCoordinate = 0;
        int yCoordinate = 0;
        int range = 0;

        try {
            strTitle = title.getText().toString();
            xCoordinate = Integer.parseInt(xCoorContent.getText().toString());
            yCoordinate = Integer.parseInt(yCoorContent.getText().toString());
            range = Integer.parseInt(rangeContent.getText().toString());
        }
        catch(Exception e){
            Toast.makeText(this, "Invalid fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(strTitle.isEmpty()){
            Toast.makeText(this, "Invalid fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseConstants.LocationEntry.COL_TASK_TITLE, strTitle);
        values.put(DatabaseConstants.LocationEntry.COL_TASK_X_COORDINATE, xCoordinate);
        values.put(DatabaseConstants.LocationEntry.COL_TASK_Y_COORDINATE, yCoordinate);
        values.put(DatabaseConstants.LocationEntry.COL_TASK_RANGE, range);

        db.insertWithOnConflict(DatabaseConstants.LocationEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        Toast.makeText(this, "Location added!", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
