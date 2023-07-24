package utilities;

import lombok.SneakyThrows;

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

        images.put(resourceName, uploadImageToGPU(resourceName));

        return getImage(resourceName);
    }

    private synchronized BufferedImage uploadImageToGPU(String resourceName) throws IOException {

        if (images.containsKey(resourceName)) {
            return images.get(resourceName);
        }

        BufferedImage image = ImageIO.read(getResource(resourceName));
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        BufferedImage convertedImage = gc.createCompatibleImage(image.getWidth(),
                image.getHeight(),
                image.getTransparency());
        Graphics2D g2d = convertedImage.createGraphics();
        g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g2d.dispose();

        images.put(resourceName, convertedImage);

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
