import java.util.ArrayList;
import java.util.Random;

/**
 * Class that contains the functionality for the Star objects.
 */
public class Star {
    private ArrayList<StarLine> lines;
    private double xPosition;
    private double yPosition;
    private final double speed;
    private double direction;

    /**
     * Constructor for the Star class. The Star gets a
     * random position on width 0 - 1180 and height 0 - 820.
     * The Star object also gets a random starting direction,
     * which is an angle between 0 and 2pi.
     */
    public Star(){
        this.lines = new ArrayList<>();
        this.xPosition = 1180 * Math.random();
        this.yPosition = 820 * Math.random();
        this.speed = 0.5;
        this.direction = Math.random() * 2*Math.PI;
    }

    /**
     * Method that adds a new StarLine to this Star's
     * ArrayList of StarLines.
     * @param line The new StarLine
     */
    public void addLine(StarLine line){
        this.lines.add(line);
    }

    /**
     * Returns the x-coordinate of the star.
     * @return the x-coordinate of the star.
     */
    public double getxPosition(){
        return this.xPosition;
    }

    /**
     * Returns the y-coordinate of the star.
     * @return the y-coordinate of the star.
     */
    public double getyPosition(){
        return this.yPosition;
    }

    /**
     * Method that calculates the new direction when a Star
     * bumps into a wall, depending on the direction it had before.
     * This method is called specifically when the Star object bumps
     * into the left or right wall.
     */
    private void updateDirectionX(){
        if (0 <= this.direction && this.direction <= Math.PI){
            this.direction = Math.PI - this.direction;
        } else if (Math.PI < this.direction && this.direction <= 2*Math.PI){
            this.direction = 3*Math.PI - this.direction;
        }
    }

    /**
     * Method that calculates the new direction when a Star
     * bumps into a wall. This method is called specifically when
     * the Star object bumps into the top or bottom wall.
     */
    private void updateDirectionY(){
        this.direction = 2*Math.PI - this.direction;
    }

    /**
     * Method that updates the x-coordinate of the Star.
     */
    private void updateXPosition(){
        double newValue = this.xPosition + this.speed*Math.cos(this.direction);
        if (newValue <= 0 || newValue >= 1180){
            updateDirectionX();
            newValue = this.xPosition + this.speed*Math.cos(this.direction);
        }
        this.xPosition = newValue;
    }

    /**
     * Method that updates the y-coordinate of the Star.
     */
    private void updateYPosition(){
        double newValue = this.yPosition+ this.speed*Math.sin(this.direction);
        if (newValue <= 0 || newValue >= 820){
            updateDirectionY();
            newValue = this.yPosition+ this.speed*Math.sin(this.direction);
        }

        this.yPosition = newValue;
    }

    /**
     * Method that updates the x- and y-coordinates of the Star.
     */
    public void updatePosition(){
        updateXPosition();
        updateYPosition();
    }

}
