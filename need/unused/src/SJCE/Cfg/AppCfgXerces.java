package SJCE.Cfg;
//import SJfc.more.aktion;
import static SJCE.XChessFrame.aktion;
import com.romanenco.configloader.ConfigLoader;
import java.io.*;
public class AppCfgXerces {
  // ПОДДЕРЖИВАЕТ ВЛОЖЕННЫЕ ТЕГИ
  public static FileWriter writeCFG = null;

  public static void Load () {
    ConfigLoader cfg=new ConfigLoader("org.apache.xerces.parsers.SAXParser");
    try {
        cfg.LoadFromFile("cfg/sjce_xerces.xml");
        aktion.currentLAF=cfg.getTagValue("sjce.config.skin.laf");
        aktion.currentTheme=cfg.getTagValue("sjce.config.skin.theme");
        aktion.useEffects=cfg.getTagValue("sjce.config.skin.effects");
        aktion.UseClock = cfg.getTagValue("sjce.config.UseClock");
        aktion.Mode = cfg.getTagValue("sjce.config.Mode");  
        aktion.Depth = Integer.parseInt(cfg.getTagValue("sjce.config.Depth"));
        aktion.Time = Integer.parseInt(cfg.getTagValue("sjce.config.Time"));
        //aktion.mainEngine = cfg.getTagValue("sjce.config.Engine"); 
        //aktion.EngineColor = cfg.getTagValue("sjce.config.EngineColor");        
        aktion.BoardThemeFig = cfg.getTagValue("sjce.config.BoardTheme.BoardThemeFig");
        aktion.BoardThemeFon = cfg.getTagValue("sjce.config.BoardTheme.BoardThemeFon"); 
        aktion.currentMute=cfg.getTagValue("sjce.config.mute"); 
        aktion.currentMixer=Integer.parseInt(cfg.getTagValue("sjmp3.config.mixer"));         
        //aktion.guiInit();     
    } 
    catch (NullPointerException e)  {  aktion.DefCfg(); System.out.println(e); }
    catch (RuntimeException e)      {  aktion.DefCfg(); System.out.println(e); }
  }
 public static void Save () {
    try {
            File cfgdir = new File("cfg");
            cfgdir.mkdir();
            //cfgfile.mkdirs();            
            File cfgfile = new File("cfg/sjce_xerces.xml");
            writeCFG = new FileWriter(cfgfile);
            writeCFG.append("<sjce>\n");            
            writeCFG.append("  <config>\n");
            writeCFG.append("   <skin>\n");            
            writeCFG.append("     <laf>"+aktion.currentLAF+"</laf>\n");
            writeCFG.append("     <theme>"+aktion.currentTheme+"</theme>\n");
            writeCFG.append("     <effects>"+aktion.useEffects+"</effects>\n");
            writeCFG.append("   </skin>\n"); 
            writeCFG.append("   <BoardTheme>\n");            
            writeCFG.append("     <BoardThemeFig>"+aktion.BoardThemeFig+"</BoardThemeFig>\n");
            writeCFG.append("     <BoardThemeFon>"+aktion.BoardThemeFon+"</BoardThemeFon>\n");            
            writeCFG.append("   </BoardTheme>\n");             
            writeCFG.append("   <UseClock>"+aktion.UseClock+"</UseClock>\n");
            writeCFG.append("   <Mode>"+aktion.Mode+"</Mode>\n"); 
            writeCFG.append("   <Depth>"+aktion.Depth+"</Depth>\n");
            //writeCFG.append("   <Engine>"+aktion.mainEngine+"</Engine>\n"); 
            //writeCFG.append("   <EngineColor>"+aktion.EngineColor+"</EngineColor>\n");            
            writeCFG.append("   <Time>"+aktion.Time+"</Time>\n");
            writeCFG.append("   <mixer>"+aktion.currentMixer+"</mixer>\n"); 
            writeCFG.append("   <mute>"+aktion.currentMute+"</mute>\n");                        
            writeCFG.append("  </config>\n");
            writeCFG.append("</sjce>\n");            
        } 
    catch (IOException ex) {  ex.printStackTrace();  }
    finally 
            {
                if(writeCFG != null) 
                {
                 try { writeCFG.close(); } 
                 catch (IOException e) { e.printStackTrace(); }
                }
            }            
    }
}
