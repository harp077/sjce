package SJCE.more;

import static SJCE.XChessFrame.frame;
import javax.swing.JOptionPane;

public class Msg_Thread implements Runnable {
    
    private String msg;
    private Thread go;
    
    public Msg_Thread (String s) 
        {
            msg = s;
            go = new Thread(this);
            go.start();
        }
    
    @Override
    public void run () {
        Thread th = Thread.currentThread();
        //while(go == th) 
        // {
        //SwingUtilities.invokeLater
        java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {        
            if (go == th) { JOptionPane.showMessageDialog(frame,msg); stop(); }
            }
        }); 
        // }        
    }
    public void stop () { go = null; }
}
