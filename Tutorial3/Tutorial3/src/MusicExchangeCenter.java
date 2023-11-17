import java.util.ArrayList;
public class MusicExchangeCenter {
    private ArrayList<User> users;

    public MusicExchangeCenter() {
        users = new ArrayList<User>();
    }
    public ArrayList<User> onlineUsers() {
        ArrayList<User> onlineList = new ArrayList<User>();
        for (User person : users) {
            if (person.isOnline()) {
                onlineList.add(person);
            }
        }
        return onlineList;
    }

    public Song getSong(String title, String ownerName) {
        for (Song track : allAvailableSongs()) {
            if (track.getOwner().equals(ownerName)) {
                return track;
            }
        }
        return null;
    }
    public ArrayList<Song> allAvailableSongs() {
        ArrayList<Song> allSongs = new ArrayList<Song>();
        for (User person : onlineUsers()) {
            for (Song track : person.getSonglist()) {
                allSongs.add(track);
            }
        }
        return allSongs;
    }

    public String toString() {
        return "Music Exchange Center (" + onlineUsers().size() + " users online, "
                +  allAvailableSongs().size() + " songs available)";
    }

    public User userWithName(String s) {
        for (User person : users) {
            if (person.getUserName().equals(s)) {
                return person;
            }
        }
        return null;
    }

    public void registerUser(User x) {
        if (userWithName(x.getUserName()) == null) {
            users.add(x);
        }
    }

    public ArrayList<Song> availableSongsByArtist(String artist) {
        ArrayList<Song> songsByArtist = new ArrayList<Song>();
        for (Song track : allAvailableSongs()) {
            if (track.getArtist().equals(artist)) {
                songsByArtist.add(track);
            }
        }
        return songsByArtist;
    }
}
