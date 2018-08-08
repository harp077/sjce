package SJCE.Cfg;

import static SJCE.XChessFrame.aktion;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;

public class AppCfgPref {

    public static void Save() {
        aktion.pref.put("skin", aktion.currentLAF);
        //aktion.pref.put("theme", aktion.currentTheme);
        aktion.pref.put("use-clock", aktion.UseClock);
        aktion.pref.put("mode", aktion.Mode);
        aktion.pref.put("depth", "" + aktion.Depth);
        aktion.pref.put("time", "" + aktion.Time);
        ///////////
        aktion.pref.put("bt-fig", aktion.BoardThemeFig);
        aktion.pref.put("bt-fon", aktion.BoardThemeFon);
        ////////////
        aktion.pref.put("wp-ce", aktion.whitePlayerCE);
        aktion.pref.put("wp-tip", aktion.whitePlayerTip);
        /////////////
        aktion.pref.put("bp-ce", aktion.blackPlayerCE);
        aktion.pref.put("bp-tip", aktion.blackPlayerTip);
        /////////////
        aktion.pref.put("mixer", "" + aktion.currentMixer);
        aktion.pref.put("mute", aktion.currentMute);
        aktion.pref.put("use-sound", aktion.useSound);
        //File exp = new File("cfg");
        try {
            aktion.pref.flush();
            aktion.pref.sync();
            //if (!exp.exists()) exp.mkdir();
            //pref.exportNode(new FileOutputStream("cfg/sjce.xml"));
            //aktion.pref.exportSubtree(new FileOutputStream("cfg/sjce.xml"));
        } catch (BackingStoreException ex) {
            Logger.getLogger(AppCfgPref.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Load() {
        //FileInputStream fis = null;
        /*try (FileInputStream fis = new FileInputStream("cfg/sjce.xml")) {
            Preferences.importPreferences(fis);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppCfgPref.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | InvalidPreferencesFormatException ex) {
            Logger.getLogger(AppCfgPref.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        ////////////////
        aktion.currentLAF =     aktion.pref.get("skin", "org.pushingpixels.substance.api.skin.SubstanceSaharaLookAndFeel");//config.getChild("skin").getChildText("laf");
        //aktion.currentTheme =   aktion.pref.get("theme","");//config.getChild("skin").getChildText("theme");
        //aktion.useEffects = aktion.pref.get("theme","");//config.getChild("skin").getChildText("effects");
        aktion.UseClock =       aktion.pref.get("use-clock","true");//config.getChildText("UseClock");
        aktion.Mode =           aktion.pref.get("mode","easy");//config.getChildText("Mode");
        aktion.BoardThemeFig =  aktion.pref.get("bt-fig","alpha");//config.getChild("BoardTheme").getChildText("BoardThemeFig");
        aktion.BoardThemeFon =  aktion.pref.get("bt-fon","sea-green");//config.getChild("BoardTheme").getChildText("BoardThemeFon");
        aktion.whitePlayerCE =  aktion.pref.get("wp-ce","Human");//config.getChild("WhitePlayer").getChildText("WhitePlayerCE");
        aktion.whitePlayerTip = aktion.pref.get("wp-tip","human");//config.getChild("WhitePlayer").getChildText("WhitePlayerTip");
        aktion.blackPlayerCE =  aktion.pref.get("bp-ce","Koedem");//config.getChild("BlackPlayer").getChildText("BlackPlayerCE");
        aktion.blackPlayerTip = aktion.pref.get("bp-tip","uci");//config.getChild("BlackPlayer").getChildText("BlackPlayerTip");
        aktion.Depth =          Integer.parseInt(aktion.pref.get("depth","2"));//Integer.parseInt(config.getChildText("Depth"));
        aktion.Time =           Integer.parseInt(aktion.pref.get("time","5"));//Integer.parseInt(config.getChildText("Time"));
        aktion.currentMute =    aktion.pref.get("mute","false");//config.getChildText("mute");
        aktion.useSound =       aktion.pref.get("use-sound","false");//config.getChildText("useSound");
        aktion.currentMixer =   Integer.parseInt(aktion.pref.get("mixer","100"));//Integer.parseInt(config.getChildText("mixer"));

    }
}
