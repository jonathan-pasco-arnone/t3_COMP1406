/* Music Center program

Created by: Jonathan Pasco-Arnone
Created on: November 2023

*/
import java.util.*;

public class MusicExchangeCenter {
    private ArrayList<User> users;
    private HashMap<String, Float> royalties = new HashMap<>();
    private ArrayList<Song> downloadedSongs = new ArrayList<>();
    public MusicExchangeCenter() {
        users = new ArrayList<>();
    }
    public ArrayList<User> onlineUsers() {
        ArrayList<User> onlineList = new ArrayList<>();
        for (User person : users) {
            if (person.isOnline()) {
                onlineList.add(person);
            }
        }
        return onlineList;
    }

    public ArrayList<Song> getDownloadedSongs() {
        return downloadedSongs;
    }
    public Song getSong(String title, String ownerName) {
        for (Song track : allAvailableSongs()) {
            if (track.getOwner().getUserName().equals(ownerName) && track.getTitle().equals(title)) {
                downloadedSongs.add(track);

                // Royalties
                if (royalties.containsKey(track.getArtist())) {
                    royalties.put(track.getArtist(), (float) 0.25 + royalties.get(track.getArtist()));
                } else {
                    royalties.put(track.getArtist(), (float) 0.25);
                }

                return track;
            }
        }
        return null;
    }
    public ArrayList<Song> allAvailableSongs() {
        ArrayList<Song> allSongs = new ArrayList<>();
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
        ArrayList<Song> songsByArtist = new ArrayList<>();
        for (Song track : allAvailableSongs()) {
            if (track.getArtist().equals(artist)) {
                songsByArtist.add(track);
            }
        }
        return songsByArtist;
    }

    public void displayRoyalties() {
        System.out.println("Amount  Artist\n---------------");
        for (String name : royalties.keySet()) {
            System.out.println(String.format("%-8s", "$" + String.format("%.2f", royalties.get(name))) + name);
        }
    }

    public TreeSet<Song> uniqueDownloads() {
        TreeSet<Song> orderedSongs = new TreeSet<>();
        Collections.sort(downloadedSongs);

        for (Song track : downloadedSongs) {
            if (!orderedSongs.contains(track)) {
                orderedSongs.add(track);
            }
        }

        return orderedSongs;
    }

    public ArrayList<Pair<Integer,Song>> songsByPopularity() {
        ArrayList<Pair<Integer,Song>> songList = new ArrayList<>();
        ArrayList<Pair<Song,Integer>> songViews = new ArrayList<>();
        for (Song track : downloadedSongs) {

            int counter = 0;
            boolean songAdded = false;
            for(Pair<Song,Integer> currentViews : songViews) {
                if (currentViews.getKey().getTitle().equals(track.getTitle())) {
                    // Grabs the current view count of a song and adds one to it
                    songViews.get(counter).setValue(songViews.get(counter).getValue() + 1);
                    songAdded = true;
                }
                counter++;
            }

            // If the song has not already been added to the list then it will be added
            if (!songAdded) {
                Pair<Song, Integer> newSong = new Pair<>(track, 1);
                songViews.add(newSong);
            }


        }

        // Move the pairs of song -> views to the list that wants views -> song
        for (Pair<Song, Integer> songIntegerPair : songViews) {
            Pair<Integer, Song> newPair = new Pair<>(songIntegerPair.getValue(), songIntegerPair.getKey());
            songList.add(newPair);
        }

        Collections.sort(songList, new Comparator<Pair<Integer, Song>>() {
            public int compare(Pair<Integer, Song> p1, Pair<Integer, Song> p2) {
                return p2.getKey() - p1.getKey();
            }
        });

        return songList;
    }
}
