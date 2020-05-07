package movable;

import engine.GameEngine;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class tankbomb extends MovableObject{
        public tankbomb(GameEngine engine, double x, double y)
        {
            super(engine,x,y);
            try {
                this.img = ImageIO.read(new File("./images/bomb.png"));
            }catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }

    @Override
    public void loop() {

    }
}
