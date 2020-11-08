/**
 * The Main Class, containing the main method.
 */
public class Main {

    public static void main(String[] args){
        int distanceThreshold = 400;
        double pullingFactor = 0.003;
        StarSet starSet = new StarSet(distanceThreshold, pullingFactor);
        new StarSetFrame(starSet, distanceThreshold);
        try{
            starSet.moveStars();
        } catch (Exception e){
            System.out.println("Couldn't pause program");
        }

    }

}
