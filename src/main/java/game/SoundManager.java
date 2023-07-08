package game;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private float globalVolume = 0.8f;
    private Clip[] sounds;
    private Map<String, Integer> soundIds;
    private Integer currentSoundId;
    private Integer currentMusicId;

    public void loadSounds(String folder) {
        try {
            URL folderURL = getClass().getResource(folder);
            File file = new File(folderURL.toURI());
            File[] files = file.listFiles();

            this.sounds = new Clip[files.length];
            this.soundIds = new HashMap<String, Integer>(files.length);

            for (int i = 0; i < files.length; i++) {

                AudioInputStream audio = AudioSystem.getAudioInputStream(files[i]);
                Clip clip = AudioSystem.getClip();
                clip.open(audio);

                setVolume(clip, globalVolume);

                this.sounds[i] = clip;
                this.soundIds.put(files[i].getName(), i);
            }

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    private void stopSound(Integer id) {
        if (id != null && sounds[id].isActive()) {
            sounds[id].stop();
        }
    }

    public boolean isPlaying(String filename) {
        Clip sound = sounds[soundIds.get(filename)];
        return sound.isActive();
    }

    public void playSound(String filename) {

        currentSoundId = soundIds.get(filename);

        if (sounds[currentSoundId].getMicrosecondPosition() > 0) {
            sounds[currentSoundId].setMicrosecondPosition(0);
        }

        sounds[currentSoundId].start();
    }

    public void playMusic(String filename) {

        stopSound(currentMusicId);

        currentMusicId = soundIds.get(filename);

        sounds[currentMusicId].setMicrosecondPosition(0);
        sounds[currentMusicId].loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void toogleMute(boolean mute) {
        for (Clip sound : sounds) {
            BooleanControl booleanControl = (BooleanControl) sound.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(mute);
        }
    }

    public void setGlobalVolume(float globalVolume) {
        this.globalVolume = globalVolume;

        for (Clip sound : sounds) {
            setVolume(sound, globalVolume);
        }
    }

    private void setVolume(Clip sound, float volume) {
        try {
            FloatControl gainControl = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        } catch (Exception exception) {
            exception.printStackTrace(System.err);
        }
    }

}
