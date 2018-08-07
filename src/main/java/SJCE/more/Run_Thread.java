package SJCE.more;

import static SJCE.XChessFrame.frame;
import static SJCE.XChessFrame.boardUI;
import static SJCE.XChessFrame.borderPanel;
import SJCE.xgui.Agent.Agent;
import javax.swing.SwingUtilities;

public class Run_Thread extends Thread {

    public Run_Thread () {  start();  }
    @Override
    public void run () {
        //SwingUtilities.invokeLater
        //java.awt.EventQueue
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.loadBoardTheme();
                System.out.println("boardUI = "+boardUI.getSize());
                System.out.println("borderPanel = "+borderPanel.getSize());
                //frame.newGame(Agent.USER_AGENT, Agent.ENGINE_AGENT);                
            }
        }); 
    }    
}    
