package hecosodulieudaphuongtien.demonhom19.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ArrayList;

import hecosodulieudaphuongtien.demonhom19.R;
import hecosodulieudaphuongtien.demonhom19.mediaplayer.MyPlayer;
import hecosodulieudaphuongtien.demonhom19.sqlite.DataBaseHelper;
import hecosodulieudaphuongtien.demonhom19.sqlite.DataSource;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private TabLayout tabLayout;
    private MyPlayer myPlayer;
    public int DIALOG_DOWNLOAD = 1011;
    public ProgressDialog progressDialog;
    private ArrayList<MyListener> listenersArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPlayer = new MyPlayer(this);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        dataBaseHelper.createDataBase();
        try {
            dataBaseHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        init();
        setUpViewPagerWithTab();

    }

    public void makeNotification(String message) {
        Snackbar.make(this.getCurrentFocus(), message, Snackbar.LENGTH_SHORT).show();
    }

    public void changeModePlay(boolean checkOnline) {
        for (int i = 0; i < listenersArrayList.size(); i++) {
            listenersArrayList.get(i).playModeChange(checkOnline);
        }
    }

    public void downloadComplete() {
        for (int i = 0; i < listenersArrayList.size(); i++) {
            listenersArrayList.get(i).downLoadFinish();
        }
    }

    public MyPlayer getPlayerController() {
        return myPlayer;
    }

    private void setUpViewPagerWithTab() {
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void init() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DOWNLOAD) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Downloading..");
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            return progressDialog;
        }
        return null;
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        PageOnlineFragment pageOnlineFragment;
        PageDownloadedFragment pageDownloadedFragment;

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            pageOnlineFragment = new PageOnlineFragment();
            pageDownloadedFragment = new PageDownloadedFragment();
            listenersArrayList.add(pageOnlineFragment);
            listenersArrayList.add(pageDownloadedFragment);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return pageOnlineFragment;
                case 1:
                    return pageDownloadedFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) return "Online";
            else return "Downloaded";
        }
    }

    interface MyListener {
        public void playModeChange(boolean isOnline);

        public void downLoadFinish();
    }
}
