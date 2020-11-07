/**
 * The Main Class, containing the main method.
 */
public class Main {

    public static void main(String[] args){
        StarSet starSet = new StarSet();
        new StarSetFrame(starSet);
        try{
            starSet.moveStars();
        } catch (Exception e){
            System.out.println("Couldn't pause program");
        }

    }

}
