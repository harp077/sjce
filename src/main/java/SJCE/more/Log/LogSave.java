package SJCE.more.Log;
import static SJCE.XChessFrame.frame;
import static SJCE.XChessFrame.logFrame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
public class LogSave {
    public static String putf="",putd="";
    public static FileInputStream fis;
    public static BufferedReader br;
    public static String bufer="",txt="";
    public static String fullFileName;
    public static FileWriter writeFile = null;
    public static void Save () {  
        JFileChooser fc=new JFileChooser();
        fc.addChoosableFileFilter(new MFileFilter(".log"));
        fc.addChoosableFileFilter(new MFileFilter(".LOG"));        
        fc.setAcceptAllFileFilterUsed(false);
        fc.setDialogType(JFileChooser.SAVE_DIALOG);
        if (fc.showDialog(logFrame,"Save Log") == JFileChooser.APPROVE_OPTION)
           {
           try 
           {
                fullFileName=fc.getSelectedFile().getPath();
                File outputfile = new File(fullFileName + ".log");
                writeFile = new FileWriter(outputfile);
                writeFile.append(frame.outputArea.getText());
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(LogSave.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally 
            {
                if(writeFile != null) 
                {
                 try { writeFile.close(); } 
                 catch (IOException e) { e.printStackTrace(); }
                }
            }            
           }
        
    }  
    
}
