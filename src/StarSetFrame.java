import javax.swing.*;
import java.awt.*;

/**
 * The StarSetFrame class.
 */
public class StarSetFrame extends JFrame {

    /**
     * The constructor for the StarSetFrame.
     * @param starSet The corresponding starSet.
     */
    public StarSetFrame(StarSet starSet, int distanceThreshold){
        super("Star set");
        StarSetPanel panel = new StarSetPanel(starSet, distanceThreshold);
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1200,850));
        setMinimumSize(new Dimension(830, 500));
        pack();
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

}
