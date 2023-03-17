import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE=320;
    private final int DOT_SIZE=16;
    private final int ALL_DOTS=400;
    private Image dot;
    private Image apple;
    private int appleX;
    private  int appleY;
    private int[] x=new int[ALL_DOTS];
    private int[] y=new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left;
    private boolean right=true;
    private boolean up;
    private boolean down;
    private boolean inGame=true;
    public GameField() {
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldListenner());
        setFocusable(true);
    }
    public void loadImages(){
        ImageIcon ii=new ImageIcon("dot.jpeg");
        dot=ii.getImage();
        ImageIcon ip=new ImageIcon("apple.jpeg");
        apple=ip.getImage();
    }
public void initGame(){
        dots=3;
        for (int i=1;i<3;i++){
           x[i]=48-i*DOT_SIZE;
           y[i]=48;
        }
        timer=new Timer(250,this);
        timer.start();
        createWeapone();
}

    private void createWeapone() {
        appleX=new Random().nextInt(20)*DOT_SIZE;
        appleY=new Random().nextInt(20)*DOT_SIZE;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            checkSweeper();
            move();
            checkCollisions();
        }
        repaint();
    }

    private void checkCollisions() {
        for(int i=dots;i>0;i--){
            if(i>4 && x[0]==x[i] && y[0]==y[i]){
                  inGame=false;
            }
            if(x[0]>SIZE){
                inGame=false;
            }
            if(x[0]<0){
                inGame=false;
            }
            if(y[0]>SIZE){
                inGame=false;
            }
            if(y[0]<0){
                inGame=false;
            }
        }
    }

    private void checkSweeper() {
        if(x[0]==appleX && y[0]==appleY){
            dots++;
            createWeapone();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);
        for (int i=0;i<dots;i++){
            g.drawImage(dot,x[i],y[i],this);
        }
        }
        else {
            String str="GAME OVER";
            g.setColor(Color.white);
            g.drawString(str,125,SIZE/2);
        }
    }

    private void move() {
        for (int i=dots;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if (left){
            x[0]-=DOT_SIZE;
        }
        if (right){
            x[0]+=DOT_SIZE;
        }
        if (up){
            y[0]-=DOT_SIZE;
        }
        if (down){
            y[0]+=DOT_SIZE;
        }
    }
    class FieldListenner extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key=e.getKeyCode();
            if(key==KeyEvent.VK_LEFT && !right){
            left=true;
            up=false;
            down=false;}
            if(key==KeyEvent.VK_RIGHT && !left){
                right=true;
                up=false;
                down=false;}
            if(key==KeyEvent.VK_UP && !down){
                right=false;
                up=true;
                left=false;}
            if(key==KeyEvent.VK_DOWN && !up){
                down=true;
                left=false;
                right=false;}
        }
    }
}
