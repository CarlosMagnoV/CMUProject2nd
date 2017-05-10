package pt.ulisboa.tecnico.cmov.projectcmu;

import android.content.Context;

/**
 * Created by Romeu Viana on 09/05/2017.
 */

public class Session {

    private static Session currentSession;

    public static String username;

    public static synchronized Session getInstance(String username) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (currentSession == null) {
            currentSession = new Session(username);
        }
        return currentSession;
    }

    public Session(String username){
        this.username = username;
    }


}
