package es.tessier.mememaker.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Evan Anger on 7/28/14.
 */
public class FileUtilities {

    private static final String TAG ="Error";
    private static final int TAM_BUFFER =1024 ;

    public static void saveAssetImage(Context context, String assetName) {

        File fileDirectory= context.getFilesDir();
        File fileToWrite = new File(fileDirectory,assetName);
        AssetManager assetManager = context.getAssets();
        InputStream in = null;
        FileOutputStream out=null;
        try {
            in = assetManager.open(assetName);
            out = new FileOutputStream(fileToWrite);

            byte[]buffer =new byte[TAM_BUFFER];
            int read;
            while((read=in.read(buffer))!=-1){
                out.write(buffer,0,read);
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "FileNotFoundException caught ", e);
        } catch (IOException e) {
            Log.e(TAG,"IOException caught ",e);

        }finally{
            try{
                in.close();
                out.close();

            }catch (FileNotFoundException e){
                Log.e(TAG, "FileNotFoundException caught ", e);
            }catch(IOException e){
                Log.e(TAG, "FileNotFoundException caught ", e);
            }
        }


    }

    public static Uri saveImageForSharing(Context context, Bitmap bitmap,  String assetName) {
        File fileToWrite = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), assetName);

        try {
            FileOutputStream outputStream = new FileOutputStream(fileToWrite);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return Uri.fromFile(fileToWrite);
        }
    }


    public static void saveImage(Context context, Bitmap bitmap, String name) {
        File fileDirectory = context.getFilesDir();
        File fileToWrite = new File(fileDirectory, name);

        try {
            FileOutputStream outputStream = new FileOutputStream(fileToWrite);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
