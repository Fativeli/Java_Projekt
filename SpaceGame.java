import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class SpaceGame extends JPanel implements ActionListener {

    private Sequencer sequencer;


    private int Punktestand = 0;


    private Image spaceShip;
    private Image star;
    private int spaceWidth, spaceHeight;

    boolean isStar = true ;


    private Timer timerStar;
    private int shipX, shipY;
    private int starX, starY;
    private int shipWidth = 60, shipHeight = 60;
    private int starWidth = 50, starHeight = 50;

    private int speed = 10;
    private int delay = 40;

    private boolean isLeftPressed, isRightPressed, isUpPressed, isDownPressed;

    private Timer timer = new Timer(delay,this);

    int maxX = 800 - 50;
    int minX = 20;
    int rangeX = maxX - minX + 1;


    int maxY = 800 - 50;
    int minY = 20;
    int rangeY = maxY - minY + 1;


    public SpaceGame(Image i,int sW, int sH) {

        JTextArea gameManuel = new JTextArea();

        gameManuel.setFont(new Font("Arial", Font.ITALIC, 20));
        gameManuel.setForeground(Color.RED);
        gameManuel.setBackground(Color.BLACK);

        gameManuel.setMargin(new Insets(30, 30, 30, 30));

        gameManuel.setText("    STAR     COLLECTOR    " );
        gameManuel.setEditable(false);

        this.add(gameManuel, BorderLayout.NORTH);




        spaceShip = i;
        spaceWidth = sW;
        spaceHeight = sH;

        try {
            star = ImageIO.read(new File("star.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        starX = 700;
        starY = 700;
        shipX = 100;
        shipY = 200;

        setBackground(Color.BLACK);



        this.requestFocusInWindow();
        this.setFocusable(true);

        timer.start();


        fly();

        timerStar= new Timer(4000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                    isStar = true;
                    starX = randomStarX();
                    starY = randomStarY();
                    repaint();

            }
        });
        timerStar.start();



    }

    private int randomStarX(){return(int) (Math.random() * rangeX) + 1;}

    private int randomStarY(){return(int) (Math.random() * rangeY) + 1;}



    private void fly(){




        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                int action = e.getKeyCode();

                switch(action){
                    case KeyEvent.VK_LEFT:
                    isLeftPressed=true;
                    break;
                    case KeyEvent.VK_RIGHT:
                    isRightPressed=true;
                    break;
                    case KeyEvent.VK_UP:
                        isUpPressed=true;
                        break;
                    case KeyEvent.VK_DOWN:
                        isDownPressed=true;
                        break;

                }






            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                int action = e.getKeyCode();

                switch(action){
                    case KeyEvent.VK_LEFT:
                        isLeftPressed=false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        isRightPressed=false;
                        break;
                    case KeyEvent.VK_UP:
                        isUpPressed=false;
                        break;
                    case KeyEvent.VK_DOWN:
                        isDownPressed=false;
                        break;
                }

            }

        });

    }
    int i;
    private void setStartMusicNow() {
        try {
            var synthesizer = MidiSystem.getSynthesizer();
            synthesizer.loadAllInstruments(synthesizer.getDefaultSoundbank());
            sequencer = MidiSystem.getSequencer();
            var sequence = MidiSystem.getSequence(new File("starwars3.mid"));
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









    private boolean collision (){

        return  shipX < starX + starWidth &&
                shipY < starY + starHeight &&
                starX < shipX + shipWidth &&
                starY < shipY + shipHeight;
    }


    @Override
    public void paintComponent (Graphics g){
        super.paintComponent(g);

        g.drawImage(spaceShip,shipX,shipY,shipWidth,shipHeight,this);


        if(isStar) {
            g.drawImage(star, starX, starY, starWidth, starHeight, this);
            //g.setColor(Color.WHITE);
            //g.drawRect(starX,starY,starWidth,starHeight);
        }


        g.setColor(Color.WHITE);
        g.drawString("Collected Stars:  " + Punktestand + "/10",100,100);

        //g.drawRect(shipX, shipY, shipWidth, shipHeight);


    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if(Punktestand <= 9) {

            if(collision()){

                if(isStar) {
                    Punktestand++;
                }

                isStar = false;

                repaint();




            }
        }  else {
            System.exit(0);
        }



        if(i<1) {
            setStartMusicNow();
            i++;
        }

        if(isLeftPressed){
            shipX -= speed;
        }

        if(isRightPressed){
            shipX += speed;
        }

        if(isUpPressed){
            shipY -= speed;
        }

        if(isDownPressed){
            shipY += speed;
        }

        if(shipX > spaceWidth){
            shipX = 0;
        }

        if(shipX< 0){
            shipX = spaceWidth;

        }

        if(shipY < 0){
            shipY = spaceHeight;
        }

        if(shipY > spaceHeight){
            shipY=0;
        }

repaint();


        }

    }

