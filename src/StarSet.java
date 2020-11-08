import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * StarSet class.
 */
public class StarSet extends Observable implements Observer {
    private final ArrayList<Star> stars;
    private final ArrayList<StarLine> lines;
    private final int maximumDistanceThreshold;
    private final int minimumDistanceThreshold;
    private final double pullingFactor;
    private final double pushingFactor;

    /**
     * The StarSet constructor. 10 Stars are added.
     */
    public StarSet(int maximumDistanceThreshold, int minimumDistanceThreshold,
                   double pullingFactor, double pushingFactor){
        this.pushingFactor = pushingFactor;
        this.minimumDistanceThreshold = minimumDistanceThreshold;
        this.maximumDistanceThreshold = maximumDistanceThreshold;
        this.pullingFactor = pullingFactor;
        this.stars = new ArrayList<>();
        this.lines = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            addStar();
        }
    }

    /**
     * Method that updates the positions of the stars, and pauses for
     * 5 milliseconds after all of them are moved.
     * @throws InterruptedException Exception that is thrown when something
     * goes wrong with pausing the program.
     */
    public void moveStars() throws InterruptedException {
        while (true){
            for (Star star : this.stars){
                star.updatePosition();
            }
            update();
            java.util.concurrent.TimeUnit.MILLISECONDS.sleep(5);
        }
    }

    /**
     * Method that adds a Star to the StarSet, as well as
     * all the lines that connect the new Star to all the
     * existing stars.
     */
    private void addStar(){
        Star newStar = new Star(this, this.maximumDistanceThreshold, this.minimumDistanceThreshold,
                this.pullingFactor, this.pushingFactor);
        for (Star star : stars) {
            StarLine newLine = new StarLine(star, newStar);
            this.lines.add(newLine);
            newStar.addLine(newLine);
            star.addLine(newLine);
        }
        this.stars.add(newStar);
        update();
    }

    /**
     * Returns the arraylist of stars.
     * @return The arraylist of stars.
     */
    public ArrayList<Star> getStars(){
        return this.stars;
    }

    /**
     * Returns the arraylist of lines.
     * @return The arraylist of lines.
     */
    public ArrayList<StarLine> getLines(){
        return this.lines;
    }

    /**
     * Method that is called when one of the StarSet's observables is changed.
     * @param observable The observed object.
     * @param o Object
     */
    @Override
    public void update(Observable observable, Object o) {
        setChanged();
        notifyObservers();
    }

    /**
     * The update method that notifies the observers of the StarSet of a change.
     */
    public void update(){
        setChanged();
        notifyObservers();
    }
}
