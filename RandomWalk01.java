//Random walk 01: Create a Swing GUI program that consists of a JFrame that holds one JPanel (which I will call the drawing panel).
//        Divide the drawing panel into a grid (the size of which should be controlled by command line arguments and defaulting to
//        50 by 50) of cells. Each cell should start off black. Draw thin grey lines between each cell of the grid. The current cell
//        should be red and the current cell should start around the middle of the panel. Your program should then simulate a random
//        walk around the screen i.e. each time step (the length of which is up to you, but should be such that the simulation is easy
//        to follow) the current cell should move one step either up, down, left, or right and should not be allowed to walk off of the
//        screen. Note that the size of the cells will stay fixed, so rows and columns will have to be "added" or "removed" as the frame
//        is resized.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class RandomWalk01 extends JFrame {

    private static double rectHeight = 50;
    private static double rectWidth = 50;

    private static final DrawingPanel drawingPanel = new DrawingPanel();
    public static class DrawingPanel extends JPanel {

        public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        public double width = screenSize.getWidth();
        public double height = screenSize.getHeight();
        private int ROWS = (int) (height / rectHeight);
        private int COLUMNS = (int) (width / rectWidth);

        public int x = 300;
        public int y= 300;

        public void randomMove(){
            Random rand = new Random();
            int dir=rand.nextInt(4);
            if (dir == 0) {
                x = x + (int)rectWidth;
            } else if (dir == 1 ) {
                x = x - (int)rectWidth;
            } else if (dir == 2 ) {
                y = y + (int)rectHeight;
            } else if (dir == 3  ){
                y = y - (int)rectHeight;
            }
            if ( x-rectWidth/2<=0) {
                x = x + (int)rectWidth;
            } else if (x + rectWidth/2 >= drawingPanel.getWidth()) {
                x = x - (int)rectWidth;
            } else if ( y - rectHeight/2 <= 0) {
                y = y + (int)rectHeight;
            } else if ( y + rectHeight/2 >= drawingPanel.getHeight() ){
                y = y - (int)rectHeight;
            }
        }
        public DrawingPanel() {
            setBackground(Color.white);
            Timer animationTimer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    randomMove();
                    repaint();
                }
            });
            animationTimer.start();
        }
        @Override
        protected void paintComponent(Graphics graphic) {
            super.paintComponent(graphic);
            drawingSquares(graphic);
            Graphics2D graphic2d = (Graphics2D) graphic.create();
            graphic2d.setColor(Color.RED);
            graphic2d.fillRect(x, y, (int)(rectWidth), (int)(rectHeight));    //and paints the square that moves over the grid
            graphic2d.dispose();
        }
        public void drawingSquares(Graphics graphic) {
            Graphics2D brick = (Graphics2D) graphic.create();
            double x = 0;
            double y = 0;
            for (int i = 0; i <= ROWS; i++) {
                for (int j = 0; j <= COLUMNS; j++) {
                    brick.setColor(Color.BLACK);
                    Rectangle2D.Double rect = new Rectangle2D.Double(x, y, rectWidth, rectHeight);
                    brick.fill(rect);
                    brick.setColor(Color.gray);
                    brick.draw(rect);
                    x += rectWidth;
                }
                x = 0;
                y += rectHeight;
            }
        }
    }
    public RandomWalk01(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,600);
        add(drawingPanel);
    }
    public static void main(String[] args){
        if (args.length>0) {
            try {
                rectHeight = Integer.parseInt(args[0]);
                rectWidth = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.print("Your entry is not a number.");
                System.exit(1);
            }
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RandomWalk01().setVisible(true);
            }
        });
    }
}

