package com.cindy.geolocation.tool;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Cindyyh_Chou on 2017/4/13.
 */

public class ReadExternalFile {
    String TAG= "ReadExternalFile";

    private static final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    File mDirectory;

    public ReadExternalFile(String folder){
        mDirectory = new File(FILE_PATH+"/"+folder);
    }

    public void readFile(){
        Log.d(TAG, "FilePath: "+mDirectory.getAbsolutePath());

        if(mDirectory.isDirectory()){
            File[] listFile= mDirectory.listFiles();
            Log.d(TAG, "File Count: "+listFile.length);
            if(listFile!=null){
                for(File readFile : listFile){
                    InputStream inputStream = null;
                    try  {
                        inputStream = new FileInputStream(readFile);
                        if(inputStream!=null){
                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                            BufferedReader bufferedReader= new BufferedReader(inputStreamReader);

                            String line= null;
                            do{
                                line= bufferedReader.readLine();
                                Log.d(TAG, "data: "+line);
                            }while (line!=null);
                        }
                    }catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        if(inputStream!=null){
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }else {
                Log.d(TAG, "Error");
            }

        }
    }
}
