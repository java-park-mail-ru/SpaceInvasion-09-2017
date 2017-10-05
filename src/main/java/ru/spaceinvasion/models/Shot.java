package ru.spaceinvasion.models;

/**
 * Created by egor on 01.09.17.
 */
public class Shot extends Sprite {
    private int speed;
    private double dirrection;
    private int accelerate;
    private int leftDistance;

    public Shot(Coordinates coordinates0, int distanceOfMovement,
                      double dir, int speed0, int acc) {
        Coordinates coordinates;
        leftDistance = distanceOfMovement;
        speed = speed0;
        this.accelerate = acc;
        this.dirrection = dir;
    }

    @Override
    public void update() {
        final int speedX = (int) (speed * Math.cos(dirrection));
        final int speedY = (int) (speed * Math.sin(dirrection));
        speed += accelerate;
        getCoordinates().x += speedX;
        getCoordinates().y += speedY;
        leftDistance -= speed;
        if (speed == 0 || leftDistance == 0) {
            this.remove();
        }

    }

}
