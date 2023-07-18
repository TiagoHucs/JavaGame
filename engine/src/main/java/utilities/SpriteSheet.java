package utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {

    private BufferedImage image;
    private List<BufferedImage> imageList;

    public SpriteSheet(String path,int columns, int rows){
        imageList = new ArrayList<>(columns * rows);

        try {
            image = ImageIO.read(getClass().getResource(path));
            System.out.println("Loading spritesheet");
        } catch (IOException | IllegalArgumentException e){
            System.err.println("Error loading spritesheet");
        }

        int unityWidth = image.getWidth() / columns;
        int unityHeight = image.getHeight() / rows;

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                imageList.add(image.getSubimage(
                        x == 0 ? 0 : x * unityWidth,
                        y == 0 ? 0 : y * unityHeight,
                        unityWidth,
                        unityHeight));
            }
        }


    }

    public BufferedImage getSprite(int n){
        return imageList.get(n);
    }

}
