package SJCE;

import SJCE.xgui.Interfaces.IMainFrameConst;
import SJCE.xgui.Interfaces.IChessContext;
import SJCE.Cfg.AppCfgPref;
import SJCE.Cfg.BoardThemeSelect;
import SJCE.more.Actions;
import SJCE.more.Donate;
import SJCE.more.Log.LogShow;
import SJCE.more.Mixer.MixerFrame;
import SJCE.more.Run_Thread;
import SJCE.xgui.Agent.Agent;
import SJCE.xgui.Agent.EngineAgent;
import SJCE.xgui.Agent.EngineAgentExt;
import SJCE.xgui.Agent.UserAgent;
import SJCE.xgui.EventObject.EngineEvent;
import SJCE.xgui.Interfaces.EngineAdapter;
import SJCE.xgui.JList.MoveListUI;
import SJCE.xgui.JPanel.BoardUI;
import SJCE.xgui.JPanel.ChessClock;
import SJCE.xgui.Utility;
import SJCE.more.Mixer.ControlSound;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import static java.lang.System.err;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.Image;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

public class XChessFrame extends JFrame implements IChessContext, IMainFrameConst {
    public static ChessClock chessClock;
    public static MoveListUI moveListUI;
    public static BoardUI boardUI;
    //public JOptionPane MSG;
    public static Agent alphaAgent;
    public static Agent betaAgent;
    public static XChessFrame frame;
    public static BorderLayout BL;
    public static java.awt.event.ActionEvent ae;
    public static List<String> lookAndFeelsDisplay = new ArrayList<>();
    public static List<String> lookAndFeelsRealNames = new ArrayList<>();
    public static Actions aktion=new Actions();
    public static List<String> selectCE = new ArrayList<>();
    public static EngineAgent whiteEngineAgent;
    public static EngineAgent blackEngineAgent;  
    public static EngineAgentExt whiteEngineAgentExt;
    public static EngineAgentExt blackEngineAgentExt;      
    public static final int borderPanelSize=415;
    public static final int boardUISize=400;
    //public static EngineIO engineIO;
    public ImageIcon welcomeIcon = new ImageIcon(getClass().getResource("/SJCE/img/sjce-130x87.png"));
    //public static JLabel ceLabel=new JLabel();
    public ImageIcon FrameIcon = new ImageIcon(getClass().getResource("/SJCE/img/SubFrameIcon.png"));
    public static final String sjceTitle="SJCE = Strong Java Chess Engines, maven build 07.08.18";
    public static LogShow logFrame; 
    
    public XChessFrame() {
        initComponents();
        this.setTitle(sjceTitle);
        ImageIcon icone = new ImageIcon(getClass().getResource("/SJCE/img/SubFrameIcon.png"));
        this.setIconImage(icone.getImage());
        this.selectCE.addAll(Arrays.asList(this.selectEnginesArray));
        //bMode.setVisible(false);
        //mrKenny.setVisible(false);
        //mrCalculon.setVisible(false);
        //this.bEngine.setModel(selectCE);
        this.boardSetSize ();
        //File themesFig = new File("themes");
        //String [] themesList = themes.list();
        //selectBoardTheme.clear();
        //for (int i=0; i<themesList.length; i++)   { 
                //System.out.println(i+")"+themesList[i]);
                //selectBoardTheme.add(themesList[i]);
            //}   
        //selectBoardTheme.add(themeList.list());
        this.comboWPlayerCE.setModel(new javax.swing.DefaultComboBoxModel<>(selectEnginesArray));        
        this.comboWPlayerCE.setSelectedItem(aktion.whitePlayerCE);
        this.comboBPlayerCE.setModel(new javax.swing.DefaultComboBoxModel<>(selectEnginesArray));        
        this.comboBPlayerCE.setSelectedItem(aktion.blackPlayerCE); 
        this.bcomboDepth.setModel(new javax.swing.DefaultComboBoxModel<>(arrayDepth));        
        this.bcomboDepth.setSelectedItem(""+aktion.Depth);        
        outputArea = new JTextArea() {
            Image image = welcomeIcon.getImage();
            { setOpaque(false); }
            public void paint(Graphics g) {
                g.drawImage(image,outputArea.getWidth()-135, 0, this);
                super.paint(g);
            }
        };
        //this.setResizable(true);
        //this.bDonate.setVisible(false);
        //this.bAbout.setVisible(false); 
        //this.bLinks.setVisible(false);        
        this.outputArea.setEditable(false);
        this.outputArea.append("Please, select chess engine and press New Game !");        
    }
    
    public void MixerInit () {
        if ((Actions.currentMixer>0)&(Actions.currentMixer<100/3))
          frame.bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/audio_volume_low.png")));   
        if ((Actions.currentMixer>100/3)&(Actions.currentMixer<200/3))
          frame.bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/audio_volume_medium.png")));
        if (Actions.currentMixer>200/3)
          frame.bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/audio_volume_high.png")));        
        if (Actions.currentMixer==0)
            frame.bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/audio_volume_muted.png")));
        if (Actions.currentMute.equals("true")) 
         {
            ControlSound.setMasterOutputMute(true);
            //mf.bMute.setSelected(true);
            System.out.println("Volume set to ZERO = 0");
            //mf.MixerFrameSlider.setEnabled(false);
            frame.bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/audio_volume_muted.png")));   
         }
        try {
            ControlSound.setMasterOutputVolume((float)Actions.currentMixer/100);
        } catch (RuntimeException rr) {System.out.println("Master output port not found");}
    }            
    
    public void MixerSet () {
        MixerFrame mf=new MixerFrame(frame,true);
        mf.setLocationRelativeTo(frame);
        mf.setVisible(true);
        MixerFrame.MixerFrameSlider.setValue(Actions.currentMixer);
        MixerInit();
    }    
    
    public void boardSetSize () {
        borderPanel.setSize(borderPanelSize,borderPanelSize);
        borderPanel.setPreferredSize(new Dimension(borderPanelSize,borderPanelSize));
        borderPanel.setMinimumSize(new Dimension(borderPanelSize,borderPanelSize)); 
        //boardUI.setSize(boardUISize,boardUISize);
        //boardUI.setPreferredSize(new Dimension(boardUISize,boardUISize));
        //boardUI.setMinimumSize(new Dimension(boardUISize,boardUISize)); 
        horizontalSplit.setDividerLocation(borderPanelSize);
        horizontalSplit.setDividerSize(0);
        verticalSplit.setDividerLocation(borderPanelSize);
        verticalSplit.setDividerSize(0);        
    }
    
    public void newGame(int agent1, int agent2) {
        //boardUI.setVisible(true);
        //aktion.deleteLogFile ("log/Bagatur.log");
        aktion.enemyTip="";
        aktion.gameTip="";
        aktion.uciAllMovesString="";
        aktion.enginePromotionFig="";
        aktion.enginePromotionType="";
        aktion.blackRivalMovesString="";
        aktion.whiteRivalMovesString="";        
        aktion.restColorWhite_restColorBlack();
        loadBoardTheme();
        if(alphaAgent != null) alphaAgent.dispose();
        if(betaAgent != null) betaAgent.dispose();
        if (whiteEngineAgent.engineIOwhite!=null) whiteEngineAgent.engineIOwhite.destroy();  // MAKED ON 13-05-16 !!!
        if (blackEngineAgent.engineIOblack!=null) blackEngineAgent.engineIOblack.destroy();
    //aktion.killAllEngines();
        outputArea.setText("");
        chessClock.stop();
        chessClock.setTime(aktion.Time*60000);
    //chessClock.setTime(chessClock.WHITE_TURN, Actions.Time*60000);
    //chessClock.setTime(chessClock.BLACK_TURN, Actions.Time*60000);        
        chessClock.setTurn(ChessClock.WHITE_TURN);
        boardUI.setBoard(Utility.INITIAL_BOARD);
        moveListUI.clear();
        ////////////////////
        if (aktion.whitePlayerCE.equals("Human"))
            alphaAgent = new UserAgent(frame,"Human","white","human");
        else
            alphaAgent = whiteEngineAgent.createEngine(frame,aktion.whitePlayerCE,"white",aktion.whitePlayerTip);
        //////////////////////////
        if (aktion.blackPlayerCE.equals("Human"))
            betaAgent = new UserAgent(frame,"Human","black","human");
        else
            betaAgent = blackEngineAgent.createEngine(frame,aktion.blackPlayerCE,"black",aktion.blackPlayerTip);        
        /////////////////////////
        alphaAgent.setOpponentAgent(betaAgent);
        alphaAgent.setTurn(chessClock.WHITE_TURN);
        betaAgent.setOpponentAgent(alphaAgent);
        betaAgent.setTurn(chessClock.BLACK_TURN);
        alphaAgent.newGame();
        betaAgent.newGame();
        System.out.println("whitePlayerTip = "+aktion.whitePlayerTip);
        System.out.println("blackPlayerTip = "+aktion.blackPlayerTip);
        if (aktion.whitePlayerTip.equals("xboard") && aktion.blackPlayerTip.equals("uci"))
            aktion.enemyTip="another";
        if (aktion.whitePlayerTip.equals("xboard") && aktion.blackPlayerTip.equals("xboard"))
            aktion.enemyTip="like";
        if (aktion.whitePlayerTip.equals("uci") && aktion.blackPlayerTip.equals("xboard"))
            aktion.enemyTip="another";
        if (aktion.whitePlayerTip.equals("uci") && aktion.blackPlayerTip.equals("uci"))
            aktion.enemyTip="like";        
        System.out.println("Enemy Type = "+aktion.enemyTip);
        System.out.println("Depth = "+aktion.Depth);        
        System.out.println("Time = "+aktion.Time);
        if (!aktion.whitePlayerCE.equals("Human") && !aktion.blackPlayerCE.equals("Human"))
            {
                aktion.gameTip="EE"; 
                frame.bUndoLast.setEnabled(false); 
                frame.mUndoLast.setEnabled(false);
                if (aktion.useSound.equals("true")) aktion.useSoundSwitch();
            }
        if ( aktion.whitePlayerCE.equals("Human") &&  aktion.blackPlayerCE.equals("Human"))
            aktion.gameTip="HH";        
        if (aktion.whitePlayerCE.equals("Human") && !aktion.blackPlayerCE.equals("Human"))
         {
            aktion.gameTip="HE";
            ((EngineAgent)betaAgent).addIEngineListener(new EngineAdapter() {
            @Override
            public void dataPrinted(EngineEvent e) {
                outputArea.setText(outputArea.getText() + "[OUT] " + e.getData() + "\n");
            }
            @Override
            public void dataEntered(EngineEvent e) {
                outputArea.setText(outputArea.getText() + "[IN] " + e.getData() + "\n");
            }
            });
         } 
        if (!aktion.whitePlayerCE.equals("Human") && aktion.blackPlayerCE.equals("Human"))
         {
            aktion.gameTip="EH";             
            ((EngineAgent)alphaAgent).addIEngineListener(new EngineAdapter() {
            @Override
            public void dataPrinted(EngineEvent e) {
                outputArea.setText(outputArea.getText() + "[OUT] " + e.getData() + "\n");
            }
            @Override
            public void dataEntered(EngineEvent e) {
                outputArea.setText(outputArea.getText() + "[IN] " + e.getData() + "\n");
            }
            });
         }
        System.out.println("Game Type = "+aktion.gameTip);        
        loadBoardTheme();
        // RUN WHITE ENGINES
        //System.out.println("======================= RUN WHITE =========================");
        if (aktion.whitePlayerTip.equals("xboard")) { 
          try {  Thread.sleep(2000);  } 
          catch (InterruptedException ex) {
                 Logger.getLogger(XChessFrame.class.getName()).log(Level.SEVERE, null, ex);
          }
    //chessClock.setTime(aktion.Time*60000);      
          if (!aktion.whitePlayerCE.equals("ArabianKnight") && !aktion.whitePlayerCE.equals("OliThink")&&!aktion.whitePlayerCE.equals("Eden")&&!aktion.whitePlayerCE.equals("Tiffanys")) 
             aktion.sendEngineCmd("white","white");  
          aktion.sendEngineCmd("white","go");
          System.out.println("========================= RUN WHITE ===========================");
        } 
        if (aktion.whitePlayerTip.equals("uci"))  { 
            try {  Thread.sleep(2000);  } 
            catch (InterruptedException ex) {
                    Logger.getLogger(XChessFrame.class.getName()).log(Level.SEVERE, null, ex);
            } 
    //chessClock.setTime(aktion.Time*60000);        
            aktion.sendEngineCmd("white","position startpos");
            aktion.sendEngineCmd("white","go depth " + aktion.Depth);
            System.out.println("========================= RUN WHITE ===========================");
        } 
    }
    
    public BoardUI getBoardUI() {
        return boardUI;
    }
    
    public ChessClock getChessClock() {
        return chessClock;
    }
    
    public MoveListUI getMoveListUI() {
        return moveListUI;
    } 
    
    public void changeLF() {
        //aktion.currentTheme="";
        String changeLook = (String) JOptionPane.showInputDialog(this, "Choose Skin Here:", "Select Skin", JOptionPane.QUESTION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/select-by-color-32.png")), lookAndFeelsDisplay.toArray(), null);
        if (changeLook != null) {
            for (int a = 0; a < lookAndFeelsDisplay.size(); a++) {
                if (changeLook.equals(lookAndFeelsDisplay.get(a))) {
                    try {
                        aktion.currentLAF=lookAndFeelsRealNames.get(a);
                        UIManager.setLookAndFeel(lookAndFeelsRealNames.get(a));
                        SwingUtilities.updateComponentTreeUI(this);
                        this.pack();
                        borderPanel.updateUI();
                        break;
                    }
                    catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                        err.println(ex);
                        ex.printStackTrace(System.err);
                    }
                }
            }
        }
    } 
    
    public void changeCE(String colorCE) {
        String changeLook="";
        switch (colorCE) {
            case "white":
            changeLook = (String) JOptionPane.showInputDialog(this, "Choose White Player Here:", "Select White Player", JOptionPane.QUESTION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/knight-white-32.png")), selectCE.toArray(), null);
            if (changeLook != null) {
                for (int a = 0; a < selectCE.size(); a++) {
                    if (changeLook.equals(selectCE.get(a))) {
                        aktion.whitePlayerCE=selectCE.get(a);
                        comboWPlayerCE.setSelectedItem(aktion.whitePlayerCE);
                        break;
                    }
                }
            }
            break;
            case "black":
            changeLook = (String) JOptionPane.showInputDialog(this, "Choose Black Player Here:", "Select Black Player", JOptionPane.QUESTION_MESSAGE, new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/knight-black-32.png")), selectCE.toArray(), null);
            if (changeLook != null) {
                for (int a = 0; a < selectCE.size(); a++) {
                    if (changeLook.equals(selectCE.get(a))) {
                        aktion.blackPlayerCE=selectCE.get(a);
                        comboBPlayerCE.setSelectedItem(aktion.blackPlayerCE);
                        break;
                    }
                }
            }
            break;            
        }
    } 
    
    public void loadBoardTheme() {
        for (int i=1; i<3; i++)
        {
            boardUI.chessTheme.loadTheme();
            boardUI.chessTheme.adjustTheme(boardUI.getSize());
            boardUI.update(SQUARES);
            borderPanel.repaint();
            borderPanel.updateUI();
            boardUI.repaint();
            boardUI.updateUI();
            SwingUtilities.updateComponentTreeUI(this);
            boardUI.update(SQUARES);
            boardUI.repaint();
            boardUI.updateUI(); 
            //try {
            //    Thread.sleep(100);
            //} catch (InterruptedException ex) {
            //    Logger.getLogger(XChessFrame.class.getName()).log(Level.SEVERE, null, ex);
            //}
        }
    }
    
    public void changeBoardTheme() {
        BoardThemeSelect bts = new BoardThemeSelect(frame,true);
        bts.setVisible(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        verticalSplit = new javax.swing.JSplitPane();
        scrollOutputArea = new javax.swing.JScrollPane();
        outputArea = new javax.swing.JTextArea();
        horizontalSplit = new javax.swing.JSplitPane();
        sidePanel = new javax.swing.JPanel();
        scrollMoveList = new javax.swing.JScrollPane();
        borderPanel = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jToolBar1 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();
        bUndoLast = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        bSelectWhite = new javax.swing.JButton();
        bChangeSkin = new javax.swing.JButton();
        bBoardTheme = new javax.swing.JButton();
        bLinks = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        bDonate = new javax.swing.JButton();
        bAbout = new javax.swing.JButton();
        bExit = new javax.swing.JButton();
        jToolBar3 = new javax.swing.JToolBar();
        bNew = new javax.swing.JButton();
        bKillAll = new javax.swing.JButton();
        bSaveCfg = new javax.swing.JButton();
        bSoundMixer = new javax.swing.JButton();
        bUseSound = new javax.swing.JToggleButton();
        bcomboMode = new javax.swing.JComboBox<>();
        bcomboDepth = new javax.swing.JComboBox<>();
        bcomboTime = new javax.swing.JComboBox();
        bUseClock = new javax.swing.JToggleButton();
        bSendWhite = new javax.swing.JButton();
        bSendBlack = new javax.swing.JButton();
        comboWPlayerCE = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        comboBPlayerCE = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jMenuBar1 = new javax.swing.JMenuBar();
        mFile = new javax.swing.JMenu();
        mNewGame = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        mExit = new javax.swing.JMenuItem();
        mOptions = new javax.swing.JMenu();
        mTimeMenu = new javax.swing.JMenu();
        mUseClock = new javax.swing.JCheckBoxMenuItem();
        mTime = new javax.swing.JMenu();
        mTime5 = new javax.swing.JRadioButtonMenuItem();
        mTime10 = new javax.swing.JRadioButtonMenuItem();
        mTime15 = new javax.swing.JRadioButtonMenuItem();
        mTime20 = new javax.swing.JRadioButtonMenuItem();
        mTime25 = new javax.swing.JRadioButtonMenuItem();
        mTime30 = new javax.swing.JRadioButtonMenuItem();
        jMenu1 = new javax.swing.JMenu();
        mUseSound = new javax.swing.JCheckBoxMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        mBoardTheme = new javax.swing.JMenuItem();
        mChangeSkin = new javax.swing.JMenuItem();
        mSaveCfg = new javax.swing.JMenuItem();
        mPlayers = new javax.swing.JMenu();
        mSelectCEwhite = new javax.swing.JMenuItem();
        mSelectCEblack = new javax.swing.JMenuItem();
        mChessEngines = new javax.swing.JMenu();
        mEngineConfig = new javax.swing.JMenu();
        mEngineMode = new javax.swing.JMenu();
        mModeEasy = new javax.swing.JRadioButtonMenuItem();
        mModeHard = new javax.swing.JRadioButtonMenuItem();
        mEngineDepth = new javax.swing.JMenu();
        mDepth2 = new javax.swing.JRadioButtonMenuItem();
        mDepth3 = new javax.swing.JRadioButtonMenuItem();
        mDepth4 = new javax.swing.JRadioButtonMenuItem();
        mDepth5 = new javax.swing.JRadioButtonMenuItem();
        mDepth6 = new javax.swing.JRadioButtonMenuItem();
        mDepth7 = new javax.swing.JRadioButtonMenuItem();
        mDepth8 = new javax.swing.JRadioButtonMenuItem();
        mDepth9 = new javax.swing.JRadioButtonMenuItem();
        mSendWhite = new javax.swing.JMenuItem();
        mSendBlack = new javax.swing.JMenuItem();
        mKillAll = new javax.swing.JMenuItem();
        mUndoLast = new javax.swing.JMenuItem();
        mInfo = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        mDonate = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(610, 650));
        setUndecorated(true);
        setResizable(false);

        verticalSplit.setDividerLocation(415);
        verticalSplit.setDividerSize(0);
        verticalSplit.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        verticalSplit.setEnabled(false);
        verticalSplit.setMaximumSize(new java.awt.Dimension(550, 550));
        verticalSplit.setMinimumSize(new java.awt.Dimension(550, 550));
        verticalSplit.setOpaque(false);
        verticalSplit.setPreferredSize(new java.awt.Dimension(550, 550));

        scrollOutputArea.setBorder(javax.swing.BorderFactory.createTitledBorder("Engine Output"));

        outputArea.setEditable(false);
        outputArea.setColumns(20);
        outputArea.setRows(5);
        outputArea.setMaximumSize(new java.awt.Dimension(102, 62));
        outputArea.setMinimumSize(new java.awt.Dimension(102, 62));
        scrollOutputArea.setViewportView(outputArea);

        verticalSplit.setBottomComponent(scrollOutputArea);
        scrollOutputArea.getAccessibleContext().setAccessibleParent(verticalSplit);

        horizontalSplit.setDividerLocation(415);
        horizontalSplit.setDividerSize(0);
        horizontalSplit.setEnabled(false);
        horizontalSplit.setOpaque(false);

        sidePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Moves"));
        sidePanel.setMaximumSize(new java.awt.Dimension(120, 450));
        sidePanel.setMinimumSize(new java.awt.Dimension(120, 450));
        sidePanel.setOpaque(false);
        sidePanel.setPreferredSize(new java.awt.Dimension(120, 450));

        javax.swing.GroupLayout sidePanelLayout = new javax.swing.GroupLayout(sidePanel);
        sidePanel.setLayout(sidePanelLayout);
        sidePanelLayout.setHorizontalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollMoveList, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
        );
        sidePanelLayout.setVerticalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollMoveList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
        );

        horizontalSplit.setRightComponent(sidePanel);
        sidePanel.getAccessibleContext().setAccessibleParent(horizontalSplit);

        borderPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Chess Board"));
        borderPanel.setDoubleBuffered(false);
        borderPanel.setMaximumSize(new java.awt.Dimension(410, 410));
        borderPanel.setMinimumSize(new java.awt.Dimension(410, 410));
        borderPanel.setPreferredSize(new java.awt.Dimension(410, 410));

        javax.swing.GroupLayout borderPanelLayout = new javax.swing.GroupLayout(borderPanel);
        borderPanel.setLayout(borderPanelLayout);
        borderPanelLayout.setHorizontalGroup(
            borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 404, Short.MAX_VALUE)
        );
        borderPanelLayout.setVerticalGroup(
            borderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 428, Short.MAX_VALUE)
        );

        horizontalSplit.setLeftComponent(borderPanel);

        verticalSplit.setTopComponent(horizontalSplit);
        horizontalSplit.getAccessibleContext().setAccessibleParent(verticalSplit);

        getContentPane().add(verticalSplit, java.awt.BorderLayout.CENTER);

        jToolBar2.setFloatable(false);
        jToolBar2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jToolBar1.setFloatable(false);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/log-24-blue.png"))); // NOI18N
        jButton3.setToolTipText("Show Game Log in separate window");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        bUndoLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/undo-red-24.png"))); // NOI18N
        bUndoLast.setToolTipText("Undo Last One Move");
        bUndoLast.setFocusable(false);
        bUndoLast.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bUndoLast.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bUndoLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUndoLastActionPerformed(evt);
            }
        });
        jToolBar1.add(bUndoLast);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/knight-black-24.png"))); // NOI18N
        jButton1.setToolTipText("Select Black Player Chess Engine");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        bSelectWhite.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/knight-white-24.png"))); // NOI18N
        bSelectWhite.setToolTipText("Select White Player Chess Engine");
        bSelectWhite.setFocusable(false);
        bSelectWhite.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bSelectWhite.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bSelectWhite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSelectWhiteActionPerformed(evt);
            }
        });
        jToolBar1.add(bSelectWhite);

        bChangeSkin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/skin_color_chooser-24.png"))); // NOI18N
        bChangeSkin.setToolTipText("Change Skin");
        bChangeSkin.setFocusable(false);
        bChangeSkin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bChangeSkin.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bChangeSkin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bChangeSkinActionPerformed(evt);
            }
        });
        jToolBar1.add(bChangeSkin);

        bBoardTheme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/color-swatch-24.png"))); // NOI18N
        bBoardTheme.setToolTipText("Select Board Theme");
        bBoardTheme.setFocusable(false);
        bBoardTheme.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bBoardTheme.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bBoardTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBoardThemeActionPerformed(evt);
            }
        });
        jToolBar1.add(bBoardTheme);

        bLinks.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/x-b-24.png"))); // NOI18N
        bLinks.setToolTipText("Xboard Engine Links & Ratings");
        bLinks.setFocusable(false);
        bLinks.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bLinks.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bLinks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLinksActionPerformed(evt);
            }
        });
        jToolBar1.add(bLinks);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/U-blue-24.png"))); // NOI18N
        jButton2.setToolTipText("Uci Engines Links & Ratings");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        bDonate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/coins-add-icon.png"))); // NOI18N
        bDonate.setToolTipText("Donate");
        bDonate.setFocusable(false);
        bDonate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bDonate.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bDonate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDonateActionPerformed(evt);
            }
        });
        jToolBar1.add(bDonate);

        bAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/info-book-green.png"))); // NOI18N
        bAbout.setToolTipText("About");
        bAbout.setFocusable(false);
        bAbout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bAbout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAboutActionPerformed(evt);
            }
        });
        jToolBar1.add(bAbout);

        bExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/exit.png"))); // NOI18N
        bExit.setToolTipText("Exit");
        bExit.setFocusable(false);
        bExit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bExit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bExitActionPerformed(evt);
            }
        });
        jToolBar1.add(bExit);

        jToolBar2.add(jToolBar1);

        jToolBar3.setFloatable(false);

        bNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/new.png"))); // NOI18N
        bNew.setToolTipText("New Game");
        bNew.setFocusable(false);
        bNew.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bNew.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bNewActionPerformed(evt);
            }
        });
        jToolBar3.add(bNew);

        bKillAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/stop-red.png"))); // NOI18N
        bKillAll.setToolTipText("Kill All Engines and reset Board");
        bKillAll.setFocusable(false);
        bKillAll.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bKillAll.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bKillAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bKillAllActionPerformed(evt);
            }
        });
        jToolBar3.add(bKillAll);

        bSaveCfg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/floppy_disk_green.png"))); // NOI18N
        bSaveCfg.setToolTipText("Save Config");
        bSaveCfg.setFocusable(false);
        bSaveCfg.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bSaveCfg.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bSaveCfg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSaveCfgActionPerformed(evt);
            }
        });
        jToolBar3.add(bSaveCfg);

        bSoundMixer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/audio_volume_high.png"))); // NOI18N
        bSoundMixer.setToolTipText("Sound Volume");
        bSoundMixer.setFocusable(false);
        bSoundMixer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bSoundMixer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bSoundMixer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSoundMixerActionPerformed(evt);
            }
        });
        jToolBar3.add(bSoundMixer);

        bUseSound.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/sound-add-icon.png"))); // NOI18N
        bUseSound.setToolTipText("Use Sound");
        bUseSound.setFocusable(false);
        bUseSound.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bUseSound.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bUseSound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUseSoundActionPerformed(evt);
            }
        });
        jToolBar3.add(bUseSound);

        bcomboMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "hard", "easy" }));
        bcomboMode.setToolTipText("Engine mode");
        bcomboMode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcomboModeActionPerformed(evt);
            }
        });
        jToolBar3.add(bcomboMode);

        bcomboDepth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));
        bcomboDepth.setToolTipText("Search Depth ");
        bcomboDepth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcomboDepthActionPerformed(evt);
            }
        });
        jToolBar3.add(bcomboDepth);

        bcomboTime.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5", "10", "15", "20", "25", "30" }));
        bcomboTime.setToolTipText("Set Time (min)");
        bcomboTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcomboTimeActionPerformed(evt);
            }
        });
        jToolBar3.add(bcomboTime);

        bUseClock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/clock-plus-24.png"))); // NOI18N
        bUseClock.setToolTipText("Use Clock");
        bUseClock.setFocusable(false);
        bUseClock.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bUseClock.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bUseClock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUseClockActionPerformed(evt);
            }
        });
        jToolBar3.add(bUseClock);

        bSendWhite.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/send-white-24-green.png"))); // NOI18N
        bSendWhite.setToolTipText("Send Command to White CE");
        bSendWhite.setFocusable(false);
        bSendWhite.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bSendWhite.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bSendWhite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSendWhiteActionPerformed(evt);
            }
        });
        jToolBar3.add(bSendWhite);

        bSendBlack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/24x24/send-black-24-right.png"))); // NOI18N
        bSendBlack.setToolTipText("Send Command to Black CE");
        bSendBlack.setFocusable(false);
        bSendBlack.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bSendBlack.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bSendBlack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSendBlackActionPerformed(evt);
            }
        });
        jToolBar3.add(bSendBlack);

        comboWPlayerCE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Animats", "ArabianKnight ", "BremboCE", "CaveChess", "CupCake", "ChessBotX       ", "DeepBrutePos", "Frittle", "FrankWalter", "Gladiator", "GNU Chess", "Jchess", "Javalin", "KingsOut      ", "OliThink", "Tiffanys" }));
        comboWPlayerCE.setToolTipText("Select White Player Chess Engine");
        comboWPlayerCE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboWPlayerCEActionPerformed(evt);
            }
        });
        jToolBar3.add(comboWPlayerCE);
        jToolBar3.add(jSeparator2);

        comboBPlayerCE.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Animats", "ArabianKnight ", "BremboCE", "CaveChess", "CupCake", "ChessBotX       ", "DeepBrutePos", "Frittle", "FrankWalter", "Gladiator", "GNU Chess", "Jchess", "Javalin", "KingsOut      ", "OliThink", "Tiffanys" }));
        comboBPlayerCE.setToolTipText("Select Black Player Chess Engine");
        comboBPlayerCE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBPlayerCEActionPerformed(evt);
            }
        });
        jToolBar3.add(comboBPlayerCE);
        jToolBar3.add(jSeparator1);

        jToolBar2.add(jToolBar3);

        getContentPane().add(jToolBar2, java.awt.BorderLayout.NORTH);

        mFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/Actions-document-open-icon.png"))); // NOI18N
        mFile.setText("File");

        mNewGame.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/new.png"))); // NOI18N
        mNewGame.setText("New Game");
        mNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mNewGameActionPerformed(evt);
            }
        });
        mFile.add(mNewGame);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/log-16-blue.png"))); // NOI18N
        jMenuItem2.setText("View Game Log");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        mFile.add(jMenuItem2);

        mExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/quit.png"))); // NOI18N
        mExit.setText("Exit");
        mExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mExitActionPerformed(evt);
            }
        });
        mFile.add(mExit);

        jMenuBar1.add(mFile);

        mOptions.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/application-16.png"))); // NOI18N
        mOptions.setText("Options");

        mTimeMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/time-16.png"))); // NOI18N
        mTimeMenu.setText("Time");

        mUseClock.setText("Use Clock");
        mUseClock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/Alarm-clock-16.png"))); // NOI18N
        mUseClock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mUseClockActionPerformed(evt);
            }
        });
        mTimeMenu.add(mUseClock);

        mTime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/calendar_select_day.png"))); // NOI18N
        mTime.setText("Set Time (min)");

        mTime5.setText("5");
        mTime5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mTime5ActionPerformed(evt);
            }
        });
        mTime.add(mTime5);

        mTime10.setText("10");
        mTime10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mTime10ActionPerformed(evt);
            }
        });
        mTime.add(mTime10);

        mTime15.setText("15");
        mTime15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mTime15ActionPerformed(evt);
            }
        });
        mTime.add(mTime15);

        mTime20.setText("20");
        mTime20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mTime20ActionPerformed(evt);
            }
        });
        mTime.add(mTime20);

        mTime25.setText("25");
        mTime25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mTime25ActionPerformed(evt);
            }
        });
        mTime.add(mTime25);

        mTime30.setText("30");
        mTime30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mTime30ActionPerformed(evt);
            }
        });
        mTime.add(mTime30);

        mTimeMenu.add(mTime);

        mOptions.add(mTimeMenu);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/sound-juk.png"))); // NOI18N
        jMenu1.setText("Sound Control");

        mUseSound.setSelected(true);
        mUseSound.setText("Use Sound");
        mUseSound.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/document_music_playlist.png"))); // NOI18N
        mUseSound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mUseSoundActionPerformed(evt);
            }
        });
        jMenu1.add(mUseSound);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/volume_loud.png"))); // NOI18N
        jMenuItem3.setText("Sound Volume");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        mOptions.add(jMenu1);

        mBoardTheme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/color_swatch.png"))); // NOI18N
        mBoardTheme.setText("Change Board Theme");
        mBoardTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mBoardThemeActionPerformed(evt);
            }
        });
        mOptions.add(mBoardTheme);

        mChangeSkin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/gnome_color_chooser.png"))); // NOI18N
        mChangeSkin.setText("Change Skin");
        mChangeSkin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mChangeSkinActionPerformed(evt);
            }
        });
        mOptions.add(mChangeSkin);

        mSaveCfg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/save.png"))); // NOI18N
        mSaveCfg.setText("Save Config");
        mSaveCfg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSaveCfgActionPerformed(evt);
            }
        });
        mOptions.add(mSaveCfg);

        jMenuBar1.add(mOptions);

        mPlayers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/play_green-1.png"))); // NOI18N
        mPlayers.setText("Players");

        mSelectCEwhite.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/white_knight-16.png"))); // NOI18N
        mSelectCEwhite.setText("Select White Player Chess Engine");
        mSelectCEwhite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSelectCEwhiteActionPerformed(evt);
            }
        });
        mPlayers.add(mSelectCEwhite);

        mSelectCEblack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/black-knight-16.png"))); // NOI18N
        mSelectCEblack.setText("Select Black Player Chess Engine");
        mSelectCEblack.setToolTipText("");
        mSelectCEblack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSelectCEblackActionPerformed(evt);
            }
        });
        mPlayers.add(mSelectCEblack);

        jMenuBar1.add(mPlayers);

        mChessEngines.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/Knight-Yellow-16.png"))); // NOI18N
        mChessEngines.setText("Engines");

        mEngineConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/options-16.png"))); // NOI18N
        mEngineConfig.setText("Engine Config");
        mEngineConfig.setToolTipText("for Frittle/KingsOut/ArabianKnight");

        mEngineMode.setText("Engine Mode");
        mEngineMode.setToolTipText("");

        mModeEasy.setText("Easy ( pondering OFF )");
        mModeEasy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mModeEasyActionPerformed(evt);
            }
        });
        mEngineMode.add(mModeEasy);

        mModeHard.setText("Hard ( pondering ON )");
        mModeHard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mModeHardActionPerformed(evt);
            }
        });
        mEngineMode.add(mModeHard);

        mEngineConfig.add(mEngineMode);

        mEngineDepth.setText("Engine Search Depth");
        mEngineDepth.setToolTipText("");

        mDepth2.setText("2");
        mDepth2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mDepth2ActionPerformed(evt);
            }
        });
        mEngineDepth.add(mDepth2);

        mDepth3.setText("3");
        mDepth3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mDepth3ActionPerformed(evt);
            }
        });
        mEngineDepth.add(mDepth3);

        mDepth4.setText("4");
        mDepth4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mDepth4ActionPerformed(evt);
            }
        });
        mEngineDepth.add(mDepth4);

        mDepth5.setText("5");
        mDepth5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mDepth5ActionPerformed(evt);
            }
        });
        mEngineDepth.add(mDepth5);

        mDepth6.setText("6");
        mDepth6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mDepth6ActionPerformed(evt);
            }
        });
        mEngineDepth.add(mDepth6);

        mDepth7.setText("7");
        mDepth7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mDepth7ActionPerformed(evt);
            }
        });
        mEngineDepth.add(mDepth7);

        mDepth8.setText("8");
        mDepth8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mDepth8ActionPerformed(evt);
            }
        });
        mEngineDepth.add(mDepth8);

        mDepth9.setText("9");
        mDepth9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mDepth9ActionPerformed(evt);
            }
        });
        mEngineDepth.add(mDepth9);

        mEngineConfig.add(mEngineDepth);

        mChessEngines.add(mEngineConfig);

        mSendWhite.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/send-white-16-green.png"))); // NOI18N
        mSendWhite.setText("Send Command to White CE");
        mSendWhite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSendWhiteActionPerformed(evt);
            }
        });
        mChessEngines.add(mSendWhite);

        mSendBlack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/send-black-16-right.png"))); // NOI18N
        mSendBlack.setText("Send Command to Black CE");
        mSendBlack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSendBlackActionPerformed(evt);
            }
        });
        mChessEngines.add(mSendBlack);

        mKillAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/stop_red.png"))); // NOI18N
        mKillAll.setText("Kill All Engine Process");
        mKillAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mKillAllActionPerformed(evt);
            }
        });
        mChessEngines.add(mKillAll);

        mUndoLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/undo-red-16.png"))); // NOI18N
        mUndoLast.setText("Undo Last One Move");
        mUndoLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mUndoLastActionPerformed(evt);
            }
        });
        mChessEngines.add(mUndoLast);

        jMenuBar1.add(mChessEngines);

        mInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/info-green-16.png"))); // NOI18N
        mInfo.setText("Info");

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/x-b-16.png"))); // NOI18N
        jMenuItem4.setText("Xboard Engines Links & Ratings");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        mInfo.add(jMenuItem4);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/U-blue-16.png"))); // NOI18N
        jMenuItem5.setText("Uci Engines Links & Ratings");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        mInfo.add(jMenuItem5);

        mDonate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/donate-coins_add.png"))); // NOI18N
        mDonate.setText("Donate");
        mDonate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mDonateActionPerformed(evt);
            }
        });
        mInfo.add(mDonate);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SJCE/img/16x16/help-green-16.png"))); // NOI18N
        jMenuItem1.setText("About");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        mInfo.add(jMenuItem1);

        jMenuBar1.add(mInfo);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void mExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mExitActionPerformed
        int r = JOptionPane.showConfirmDialog(frame,"Really Quit ?", "Quit ?",JOptionPane.YES_NO_OPTION);
        if(r == JOptionPane.YES_OPTION) {  System.exit(0); }
    }//GEN-LAST:event_mExitActionPerformed

    private void mNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mNewGameActionPerformed
        this.newGame(Agent.USER_AGENT, Agent.ENGINE_AGENT);
    }//GEN-LAST:event_mNewGameActionPerformed

    private void mModeEasyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mModeEasyActionPerformed
        aktion.Mode="easy";
        this.mModeEasy.setSelected(true);
        this.mModeHard.setSelected(false);
        bcomboMode.setSelectedItem(aktion.Mode);
        //newGame(Agent.USER_AGENT, Agent.ENGINE_AGENT);
    }//GEN-LAST:event_mModeEasyActionPerformed

    private void mModeHardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mModeHardActionPerformed
        aktion.Mode="hard";
        this.mModeEasy.setSelected(false);
        this.mModeHard.setSelected(true);
        bcomboMode.setSelectedItem(aktion.Mode);
    }//GEN-LAST:event_mModeHardActionPerformed

    private void mDepth9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mDepth9ActionPerformed
        aktion.changeDepth(9);
    }//GEN-LAST:event_mDepth9ActionPerformed

    private void mDepth2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mDepth2ActionPerformed
        aktion.changeDepth(2);
    }//GEN-LAST:event_mDepth2ActionPerformed

    private void mDepth3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mDepth3ActionPerformed
        aktion.changeDepth(3);
    }//GEN-LAST:event_mDepth3ActionPerformed

    private void mDepth4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mDepth4ActionPerformed
        aktion.changeDepth(4);
    }//GEN-LAST:event_mDepth4ActionPerformed

    private void mDepth5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mDepth5ActionPerformed
        aktion.changeDepth(5);
    }//GEN-LAST:event_mDepth5ActionPerformed

    private void mDepth6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mDepth6ActionPerformed
        aktion.changeDepth(6);
    }//GEN-LAST:event_mDepth6ActionPerformed

    private void mDepth7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mDepth7ActionPerformed
        aktion.changeDepth(7);
    }//GEN-LAST:event_mDepth7ActionPerformed

    private void mChangeSkinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mChangeSkinActionPerformed
        changeLF();
        borderPanel.add(boardUI, BorderLayout.CENTER);
    }//GEN-LAST:event_mChangeSkinActionPerformed

    private void mTime5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mTime5ActionPerformed
        aktion.changeTime(5);
    }//GEN-LAST:event_mTime5ActionPerformed

    private void mTime10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mTime10ActionPerformed
        aktion.changeTime(10);
    }//GEN-LAST:event_mTime10ActionPerformed

    private void mTime15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mTime15ActionPerformed
        aktion.changeTime(15);
    }//GEN-LAST:event_mTime15ActionPerformed

    private void mTime20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mTime20ActionPerformed
        aktion.changeTime(20);
    }//GEN-LAST:event_mTime20ActionPerformed

    private void mTime25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mTime25ActionPerformed
        aktion.changeTime(25);
    }//GEN-LAST:event_mTime25ActionPerformed

    private void mTime30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mTime30ActionPerformed
        aktion.changeTime(30);
    }//GEN-LAST:event_mTime30ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        aktion.About();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void mUseClockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mUseClockActionPerformed
        aktion.useTime();
    }//GEN-LAST:event_mUseClockActionPerformed

    private void mSaveCfgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSaveCfgActionPerformed
        AppCfgPref.Save();
        //AppCfgXerces.Save(); 
        //AppCfgProperties.Save(aktion.currentLAF,aktion.currentTheme,aktion.UseClock,aktion.Mode,""+aktion.Depth,""+aktion.Time);        
    }//GEN-LAST:event_mSaveCfgActionPerformed

    private void mSelectCEwhiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSelectCEwhiteActionPerformed
        this.changeCE("white");
    }//GEN-LAST:event_mSelectCEwhiteActionPerformed

    private void mDonateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mDonateActionPerformed
      Donate dd=new Donate(frame,true);
      dd.setVisible(true);  
    }//GEN-LAST:event_mDonateActionPerformed

    private void mBoardThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mBoardThemeActionPerformed
        this.changeBoardTheme();
    }//GEN-LAST:event_mBoardThemeActionPerformed

    private void mDepth8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mDepth8ActionPerformed
        aktion.changeDepth(8);
    }//GEN-LAST:event_mDepth8ActionPerformed

    private void mKillAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mKillAllActionPerformed
        aktion.killAllEngines();
    }//GEN-LAST:event_mKillAllActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        aktion.LinksX();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void mSelectCEblackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSelectCEblackActionPerformed
        this.changeCE("black");
    }//GEN-LAST:event_mSelectCEblackActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        MixerSet();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void mUseSoundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mUseSoundActionPerformed
        aktion.useSoundSwitch();
    }//GEN-LAST:event_mUseSoundActionPerformed

    private void mSendWhiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSendWhiteActionPerformed
        aktion.sendEngineCmd("white");
    }//GEN-LAST:event_mSendWhiteActionPerformed

    private void mSendBlackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSendBlackActionPerformed
        aktion.sendEngineCmd("black");
    }//GEN-LAST:event_mSendBlackActionPerformed

    private void bDonateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDonateActionPerformed
        Donate dd=new Donate(frame,true);
        dd.setVisible(true);
        //aktion.inputForm();
    }//GEN-LAST:event_bDonateActionPerformed

    private void bLinksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLinksActionPerformed
        aktion.LinksX();
    }//GEN-LAST:event_bLinksActionPerformed

    private void bAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAboutActionPerformed
        aktion.About();
    }//GEN-LAST:event_bAboutActionPerformed

    private void bNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bNewActionPerformed
        frame.newGame(Agent.USER_AGENT, Agent.ENGINE_AGENT);
    }//GEN-LAST:event_bNewActionPerformed

    private void bKillAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bKillAllActionPerformed
        aktion.killAllEngines();
    }//GEN-LAST:event_bKillAllActionPerformed

    private void bSaveCfgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveCfgActionPerformed
        AppCfgPref.Save();
        //AppCfgXerces.Save();
        //AppCfgProperties.Save(aktion.currentLAF,aktion.currentTheme,aktion.UseClock,aktion.Mode,""+aktion.Depth,""+aktion.Time);
    }//GEN-LAST:event_bSaveCfgActionPerformed

    private void bBoardThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBoardThemeActionPerformed
        this.changeBoardTheme();
    }//GEN-LAST:event_bBoardThemeActionPerformed

    private void bChangeSkinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bChangeSkinActionPerformed
        changeLF();
        borderPanel.add(boardUI, BorderLayout.CENTER);
    }//GEN-LAST:event_bChangeSkinActionPerformed

    private void bcomboModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcomboModeActionPerformed
        aktion.Mode=bcomboMode.getSelectedItem().toString();
        if (aktion.Mode.equals("hard"))
        {
            mModeHard.setSelected(true);
            mModeEasy.setSelected(false);
        }
        else
        {
            mModeHard.setSelected(false);
            mModeEasy.setSelected(true);
        }
    }//GEN-LAST:event_bcomboModeActionPerformed

    private void bcomboDepthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcomboDepthActionPerformed
        aktion.Depth=Integer.parseInt(bcomboDepth.getSelectedItem().toString());
        aktion.changeDepth(aktion.Depth);
    }//GEN-LAST:event_bcomboDepthActionPerformed

    private void bcomboTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcomboTimeActionPerformed
        aktion.Time=Integer.parseInt(bcomboTime.getSelectedItem().toString());
        aktion.changeTime(aktion.Time);
    }//GEN-LAST:event_bcomboTimeActionPerformed

    private void bUseClockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUseClockActionPerformed
        aktion.useTime();
    }//GEN-LAST:event_bUseClockActionPerformed

    private void bSoundMixerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSoundMixerActionPerformed
        MixerSet();
    }//GEN-LAST:event_bSoundMixerActionPerformed

    private void bUseSoundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUseSoundActionPerformed
        aktion.useSoundSwitch();
    }//GEN-LAST:event_bUseSoundActionPerformed

    private void bSendWhiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSendWhiteActionPerformed
        aktion.sendEngineCmd("white");
    }//GEN-LAST:event_bSendWhiteActionPerformed

    private void comboWPlayerCEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboWPlayerCEActionPerformed
        aktion.viborCE(comboWPlayerCE.getSelectedItem().toString(),"white");
    }//GEN-LAST:event_comboWPlayerCEActionPerformed

    private void bSendBlackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSendBlackActionPerformed
        aktion.sendEngineCmd("black");
    }//GEN-LAST:event_bSendBlackActionPerformed

    private void comboBPlayerCEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBPlayerCEActionPerformed
        aktion.viborCE(comboBPlayerCE.getSelectedItem().toString(),"black");
    }//GEN-LAST:event_comboBPlayerCEActionPerformed

    private void bSelectWhiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSelectWhiteActionPerformed
        this.changeCE("white");
    }//GEN-LAST:event_bSelectWhiteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.changeCE("black");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void bExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bExitActionPerformed
        int r = JOptionPane.showConfirmDialog(frame,"Really Quit ?", "Quit ?",JOptionPane.YES_NO_OPTION);
        if(r == JOptionPane.YES_OPTION) {  System.exit(0); }
    }//GEN-LAST:event_bExitActionPerformed

    private void bUndoLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUndoLastActionPerformed
        aktion.bmUndoLastMoveEventHandler();
    }//GEN-LAST:event_bUndoLastActionPerformed

    private void mUndoLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mUndoLastActionPerformed
        aktion.bmUndoLastMoveEventHandler();
    }//GEN-LAST:event_mUndoLastActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        aktion.LinksU();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        aktion.LinksU();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        aktion.logShow();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        aktion.logShow();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frame=new XChessFrame();
                chessClock = new ChessClock();
                frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true); 
            AppCfgPref.Load();
            //AppCfgXerces.Load();
                frame.MixerInit();
                moveListUI = new MoveListUI();
                boardUI = new BoardUI();                
                moveListUI.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
                scrollOutputArea.setViewportView(outputArea);
                sidePanel.setLayout(new BorderLayout());
                sidePanel.add(scrollMoveList, BorderLayout.CENTER);                
                scrollMoveList.setViewportView(moveListUI);
                borderPanel.setLayout(BL=new BorderLayout());
                //borderPanel.add(boardUI, BorderLayout.CENTER);                
                boardUI.setOpaque(true);
                //borderPanel.add(boardUI, BorderLayout.CENTER);                 
                aktion.appInit();
                //sidePanel.updateUI();
                aktion.InstallLF();
                aktion.setSkin();
                //aktion.skinEffects();
                //aktion.viborColorCE(aktion.EngineColor);
                aktion.viborCE(aktion.whitePlayerCE,"white");
                aktion.viborCE(aktion.blackPlayerCE,"black");                
                //frame.newGame(Agent.USER_AGENT, Agent.ENGINE_AGENT);
                //borderPanel.updateUI();  
                frame.setVisible(true);
                //frame.newGame(Agent.USER_AGENT, Agent.ENGINE_AGENT);
                borderPanel.add(boardUI, BorderLayout.CENTER);
                //boardUI.setVisible(false);
                //frame.changeBoardTheme();
                //Graphics g=boardUI.getRootPane().getGraphics();
                //boardUI.paint(g);                
                //boardUI.offPaint();
                        //SwingUtilities.updateComponentTreeUI(frame);
                        //frame.pack();
                        //borderPanel.updateUI();                
                //borderPanel.updateUI();
                //borderPanel.repaint();
                //frame.setVisible(true);
                //frame.newGame(Agent.USER_AGENT, Agent.ENGINE_AGENT);                
                //try { Thread.sleep(1000); } catch (InterruptedException ex) 
                //{ Logger.getLogger(XChessFrame.class.getName()).log(Level.SEVERE, null, ex); }
                //aktion.setSkin();
                //borderPanel.repaint();
                //frame.newGame(Agent.USER_AGENT, Agent.ENGINE_AGENT);
                //borderPanel.repaint();                
                //borderPanel.add(boardUI, BorderLayout.CENTER);
                frame.boardSetSize();
                System.out.println("boardUI = "+boardUI.getSize());
                System.out.println("borderPanel = "+borderPanel.getSize());
                //frame.newGame(Agent.USER_AGENT, Agent.ENGINE_AGENT);
                new Run_Thread();
            }
        }); 
        //new Run_Thread();
        //this.newGame(Agent.USER_AGENT, Agent.ENGINE_AGENT);
        //Graphics g=boardUI.getRootPane().getGraphics();
        //boardUI.paint(g);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton bAbout;
    public static javax.swing.JButton bBoardTheme;
    private javax.swing.JButton bChangeSkin;
    public static javax.swing.JButton bDonate;
    private javax.swing.JButton bExit;
    private javax.swing.JButton bKillAll;
    private javax.swing.JButton bLinks;
    private javax.swing.JButton bNew;
    private javax.swing.JButton bSaveCfg;
    private javax.swing.JButton bSelectWhite;
    private javax.swing.JButton bSendBlack;
    private javax.swing.JButton bSendWhite;
    public static javax.swing.JButton bSoundMixer;
    public static javax.swing.JButton bUndoLast;
    public static javax.swing.JToggleButton bUseClock;
    public static javax.swing.JToggleButton bUseSound;
    public static javax.swing.JComboBox<String> bcomboDepth;
    public static javax.swing.JComboBox<String> bcomboMode;
    public static javax.swing.JComboBox bcomboTime;
    public static javax.swing.JPanel borderPanel;
    public static javax.swing.JComboBox<String> comboBPlayerCE;
    public static javax.swing.JComboBox<String> comboWPlayerCE;
    private javax.swing.JSplitPane horizontalSplit;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JMenuItem mBoardTheme;
    private javax.swing.JMenuItem mChangeSkin;
    private javax.swing.JMenu mChessEngines;
    public static javax.swing.JRadioButtonMenuItem mDepth2;
    public static javax.swing.JRadioButtonMenuItem mDepth3;
    public static javax.swing.JRadioButtonMenuItem mDepth4;
    public static javax.swing.JRadioButtonMenuItem mDepth5;
    public static javax.swing.JRadioButtonMenuItem mDepth6;
    public static javax.swing.JRadioButtonMenuItem mDepth7;
    public static javax.swing.JRadioButtonMenuItem mDepth8;
    public static javax.swing.JRadioButtonMenuItem mDepth9;
    private javax.swing.JMenuItem mDonate;
    private javax.swing.JMenu mEngineConfig;
    private javax.swing.JMenu mEngineDepth;
    private javax.swing.JMenu mEngineMode;
    private javax.swing.JMenuItem mExit;
    private javax.swing.JMenu mFile;
    private javax.swing.JMenu mInfo;
    private javax.swing.JMenuItem mKillAll;
    public static javax.swing.JRadioButtonMenuItem mModeEasy;
    public static javax.swing.JRadioButtonMenuItem mModeHard;
    private javax.swing.JMenuItem mNewGame;
    private javax.swing.JMenu mOptions;
    private javax.swing.JMenu mPlayers;
    private javax.swing.JMenuItem mSaveCfg;
    private javax.swing.JMenuItem mSelectCEblack;
    private javax.swing.JMenuItem mSelectCEwhite;
    private javax.swing.JMenuItem mSendBlack;
    private javax.swing.JMenuItem mSendWhite;
    public static javax.swing.JMenu mTime;
    public static javax.swing.JRadioButtonMenuItem mTime10;
    public static javax.swing.JRadioButtonMenuItem mTime15;
    public static javax.swing.JRadioButtonMenuItem mTime20;
    public static javax.swing.JRadioButtonMenuItem mTime25;
    public static javax.swing.JRadioButtonMenuItem mTime30;
    public static javax.swing.JRadioButtonMenuItem mTime5;
    private javax.swing.JMenu mTimeMenu;
    public static javax.swing.JMenuItem mUndoLast;
    public static javax.swing.JCheckBoxMenuItem mUseClock;
    public static javax.swing.JCheckBoxMenuItem mUseSound;
    public static javax.swing.JTextArea outputArea;
    public static javax.swing.JScrollPane scrollMoveList;
    public static javax.swing.JScrollPane scrollOutputArea;
    public static javax.swing.JPanel sidePanel;
    private javax.swing.JSplitPane verticalSplit;
    // End of variables declaration//GEN-END:variables
}
