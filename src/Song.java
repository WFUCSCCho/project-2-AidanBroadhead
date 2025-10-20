/*******************************************************************
 ∗ @file: Song.java
 ∗ @description: This program implements a custom class for the songs in the csv file. This file includes private
                    fields, constructors, getters/setters, toString(), equals(), and compareTo().
 ∗ @author: Aidan Broadhead
 ∗ @date: October 21, 2025
 ********************************************************************/

public class Song implements Comparable<Song> {

    // private fields
    private String id;
    private String name;
    private double duration;
    private double energy;
    private int key;
    private double loudness;
    private int mode;
    private double speechiness;
    private double acousticness;
    private double instrumentalness;
    private double liveness;
    private double valence;
    private double tempo;
    private double danceability;

    // default constructor
    public Song() {
        this.id = "";
        this.name = "";
        this.duration = 0.0;
        this.energy = 0.0;
        this.key = 0;
        this.loudness = 0.0;
        this.mode = 0;
        this.speechiness = 0.0;
        this.acousticness = 0.0;
        this.instrumentalness = 0.0;
        this.liveness = 0.0;
        this.valence = 0.0;
        this.tempo = 0.0;
        this.danceability = 0.0;
    }

    // parametrized constructor
    public Song(String id, String name, double duration, double energy, int key, double loudness, int mode,
                double speechiness, double acousticness, double instrumentalness,
                double liveness, double valence, double tempo, double danceability) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.energy = energy;
        this.key = key;
        this.loudness = loudness;
        this.mode = mode;
        this.speechiness = speechiness;
        this.acousticness = acousticness;
        this.instrumentalness = instrumentalness;
        this.liveness = liveness;
        this.valence = valence;
        this.tempo = tempo;
        this.danceability = danceability;
    }

    // copy constructor
    public Song(Song copy) {
        this.id = copy.id;
        this.name = copy.name;
        this.duration = copy.duration;
        this.energy = copy.energy;
        this.key = copy.key;
        this.loudness = copy.loudness;
        this.mode = copy.mode;
        this.speechiness = copy.speechiness;
        this.acousticness = copy.acousticness;
        this.instrumentalness = copy.instrumentalness;
        this.liveness = copy.liveness;
        this.valence = copy.valence;
        this.tempo = copy.tempo;
        this.danceability = copy.danceability;
    }

    // getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getDuration() {
        return duration;
    }

    public double getEnergy() {
        return energy;
    }

    public int getKey() {
        return key;
    }

    public double getLoudness() {
        return loudness;
    }

    public int getMode() {
        return mode;
    }

    public double getSpeechiness() {
        return speechiness;
    }

    public double getAcousticness() {
        return acousticness;
    }

    public double getInstrumentalness() {
        return instrumentalness;
    }

    public double getLiveness() {
        return liveness;
    }

    public double getValence() {
        return valence;
    }

    public double getTempo() {
        return tempo;
    }

    public double getDanceability() {
        return danceability;
    }

    // setters
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setLoudness(double loudness) {
        this.loudness = loudness;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setSpeechiness(double speechiness) {
        this.speechiness = speechiness;
    }

    public void setAcousticness(double acousticness) {
        this.acousticness = acousticness;
    }

    public void setInstrumentalness(double instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    public void setLiveness(double liveness) {
        this.liveness = liveness;
    }

    public void setValence(double valence) {
        this.valence = valence;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    public void setDanceability(double danceability) {
        this.danceability = danceability;
    }

    // puts values of a song to string
    @Override
    public String toString() {

        return id + "," + name + "," + duration + "," + energy + "," + key + "," + loudness + ","
                + mode + "," + speechiness + "," + acousticness + "," + instrumentalness + ","
                + liveness + "," + valence + "," + tempo + "," + danceability;
    }

    // checks if two objects are equal
    @Override
    public boolean equals(Object obj) {

        // checks if objects are in same location
        if (this == obj) {
            return true;
        }

        // check object type
        if (!(obj instanceof Song)) {
            return false;
        }

        Song other = (Song) obj;

        // compares the id and name values for equality
        return this.id.equals(other.id) && this.name.equals(other.name);

    }

    // compares the name of two songs
    @Override
    public int compareTo(Song other) {

        return this.name.compareToIgnoreCase(other.name);
    }

}
