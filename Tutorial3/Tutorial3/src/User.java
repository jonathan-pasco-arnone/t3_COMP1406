/* User class file program

Created by: Jonathan Pasco-Arnone
Created on: November 2023

*/
import java.util.ArrayList;
public class User {
    private String     userName;
    private boolean    online;
    private ArrayList<Song> songlist;

    public User()  { this(""); }

    public User(String u)  {
        userName = u;
        online = false;
        songlist = new ArrayList<>();
    }

    public ArrayList<Song> getSonglist() { return songlist; }
    public String getUserName() { return userName; }
    public boolean isOnline() { return online; }

    public String toString()  {
        String s = "" + userName + ": " + songlist.size() + " songs (";
        if (!online) s += "not ";
        return s + "online)";
    }

    public void addSong(Song s) {
        songlist.add(new Song(s.getTitle(), s.getArtist(), s.getMinutes(), s.getSeconds(), this));
    }

    public ArrayList<String> requestCompleteSonglist(MusicExchangeCenter m) {
        ArrayList<String> allSongs = new ArrayList<>();
        allSongs.add(String.format("%9s", "TITLE") + String.format("%41s", "ARTIST")
                + String.format("%18s", "TIME") + String.format("%7s", "OWNER") + "\n");

        for (Song track : m.allAvailableSongs()) {
            String text = String.format("%-2d", allSongs.size()) + ". " + String.format("%-40s", track.getTitle())
                    + String.format("%-20s", track.getArtist()) + track.getMinutes()
                    + ":" + String.format("%02d", track.getSeconds()) + "  " + (track.getOwner()).getUserName();
            allSongs.add(text);
        }
        return allSongs;
    }

    public ArrayList<String> requestSonglistByArtist(MusicExchangeCenter m, String artist) {
        ArrayList<String> allSongs = new ArrayList<>();
        allSongs.add(String.format("%9s", "TITLE") + String.format("%41s", "ARTIST")
                + String.format("%18s", "TIME") + String.format("%7s", "OWNER") + "\n");

        for (Song track : m.availableSongsByArtist(artist)) {
            String text = String.format("%-2d", allSongs.size()) + ". " + String.format("%-40s", track.getTitle())
                    + String.format("%-20s", track.getArtist()) + track.getMinutes()
                    + ":" + String.format("%02d", track.getSeconds()) + "  " + (track.getOwner()).getUserName();
            allSongs.add(text);
        }
        return allSongs;
    }

    public int totalSongTime() {
        int totalTime = 0;
        for (Song track : songlist) {
            totalTime += track.getDuration();
        }
        return totalTime;
    }

    public void register(MusicExchangeCenter m) {
        m.registerUser(this);
    }

    public void logon() {
        online = true;
    }

    public void logoff() {
        online = false;
    }

    public void downloadSong(MusicExchangeCenter m, String title, String ownerName) {
        Song track = m.getSong(title, ownerName);
        if (track != null) {
            this.addSong(track);
        }
    }
}