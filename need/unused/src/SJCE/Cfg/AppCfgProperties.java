package SJCE.Cfg;
//import SJfc.more.aktion;
import static SJCE.XChessFrame.aktion;
import java.io.*;
import java.util.Properties;
public class AppCfgProperties {
 public static void Load () {
    Properties prop = new Properties();
    try 
      {
        prop.load(new InputStreamReader(new FileInputStream("cfg/sjce_prop.cfg"),"UTF-8"));
        aktion.currentLAF = prop.getProperty("laf");
        aktion.currentTheme = prop.getProperty("theme");             
        aktion.UseClock = prop.getProperty("UseClock");
        aktion.Mode = prop.getProperty("Mode");             
        aktion.Depth = Integer.parseInt(prop.getProperty("Depth")); 
        aktion.Time = Integer.parseInt(prop.getProperty("Time"));        
        //aktion.guiInit();   
      }
    catch (IOException ex)          { aktion.DefCfg(); } 
    catch (NullPointerException e)  { aktion.DefCfg(); };
  }
 public static void Save (String s, String tm, String uc, String m, String d, String t) {
    Properties prop = new Properties();
    try {
        prop.setProperty("laf",s);
        prop.setProperty("theme",tm);
        prop.setProperty("UseClock",uc); 
        prop.setProperty("Mode",m); 
        prop.setProperty("Depth",d);  
        prop.setProperty("Time",t);         
        File cfgdir = new File("cfg");
        cfgdir.mkdir();        
        prop.store(new BufferedWriter(new OutputStreamWriter(new FileOutputStream("cfg/sjce_prop.cfg"), "UTF-8")), null);
        } 
    catch (IOException ex) {  ex.printStackTrace();   }
    }
}
