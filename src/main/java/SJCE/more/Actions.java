package SJCE.more;

import SJCE.more.Log.TextTransfer;
import SJCE.more.Log.ShowClipBoard;
import SJCE.more.Log.LogShow;
import SJCE.more.Log.ClipboardTextTransfer;
import SJCE.more.Links.CElinksX;
import SJCE.more.Links.CElinksU;
import SJCE.XChessFrame;
import static SJCE.XChessFrame.aktion;
import static SJCE.XChessFrame.bUseClock;
import static SJCE.XChessFrame.chessClock;
import static SJCE.XChessFrame.mModeEasy;
import static SJCE.XChessFrame.mModeHard;
import static SJCE.XChessFrame.mTime;
import static SJCE.XChessFrame.mUseClock;
import static SJCE.XChessFrame.sidePanel;
import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
//import static SJCE.XChessFrame.mcbEffects;
import org.pushingpixels.lafwidget.animation.AnimationConfigurationManager;
import org.pushingpixels.lafwidget.animation.AnimationFacet;
import static SJCE.XChessFrame.boardUI;
import static SJCE.XChessFrame.moveListUI;
import static SJCE.XChessFrame.outputArea;
import SJCE.xgui.Utility;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import static SJCE.XChessFrame.bcomboMode;
import static SJCE.XChessFrame.bcomboDepth;
import static SJCE.XChessFrame.bcomboTime;
import static SJCE.XChessFrame.comboBPlayerCE;
import static SJCE.XChessFrame.frame;
import java.awt.Color;
import static SJCE.XChessFrame.comboWPlayerCE;
import static SJCE.XChessFrame.logFrame;
import static SJCE.XChessFrame.sjceTitle;
import SJCE.more.Log.FileWorker;
import SJCE.xgui.JList.MoveListUI;
import SJCE.xgui.Move;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class Actions {

    public static String Mode = "hard";
    public static int Depth;
    public static String currentLAF = "org.pushingpixels.substance.api.skin.SubstanceSaharaLookAndFeel";
    //public static String currentTheme="";  
    public static int Time;
    public static String UseClock = "true";
    public static String BoardThemeFig = "cyan-red";
    public static String BoardThemeFon = "yellow-green";
    //public static String useEffects="true";
    public static int Prohod_White_Event = 0;
    public static int Prohod_White_Destination = -1;
    public static int Prohod_Black_Event = 0;
    public static int Prohod_Black_Destination = -1;
    public static ImageIcon ceIcon;
    public static String uciAllMovesString = "";
    public static String enginePromotionType = "";
    public static String enginePromotionFig = "";
    public static final Color moveColor = Color.GREEN;
    public static final Color restColor = Color.WHITE;
    public static String whitePlayerCE = "";
    public static String whitePlayerTip = "";
    public static String blackPlayerCE = "";
    public static String blackPlayerTip = "";
    public static String gameTip = "";
    public static String enemyTip = "";
    public static String currentMute = "false";
    public static int currentMixer = 100;
    public static String useSound = "true";
    public static int promotionCount = 0;
    public static Move whiteLastMove = null;
    public static Move blackLastMove = null;
    public static String blackRivalMovesString = "";
    public static String whiteRivalMovesString = "";
    public static String jchecsEngineTip = "jChecs.NegaScout";
    public static List<String> jchecsCeSelect = new ArrayList<>();
    public static Preferences pref = Preferences.userRoot().node("sjce/cfg");

    public Actions() {
        this.jchecsCeSelect.add("jChecs.AlphaBeta");
        //this.jchecsCeSelect.add("jChecs.MiniMax");            
        //this.jchecsCeSelect.add("jChecs.MiniMax++");
        this.jchecsCeSelect.add("jChecs.NegaScout");
    }

    public static void changeDepth(int dd) {
        frame.mDepth2.setSelected(false);
        frame.mDepth3.setSelected(false);
        frame.mDepth4.setSelected(false);
        frame.mDepth5.setSelected(false);
        frame.mDepth6.setSelected(false);
        frame.mDepth7.setSelected(false);
        frame.mDepth8.setSelected(false);
        frame.mDepth9.setSelected(false);
        switch (dd) {
            case 2:
                aktion.Depth = 2;
                frame.mDepth2.setSelected(true);
                break;
            case 3:
                aktion.Depth = 3;
                frame.mDepth3.setSelected(true);
                break;
            case 4:
                aktion.Depth = 4;
                frame.mDepth4.setSelected(true);
                break;
            case 5:
                aktion.Depth = 5;
                frame.mDepth5.setSelected(true);
                break;
            case 6:
                aktion.Depth = 6;
                frame.mDepth6.setSelected(true);
                break;
            case 7:
                aktion.Depth = 7;
                frame.mDepth7.setSelected(true);
                break;
            case 8:
                aktion.Depth = 8;
                frame.mDepth8.setSelected(true);
                break;
            case 9:
                aktion.Depth = 9;
                frame.mDepth9.setSelected(true);
                break;
        }
        bcomboDepth.setSelectedItem("" + aktion.Depth);
        //if (dd==1 && (mainEngine.equals("Magnum")||mainEngine.equals("Bagatur"))) {
        //    changeDepth(2); // rekursia
        //    JOptionPane.showMessageDialog(frame, "Magnum/Bagatur engines can't play with depth=1 !\nI am set minimum depth=2 for Bagatur/Magnum.");
        //}
    }

    public static void changeTime(int tt) {
        frame.mTime5.setSelected(false);
        frame.mTime10.setSelected(false);
        frame.mTime15.setSelected(false);
        frame.mTime20.setSelected(false);
        frame.mTime25.setSelected(false);
        frame.mTime30.setSelected(false);
        switch (tt) {
            case 5:
                aktion.Time = 5;
                frame.mTime5.setSelected(true);
                break;
            case 10:
                aktion.Time = 10;
                frame.mTime10.setSelected(true);
                break;
            case 15:
                aktion.Time = 15;
                frame.mTime15.setSelected(true);
                break;
            case 20:
                aktion.Time = 20;
                frame.mTime20.setSelected(true);
                break;
            case 25:
                aktion.Time = 25;
                frame.mTime25.setSelected(true);
                break;
            case 30:
                aktion.Time = 30;
                frame.mTime30.setSelected(true);
                break;
        }
        bcomboTime.setSelectedItem("" + aktion.Time);
    }

    public static void MyInstLF(String lf) {
        //UIManager.installLookAndFeel(lf,lf);  
        XChessFrame.lookAndFeelsDisplay.add(lf);
        XChessFrame.lookAndFeelsRealNames.add(lf);
    }

    public static void InstallLF() {
        //MyInstLF("javax.swing.plaf.metal.MetalLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceSaharaLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceAutumnLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceCremeLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceCremeCoffeeLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceModerateLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceMagellanLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceOfficeBlue2007LookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceOfficeBlack2007LookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceOfficeSilver2007LookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceNebulaLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceNebulaBrickWallLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceGeminiLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceDustCoffeeLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceDustLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceMarinerLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceCeruleanLookAndFeel");
        MyInstLF("org.pushingpixels.substance.api.skin.SubstanceTwilightLookAndFeel");
        //MyInstLF("org.pushingpixels.substance.api.skin.SubstanceEmeraldDuskLookAndFeel");
        //MyInstLF("org.pushingpixels.substance.api.skin.SubstanceChallengerDeepLookAndFeel");
    }
    
    public void about(JFrame frame) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/SJCE/img/sjce-130x87.png"));
        JOptionPane.showMessageDialog(frame,
            "SJCE - free portable cross-platform graphical chess game.\n"+
            "Support many best free java xboard/uci chess ehgines.\n"+
            "It is possible to play Human-Human, Human-Engine and\n"+
            "Engine-vs-Engine, both White and Black.\n"+                    
            "Special thanks for Norbert Raimund Leisner - \n"+
            "http://computer-chess.org/ , and also for Dr. \n"+
            "Roland Stuckardt - http://www.stuckardt.de/ ,\n"+ 
            "and also for all Java-chess-engine developers.\n"+
            "Tested on Windows/Linux. Need jre1.8.\n"+                    
            "Roman Koldaev, Saratov city, Russia \n"+
            "Home = http://sjce.sf.net , or \n"+
            "https://github.com/harp077/sjce ,\n"+
            "Mail = harp07@mail.ru .",
            sjceTitle, JOptionPane.INFORMATION_MESSAGE, icon);
    }        

    /*public void About() {
        About dd = new About(frame, true);
        dd.setVisible(true);
    }*/

    public void LinksX() {
        CElinksX celx = new CElinksX(frame, true);
        celx.setVisible(true);
    }

    public void LinksU() {
        CElinksU celu = new CElinksU(frame, true);
        celu.setVisible(true);
    }

    public void useTime() {
        if (aktion.UseClock.equals("true")) {
            aktion.UseClock = "false";
            //aktion.Time=Integer.MAX_VALUE;
            bUseClock.setSelected(false);
            mUseClock.setSelected(false);
            sidePanel.remove(frame.chessClock);
            mTime.setEnabled(false);
            bcomboTime.setEnabled(false);
            sidePanel.updateUI();
            frame.mUseClock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/clock-stop-16.png")));
            frame.bUseClock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/clock-stop-24.png")));
        } else {
            aktion.UseClock = "true";
            //aktion.Time=10;
            //aktion.changeTime(10);
            bUseClock.setSelected(true);
            mUseClock.setSelected(true);
            sidePanel.add(frame.chessClock, BorderLayout.NORTH);
            mTime.setEnabled(true);
            bcomboTime.setEnabled(true);
            sidePanel.updateUI();
            frame.mUseClock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/clock-plus-16.png")));
            frame.bUseClock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/clock-plus-24.png")));
        }
    }

    public void useSoundSwitch() {
        if (aktion.useSound.equals("true")) {
            aktion.useSound = "false";
            frame.mUseSound.setSelected(false);
            frame.bUseSound.setSelected(false);
            frame.mUseSound.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/speaker_minus.png")));
            frame.bUseSound.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/sound-delete-icon.png")));
        } else {
            aktion.useSound = "true";
            frame.mUseSound.setSelected(true);
            frame.bUseSound.setSelected(true);
            frame.mUseSound.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/speaker_plus.png")));
            frame.bUseSound.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/sound-add-icon.png")));
        }
    }

    public static void setSkin() {
        try {
            UIManager.setLookAndFeel(aktion.currentLAF);
            SwingUtilities.updateComponentTreeUI(frame);
            frame.pack();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(XChessFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(XChessFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(XChessFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(XChessFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void appInit() {
        aktion.enableEffects();
        if (aktion.Mode.equals("hard")) {
            mModeHard.setSelected(true);
            mModeEasy.setSelected(false);
        } else {
            mModeHard.setSelected(false);
            mModeEasy.setSelected(true);
        }
        bcomboTime.setSelectedItem("" + aktion.Time);
        bcomboMode.setSelectedItem(aktion.Mode);
        bcomboDepth.setSelectedItem("" + aktion.Depth);
        if (aktion.UseClock.equals("true")) {
            bUseClock.setSelected(true);
            mUseClock.setSelected(true);
            sidePanel.add(chessClock, BorderLayout.NORTH);
            mTime.setEnabled(true);
            bcomboTime.setEnabled(true);
            frame.mUseClock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/clock-plus-16.png")));
            frame.bUseClock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/clock-plus-24.png")));
        } else {
            bUseClock.setSelected(false);
            mUseClock.setSelected(false);
            sidePanel.remove(chessClock);
            mTime.setEnabled(false);
            bcomboTime.setEnabled(false);
            frame.mUseClock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/clock-stop-16.png")));
            frame.bUseClock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/clock-stop-24.png")));
        }
        aktion.changeDepth(aktion.Depth);
        if (aktion.useSound.equals("false")) {
            aktion.useSound = "false";
            frame.mUseSound.setSelected(false);
            frame.bUseSound.setSelected(false);
            frame.mUseSound.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/speaker_minus.png")));
            frame.bUseSound.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/sound-delete-icon.png")));
        } else {
            aktion.useSound = "true";
            frame.mUseSound.setSelected(true);
            frame.bUseSound.setSelected(true);
            frame.mUseSound.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/speaker_plus.png")));
            frame.bUseSound.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/sound-add-icon.png")));
        }
    }

    public static void viborCE(String ce, String colorCE) {
        if (colorCE.equals("white")) {
            whitePlayerCE = ce;
            comboWPlayerCE.setSelectedItem(ce);
        } else {
            blackPlayerCE = ce;
            comboBPlayerCE.setSelectedItem(ce);
        }
        switch (ce) {
            case "Human":
                if (colorCE.equals("white")) {
                    setIconAndTipCE("ce/logo/user-white.png", colorCE, "human");
                } else {
                    setIconAndTipCE("ce/logo/user-black.png", colorCE, "human");
                }
                break;
            case "Chess22k":
                setIconAndTipCE("ce/logo/22k-well_100x50.png", colorCE, "uci");
                break;
            case "Alf":
                setIconAndTipCE("ce/logo/alf_100x50.gif", colorCE, "xboard");
                break;
            case "Animats":
                setIconAndTipCE("ce/logo/animatschess_100x50.gif", colorCE, "xboard");
                frame.bUndoLast.setEnabled(false);
                frame.mUndoLast.setEnabled(false);
                break;
            case "ArabianKnight":
                setIconAndTipCE("ce/logo/arabian_knight_100x50.gif", colorCE, "xboard");
                break;
            case "Bagatur":
                setIconAndTipCE("ce/logo/bagatur_100x50.png", colorCE, "uci");
                break;
            case "BremboCE":
                setIconAndTipCE("ce/logo/brembo-well_100x50.png", colorCE, "xboard");
                break;
            case "Calculon":
                setIconAndTipCE("ce/logo/calculon-black_100x50.png", colorCE, "uci");
                break;
            case "Carballo":
                setIconAndTipCE("ce/logo/carballo_100x50.png", colorCE, "uci");
                break;
            case "CaveChess":
                setIconAndTipCE("ce/logo/cave_100x50.png", colorCE, "xboard");
                frame.bUndoLast.setEnabled(false);
                frame.mUndoLast.setEnabled(false);
                break;
            case "CupCake":
                setIconAndTipCE("ce/logo/cupcake_100x50.gif", colorCE, "xboard");
                frame.bUndoLast.setEnabled(false);
                frame.mUndoLast.setEnabled(false);
                break;
            case "Cuckoo":
                setIconAndTipCE("ce/logo/cuckoochess_100x50.gif", colorCE, "uci");
                break;
            case "ChessBotX":
                setIconAndTipCE("ce/logo/ChessBotX 3.png", colorCE, "xboard");
                frame.bUndoLast.setEnabled(false);
                frame.mUndoLast.setEnabled(false);
                break;
            case "DeepBrutePos":
                setIconAndTipCE("ce/logo/pos_100x50.gif", colorCE, "xboard");
                frame.bUndoLast.setEnabled(false);
                frame.mUndoLast.setEnabled(false);
                break;
            case "Detroid":
                setIconAndTipCE("ce/logo/detroid-well_100x50.png", colorCE, "uci");
                break;
            case "Eden":
                setIconAndTipCE("ce/logo/Eden2_100x50.gif", colorCE, "uci");
                break;
            case "FairyPrincess":
                setIconAndTipCE("ce/logo/fp_100x50.png", colorCE, "xboard");
                break;
            case "Fischerle":
                setIconAndTipCE("ce/logo/fischerle_100x50.png", colorCE, "uci");
                JOptionPane.showMessageDialog(frame, "Fischerle create by Dr. Roland Stuckardt - http://www.stuckardt.de/\nSJCE use Fischerle version 0.9.70 SE 32 bit\nDocuments about Fischerle and licence see please\nin folder: /ce/Fischerle/");
                break;
            case "Flux":
                setIconAndTipCE("ce/logo/fluxII_100x50.gif", colorCE, "uci");
                break;
            case "Frittle":
                setIconAndTipCE("ce/logo/frittle_100x50.gif", colorCE, "xboard");
                break;
            case "FrankWalter":
                setIconAndTipCE("ce/logo/frank-walter_100x50.gif", colorCE, "xboard");
                break;
            case "Gladiator":
                setIconAndTipCE("ce/logo/gladiator7_100x50.jpg", colorCE, "xboard");
                frame.bUndoLast.setEnabled(false);
                frame.mUndoLast.setEnabled(false);
                break;
            case "GNU Chess":
                setIconAndTipCE("ce/logo/gnu-chess_100x50.png", colorCE, "xboard");
                break;
            case "Jchess":
                setIconAndTipCE("ce/logo/jchess_100x50.gif", colorCE, "xboard");
                frame.bUndoLast.setEnabled(false);
                frame.mUndoLast.setEnabled(false);
                break;
            case "Javalin":
                setIconAndTipCE("ce/logo/javalin-native_100x50.png", colorCE, "xboard");
                frame.bUndoLast.setEnabled(false);
                frame.mUndoLast.setEnabled(false);
                break;
            case "jChecs":
                setIconAndTipCE("ce/logo/jchecs-native_100x50.png", colorCE, "xboard");
                aktion.gojChecsSelectCE();
                //frame.bUndoLast.setEnabled(false); frame.mUndoLast.setEnabled(false);
                break;
            case "Kasparov":
                setIconAndTipCE("ce/logo/kasparov-chess_100x50.png", colorCE, "uci");
                break;
            case "KennyClassIQ":
                setIconAndTipCE("ce/logo/KennyClassIQ_100x50.png", colorCE, "xboard");
                break;
            case "KingsOut":
                setIconAndTipCE("ce/logo/kingsout_100x50_2.gif", colorCE, "xboard");
                break;
            case "Koedem":
                setIconAndTipCE("ce/logo/koedem_100x50.png", colorCE, "uci");
                break;
            case "Krudo":
                setIconAndTipCE("ce/logo/krudo.png", colorCE, "uci");
                break;
            case "Magnum":
                setIconAndTipCE("ce/logo/magnum_100x50.gif", colorCE, "uci");
                break;
            case "Mediocre":
                setIconAndTipCE("ce/logo/mediocre_100x50.gif", colorCE, "uci");
                break;
            case "OliThink":
                setIconAndTipCE("ce/logo/olithink-java_100x50.gif", colorCE, "xboard");
                break;
            case "Presbyter":
                setIconAndTipCE("ce/logo/presbyter_100x50.png", colorCE, "uci");
                break;
            case "Phoenix":
                setIconAndTipCE("ce/logo/phoenix-well_100x27.png", colorCE, "uci");
                break;
            case "Pulse":
                setIconAndTipCE("ce/logo/pulse_100x50.png", colorCE, "uci");
                break;
            case "Rival":
                setIconAndTipCE("ce/logo/RivalUCI_100x50.gif", colorCE, "uci");
                //JOptionPane.showMessageDialog(frame,"Pay attention: Rival have a 1 bug: \nwhen loses - he does 2 identical moves,\nand the last move already does after a mat state,\nfor this reason from a board the 1 piece can be gone, \nbut in general - it is well engine");
                break;
            case "Rumney":
                setIconAndTipCE("ce/logo/rumney_100x29.png", colorCE, "uci");
                break;
            case "Talvmenni":
                setIconAndTipCE("ce/logo/talvmenni_100x50.gif", colorCE, "xboard");
                frame.bUndoLast.setEnabled(false);
                frame.mUndoLast.setEnabled(false);
                break;
            case "Tiffanys":
                setIconAndTipCE("ce/logo/Tiffanys 2.png", colorCE, "xboard");
                frame.bUndoLast.setEnabled(false);
                frame.mUndoLast.setEnabled(false);
                break;
            case "Tri-OS":
                setIconAndTipCE("ce/logo/tri-os_100x50.png", colorCE, "xboard");
                //JOptionPane.showMessageDialog(frame,"Pay attention: \nTri-OS don't support en-passant !\ntherefore engine tournament with Tri-OS may be interrupted");
                break;
            case "Unidexter":
                setIconAndTipCE("ce/logo/unidexter_100x50.png", colorCE, "uci");
                break;
            case "Ziggy":
                setIconAndTipCE("ce/logo/ziggy_100x50.gif", colorCE, "uci");
                break;
        }
    }

    public static void CopyToClipBoard(String cps) {
        ClipboardTextTransfer textTransfer = new ClipboardTextTransfer();
        textTransfer.setClipboardContents(cps);
    }

    public static void enableEffects() {
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.ARM);
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.FOCUS);
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.FOCUS_LOOP_ANIMATION);
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.GHOSTING_BUTTON_PRESS);
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.GHOSTING_ICON_ROLLOVER);
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.ICON_GLOW);
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.PRESS);
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.ROLLOVER);
        AnimationConfigurationManager.getInstance().allowAnimations(AnimationFacet.SELECTION);
        //AnimationConfigurationManager.getInstance().setTimelineDuration(500);        
    }

    public static void setIconAndTipCE(String pathce, String colorCEn, String tipCE) {
        frame.bUndoLast.setEnabled(true);
        frame.mUndoLast.setEnabled(true);
        if (colorCEn.equals("white")) {
            whitePlayerTip = tipCE;
            frame.chessClock.label[2].setIcon(new ImageIcon(pathce));
        } else {
            blackPlayerTip = tipCE;
            frame.chessClock.label[3].setIcon(new ImageIcon(pathce));
        }
    }

    public void killEngineProc(String color) {
        switch (color) {
            case "white":
                if (frame.whiteEngineAgent.engineIOwhite != null) {
                    frame.whiteEngineAgent.engineIOwhite.writeLine("quit");
                    frame.whiteEngineAgent.engineIOwhite.destroy();  // MAKED ON 13-05-16 !!!
                }
                //else JOptionPane.showMessageDialog(frame, "White Engine process not running !");
                break;
            case "black":
                if (frame.blackEngineAgent.engineIOblack != null) {
                    frame.blackEngineAgent.engineIOblack.writeLine("quit");
                    frame.blackEngineAgent.engineIOblack.destroy();
                }
            //else JOptionPane.showMessageDialog(frame, "Black Engine process not running !");                 
        }
    }

    public void killAllEngines() {
        aktion.killEngineProc("white");
        aktion.killEngineProc("black");
        outputArea.setText("Please, select chess engine and press New Game !");
        chessClock.stop();
        chessClock.setTime(Actions.Time * 60000);
        //if (aktion.EngineColor.equals("white"))
        //    chessClock.setTurn(ChessClock.WHITE_TURN);
        //else
        //    chessClock.setTurn(ChessClock.BLACK_TURN);
        boardUI.setBoard(Utility.INITIAL_BOARD);
        moveListUI.clear();
        frame.loadBoardTheme();
        //EngineAgent.engineIO=null;
        restColorWhite_restColorBlack();
    }

    // peregruzka    
    public void sendEngineCmd(String color) {
        switch (color) {
            case "white":
                if (frame.whiteEngineAgent.engineIOwhite != null) {
                    String buf = "";
                    buf = (String) JOptionPane.showInputDialog(frame, " Please, enter Command for send to White Engine ( be careful ! ): ", "Command for send to White Engine", JOptionPane.OK_CANCEL_OPTION, new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/knight-white-32.png")), null, null);
                    if (buf != null && buf.length() > 0) {
                        frame.whiteEngineAgent.engineIOwhite.writeLine(buf);
                        outputArea.append("<write to White Engine>: " + buf + "\n");
                        if (buf.equals("quit")) {
                            aktion.killEngineProc("white");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "White Engine process not running !");
                }
                break;
            case "black":
                if (frame.blackEngineAgent.engineIOblack != null) {
                    String buf = "";
                    buf = (String) JOptionPane.showInputDialog(frame, " Please, enter Command for send to Black Engine ( be careful ! ): ", "Command for send to Black Engine", JOptionPane.OK_CANCEL_OPTION, new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/knight-black-32.png")), null, null);
                    if (buf != null && buf.length() > 0) {
                        frame.blackEngineAgent.engineIOblack.writeLine(buf);
                        outputArea.append("<write to Black Engine>: " + buf + "\n");
                        if (buf.equals("quit")) {
                            aktion.killEngineProc("black");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Black Engine process not running !");
                }
                break;
        }
    }

    // peregruzka
    public static void sendEngineCmd(String color, String buf) {
        switch (color) {
            case "white":
                if (frame.whiteEngineAgent.engineIOwhite != null) {
                    if (buf != null && buf.length() > 0) {
                        frame.whiteEngineAgent.engineIOwhite.writeLine(buf);
                        outputArea.append("<write to White Engine>: " + buf + "\n");
                    }
                }
                break;
            case "black":
                if (frame.blackEngineAgent.engineIOblack != null) {
                    if (buf != null && buf.length() > 0) {
                        frame.blackEngineAgent.engineIOblack.writeLine(buf);
                        outputArea.append("<write to Black Engine>: " + buf + "\n");
                    }
                }
                break;
        }
    }

    public void moveColorWhite_restColorBlack() {
        frame.chessClock.label[0].setBackground(moveColor);
        frame.chessClock.label[2].setBackground(moveColor);
        frame.chessClock.label[1].setBackground(restColor);
        frame.chessClock.label[3].setBackground(restColor);
    }

    public void restColorWhite_moveColorBlack() {
        frame.chessClock.label[0].setBackground(restColor);
        frame.chessClock.label[2].setBackground(restColor);
        frame.chessClock.label[1].setBackground(moveColor);
        frame.chessClock.label[3].setBackground(moveColor);
    }

    public void restColorWhite_restColorBlack() {
        frame.chessClock.label[0].setBackground(restColor);
        frame.chessClock.label[2].setBackground(restColor);
        frame.chessClock.label[1].setBackground(restColor);
        frame.chessClock.label[3].setBackground(restColor);
    }

    public static void playWAV(String filename) {
        File snd = null;
        snd = new File(filename);
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(snd));
            clip.start();
            //Thread.sleep(300);
            //clip.close();
        } catch (Exception exc) {
            exc.printStackTrace(System.out);
        }
    }

    public static void undoLastMove(Move move, String color) {

        if (!gameTip.equals("EE")) {
            if (whitePlayerTip.equals("uci") || blackPlayerTip.equals("uci")) {
                String[] bf = uciAllMovesString.trim().split("\\s+");
                String bufer = "";
                for (int i = 0; i < bf.length - 1; i++) {
                    bufer = bufer + " " + bf[i];
                }
                uciAllMovesString = bufer;
            }
            switch (color) {
                case "white":
                    if (whitePlayerTip.equals("xboard")) {
                        switch (whitePlayerCE) {
                            case "Alf":
                                sendEngineCmd("white", "undo");
                                sendEngineCmd("white", "undo");
                                break;
                            //case "Animats": sendEngineCmd("white","undo"); sendEngineCmd("white","undo"); break; 
                            case "ArabianKnight":
                                sendEngineCmd("white", "undo");
                                sendEngineCmd("white", "undo");
                                break;
                            case "BremboCE":
                                sendEngineCmd("white", "remove");
                                break;
                            //case "CaveChess": sendEngineCmd("white","undo"); sendEngineCmd("white","undo"); break; 
                            //case "CupCake": sendEngineCmd("white","undo"); sendEngineCmd("white","undo"); break;
                            //case "ChessBotX": sendEngineCmd("white","undo"); sendEngineCmd("white","undo"); break;
                            //case "DeepBrutePos": sendEngineCmd("white","undo"); sendEngineCmd("white","undo"); break;
                            case "FrankWalter":
                                sendEngineCmd("white", "undo");
                                sendEngineCmd("white", "undo");
                                break;
                            case "Frittle":
                                sendEngineCmd("white", "undo");
                                sendEngineCmd("white", "undo");
                                break;
                            //case "Gladiator": sendEngineCmd("white","remove"); sendEngineCmd("white","remove"); break; 
                            case "GNU Chess":
                                sendEngineCmd("white", "undo");
                                sendEngineCmd("white", "undo");
                                break;
                            //case "Javalin": sendEngineCmd("white","undo"); sendEngineCmd("white","undo"); break; 
                            //case "Jchess": sendEngineCmd("white","undo"); sendEngineCmd("white","undo"); break; 
                            case "KingsOut":
                                sendEngineCmd("white", "remove");
                                break;
                            case "OliThink":
                                sendEngineCmd("white", "remove");
                                break;
                            //case "Talvmenni": sendEngineCmd("white","undo"); sendEngineCmd("white","undo"); break; 
                            //case "Tiffanys": sendEngineCmd("white","undo"); sendEngineCmd("white","undo"); break; 
                            case "Tri-OS":
                                sendEngineCmd("white", "undo");
                                break;
                            case "jChecs":
                                sendEngineCmd("white", "undo");
                                sendEngineCmd("white", "undo");
                                break;
                        }
                    }
                    break;
                case "black":
                    if (blackPlayerTip.equals("xboard")) {
                        switch (blackPlayerCE) {
                            case "Alf":
                                sendEngineCmd("black", "undo");
                                sendEngineCmd("black", "undo");
                                break;
                            //case "Animats": sendEngineCmd("black","remove"); break;
                            case "ArabianKnight":
                                sendEngineCmd("black", "undo");
                                sendEngineCmd("black", "undo");
                                break;
                            case "BremboCE":
                                sendEngineCmd("black", "remove");
                                break;
                            //case "CaveChess": sendEngineCmd("black","undo"); sendEngineCmd("black","undo"); break; 
                            //case "CupCake": sendEngineCmd("black","remove"); break;
                            //case "ChessBotX": sendEngineCmd("black","undo"); sendEngineCmd("black","undo"); break;
                            //case "DeepBrutePos": sendEngineCmd("black","remove"); break;
                            case "FrankWalter":
                                sendEngineCmd("black", "undo");
                                sendEngineCmd("black", "undo");
                                break;
                            case "Frittle":
                                sendEngineCmd("black", "undo");
                                sendEngineCmd("black", "undo");
                                break;
                            //case "Gladiator": sendEngineCmd("black","undo"); sendEngineCmd("black","undo"); break; 
                            case "GNU Chess":
                                sendEngineCmd("black", "undo");
                                sendEngineCmd("black", "undo");
                                break;
                            //case "Javalin": sendEngineCmd("black","remove"); break; 
                            //case "Jchess": sendEngineCmd("black","remove"); break; 
                            case "KingsOut":
                                sendEngineCmd("black", "remove");
                                break;
                            case "OliThink":
                                sendEngineCmd("black", "remove");
                                break;
                            //case "Talvmenni": sendEngineCmd("black","remove"); break; 
                            //case "Tiffanys": sendEngineCmd("black","remove"); break;
                            case "Tri-OS":
                                sendEngineCmd("black", "undo");
                                break;
                            case "jChecs":
                                sendEngineCmd("black", "undo");
                                sendEngineCmd("black", "undo");
                                break;
                        }
                    }
                    break;
            }
            int source = move.getSource();
            int destin = move.getDestination();
            //int sourcePiece=boardUI.getPiece(source);
            //int destinPiece=boardUI.getPiece(destin);            
            move.undoMove(boardUI.getBoard());
            //boardUI.update(frame.SQUARES);
            //int type = move.doMove(boardUI.getBoard());
            //boardUI.setPiece(sourcePiece, source);
            //boardUI.setPiece(destinPiece, destin); 
            boardUI.update(source);
            boardUI.update(destin);
            boardUI.update(frame.SQUARES);
            MoveListUI.moveList.remove(MoveListUI.moveList.lastIndexOf(move));
            MoveListUI.listModel.removeElementAt(MoveListUI.listModel.size() - 1);
            //MoveListUI.count=MoveListUI.count-2;
        }
    }

    public static void bmUndoLastMoveEventHandler() {
        if (gameTip.equals("EE")) {
            JOptionPane.showMessageDialog(frame, "Undo not support for engine tournament !");
            return;
        } else {
            if (whitePlayerCE.equals("Animats") || whitePlayerCE.equals("CaveChess") || whitePlayerCE.equals("CupCake") || whitePlayerCE.equals("ChessBotX") || whitePlayerCE.equals("DeepBrutePos") || whitePlayerCE.equals("Tiffanys") || whitePlayerCE.equals("Gladiator") || whitePlayerCE.equals("Talvmenni") || whitePlayerCE.equals("Jchess") || whitePlayerCE.equals("Javalin")) {
                JOptionPane.showMessageDialog(frame, "This engine not support undo or remove comands !");
                return;
            }
            if (blackPlayerCE.equals("Animats") || blackPlayerCE.equals("CaveChess") || blackPlayerCE.equals("CupCake") || blackPlayerCE.equals("ChessBotX") || blackPlayerCE.equals("DeepBrutePos") || blackPlayerCE.equals("Tiffanys") || blackPlayerCE.equals("Gladiator") || blackPlayerCE.equals("Talvmenni") || blackPlayerCE.equals("Jchess") || blackPlayerCE.equals("Javalin")) {
                JOptionPane.showMessageDialog(frame, "This engine not support undo or remove comands  !");
                return;
            }
            if (aktion.whiteLastMove != null && aktion.blackLastMove != null) {
                if (gameTip.equals("EH")) {
                    aktion.undoLastMove(aktion.whiteLastMove, "white");
                    aktion.undoLastMove(aktion.blackLastMove, "black");
                }
                if (gameTip.equals("HE")) {
                    aktion.undoLastMove(aktion.blackLastMove, "black");
                    aktion.undoLastMove(aktion.whiteLastMove, "white");
                }
                if (gameTip.equals("HH")) {
                    aktion.undoLastMove(aktion.whiteLastMove, "white");
                    aktion.undoLastMove(aktion.blackLastMove, "black");
                }
                aktion.whiteLastMove = null;
                aktion.blackLastMove = null;
            } else {
                JOptionPane.showMessageDialog(frame, "Please, at first make a move and then you can make undo !");
            }
        }
    }

    public static void logShow() {
        logFrame = new LogShow();
        logFrame.gameLogTextArea.setText(frame.outputArea.getText());
        logFrame.setVisible(true);
    }

    public static void clipboardShow() {
        TextTransfer textTransfer = new TextTransfer();
        String buf = textTransfer.getClipboardContents();
        ShowClipBoard sc = new ShowClipBoard(logFrame, true);
        sc.ClipboardText.setText(buf);
        sc.setVisible(true);
    }

    public static void deleteLogFile(String path) {
        try {
            FileWorker.delete(path);
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void gojChecsSelectCE() {
        String changeLook = (String) JOptionPane.showInputDialog(frame, "Choose jChecs engine algorithm Here:", "Select jChecs engine algorithm", JOptionPane.QUESTION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/jchecs-native_100x50.png")), jchecsCeSelect.toArray(), null);
        if (changeLook != null) {
            for (int a = 0; a < jchecsCeSelect.size(); a++) {
                if (changeLook.equals(jchecsCeSelect.get(a))) {
                    jchecsEngineTip = jchecsCeSelect.get(a);
                    break;
                }
            }
        }
    }

}
