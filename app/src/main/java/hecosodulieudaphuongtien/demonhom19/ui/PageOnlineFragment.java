package hecosodulieudaphuongtien.demonhom19.ui;

import android.content.Context;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import hecosodulieudaphuongtien.demonhom19.R;
import hecosodulieudaphuongtien.demonhom19.entity.Audio;
import hecosodulieudaphuongtien.demonhom19.sqlite.DataSource;
import hecosodulieudaphuongtien.demonhom19.sqlite.DownloadAudio;

/**
 * Created by admin on 3/24/2016.
 */
public class PageOnlineFragment extends Fragment implements MainActivity.MyListener {
    private final String TAG_SUCCESS = "success";
    private final String TAG_AUDIO_ARRAY = "audios";
    private final String AUDIO_ID = "id";
    private final String AUDIO_TITLE = "title";
    private final String AUDIO_URL = "url";
    private final String URL_GET_ALL_AUDIOS = Audio.IP+"hecsdldpt19/android_connect/get_all_audios.php";

    private ListView listView;
    private ProgressBar progressBar;
    private ArrayList<Audio> listAudios = new ArrayList<>();
    private MyAdapter adapter;
    private MainActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress);
        adapter = new MyAdapter(this.getActivity(), listAudios);
        listView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoadAllAudios loadAllAudios = new LoadAllAudios();
        loadAllAudios.execute();
    }

    @Override
    public void playModeChange(boolean isOnline) {
        if (!isOnline) {
            for (int i = 0; i < listAudios.size(); i++) {
                listAudios.get(i).isPlaying = false;
            }
            adapter.updateUI();
        }
    }

    @Override
    public void downLoadFinish() {

    }

    class LoadAllAudios extends AsyncTask {
        private HttpURLConnection connection;
        private BufferedReader bufferedReader;

        @Override
        protected Object doInBackground(Object[] params) {

            try {
                URL url = new URL(URL_GET_ALL_AUDIOS);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }

                String fromSever = stringBuilder.toString();
                //----
                JSONObject jsonObjectFromSever = new JSONObject(fromSever);
                int success = jsonObjectFromSever.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray jsonArray = jsonObjectFromSever.getJSONArray(TAG_AUDIO_ARRAY);
                    for (int i = 0; i <  jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Audio audio = new Audio();
                        audio.id = jsonObject.getInt(AUDIO_ID);
                        audio.title = jsonObject.getString(AUDIO_TITLE);
                        audio.url = jsonObject.getString(AUDIO_URL);
                        audio.number = i + 1;
                        listAudios.add(audio);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) connection.disconnect();
                try {
                    if (bufferedReader != null) bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressBar.setVisibility(View.GONE);
            adapter.updateUI();

        }
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
                myViewHolder.btnDownload = (LinearLayout) convertView.findViewById(R.id.btn_sub_item);
                myViewHolder.itemClick = (LinearLayout) convertView.findViewById(R.id.btn_item);
                myViewHolder.iv_Playing = (ImageView) convertView.findViewById(R.id.iv_playing);
                myViewHolder.tv_Number = (TextView) convertView.findViewById(R.id.tv_number);
                myViewHolder.tv_Title = (TextView) convertView.findViewById(R.id.tv_title);

                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }
            myViewHolder.tv_Title.setText(listAudios.get(position).title);
            myViewHolder.tv_Number.setText(listAudios.get(position).number + "");
            if (listAudios.get(position).isPlaying) {
                myViewHolder.tv_Number.setVisibility(View.GONE);
                myViewHolder.iv_Playing.setVisibility(View.VISIBLE);
            } else {
                myViewHolder.tv_Number.setVisibility(View.VISIBLE);
                myViewHolder.iv_Playing.setVisibility(View.GONE);
            }
            myViewHolder.btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!DataSource.checkFileExists(listAudios.get(position))) {
                        DownloadAudio downloadAudio = new DownloadAudio(activity, listAudios.get(position));
                        downloadAudio.execute();
                    } else {
                        activity.makeNotification("Bài này đã được tải rồi !");
                    }
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
                    activity.getPlayerController().playOnline(audios.get(position));
                    activity.changeModePlay(true);
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
            TextView tv_Number;
            LinearLayout btnDownload;
            LinearLayout itemClick;
        }
    }

}
