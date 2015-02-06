package es.tessier.mememaker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import es.tessier.mememaker.models.Meme;
import es.tessier.mememaker.models.MemeAnnotation;

/**
 * Created by Evan Anger on 8/17/14.
 */
public class MemeDatasource {

    private Context mContext;
    private MemeSQLiteHelper mMemeSQLiteHelper;

    public MemeDatasource(Context context) {
        mContext = context;
        mMemeSQLiteHelper=new MemeSQLiteHelper(mContext);

    }

    public ArrayList<Meme> read(){
        ArrayList<Meme>memes=readMemes();
        addMemeAnnotations(memes);
        return memes;
    }
    public ArrayList<Meme> readMemes(){
        SQLiteDatabase database=openReadable();
        ArrayList<Meme>memes=new ArrayList();
        Cursor cursor;
        cursor=database.query(MemeSQLiteHelper.MEMES_TABLES,new String[]{MemeSQLiteHelper.COLUM_MEMES_ID,MemeSQLiteHelper.COLUM_MEMES_ASSET,MemeSQLiteHelper.COLUM_MEMES_NAME},null,null,null,null,null);

        if(cursor.moveToFirst()==true){
            Meme meme;
            do{
               int id=getIntFromColumnName(cursor,MemeSQLiteHelper.COLUM_MEMES_ID);
                String name=getStringFromColumnName(cursor,MemeSQLiteHelper.COLUM_MEMES_NAME);
                String asset=getStringFromColumnName(cursor,MemeSQLiteHelper.COLUM_MEMES_ASSET);
                meme=new Meme(id,asset,name,null);
                memes.add(meme);
            }while(cursor.moveToNext()!=false);
        }
        cursor.close();
        close(database);

        return memes;
    }

    public void addMemeAnnotations(ArrayList<Meme> memes){
        SQLiteDatabase database=openReadable();
        Cursor cursor=null;
        for(Meme m:memes){
            ArrayList<MemeAnnotation>annotation=new ArrayList();

            cursor=database.rawQuery("SELECT * FROM "+ MemeSQLiteHelper.ANNOTATIONS_TABLES +"\n"+
                    "WHERE " + MemeSQLiteHelper.COLUM_ANNOTATIONS_MEME_ID+"="+m.getId(),null);

            if(cursor.moveToFirst()==true){
                Meme meme;
                do{
                    int id=getIntFromColumnName(cursor,MemeSQLiteHelper.COLUM_ANNOTATIONS_ID);
                    String title=getStringFromColumnName(cursor,MemeSQLiteHelper.COLUM_ANNOTATIONS_TITLE);
                    int x =getIntFromColumnName(cursor,MemeSQLiteHelper.COLUM_ANNOTATIONS_X);
                    int y =getIntFromColumnName(cursor,MemeSQLiteHelper.COLUM_ANNOTATIONS_Y);
                    String color =getStringFromColumnName(cursor,MemeSQLiteHelper.COLUM_ANNOTATIONS_COLOR);
                    MemeAnnotation mat=new MemeAnnotation(id,color,title,x,y);
                    annotation.add(mat);
                }while(cursor.moveToNext()!=false);
            }
            m.setAnnotations(annotation);
        }
        cursor.close();
        close(database);

    }

    private int getIntFromColumnName(Cursor cursor,String name){
        int columIndex=cursor.getColumnIndex(name);
        return cursor.getInt(columIndex) ;
    }
    private String getStringFromColumnName(Cursor cursor,String name){
        int columIndex=cursor.getColumnIndex(name);
        return cursor.getString(columIndex) ;
    }

    private SQLiteDatabase openWriteable() {
        return mMemeSQLiteHelper.getWritableDatabase();
    }
    private SQLiteDatabase openReadable() {
        return mMemeSQLiteHelper.getReadableDatabase();
    }

    public void close(SQLiteDatabase database) {
        database.close();
    }

    public void create(Meme meme){
        SQLiteDatabase database=openWriteable();
        database.beginTransaction();
        ContentValues memeValues=new ContentValues();

        memeValues.put(MemeSQLiteHelper.COLUM_MEMES_ASSET,meme.getAssetLocation());
        memeValues.put(MemeSQLiteHelper.COLUM_MEMES_NAME,meme.getName());
        long memeID;
       memeID=database.insert(MemeSQLiteHelper.MEMES_TABLES,null,memeValues);

        ContentValues annotationValues=new ContentValues();
        for(MemeAnnotation ma:meme.getAnnotations()) {
            annotationValues.put(MemeSQLiteHelper.COLUM_ANNOTATIONS_TITLE,ma.getTitle());
            annotationValues.put(MemeSQLiteHelper.COLUM_ANNOTATIONS_X,ma.getLocationX());
            annotationValues.put(MemeSQLiteHelper.COLUM_ANNOTATIONS_Y,ma.getLocationY());
            annotationValues.put(MemeSQLiteHelper.COLUM_ANNOTATIONS_COLOR,ma.getColor());
            annotationValues.put(MemeSQLiteHelper.COLUM_ANNOTATIONS_MEME_ID,ma.getId());
        }
        long AnotationS;
        AnotationS=database.insert(MemeSQLiteHelper.ANNOTATIONS_TABLES,null,annotationValues);
        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);
    }


    public void update(Meme meme){
        SQLiteDatabase database=openWriteable();
        database.beginTransaction();

    }


}
