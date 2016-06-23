package hecosodulieudaphuongtien.demonhom19.sqlite;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.design.widget.Snackbar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import hecosodulieudaphuongtien.demonhom19.entity.Audio;
import hecosodulieudaphuongtien.demonhom19.ui.MainActivity;

/**
 * Created by admin on 3/30/2016.
 */
public class DownloadAudio extends AsyncTask<String, String, String> {
    private MainActivity activity;
    private Audio audio;
    File rootDir = Environment.getExternalStorageDirectory();

    public DownloadAudio(MainActivity activity, Audio audio) {
        this.activity = activity;
        this.audio = audio;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.showDialog(activity.DIALOG_DOWNLOAD);
    }

    @Override
    protected String doInBackground(String... aurl) {
        int count;

        try {
            URL url = new URL(audio.getURL());
            URLConnection connection = url.openConnection();
            connection.connect();

            int lenghtOfFile = connection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(new File("/sdcard/Music/" + audio.title + ".mp3"));

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                output.write(data, 0, count);

            }

            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
        }
        return null;

    }

    protected void onProgressUpdate(String... progress) {
        activity.progressDialog.setProgress(Integer.parseInt(progress[0]));
    }

    @Override
    protected void onPostExecute(String unused) {
        DataSource.addAudio(audio);
        activity.makeNotification("Tải về thành công !");

        activity.dismissDialog(activity.DIALOG_DOWNLOAD);
        activity.downloadComplete();
    }
}