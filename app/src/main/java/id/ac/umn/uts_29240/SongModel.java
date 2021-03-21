package id.ac.umn.uts_29240;

import java.io.Serializable;

public class SongModel implements Serializable {
    private String title;
    private String songURI;
    private String duration;
    public SongModel(String title, String duration,
                       String songURI){
        this.title = title;
        this.songURI = songURI;
        this.duration = duration;
    }

    public String getTitle() { return this.title; }
    public String getSongURI() { return this.songURI; }
    public String getDuration() {return this.duration;}

    public String getDurationFormatted(){
        String res = "";
        int second = Integer.parseInt(duration)/1000;
        int minute = second/60;
        second = second%60;
        res = String.valueOf(second) + "s";
        if(minute > 0){
            res = String.valueOf(minute) + "m " + res;
        }
        return res;
    }

    public void setTitle(String title){ this.title = title; }
    public void setsongURI(String songURI) {
        this.songURI = songURI;
    }
    public void setDuration(String duration){
        this.duration = duration;
    }


}
