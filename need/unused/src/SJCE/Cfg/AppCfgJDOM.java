package SJCE.Cfg;
//import SJfc.more.aktion;
import static SJCE.XChessFrame.aktion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ContentFilter;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class AppCfgJDOM {

    public static void Save () {
        Document xmlDoc = new Document();
        Element sjpg = new Element("sjce");
            xmlDoc.setRootElement(sjpg);
        Element config = new Element("config");
            sjpg.addContent(config);            
        Element skin = new Element("skin");
            config.addContent(skin);
        Element laf = new Element("laf");    
            laf.addContent(aktion.currentLAF);     
            skin.addContent(laf);
        Element theme = new Element("theme");    
            theme.addContent(aktion.currentTheme);     
            skin.addContent(theme);
       Element effects = new Element("effects");    
            effects.addContent(aktion.useEffects);     
            skin.addContent(effects);              
        Element UseClock = new Element("UseClock");    
            UseClock.addContent(aktion.UseClock);     
            config.addContent(UseClock);
        Element Mode = new Element("Mode");    
            Mode.addContent(aktion.Mode);     
            config.addContent(Mode);  
        Element Depth = new Element("Depth");    
            Depth.addContent(""+aktion.Depth);     
            config.addContent(Depth);            
        Element Time = new Element("Time");    
            Time.addContent(""+aktion.Time);     
            config.addContent(Time);  
        Element BoardTheme = new Element("BoardTheme");
            config.addContent(BoardTheme);
        Element BoardThemeFig = new Element("BoardThemeFig");    
            BoardThemeFig.addContent(aktion.BoardThemeFig);     
            BoardTheme.addContent(BoardThemeFig);
        Element BoardThemeFon = new Element("BoardThemeFon");    
            BoardThemeFon.addContent(aktion.BoardThemeFon);     
            BoardTheme.addContent(BoardThemeFon); 
        Element WhitePlayer = new Element("WhitePlayer");
            config.addContent(WhitePlayer);
        Element WhitePlayerCE = new Element("WhitePlayerCE");    
            WhitePlayerCE.addContent(aktion.whitePlayerCE);     
            WhitePlayer.addContent(WhitePlayerCE);
        Element WhitePlayerTip = new Element("WhitePlayerTip");    
            WhitePlayerTip.addContent(aktion.whitePlayerTip);     
            WhitePlayer.addContent(WhitePlayerTip);
        Element BlackPlayer = new Element("BlackPlayer");
            config.addContent(BlackPlayer);
        Element BlackPlayerCE = new Element("BlackPlayerCE");    
            BlackPlayerCE.addContent(aktion.blackPlayerCE);     
            BlackPlayer.addContent(BlackPlayerCE);
        Element BlackPlayerTip = new Element("BlackPlayerTip");    
            BlackPlayerTip.addContent(aktion.blackPlayerTip);     
            BlackPlayer.addContent(BlackPlayerTip); 
        Element mixer = new Element("mixer");
            mixer.addContent(""+aktion.currentMixer); 
            config.addContent(mixer);  
        Element mute = new Element("mute");
            mute.addContent(aktion.currentMute); 
            config.addContent(mute);   
        Element useSound = new Element("useSound");
            useSound.addContent(aktion.useSound); 
            config.addContent(useSound);            
        try {
            Format fmt = Format.getPrettyFormat();
            // Выводим созданный XML как поток байт на стандартный
            // вывод и в файл, используя подготовленный формат
            XMLOutputter serializer = new XMLOutputter(fmt);
            serializer.output(xmlDoc, System.out);
            File cfgdir = new File("cfg");
            cfgdir.mkdir();
            serializer.output(xmlDoc, new FileOutputStream(new File("cfg/sjce_jdom.xml")));
        }
        catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void Load () {
      SAXBuilder parser = new SAXBuilder();
      Document xmlDoc;
      try {
        xmlDoc = parser.build(new File("cfg/sjce_jdom.xml"));
        // Получаем список всех элементов head, которые
        // содержит корневой элемент
        List elements = xmlDoc.getRootElement().getContent(new ElementFilter("config"));
        System.out.println(elements);
        // Для каждого элемента config получаем значение 
        // вложенных элементов
        Iterator iterator = elements.iterator();
        while(iterator.hasNext())
            {
             Element config = (Element)iterator.next();
             aktion.currentLAF = config.getChild("skin").getChildText("laf");
             aktion.currentTheme = config.getChild("skin").getChildText("theme");
             aktion.useEffects = config.getChild("skin").getChildText("effects");             
             aktion.UseClock = config.getChildText("UseClock");
             aktion.Mode = config.getChildText("Mode"); 
             aktion.BoardThemeFig = config.getChild("BoardTheme").getChildText("BoardThemeFig");  
             aktion.BoardThemeFon = config.getChild("BoardTheme").getChildText("BoardThemeFon");
             aktion.whitePlayerCE  = config.getChild("WhitePlayer").getChildText("WhitePlayerCE");
             aktion.whitePlayerTip = config.getChild("WhitePlayer").getChildText("WhitePlayerTip");               
             aktion.blackPlayerCE  = config.getChild("BlackPlayer").getChildText("BlackPlayerCE");
             aktion.blackPlayerTip = config.getChild("BlackPlayer").getChildText("BlackPlayerTip"); 
             aktion.Depth = Integer.parseInt(config.getChildText("Depth")); 
             aktion.Time = Integer.parseInt(config.getChildText("Time"));
             aktion.currentMute = config.getChildText("mute");
             aktion.useSound = config.getChildText("useSound");             
             aktion.currentMixer = Integer.parseInt(config.getChildText("mixer"));              
            } // while
        //aktion.guiInit();
        // Получаем все комментарии в документе и выводим для
        // каждого его значение и имя элемента, который содержит
        // этот комментарий
        iterator = xmlDoc.getDescendants(new ContentFilter(ContentFilter.COMMENT));
        while(iterator.hasNext()){
            Content comment = (Content)iterator.next();
            System.out.println(comment.getParentElement().getName()+": "+ comment.getValue());
        }
      } 
      catch (JDOMException e) { aktion.DefCfg(); e.printStackTrace(); System.out.println("jdom-ex"); } 
      catch (IOException e)   { aktion.DefCfg(); e.printStackTrace(); System.out.println("io-ex"); }
  }
}
