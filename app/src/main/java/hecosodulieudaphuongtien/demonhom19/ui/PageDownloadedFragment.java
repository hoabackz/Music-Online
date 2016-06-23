package hecosodulieudaphuongtien.demonhom19.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import hecosodulieudaphuongtien.demonhom19.R;
import hecosodulieudaphuongtien.demonhom19.entity.Audio;
import hecosodulieudaphuongtien.demonhom19.sqlite.DataSource;
import hecosodulieudaphuongtien.demonhom19.sqlite.DownloadAudio;

/**
 * Created by admin on 3/24/2016.
 */
public class PageDownloadedFragment extends Fragment implements MainActivity.MyListener {
    private ListView listView;
    private ArrayList<Audio> listAudios = new ArrayList<>();
    private MyAdapter adapter;
    private MainActivity activity;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        listAudios = DataSource.getAudiosDownloaded();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        adapter = new MyAdapter(activity, listAudios);
        listView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void playModeChange(boolean isOnline) {
        if (isOnline) {
            for (int i = 0; i < listAudios.size(); i++) {
                listAudios.get(i).isPlaying = false;
            }
            adapter.updateUI();
        }
    }

    @Override
    public void downLoadFinish() {
        int id = -1;
        for (int i = 0; i < listAudios.size(); i++) {
            if (listAudios.get(i).isPlaying == true) id = listAudios.get(i).id;
        }
        listAudios = DataSource.getAudiosDownloaded();
        for (int i = 0; i < listAudios.size(); i++) {
            if (listAudios.get(i).id == id) listAudios.get(i).isPlaying = true;
        }
        adapter.updateUI();
    }

    public void deleteAudio(Audio audio) {
        DataSource.deleteAudio(audio);
        listAudios = DataSource.getAudiosDownloaded();
        adapter.updateUI();
    }

    private class MyAdapter extends BaseAdapter {
        ArrayList<Audio> audios;
        Context mContext;
        LayoutInflater inflater;

        public MyAdapter(Context context, ArrayList<Audio> audios) {
            this.mContext = context;
            this.audios = audios;
            inflater = LayoutInflater.from(this.mContext);
        }

        @Override
        public int getCount() {
            return audios.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_audio, null);
                myViewHolder = new MyViewHolder();
                myViewHolder.btnDelete = (LinearLayout) convertView.findViewById(R.id.btn_sub_item);
                myViewHolder.itemClick = (LinearLayout) convertView.findViewById(R.id.btn_item);
                myViewHolder.iv_Playing = (ImageView) convertView.findViewById(R.id.iv_playing);
                myViewHolder.iv_Delete = (ImageView) convertView.findViewById(R.id.iv_delete);

                myViewHolder.tv_Number = (TextView) convertView.findViewById(R.id.tv_number);
                myViewHolder.tv_Title = (TextView) convertView.findViewById(R.id.tv_title);

                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }
            myViewHolder.iv_Delete.setBackgroundResource(R.drawable.delete);
            myViewHolder.tv_Title.setText(listAudios.get(position).title);
            myViewHolder.tv_Number.setText(listAudios.get(position).number + "");
            if (listAudios.get(position).isPlaying) {
                myViewHolder.tv_Number.setVisibility(View.GONE);
                myViewHolder.iv_Playing.setVisibility(View.VISIBLE);
            } else {
                myViewHolder.tv_Number.setVisibility(View.VISIBLE);
                myViewHolder.iv_Playing.setVisibility(View.GONE);
            }
            myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.makeNotification("Đã xóa : " + audios.get(position).title);
                    deleteAudio(audios.get(position));

                }
            });
            myViewHolder.itemClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < listAudios.size(); i++) {
                        listAudios.get(i).isPlaying = false;
                    }
                    listAudios.get(position).isPlaying = true;
                    updateUI();
                    activity.getPlayerController().playOffline(audios.get(position));
                    activity.changeModePlay(false);
                }
            });

            return convertView;
        }

        public void updateUI() {
            audios = listAudios;
            notifyDataSetChanged();
        }

        private class MyViewHolder {
            TextView tv_Title;
            ImageView iv_Playing;
            ImageView iv_Delete;
            TextView tv_Number;
            LinearLayout btnDelete;
            LinearLayout itemClick;
        }
    }
}
