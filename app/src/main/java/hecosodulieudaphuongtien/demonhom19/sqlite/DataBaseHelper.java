package hecosodulieudaphuongtien.demonhom19.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by admin on 3/25/2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/hecosodulieudaphuongtien.demonhom19/databases/";
    private static String DB_NAME = "nhom19.db";
    private final Context myContext;
    private SQLiteDatabase myDataBase;
    private static SQLiteDatabase sqlite;
    private static DataBaseHelper instance;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
        instance = this;
    }

    public static DataBaseHelper getInstance() {
        return instance;
    }

    public void createDataBase() {
        if (isDbExist() == false) {
            this.getReadableDatabase();
            try {
                coppyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void coppyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private boolean isDbExist() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }
}
