package pt.ulisboa.tecnico.cmov.projectcmu.util;

import android.provider.BaseColumns;

/**
 * Created by Romeu Viana on 09/05/2017.
 */

public class DatabaseConstants {

    public static final String DB_NAME = "pt.ulisboa.tecnico.cmov.notepadapp2";
    public static final int DB_VERSION = 1;

    public class MessagesEntry implements BaseColumns {
        public static final String TABLE = "messages";

        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_CONTENT = "content";

    }

    public class PublicationsEntry implements BaseColumns{
        public static final String TABLE = "publications";

        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_CONTENT = "content";
    }

    public class LocationEntry implements BaseColumns{
        public static final String TABLE = "location";

        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_X_COORDINATE = "x";
        public static final String COL_TASK_Y_COORDINATE = "y";
        public static final String COL_TASK_RANGE = "range";
    }
}
