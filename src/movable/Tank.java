package movable;

import engine.GameEngine;
import settings.Settings;

public class Tank extends MovableObject{

    public Tank(GameEngine engine, double x, double y) {
        super(engine, x, y);
        r = Settings.defaultTankRadius;
    }

    @Override
    public void loop() {

    }
}
