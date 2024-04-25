import javax.swing.*;
import java.awt.*;

public class StartGameWindow extends JFrame {

    public int width=800, height=800;

    public StartGameWindow(Image image) {
        super("STAR COLLECTOR");


        this.setSize(width, height);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - this.width) / 2, (screenSize.height - this.height) / 2);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        setResizable(false);
        setVisible(true);

        int titleHeight = this.getInsets().top;
        int borderRight = this.getInsets().right;
        int borderLeft = this.getInsets().left;
        int borderBottom = this.getInsets().bottom;

        SpaceGame sp = new SpaceGame(image, this.width-borderLeft-borderRight,this.height - titleHeight - borderBottom );

        add(sp);


    }

}
