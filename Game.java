
    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.*;
    import java.util.*;
    import java.lang.*;
    
    /**
     * This Game class is simple game where user clicks 
     * on the panels and his goal is to click on 5 or 7 or 9 safe panels
     * in order to win the game depends on the difficulty level
     *
     * @author Andrejs Romanovskis ar665
     * @version 20.03.2020
     */
    public class Game extends JFrame
    {
        // creating three panels
        private JPanel bombPanel, mainPanel, levelPanel;
        //creating buttons for the game
        private JButton play, exit, easy, intermediate, difficult;
        //creating constant value for the gap between panels
        private final int GAP = 1;
        //creating constant value for the number of rows for the grid panel
        private final int ROWS = 2;
        //creating constant value for the number of columns for the grid panel
        private final int COLUMN = 5;
        //creating array list of the panels
        private JPanel [][]panel = new JPanel[COLUMN][ROWS];
        // adding variable that counts the score of the player
        private int count = 0;
        // adding variable that sets how many safe panels should be pressed in order to win
        //automatically set to easy mode can be changed when buttons of difficulty are pressed
        private int win = 5;
        //creating mines for the first panel
        private int [][] mines = new int [COLUMN][ROWS];
        //creating random object to place mines randomly
        Random rand = new Random();
        // creating label which will output you win/lost
        private JLabel label;
        // boolean variabled that controls if game is still going or finished
        private boolean finished = false;
        /**
         * Initilaise game's interface 
         */
        public Game()
        {
            super("chasing-bombs-ar665");
            setSize(1000,600);
            makeFrame();
        }
        
        /**
         * Creates the frame, panels and all of the components on it 
         * - reposnible for the interface
         * - creates actionListeners for all the buttons on the panels
         */
        public void makeFrame()
        {
            //creating container for the panels
            Container contentPane = getContentPane();
            contentPane.setLayout(new GridLayout());
            
            //creating buttons for the game
            play = new JButton("Play a Game");
            exit = new JButton("Exit");
            easy = new JButton("Easy");
            intermediate = new JButton("Intermediate");
            difficult = new JButton("Difficult");
            
            //win/lost label 
            label = new JLabel("");
            
            //creating three main panels with layout managers
            bombPanel = new JPanel(new GridLayout (ROWS,COLUMN,GAP,GAP));
            mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
            levelPanel = new JPanel();
            levelPanel.setLayout(new BoxLayout(levelPanel, BoxLayout.PAGE_AXIS));
            
            //setting colours for the main panels
            bombPanel.setBackground(Color.WHITE);
            mainPanel.setBackground(Color.BLUE);
            levelPanel.setBackground(Color.GREEN);
            
            //adding panels to the frame
            add(bombPanel);
            add(mainPanel);
            add(levelPanel);
            
            //setting up middle panel - making all buttons to work and alligned in the center
            mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
            mainPanel.add(play);
            play.addActionListener(source -> {;
                deleteMine();
                changePanel();
                setMine();
                label.setText("");
                count = 0;
                finished = false;
            });
            play.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(Box.createRigidArea(new Dimension(0,10)));
            mainPanel.add(exit);
            exit.addActionListener(source -> System.exit(0));
            exit.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(Box.createRigidArea(new Dimension(0,200)));
            mainPanel.add(label);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setPreferredSize(new Dimension (200,100));
            Font f = new Font("TimesRoman",Font.BOLD,20);
            label.setFont(f);
            
            // setting up right hand panel - making all buttons to work and alligned in the center
            levelPanel.add(Box.createRigidArea(new Dimension(0,10)));
            levelPanel.add(easy);
            easy.addActionListener(source -> win = 5);
            easy.setAlignmentX(Component.CENTER_ALIGNMENT);
            levelPanel.add(Box.createRigidArea(new Dimension(0,10)));
            levelPanel.add(intermediate);
            intermediate.addActionListener(source -> win = 7);
            intermediate.setAlignmentX(Component.CENTER_ALIGNMENT);
            levelPanel.add(Box.createRigidArea(new Dimension(0,10)));
            levelPanel.add(difficult);
            difficult.addActionListener(source -> win = 9);
            difficult.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            //setting up 10 panels in bombpanel
            setPanel();
            
            // creating one bomb in the random place
            setMine();
            
            // making all the game visible
            setVisible(true);
            
        }
        
        /**
         * Creating 10 panels for the bomb panel - using two dimesnional array
         */
        public void setPanel(){
            for (int x = 0; x < COLUMN; x++){
                int y = 0;
                panel[x][y] = new JPanel();
                bombPanel.add(panel[x][y]);
                panel[x][y].setBackground(Color.RED);
                panel[x][y].addMouseListener(new Mouse());
            }
            for (int x = 0; x<COLUMN; x++){
                int y = 1;
                panel[x][y] = new JPanel();
                bombPanel.add(panel[x][y]);
                panel[x][y].setBackground(Color.RED);
                panel[x][y].addMouseListener(new Mouse());
            }
        }
        
        /**
         * Changes panels back to Red color
         */
        public void changePanel(){
            for (int x = 0; x < COLUMN; x++){
                int y = 0;
                panel[x][y].setBackground(Color.RED);
            }
            for (int x = 0; x<COLUMN; x++){
                int y = 1;
                panel[x][y].setBackground(Color.RED);
            }
        }
        
        /**
         * This method is reposnible for deleting mine, so when next game 
         * started the previous bomb is deleted
         */
        public void deleteMine(){
            for (int x = 0; x < COLUMN; x++){
                int y = 0;
                mines[x][y] = 0;
            }
            for (int x = 0; x<COLUMN; x++){
                int y = 1;
                mines[x][y] = 0;
            }
        }
        
        /**
         * Sets one mine in the random panel
         */
        public void setMine(){
            int y= rand.nextInt(5);
            int j= rand.nextInt(2);
            mines [y][j] = 1; 
        }
        
        /**
         * Checks if the panel contains mine
         * param y,j are the coordinates of the panel
         */
        public int checkMine(int y, int j){
            int bomb = 0;
            if (mines[y][j] ==1){
                bomb = 1;
                return bomb;
            }
            else{
                return bomb;
            }
        }
        
        /**
         * Find panels coordinates and returns x-axis
         */
        public int checkPanel(JPanel panel1){
            boolean found = false;
            int z = 0;
            int c = 0;
            for (int y=0; y<5; y++){
                for(int j=0; j<2; j++){
                    if(panel1 == panel[y][j]){
                        z = y;
                        c = j;
                    }
                }
            }
            return z;
        }
        
        /**
         * Find panels coordinates and returns y-axis
         */
        public int checkPanel1(JPanel panel1){
            boolean found = false;
            int z = 0;
            int c = 0;
            for (int y=0; y<5; y++){
                for(int j=0; j<2; j++){
                    if(panel1 == panel[y][j]){
                        z = y;
                        c = j;
                    }
                }
            }
            return c;
        }
        
        /**
         * Inner Class which provides access to the MouseListener
         */
        public class Mouse implements MouseListener{

            /**
             * When panel is pressed this method is invoked
             * it finds panel and checks if mines are present if they not score goes up
             * If score reaches certain number player wins
             * If player clicks on the bomb player loses and score is displayed
             */
            public void mousePressed(MouseEvent e){
                if(!finished){
                    JPanel panel = (JPanel) e.getSource();
                    Color color = panel.getBackground();
                    if (color == Color.RED){
                        int z = checkPanel(panel);
                        int c = checkPanel1(panel);
                        if (checkMine(z,c) == 1){
                             label.setText("You lose! You got "+ count + " points");
                             finished = true;
                        }
                        else{
                            panel.setBackground(Color.YELLOW);
                            count ++;
                                if(count == win){
                                    label.setText("You win!");
                                    finished = true;
                                }
                        }
                    }
                }
        }
        @Override    
        public void mouseReleased(MouseEvent e){};
        @Override 
        public void mouseEntered(MouseEvent e){};
        @Override 
        public void mouseExited(MouseEvent e){};
        @Override 
        public void mouseClicked(MouseEvent e){};
        }
}


