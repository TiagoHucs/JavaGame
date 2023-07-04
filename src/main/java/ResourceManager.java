import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private Map<String, BufferedImage> images;

    private final static ResourceManager instance = new ResourceManager();

    private ResourceManager() {
        this.images = new HashMap<String, BufferedImage>();
    }

    public static ResourceManager get() {
        return instance;
    }

    public synchronized BufferedImage getImage(String resourceName) throws IOException {

        if (images.containsKey(resourceName)) {
            return images.get(resourceName);
        }

        images.put(resourceName, ImageIO.read(getClass().getResourceAsStream(resourceName)));

        return getImage(resourceName);
    }
    public synchronized Clip getAudio(String resourceName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream(resourceName)));
        return clip;
    }

}
