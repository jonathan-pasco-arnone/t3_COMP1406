import java.util.ArrayList;
public class User {
    private String     userName;
    private boolean    online;
    private ArrayList<Song> songlist;

    public User()  { this(""); }

    public User(String u)  {
        userName = u;
        online = false;
        songlist = new ArrayList<Song>();
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
        if (s.getOwner() == null) {
            s.setOwner(this);
        }
        songlist.add(new Song(s.getTitle(), s.getArtist(), s.getMinutes(), s.getSeconds(), this));

    }

    public ArrayList<String> requestCompleteSonglist(MusicExchangeCenter m) {
        ArrayList<String> allSongs = new ArrayList<String>();
        for (Song track : m.allAvailableSongs()) {
            String text = (allSongs.size() - 1) + ". " + String.format("%-40b", track.getTitle())
                    + String.format("%-15", track.getArtist()) + track.getMinutes()
                    + ":" + String.format("%02d", track.getSeconds()) + "  " + track.getOwner();
            allSongs.add(text);
        }
        return allSongs;
    }

    public ArrayList<String> requestSonglistByArtist(MusicExchangeCenter m, String artist) {
        ArrayList<String> allSongs = new ArrayList<String>();
        for (Song track : m.availableSongsByArtist(artist)) {
            String text = (allSongs.size() - 1) + ". " + String.format("%-40b", track.getTitle())
                    + String.format("%-15", track.getArtist()) + track.getMinutes()
                    + ":" + String.format("%02d", track.getSeconds()) + "  " + track.getOwner();
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
}
