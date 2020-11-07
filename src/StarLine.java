import java.util.ArrayList;

/**
 * Class for the StarLine.
 */
public class StarLine {
    private final ArrayList<Star> stars;

    /**
     * The StarLine constructor.
     * @param star1 The first Star object this line is connected to.
     * @param star2 The second Star object this line is connected to.
     */
    public StarLine(Star star1, Star star2){
        this.stars = new ArrayList<>();
        this.stars.add(star1);
        this.stars.add(star2);
    }

    /**
     * Returns the arraylist containing the stars this line
     * connects.
     * @return The arraylist of stars.
     */
    public ArrayList<Star> getStars(){
        return this.stars;
    }
}
