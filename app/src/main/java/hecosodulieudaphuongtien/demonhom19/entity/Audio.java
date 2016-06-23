package hecosodulieudaphuongtien.demonhom19.entity;

/**
 * Created by admin on 3/26/2016.
 */
public class Audio {
//    public static String IP = "http://172.20.10.2/";
    public static String IP = "http://192.168.56.1/";


    public int id;
    public String title;
    public String url;
    public boolean isPlaying = false;
    public int number;

    public String getURL() {
        return IP + url;
    }

    ;
}
