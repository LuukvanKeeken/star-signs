/**
 * The Main Class, containing the main method.
 */
public class Main {

    public static void main(String[] args){
        int maximumDistanceThreshold = 400;
        int minimumDistanceThreshold = 20;
        double pullingFactor = 0.003;
        double pushingFactor = 1;
        StarSet starSet = new StarSet(maximumDistanceThreshold, minimumDistanceThreshold,
                pullingFactor, pushingFactor);
        new StarSetFrame(starSet, maximumDistanceThreshold);
        try{
            starSet.moveStars();
        } catch (Exception e){
            System.out.println("Couldn't pause program");
        }

    }

}
