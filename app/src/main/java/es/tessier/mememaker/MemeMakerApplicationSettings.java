package es.tessier.mememaker;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Evan Anger on 8/13/14.
 */
public class MemeMakerApplicationSettings {
    public static String KEY_STORAGE="StorageType.INTERNAL";
    private SharedPreferences mSharedPreferences;
    public MemeMakerApplicationSettings(Context context) {
        mSharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static String getStoragePreference(){

        return KEY_STORAGE;
    }

    public String setSharedPreference(String storage){
       KEY_STORAGE=storage;
        return KEY_STORAGE;
    }
}
