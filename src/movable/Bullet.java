package movable;

import engine.GameEngine;

public class Bullet extends MovableObject {

    public Bullet(GameEngine engine, double x, double y) {
        super(engine, x, y);
    }

    @Override
    public void loop() {

    }

    public boolean CollideWith(Tank tank) {
        return false;
    }
}
