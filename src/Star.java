import java.util.ArrayList;

/**
 * Class that contains the functionality for the Star objects.
 */
public class Star {
    private ArrayList<StarLine> lines;
    private double xPosition;
    private double yPosition;
    private final double speed;
    private double direction;
    private final StarSet starSet;
    private final int distanceThreshold;
    private final double pullingFactor;

    /**
     * Constructor for the Star class. The Star gets a
     * random position on width 0 - 1180 and height 0 - 820.
     * The Star object also gets a random starting direction,
     * which is an angle between 0 and 2pi.
     */
    public Star(StarSet starSet, int distanceThreshold, double pullingFactor){
        this.pullingFactor = pullingFactor;
        this.distanceThreshold = distanceThreshold;
        this.starSet = starSet;
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
     * Method that calculates the closeness between two points,
     * on a scale from 0 to 1. If the distance is bigger than the distance threshold,
     * the closeness is set to 0, otherwise the closeness is 1 minus
     * the distance divided by the distance threshold.
     * @param firstStar The first Star object.
     * @param secondStar The second Star object.
     * @return The calculated closeness value.
     */
    private float getCloseness(Star firstStar, Star secondStar){
        float distance = (float)Math.sqrt(Math.pow(firstStar.getxPosition() - secondStar.getxPosition(), 2) +
                Math.pow(firstStar.getyPosition() - secondStar.getyPosition(), 2));
        float closeness;
        if (distance >= this.distanceThreshold){
            closeness = 0;
        } else {
            closeness = 1 - distance/this.distanceThreshold;
        }
        return closeness;
    }

    /**
     * Method that calculates how the direction of one star should
     * be influenced based on the location and distance of other
     * stars.
     */
    private void positionPullingDirectionInfluence(){
        /*Calculate the unit vector of the current direction, and
            initialise the sum of all difference vectors.
         */
        double[] currentDirUnitVec = {Math.cos(this.direction), Math.sin(this.direction)};
        double[] influencingStarsDirVect = {0, 0};

        /*
            Calculate the closeness of each star to this star, and
            add to the summed vector multiplied with the closeness factor.
         */
        ArrayList<Star> stars = this.starSet.getStars();
        for (Star star : stars){
            double closeness = getCloseness(this, star);
            double differenceX = star.getxPosition() - this.getxPosition();
            double differenceY = star.getyPosition() - this.getyPosition();
            influencingStarsDirVect[0] += closeness*differenceX;
            influencingStarsDirVect[1] += closeness*differenceY;
        }

        /*
            Average the summed vector by the number of stars, and
            divide by the magnitude to make it a unit vector.
         */
        influencingStarsDirVect[0] /= stars.size();
        influencingStarsDirVect[1] /= stars.size();
        double magnitude = Math.sqrt(Math.pow(influencingStarsDirVect[0],2) +
                Math.pow(influencingStarsDirVect[1], 2));
        if (magnitude == 0){
            magnitude += 0.00001;
        }

        influencingStarsDirVect[0] /= magnitude;
        influencingStarsDirVect[1] /= magnitude;

        /*
            Adjust the current direction vector by adding the unit
            vector of the influencing stars, multiplied with the
            pulling factor.
         */
        currentDirUnitVec[0] += this.pullingFactor*influencingStarsDirVect[0];
        currentDirUnitVec[1] += this.pullingFactor*influencingStarsDirVect[1];
        if (currentDirUnitVec[0] == 0){
            currentDirUnitVec[0] += 0.00001;
        }
        double newDirection = Math.atan(currentDirUnitVec[1]/currentDirUnitVec[0]);
        if (currentDirUnitVec[0] < 0 || (currentDirUnitVec[0] < 0 && currentDirUnitVec[1] < 0)){
            newDirection += Math.PI;
        } else if (currentDirUnitVec[1] < 0){
            newDirection += 2*Math.PI;
        }
        this.direction = newDirection;
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
        positionPullingDirectionInfluence();
        updateXPosition();
        updateYPosition();
    }

}
