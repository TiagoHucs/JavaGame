package utilities;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private Map<String, URL> resources;
    private Map<String, BufferedImage> images;
    private final static ResourceManager instance = new ResourceManager();
    private ResourceManager() {
        this.images = new HashMap<String, BufferedImage>();
        this.resources = new HashMap<String, URL>();
    }
    public static ResourceManager get() {
        return instance;
    }
    public synchronized BufferedImage getImage(String resourceName) throws IOException {

        if (images.containsKey(resourceName)) {
            return images.get(resourceName);
        }

        images.put(resourceName, ImageIO.read(getResource(resourceName)));

        return getImage(resourceName);
    }

    public synchronized BufferedImage getImage(String resourceName, int w, int h) throws IOException {

        if (images.containsKey(resourceName)) {
            return images.get(resourceName);
        }

        BufferedImage bufferedImage = ImageIO.read(getResource(resourceName));
        BufferedImage bufferedImageResized = new BufferedImage(w, h, bufferedImage.getType());
        Graphics2D graphics2D = bufferedImageResized.createGraphics();
        graphics2D.drawImage(bufferedImage, 0, 0, w, h, null);
        graphics2D.dispose();

        images.put(resourceName, bufferedImageResized);

        return getImage(resourceName);
    }
    public synchronized Clip getAudio(String resourceName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(getResource(resourceName)));
        return clip;
    }

    private URL getResource(String resourceName) {
        if (resources.containsKey(resourceName)) {
            return resources.get(resourceName);
        }
        resources.put(resourceName, getClass().getResource(resourceName));
        return getResource(resourceName);
    }

    public void loadResources(String ...resources) {
        for (String resource : resources) {
            getResource(resource);
        }
    }
}
