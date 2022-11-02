package us.mcmagic.tron.handlers;

/**
 * Created by Marc on 2/8/16
 */
public enum AudioFile {
    LASER("laser"), DISC("discwars"), LIGHTCYCLE("lightcycle");

    private String fileName;

    AudioFile(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}