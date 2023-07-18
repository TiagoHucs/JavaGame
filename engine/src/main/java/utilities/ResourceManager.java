package utilities;

import engine.GameWindow;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private final static ResourceManager instance = new ResourceManager();
    private final Map<String, URL> resources;
    private final Map<String, BufferedImage> images;
    private Font font;

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

    @SneakyThrows
    public synchronized Font getFont() {
        if (font == null) {
            try (InputStream stream = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf")) {
                font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(Font.PLAIN, 10.0f);
            }
        }
        return font;
    }

    private URL getResource(String resourceName) {
        if (resources.containsKey(resourceName)) {
            return resources.get(resourceName);
        }
        resources.put(resourceName, getClass().getResource(resourceName));
        return getResource(resourceName);
    }

    public void loadImages(String folder) {
        try {
            URL folderURL = getClass().getResource(folder);
            File file = new File(folderURL.toURI());
            File[] files = file.listFiles();

            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    getImage(folder + "/" + files[i].getName());
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

}
