package invaders;

import engine.GameWindow;

import java.awt.*;

public class Invaders {
    public static void main(String[] args) {
        new GameWindow(getViewPort(), new InvadersGameComponent()).play();
    }

    public static Dimension getViewPort() {

        // Numeros mágicos baseados no tamanho da imagem do jogador
        Dimension playerSize = new Dimension(48, 58);

        // Quantidade de tiles por eixo
        Dimension tileCount = new Dimension(16, 8);

        return new Dimension(playerSize.width * tileCount.width, playerSize.height * tileCount.height);
    }
}
