package pt.ulisboa.tecnico.cmov.projectcmu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Romeu Viana on 09/05/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    public static final String MESSAGE_TABLE = "messages";
    public static final String PUBLICATION_TABLE = "publications";
    public static final String LOCATIONS_TABLE = "location";
    public static final String POST_TABLE = "posts";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_COORDINATE_X = "x";
    public static final String COLUMN_COORDINATE_Y = "y";
    public static final String COLUMN_RANGE = "range";


    private static final String DATABASE_NAME = "simple_note_app.db";
    private static final int DATABASE_VERSION = 1;


    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MESSAGES);
        db.execSQL(CREATE_TABLE_PUBLICATIONS);
        db.execSQL(CREATE_TABLE_LOCATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MESSAGE_TABLE);
        onCreate(db);
    }

    private static final String CREATE_TABLE_MESSAGES = "create table "
            + MESSAGE_TABLE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_CONTENT + " text not null)";

    private static final String CREATE_TABLE_PUBLICATIONS = "create table "
            + PUBLICATION_TABLE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_CONTENT + " text not null)";

    private static final String CREATE_TABLE_LOCATIONS = "create table "
            + LOCATIONS_TABLE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_COORDINATE_X + " integer not null,"
            + COLUMN_COORDINATE_Y + " integer not null, "
            + COLUMN_RANGE + " integer not null)";
}

