package game;

import utilities.ResourceManager;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SoundManager {
    private static SoundManager instance;
    private float globalVolume = 1.0f;
    private List<Clip> sounds = new LinkedList<Clip>();
    private SoundManager() {

    }

    public static synchronized SoundManager get() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    private Clip loadClip(String filename) {
        try {
            Clip sound = ResourceManager.get().getAudio(filename);
            setVolume(sound, globalVolume);
            sounds.add(sound);
            return sound;
        } catch (Exception ex) {
            System.err.println("Erro ao carregar o som = " + filename);
            ex.printStackTrace();
        }
        return null;
    }

    public Clip playSound(String filename) {

        Clip sound = loadClip(filename);

        if (Objects.nonNull(sound)) {
            sound.start();
        }

        return sound;
    }

    public Clip playMusic(String filename) {

        Clip sound = loadClip(filename);

        if (Objects.nonNull(sound)) {
            sound.start();
            sound.loop(Clip.LOOP_CONTINUOUSLY);
        }

        return sound;
    }

    public void checkSounds(float globalVolume) {

        this.globalVolume = globalVolume;

        List<Clip> destroySounds = new LinkedList<Clip>();

        // Thread-safe, fazendo uma cópia antes
        for (Clip sound: new LinkedList<Clip>(sounds)) {
            if (sound.isRunning()) {
                setVolume(sound, globalVolume);
                continue;
            } else {
                sound.close();
                destroySounds.add(sound);
            }
        }

        sounds.removeAll(destroySounds);
    }

    private static void setVolume(Clip sound, float volume) {

        if (volume < 0f || volume > 1f) {
            throw new IllegalArgumentException("Volume informado nao esta no range valido: " + volume);
        }

        FloatControl gainControl = (FloatControl) sound.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }

}
