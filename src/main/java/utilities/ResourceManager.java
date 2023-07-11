package utilities;

import javax.imageio.ImageIO;
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

    public synchronized Font getFont() {
        if(font == null){
            File fontFile = new File("src/main/resources/fonts/PressStart2P-Regular.ttf");
            try {
                font =  Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 16);
            } catch (FontFormatException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
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
}
