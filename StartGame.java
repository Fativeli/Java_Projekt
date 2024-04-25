import javax.swing.*;

public class StartGame {



    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                new StartWindow();

            }
        });

    }



}
