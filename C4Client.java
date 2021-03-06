/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c4app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.border.LineBorder;
import java.io.*;
import java.net.*;
import javax.swing.border.LineBorder;

/**
 *
 * @author lizzy
 */
public class C4Client extends JApplet
        implements Runnable
{
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 670;
    
    private static Cell[][] game = new Cell[7][8];
    
    private static JPanel main, board, sideBar, menu, gameHolder, buttonHolder, emptyPanel;
    private static JButton col1, col2, col3, col4 ,col5, col6, col7, col8;
    private static JButton play, pause, quit, changeColor;
    private static JLabel message, status;
    
    public static int PLAYER1 = 1;
    public static int PLAYER2 = 2;
    public static int P1_WON = 1;
    public static int P2_WON = 2;
    
    private char token1 = ' ';
    private char token2 = ' ';
    
    private static boolean myTurn = false;
    private static boolean continuePlaying = true;
    private static boolean waiting = true;
    private static boolean isStandAlone = false;
    
    private DataInputStream fromServer;
    private DataOutputStream toServer;
    
    private String host = "localhost";
    
    public C4Client() 
    {
        //create panels and set layouts
        main = new JPanel();
        main.setLayout(new BorderLayout());
        main.setBackground(Color.red);
        
        board = new JPanel();
        board.setLayout(new BorderLayout());
        board.setBackground(Color.blue);
        
        sideBar = new JPanel();
        sideBar.setLayout(new GridLayout(2, 1));
        sideBar.setBackground(Color.lightGray);
        
        gameHolder = new JPanel();
        gameHolder.setLayout(new FlowLayout());
        gameHolder.setBackground(Color.yellow);
        
        buttonHolder = new JPanel();
        buttonHolder.setLayout(new GridLayout(1, 8));
        buttonHolder.setBackground(Color.pink);
        
        menu = new JPanel();
        menu.setLayout(new GridLayout(4, 1));
        menu.setBackground(Color.gray);
        
        emptyPanel = new JPanel();
        emptyPanel.setLayout(new BorderLayout());
        emptyPanel.setBackground(Color.gray);
        
        //create buttons
        play = new JButton("Play");
        pause = new JButton("Pause");
        changeColor = new JButton("Change Color");
        quit = new JButton("Quit");
        
        col1 = new JButton("Column 1");
        col2 = new JButton("Column 2");
        col3 = new JButton("Column 3");
        col4 = new JButton("Column 4");
        col5 = new JButton("Column 5");
        col6 = new JButton("Column 6");
        col7 = new JButton("Column 7");
        col8 = new JButton("Column 8");
        
        //add listeners to buttons
        col1.addActionListener(new C4Client.ButtonListener());
        col2.addActionListener(new C4Client.ButtonListener());
        col3.addActionListener(new C4Client.ButtonListener());
        col4.addActionListener(new C4Client.ButtonListener());
        col5.addActionListener(new C4Client.ButtonListener());
        col6.addActionListener(new C4Client.ButtonListener());
        col7.addActionListener(new C4Client.ButtonListener());
        col8.addActionListener(new C4Client.ButtonListener());
        
        
        
    }
    
    public void init()
    {
        gameHolder.setLayout(new GridLayout(7,8));
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                gameHolder.add(game[i][j] = new Cell( i, j));
            }   
        }
        //add components to panels
        buttonHolder.add(col1);
        buttonHolder.add(col2);
        buttonHolder.add(col3); 
        buttonHolder.add(col4); 
        buttonHolder.add(col5); 
        buttonHolder.add(col6);
        buttonHolder.add(col7);
        buttonHolder.add(col8);
        
        menu.add(play);
        menu.add(pause);
        menu.add(changeColor);
        menu.add(quit);
        
        //add panels to eachother
        board.add(gameHolder, BorderLayout.CENTER);
        board.add(buttonHolder, BorderLayout.NORTH);
        
        sideBar.add(emptyPanel);
        sideBar.add(menu);
        
        main.add(board, BorderLayout.CENTER);
        main.add(sideBar, BorderLayout.EAST);
        
        // Connect to the server
        connectToServer();
    }
    
     private static class ButtonListener implements ActionListener {

        public void actonPreformed(ActionEvent event) 
        {
            if(event.getSource() == col1)
            {
                
            }
            else if(event.getSource() == col2)
            {
                
            }
            else if(event.getSource() == col3)
            {
                
            }
            else if(event.getSource() == col4)
            {
                
            }
            else if(event.getSource() == col5)
            {
                
            }
            else if(event.getSource() == col6)
            {
                
            }
            else if(event.getSource() == col7)
            {
                
            }
            else
            {
                
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
  

    private void connectToServer() {
        try 
        {  
            // Create socket to connect to server
            Socket socket;
            if (isStandAlone)
            {
                socket = new Socket(host, 8000);
            }
            else
            {
                socket = new Socket(getCodeBase().getHost(), 8000);
            }
        
            // Create an input stream 
            fromServer = new DataInputStream(socket.getInputStream());

            // Create an output stream 
            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (Exception e) 
        {
            System.err.println(e);
        }

        // Control game on separate thread
        Thread thread = new Thread(this);
            thread.start();
    }
    
    public void run()
    {
        try
        {
            int player = fromServer.readInt();
            
            if(player == PLAYER1)
            {
                activeToken = 'R';
                opToken = 'B';
                //log.setText("Player 1 is Red.");
                //log.setText
                
                myTurn = true;
            }
        }
        catch(Exception e)
        {}
    }
    
    public class Cell extends JPanel 
    {
        private int row;
        private int col;
        
        public Cell(int row, int col) 
        {
            this.row = row;
            this.col = col;
            setBorder(new LineBorder(Color.black, 1));
        }
         
    }
    
    public static void main(String[] args) 
    {
        //Create frame
        JFrame c4Frame = new JFrame("Connect Four Client");
        
        //Create applet
        C4Client app = new C4Client();
        app.isStandAlone = true;
        
        //Get the host
        if(args.length == 1) app.host = args[0];
        
        //Add app to frame
        
        
        //Call init() and start()
        app.init();
        //app.start();
        
        // Display the frame
        
    }
    
}
