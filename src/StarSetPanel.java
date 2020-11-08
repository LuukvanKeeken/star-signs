import javax.swing.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.awt.*;

/**
 * StarSetPanel class that is responsible for (re)painting
 * the lines and stars, with appropriate colours.
 */
public class StarSetPanel extends JPanel implements Observer {
    private final StarSet starSet;
    private final int distanceThreshold;

    /**
     * Constructor for the StarSetPanel.
     * @param starSet The corresponding starSet.
     */
    public StarSetPanel(StarSet starSet, int distanceThreshold){
        this.distanceThreshold = distanceThreshold;
        setBackground(new Color(0, 0, 0));
        setVisible(true);
        setOpaque(true);
        this.starSet = starSet;
        starSet.addObserver(this);
    }

    /**
     * Method that draws one star.
     * @param g Graphics object.
     * @param star Star object that is drawn.
     */
    private void drawStar(Graphics g, Star star){
        g.fillOval((int)star.getxPosition(), (int)star.getyPosition(), 10,10);
    }

    /**
     * Method that goes through all the stars in the star set
     * and draws them.
     * @param g Graphics object.
     */
    private void drawStars(Graphics g){
        for (Star star : starSet.getStars()){
            g.setColor(new Color(255, 255, 255));
            drawStar(g, star);
        }
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
     * Method that draws one line between two stars. The line's
     * colour is on a scale between black and white, depending
     * on the distance between the two points. If the distance
     * is very large, (which results in low closeness), the alpha
     * value denoting the opacity of the line will be 0.
     * @param g2d The graphics2d object.
     * @param line The line between two stars.
     */
    private void drawLine(Graphics2D g2d, StarLine line){
        ArrayList<Star> connectedStars = line.getStars();
        Star firstStar = connectedStars.get(0);
        Star secondStar = connectedStars.get(1);
        float closeness = getCloseness(firstStar, secondStar);
        int alpha = 255;
        if (closeness == 0){
            alpha = 0;
        }
        int rgbValue = (int)(255*closeness);
        Color color = new Color(rgbValue, rgbValue, rgbValue, alpha);
        g2d.setPaint(color);
        g2d.drawLine((int)firstStar.getxPosition() + 5, (int)firstStar.getyPosition() + 5,
                (int)secondStar.getxPosition() + 5, (int)secondStar.getyPosition() + 5);
    }

    /**
     * Method that goes through all lines in the star set
     * to draw them.
     * @param g Graphics object.
     */
    private void drawLines(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        for (StarLine line : starSet.getLines()){
            drawLine(g2d, line);
        }
    }

    /**
     * Method that is called when the panel should be repainted.
     * First the lines are drawn, then the stars are drawn.
     * @param g Graphics object.
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawLines(g);
        drawStars(g);
    }

    /**
     * Update method that is called when one of the panel's observed
     * objects is changed.
     * @param observable The observable object.
     * @param o Object
     */
    @Override
    public void update(Observable observable, Object o) { repaint(); }
}
