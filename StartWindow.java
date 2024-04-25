import org.w3c.dom.ls.LSOutput;

import javax.imageio.ImageIO;
import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class StartWindow extends JFrame implements ActionListener {





    private int width = 800;
    private int height = 600;

    private JButton startGame = null;
    private JButton startMusic = null;

    private JButton Exit = null;

    private Sequencer sequencer;

    private boolean isMusic = false;

    public StartWindow() {
        super("STAR COLLECTOR");

        this.setSize(width, height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);

        System.out.println("Bildschirmbreite" + screenSize.width);

        setResizable(false);





        //UI
        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new FlowLayout());


        panelNorth.setBackground(Color.BLACK);
        startGame = new JButton();
        //startGame.setBackground(Color.GRAY);
        //startGame.setBorderPainted(false);
        //startGame.setOpaque(true);
        startGame.setText("<html><h2 style= 'color:red'>StartGame</h2></html>");
        panelNorth.add(startGame);


        startMusic = new JButton();
        startMusic.setText("<html><h2 style= 'color:red'>StartMusic</h2></html>");
        panelNorth.add(startMusic);

        Exit = new JButton();
        Exit.setText("<html><h2 style= 'color:red'>Exit</h2></html>");
        panelNorth.add(Exit);


        this.setLayout(new BorderLayout());

        this.add(panelNorth, BorderLayout.CENTER);

       /* JTextArea gameManuel = new JTextArea();

        gameManuel.setFont(new Font("Arial", Font.ITALIC, 40));
        gameManuel.setForeground(Color.RED);
        gameManuel.setBackground(Color.CYAN);

        gameManuel.setMargin(new Insets(30, 30, 30, 30));

        gameManuel.setText("             YOU CAN´T PLAY ME");
        gameManuel.setEditable(false);


        this.add(gameManuel, BorderLayout.NORTH);*/




        setVisible(true);

        startGame.addActionListener(this);
        startMusic.addActionListener(this);
        Exit.addActionListener(this);
    }

    private void setStartMusicNow() {
        try {
            var synthesizer = MidiSystem.getSynthesizer();
            synthesizer.loadAllInstruments(synthesizer.getDefaultSoundbank());
            sequencer = MidiSystem.getSequencer();
            var sequence = MidiSystem.getSequence(new File("starwars1.mid"));
            sequencer.open();
            sequencer.setSequence(sequence);
            sequencer.setTempoFactor(1f);
            sequencer.start();


        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    int maxX = width - 100;
    int minX = 20;
    int rangeX = maxX - minX + 1;


    int maxY = height - 150;
    int minY = 50;
    int rangeY = maxY - minY + 1;


    private int giveXrand(){return(int) (Math.random() * rangeX) + 1;}

    private int giveYrand(){return(int) (Math.random() * rangeY) + 1;}

    @Override
    public void actionPerformed(ActionEvent e){

        if  (e.getSource() == startGame) {

            startGame.setLocation(giveXrand(),giveYrand());

            System.out.println(giveXrand());
            System.out.println(giveYrand());

        } else if (e.getSource() == Exit) {

            try {
                new StartGameWindow(ImageIO.read(new File("UFO.png")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


        } else if (e.getSource() == startMusic) {


            if(isMusic==false) {
                setStartMusicNow();
                startMusic.setText("<html><h2 style= 'color:red'>StopMusic</h2></html>");
                isMusic = true;
            }else {
                isMusic=false;
                startMusic.setText("<html><h2 style= 'color:red'>StartMusic</h2></html>");
                sequencer.stop();
            }

        }
    }



}
