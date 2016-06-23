package hecosodulieudaphuongtien.demonhom19.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import hecosodulieudaphuongtien.demonhom19.R;
import hecosodulieudaphuongtien.demonhom19.entity.Audio;
import hecosodulieudaphuongtien.demonhom19.ui.MainActivity;

/**
 * Created by admin on 3/25/2016.
 */
public class DataSource {
    private static SQLiteDatabase sqLiteDatabase = DataBaseHelper.getInstance().getReadableDatabase();

    public static ArrayList<Audio> getAudiosDownloaded() {

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM audio order by title", null);
        ArrayList<Audio> listAudio = new ArrayList<>();
        if (cursor.getCount() == 0) {
            Log.e("TAG", "EMPTY AUDIO", null);
            return listAudio;

        }
        cursor.moveToFirst();
        int num = 1;
        while (!cursor.isAfterLast()) {
            Audio audio = new Audio();
            audio.id = cursor.getInt(0);
            audio.title = cursor.getString(1);
            audio.url=cursor.getString(2);
            audio.number = num;
            num++;
            listAudio.add(audio);
            cursor.moveToNext();
        }
        cursor.close();
        return listAudio;
    }

    public static void addAudio(Audio audio) {
//        String query = "INSERT INTO audio (id_sever,title) VALUES(" + audio.id + "," + audio.title + ")";
        ContentValues insertValues = new ContentValues();
        insertValues.put("title", audio.title);
        insertValues.put("id", audio.id);
        insertValues.put("url", audio.url);
        sqLiteDatabase.insert("audio", null, insertValues);

    }

    public static void deleteAudio(Audio audio) {
//        Cursor cursor = sqLiteDatabase.rawQuery("DELETE * FROM audio while title =?", new String[]{audio.title});
//        cursor.moveToFirst();
//        cursor.close();
        sqLiteDatabase.delete("audio","title = ?", new String[]{audio.title});
        if (checkFileExists(audio)) {
            File file = new File("/sdcard/Music/" + audio.title + ".mp3");
            file.delete();
        }
    }

    public static boolean checkFileExists(Audio audio) {

        File extStore = Environment.getExternalStorageDirectory();
        File myFile = new File(extStore.getAbsolutePath() + "/Music/" + audio.title + ".mp3");

        if (myFile.exists()) {
            return true;
        } else {
            return false;
        }

    }
}
