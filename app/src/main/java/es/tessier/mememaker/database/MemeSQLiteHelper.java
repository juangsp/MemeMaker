package es.tessier.mememaker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by Evan Anger on 8/17/14.
 */
public class MemeSQLiteHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME = "memes.db";
    static final int DATABASE_VERSION = 3;
    //Meme Table functionality

    static final String MEMES_TABLES="MEMES";
    static final String COLUM_MEMES_ASSET="asset";
    static final String COLUM_MEMES_NAME="name";
    static final String COLUM_MEMES_ID="_id";
    static final String COLUM_MEMES_DATE="create_date";

    static final String ALTER_ADD_CREATE_DATE="ALTER TABLE "+MEMES_TABLES+" ADD COLUMN "+COLUM_MEMES_DATE+" INTEGER;";

    static final String CREATE_TABLE_MEMES="CREATE TABLE "+MEMES_TABLES+" ( "+
            COLUM_MEMES_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUM_MEMES_ASSET+" TEXT NOT NULL,"+
            COLUM_MEMES_NAME+" TEXT NOT NULL);";

    //Meme Table Annotations functionality

    static final String ANNOTATIONS_TABLES="ANNOTATIONS";
    static final String COLUM_ANNOTATIONS_X="x";
    static final String COLUM_ANNOTATIONS_Y="y";
    static final String COLUM_ANNOTATIONS_COLOR="color";
    static final String COLUM_ANNOTATIONS_MEME_ID="meme_id";
    static final String COLUM_ANNOTATIONS_TITLE="title";
    static final String COLUM_ANNOTATIONS_ID="_id";

    static final String CREATE_TABLE_ANNOTATIONS="CREATE TABLE "+ANNOTATIONS_TABLES+" ( "+
            COLUM_ANNOTATIONS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUM_ANNOTATIONS_TITLE+" TEXT NOT NULL,"+
            COLUM_ANNOTATIONS_MEME_ID+ " TEXT NOT NULL,"+
            COLUM_ANNOTATIONS_X+" INTEGER NOT NULL,"+
            COLUM_ANNOTATIONS_Y+" INTEGER NOT NULL,"+
            COLUM_ANNOTATIONS_COLOR+" INTEGER NOT NULL, "+
            "FOREIGN KEY("+COLUM_ANNOTATIONS_MEME_ID+") REFERENCES MEMES(_id));"
            ;
    private static final String TAG =MemeSQLiteHelper.class.getName();


    public MemeSQLiteHelper(Context context){
       super(context,DATABASE_NAME,null,DATABASE_VERSION);


    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MEMES);
        db.execSQL(CREATE_TABLE_ANNOTATIONS);
        Log.i(TAG,CREATE_TABLE_MEMES);
        Log.i(TAG,CREATE_TABLE_ANNOTATIONS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch(oldVersion){
            case 2:db.execSQL(ALTER_ADD_CREATE_DATE);

        }

    }
//Meme Table functionality

    //Meme Table Annotations functionality

}
