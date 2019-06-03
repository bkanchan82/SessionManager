package architech.android.com.sessionmanager.model.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {

    private final static String PREF_FILE = "DEMO_LOGIN";

    private SharedPreferences mPrefs;

    private static SharedPreferenceHelper INSTANCE = null;

    // other instance variables can be here

    private SharedPreferenceHelper(Context context) {
        mPrefs = context.getApplicationContext().getSharedPreferences(PREF_FILE, 0);
    };

    public static synchronized SharedPreferenceHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SharedPreferenceHelper(context);
        }
        return(INSTANCE);
    }

    /**
     * Remove all stored value for shared
     */
    public void clearSharedPreference(){
        mPrefs.edit().clear().commit();
    }

    /**
     * Set a string shared preference
     * @param key - Key to set shared preference
     * @param value - Value for the key
     */
    public void setSharedPreferenceString(String key, String value){
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(key, value);
        editor.apply();
    }


    /**
     * Get a string shared preference
     * @param key - Key to look up in shared preferences.
     * @param defValue - Default value to be returned if shared preference isn't found.
     * @return value - String containing value of the shared preference if found.
     */
    public String getSharedPreferenceString(String key, String defValue){
        return mPrefs.getString(key, defValue);
    }

}
