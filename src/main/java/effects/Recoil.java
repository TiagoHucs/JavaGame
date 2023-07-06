package effects;

import entities.Ator;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Recoil {
    float size = 0.0f;
    public void update(Ator ator) {
        size = Math.max(0.0f, size - 1.0f);
        recoil(ator);
    }

    private void recoil(Ator ator) {
        ator.setOffSetY((int) size);
    }

}
