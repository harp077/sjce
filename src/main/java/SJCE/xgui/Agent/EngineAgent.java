package SJCE.xgui.Agent;
import SJCE.xgui.Interfaces.IEngineListener;
import SJCE.xgui.Interfaces.IChessContext;
import static SJCE.XChessFrame.aktion;
import static SJCE.XChessFrame.blackEngineAgentExt;
import static SJCE.XChessFrame.chessClock;
import static SJCE.XChessFrame.frame;
import static SJCE.XChessFrame.whiteEngineAgentExt;
import SJCE.more.Actions;
import SJCE.more.Msg_Thread;
import SJCE.xgui.EngineIO;
import SJCE.xgui.EventObject.EngineEvent;
import SJCE.xgui.JList.MoveListUI;
import SJCE.xgui.JPanel.ChessClock;
import SJCE.xgui.Move;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
//import javafx.scene.paint.Color;

        //  readLine() and writeLine() !!!!!!!!!!!!!!!!!!!!!

public abstract class EngineAgent extends Agent {
    //public static EngineIO engineIO; // PUBLIC STATIC ON 13-05-16 !!!
    public static EngineIO engineIOblack;
    public static EngineIO engineIOwhite;
    private ArrayList<IEngineListener> listenerList = new ArrayList<IEngineListener>(5);
    private Thread parseThread;
    private String name;
    private int protocolId;
    private String forBagatur="";
    public static String [] argsBagatur1cpu = {
        " bagaturchess.engines.base.cfg.UCIConfig_BaseImpl",
        " bagaturchess.search.impl.uci_adaptor.UCISearchAdaptorImpl_PonderingOpponentMove",
        " bagaturchess.engines.base.cfg.UCISearchAdaptorConfig_BaseImpl",
        " bagaturchess.search.impl.rootsearch.parallel.MTDParallelSearch",
        " bagaturchess.engines.base.cfg.RootSearchConfig_BaseImpl_SMP",
        " bagaturchess.search.impl.alg.impl0.SearchMTD0",
        " bagaturchess.engines.bagatur.cfg.search.SearchConfigImpl_MTD_SMP",
        " bagaturchess.engines.bagatur.cfg.board.BoardConfigImpl",
        " bagaturchess.engines.bagatur.cfg.eval.BagaturEvalConfigImpl_v2"       
    };
    public static String [] argsBagaturNcpu = {
        " bagaturchess.engines.base.cfg.UCIConfig_BaseImpl",
        " bagaturchess.search.impl.uci_adaptor.UCISearchAdaptorImpl_PonderingOpponentMove",
        " bagaturchess.engines.base.cfg.UCISearchAdaptorConfig_BaseImpl",
        " bagaturchess.search.impl.rootsearch.parallel.MTDParallelSearch",
        " bagaturchess.engines.base.cfg.RootSearchConfig_BaseImpl_SMP",
        " bagaturchess.search.impl.alg.impl0.SearchMTD0",
        " bagaturchess.engines.bagatur.cfg.search.SearchConfigImpl_MTD_SMP",
        " bagaturchess.engines.bagatur.cfg.board.BoardConfigImpl",
        " bagaturchess.engines.bagatur.cfg.eval.BagaturEvalConfigImpl_v2"
    };  
    public String ceTip;
    public String goEngine;
    public String colorCE;
    public EngineAgent(IChessContext context, String goEngine, String colorCE, String ceTip) 
     {
        super(context, goEngine, colorCE, ceTip);
        this.ceTip=ceTip;
        this.goEngine=goEngine;
        this.colorCE=colorCE;          
        for (int i=0; i<9; i++) {
            forBagatur=forBagatur+argsBagatur1cpu[i];
        }
        //System.out.println(aktion.mainEngine);
        switch (this.goEngine) {
            case "Chess22k": 
                frame.outputArea.append("Chess22k v1.5, created by Sander Maassen van den Brink (Dutch)\nhttps://github.com/sandermvdb/chess22k\n");
                this.runEngineIO("java -jar ./ce/chess22k-1.5.jar"); 
                break;   
            case "Koedem": 
                frame.outputArea.append("Koedem v1.1, created by Kolja Kühn (Germany)\nhttp://computer-chess.org/doku.php?id=computer_chess:wiki:lists:chess_engine_list\n");
                this.runEngineIO("java -jar ./ce/Koedem-1.1.jar"); 
                break;                 
            case "Alf": 
                frame.outputArea.append("Alf v1.09, create by Casper Berg\nhttp://kirr.homeunix.org/chess/engines/Jim%20Ablett/ALF/\n");                
                this.runEngineIO("java -jar ./ce/Alf109.jar");
                break;            
            case "Animats": 
                frame.outputArea.append("Animats revision 23, create by Stuart Allen\nhttp://animatschess.sourceforge.net/\n");                
                this.runEngineIO("java -jar ./ce/AnimatsCE.jar xboard");
                break; 
            case "ArabianKnight":
                frame.outputArea.append("ArabianKnight v1.55, create by Marcin Gardyjan\n"); 
                frame.outputArea.append("http://computer-chess.org/doku.php?id=computer_chess:wiki:download:engine_download_list\n");
                frame.outputArea.append("http://kirr.homeunix.org/chess/engines/Jim%20Ablett/ARABIAN%20KNIGHT/\n");
                runEngineIO("java -jar ./ce/ArabianKnight.jar xboard"); 
                break;
            case "Bagatur":
                frame.outputArea.append("Bagatur v1.4c, create by Krasimir Topchiyski\nhttp://bagaturchess.sourceforge.net\nhttps://sites.google.com/site/bagaturchess/\nhttps://github.com/bagaturchess/\n");
                runEngineIO("java -jar ./ce/Bagatur_1.4c.jar"+forBagatur);
                //System.out.println("java -jar ./lib/Bagatur_1.4.jar"+forBagatur);
                break;                
            case "BremboCE":
                frame.outputArea.append("BremboCE v0.6.2, create by Gianluca Cisana\nhttp://bremboce.cisana.com/download_en.php\n");
                this.runEngineIO("java -jar ./ce/Bremboce.jar"); 
                break;
            case "Calculon":  
                frame.outputArea.append("CalculonX v0.4.2, create by Barry Smith\nhttps://code.google.com/p/calculonx/\nhttps://github.com/BarrySW19/CalculonX/\nhttp://computer-chess.org/lib/exe/fetch.php?media=computer_chess:wiki:download:calculon-r258-pn.zip\n");
                this.runEngineIO("java -jar ./ce/code_google_com_archive_p_calculonx.jar"); 
                break;  
            case "Carballo":  
                frame.outputArea.append("Carballo v1.7, created by Alberto Alonso Ruibal\nhttp://www.alonsoruibal.com/\nhttps://github.com/albertoruibal/carballo/\n");
                this.runEngineIO("java -jar ./ce/carballo-1.7.jar"); 
                break;                 
            case "CaveChess":  
                frame.outputArea.append("Cave release 62, created by \nhttp://cavechess.sourceforge.net\n");
                this.runEngineIO("java -jar ./ce/CaveChess_62.jar"); 
                break;
            case "ChessBotX":
                frame.outputArea.append("ChessBotX v1.02, created by Alexander Soto/Roman Koldaev, see:\nhttps://github.com/alexandersoto/chess-bot\nhttp://alexander.soto.io/chess-bot\n");
                if (aktion.Depth<5)
                    this.runEngineIO("java -jar ./ce/ChessBotX.jar easy"); 
                if (aktion.Depth==5)
                    this.runEngineIO("java -jar ./ce/ChessBotX.jar normal");  
                if (aktion.Depth==6)
                    this.runEngineIO("java -jar ./ce/ChessBotX.jar hard");                 
                if (aktion.Depth>6)
                    this.runEngineIO("java -jar ./ce/ChessBotX.jar ultra");                 
                break;                 
            case "CupCake":  
                frame.outputArea.append("Cupcake v11, created by Dan Honeycutt\n");
                frame.outputArea.append("http://kirr.homeunix.org/chess/engines/Jim%20Ablett/CUPCAKE/\n");
                this.runEngineIO("java -jar ./ce/cupcake.jar"); 
                break; 
            case "Cuckoo":  
                frame.outputArea.append("Cuckoo v1.12, created by Peter Osterlund, see\nhttp://web.comhem.se/petero2home/javachess/index.html");
                frame.outputArea.append("\nhttp://kirr.homeunix.org/chess/engines/Jim%20Ablett/CUCKOO/\n");
                this.runEngineIO("java -jar ./ce/cuckoo112.jar txt"); 
                break;                
            case "DeepBrutePos":
                frame.outputArea.append("DeepBrutePos v2.1, create by Folkert van Heusden\nhttps://www.vanheusden.com/DeepBrutePos/\n");
                this.runEngineIO("java -jar ./ce/DeepBrutePos-2.1.jar --io-mode xboard --depth "+aktion.Depth); 
                break;
            case "Eden":
                frame.outputArea.append("Eden v0.0.13, created by Nicolai Czempin\n");
                frame.outputArea.append("http://kirr.homeunix.org/chess/engines/Jim%20Ablett/EDEN/\n");
                this.runEngineIO("java -jar ./ce/eden-0013.jar"); 
                break; 
            case "FairyPrincess": 
                frame.outputArea.append("Fairy Princess java xboard chess engine\nhttps://github.com/mihaio07/FairyPrincess\n");
                this.runEngineIO("java -jar ./ce/github_mihaio07_FairyPrincess.jar"); 
                break;                
            case "Fischerle": 
                frame.outputArea.append("Fischerle v0.9.70 SE 32bit, created by Roland Stuckardt\nhttp://www.stuckardt.de/index.php/schachengine-fischerle.html\n");
                this.runEngineIO("java -jar ./ce/Fischerle.jar uci 32bit"); 
                break;                
            case "Flux": 
                frame.outputArea.append("Flux v2.2.1, created by Phokham Nonava\nhttps://github.com/fluxroot/flux/releases/\n");
                this.runEngineIO("java -jar ./ce/Flux-2.2.1.jar"); 
                break;                
            case "Frittle": 
                frame.outputArea.append("Frittle v1.0, create by Rohan Padhye\nhttp://frittle.sourceforge.net\n");
                this.runEngineIO("java -jar ./ce/Frittle-1.0.jar"); 
                break;
            case "FrankWalter":  
                frame.outputArea.append("FrankWalter v1.0.8, create by Laurens Winkelhagen\nhttp://kirr.homeunix.org/chess/engines/Jim%20Ablett/FRANK-WALTER/\n");
                this.runEngineIO("java -jar ./ce/FrankWalter.jar"); 
                break; 
            case "Gladiator": 
                frame.outputArea.append("Gladiator v0.0.7, create by David Garcinuño Enríquez\nhttps://github.com/dagaren/gladiator-chess\n");
                this.runEngineIO("java -jar ./ce/gladiator_v0.0.7.jar"); 
                break; 
            case "GNU Chess": 
                frame.outputArea.append("Chessbox_gnu4j version 1.02 - is a port of GNU Chess 5.0.7 from C to Java.\nCreated by Xan Gregg. See: http://www.forthgo.com/chessbox/,\n");
                frame.outputArea.append("http://kirr.homeunix.org/chess/engines/Jim%20Ablett/GNUCHESS/\n");
                this.runEngineIO("java -jar ./ce/chessbox_gnu4j.jar");
                        //new EngineIO("java -cp ./lib/chessbox_gnu4j.jar org.chessbox.gnu4j.Main");
                break;                
            case "Jchess":  
                frame.outputArea.append("JChess v1.0, created by Tomasz Michniewski - Poland\n");
                frame.outputArea.append("http://kirr.homeunix.org/chess/engines/Jim%20Ablett/JCHESS/\n");
                frame.outputArea.append("http://computer-chess.org/doku.php?id=computer_chess:wiki:download:engine_download_list\n");
                this.runEngineIO("java -jar ./ce/Jchess-1.0.jar"); 
                break; 
            case "Javalin": 
                frame.outputArea.append("Javalin v1.3.1, create by Mănica Vlad Bogdan\nhttps://github.com/bugyvlad/javalin\n");
                this.runEngineIO("java -jar ./ce/javalin_v1.3.1.jar"); 
                break;
            case "jChecs": 
                frame.outputArea.append("jChecs v0.1.0.1, create by David Cotton\nhttp://jchecs.free.fr/\nhttp://jchecs.sourceforge.net/\n");
                this.runEngineIO("java -jar ./ce/jchecs_v0.1.0.1.jar "+aktion.jchecsEngineTip); 
                //System.out.println("java -jar ./lib/jchecs_v0.1.0.1.jar "+aktion.jchecsEngineTip);
                break;
            case "Kasparov": 
                frame.outputArea.append("Kasparov Chess v1.0.0, create by Eric Liu\nhttps://github.com/eliucs/kasparov\n");
                this.runEngineIO("java -jar ./ce/KasparovChess-1.0.0.jar");
                break;                 
            case "KennyClassIQ": 
                frame.outputArea.append("http://kennyclassiq.sourceforge.net/\nhttps://github.com/artfuldev/KennyClassIQ/\n");
                this.runEngineIO("java -jar ./ce/KennyClassIQ.jar"); 
                break;                  
            case "KingsOut": 
                frame.outputArea.append("KingsOut v0.2.42, create by Bernd Nuernberger\nhttp://kirr.homeunix.org/chess/engines/Jim%20Ablett/KINGSOUT/\n");
                this.runEngineIO("java -jar ./ce/kingsout.jar"); 
                break; 
            case "Krudo": 
                frame.outputArea.append("Krudo v0.14b\nhttp://kirr.homeunix.org/chess/engines/Jim%20Ablett/KRUDO/\nhttp://www.g-sei.org/krudo/\n");
                this.runEngineIO("java -jar ./ce/Krudo.jar"); 
                break; 
            case "Magnum": 
                frame.outputArea.append("Magnum v4.0, create by Eric Stock\nhttps://code.google.com/archive/p/magnumchess/downloads\n");
                this.runEngineIO("java -jar ./ce/MagnumChess.jar");
                break; 
            case "Mediocre": 
                frame.outputArea.append("Mediocre v0.5, create by Jonatan Pettersson\nhttp://mediocrechess.blogspot.no/\nhttp://mediocrechess.sourceforge.net/\n");
                this.runEngineIO("java -jar ./ce/mediocre_v0.5.jar");
                break;                
            case "OliThink": 
                frame.outputArea.append("OliThink v5.3.2, create by Oliver Brausch\n");
                frame.outputArea.append("http://kirr.homeunix.org/chess/engines/Jim%20Ablett/OLITHINK/\n");
                this.runEngineIO("java -jar ./ce/OliThink532.jar"); 
                break; 
            case "Presbyter": 
                frame.outputArea.append("Presbyter v1.3.0, create by Jefferson Wilson\n");
                frame.outputArea.append("https://github.com/jwilson82/presbyter\n");
                this.runEngineIO("java -jar ./ce/presbyter-1.3.0.jar"); 
                break; 
            case "Phoenix": 
                frame.outputArea.append("Phoenix-Cuckoo v1.13a9, create by Rahul A R\nhttps://github.com/rahular/phoenix\n");
                this.runEngineIO("java -jar ./ce/github_rahular_phoenix.jar txt"); 
                break;   
            case "Pulse": 
                frame.outputArea.append("Pulse v1.6.1, created by Phokham Nonava\nhttps://github.com/fluxroot/pulse/releases/\nhttp://www.fluxchess.com/pulse/download/\n");
                this.runEngineIO("java -jar ./ce/pulse-1.6.1-java.jar"); 
                break;                  
            case "Rival": 
                frame.outputArea.append("Rival build 0094, written by Russell Newman and Chris Moreton\nhttps://github.com/Netsensia/rival-chess-android-engine\nhttp://www.rivalchess.com/downloads/\n");
                this.runEngineIO("java -jar ./ce/Rival-0094.jar"); 
                break;
            case "Rumney": 
                frame.outputArea.append("Rumney Chess v0.2.1\nhttps://github.com/Zaloum/\n");
                this.runEngineIO("java -jar ./ce/github_Zaloum_RumneyChess.jar"); 
                break;                 
            case "Talvmenni": 
                frame.outputArea.append("Talvmenni v0.0.1\nhttp://talvmenni.sourceforge.net\n");
                frame.outputArea.append("http://kirr.homeunix.org/chess/engines/Jim%20Ablett/TALVMENNI/\n");
                this.runEngineIO("java -jar ./ce/talvmenni.jar"); 
                break;                 
            case "Tiffanys": 
                frame.outputArea.append("Tiffanys v0.5, create by Bernhard von Gunten\nhttp://tiffanys.sourceforge.net\n");
                this.runEngineIO("java -jar ./ce/Tiffanys.jar xboard"); 
                break; 
            case "Tri-OS": 
                frame.outputArea.append("Tri-OS CS4210's Java xboard Chess Engine\nsee please: http://chess.dubmun.com/\n");
                this.runEngineIO("java -jar ./ce/Tri-OS_CS4210.jar -d 5");
                        //+aktion.Depth); 
                break;                
            case "Unidexter": 
                frame.outputArea.append("Unidexter v0.0.1, create by Michael Aherne\nhttp://computer-chess.org/lib/exe/fetch.php?media=computer_chess:wiki:download:unidexter-f5fb866-pn.jar\nhttps://github.com/micaherne/unidexter/\n");
                this.runEngineIO("java -jar ./ce/unidexter-f5fb866-pn.jar"); 
                break; 
            case "Ziggy": 
                frame.outputArea.append("Ziggy v0.7, create by Hrafn Eiríksson\nhttp://kirr.homeunix.org/chess/engines/Jim%20Ablett/ZIGGY/\nhttps://github.com/krummi/ChessEngine/\n");
                this.runEngineIO("java -jar ./ce/ziggy.jar"); 
                break;                
//case "Luzhin": this.engineIO = 
//new EngineIO("java -cp ./lib/*:luzhin.jar luzhin.WinBoardLuzhin"); break;             
        }
        initiate();
    }
    
    public void runEngineIO(String cmd) {
        if (this.colorCE.equals("white")) { 
           System.out.println("========================= START ENGINE ============================="); 
           this.engineIOwhite = new EngineIO(cmd);
           System.out.println("White = "+cmd);
        }
        else {
           this.engineIOblack = new EngineIO(cmd); 
           System.out.println("Black = "+cmd);           
        }
    }
    
    public static EngineAgent createEngine(IChessContext context, String goEngine, String colorCE, String ceTip) {
        /*if (colorCE.equals("white")) {
            whiteEngineAgentExt.ceTip=ceTip;
            whiteEngineAgentExt.goEngine=goEngine;
            whiteEngineAgentExt.colorCE=colorCE;        
            whiteEngineAgentExt= new EngineAgentExt(context, goEngine, colorCE, ceTip);
            return whiteEngineAgentExt;
        } 
        else {
            blackEngineAgentExt.ceTip=ceTip;
            blackEngineAgentExt.goEngine=goEngine;
            blackEngineAgentExt.colorCE=colorCE;        
            blackEngineAgentExt= new EngineAgentExt(context, goEngine, colorCE, ceTip);
            return whiteEngineAgentExt;            
        }*/
        EngineAgentExt.ceTip=ceTip;
        EngineAgentExt.goEngine=goEngine;
        EngineAgentExt.colorCE=colorCE;        
        return new EngineAgentExt(context, goEngine, colorCE, ceTip);
    }
    
    @Override
    public void moveDeclared(Move move) {
    //////////////////////  START THE CHESS CLOCK !!!!!!!!!!!!!!!!!!!!!!!!!!        
        chessClock.start();    
        userMove(move);
    }
    
    public void addIEngineListener(IEngineListener l) { listenerList.add(l); }
    
    public void removeIEngineListener(IEngineListener l) { listenerList.remove(l); }
    
    protected void fireMovePrinted(EngineEvent e) {
        if(listenerList == null) return;
        int count = listenerList.size();
        for(int i = 0; i < count; i++) {
            listenerList.get(i).movePrinted(e);
        }
    }
    
    protected void fireIllegalPrinted(EngineEvent e) {
        if(listenerList == null) return;
        int count = listenerList.size();
        for(int i = 0; i < count; i++) {
            listenerList.get(i).illegalPrinted(e);
        }
    }
    
    protected void fireDataPrinted(EngineEvent e) {
        if(listenerList == null) return;
        int count = listenerList.size();
        for(int i = 0; i < count; i++) {
            listenerList.get(i).dataPrinted(e);
        }
    }
    
    protected void fireDataEntered(EngineEvent e) {
        if(listenerList == null) return;
        int count = listenerList.size();
        for(int i = 0; i < count; i++) {
            listenerList.get(i).dataEntered(e);
        }
    }
    
    protected void updateContext(Move move) {
        boardUI.update(move.getAffectedSquares(move.doMove(boardUI.getBoard())));
        moveListUI.addMove(move);
        chessClock.stop();
        chessClock.switchTurn();
    }
    
    public int getProtocolId()         { return protocolId;  }
    
    public String getName()            { return name;        }
    
    public void setName(String name)   { this.name = name;   }
    
    public void initiate() {
        parseThread = new Thread() {
            @Override
            public void run() {
                while(true) {
                    try { parseCommand(); }
                    catch (IOException e) { e.printStackTrace(); break; }
                }
            }
        };
        parseThread.start();
    }
    
    public ChessClock getChessClock()       {  return chessClock;  }
    
    public void setChessClock(ChessClock chessClock) {
        this.chessClock = chessClock;
    }
    
    public String readLine() throws IOException {
        String line = "";
        //try {
            if (this.colorCE.equals("white"))
                line = engineIOwhite.readLine().toLowerCase();
            else 
                line = engineIOblack.readLine().toLowerCase();
        //} catch (NullPointerException ne) { }
        if(line.length()>0) frame.outputArea.append("<read from "+colorCE.toUpperCase()+">: "+line+"\n");
        if(!(line!= null)||line.length()==0||line.startsWith("info")) return null;
                //||line.startsWith("info")) return null;        
        if (line.contains("bestmove")&&(aktion.whiteRivalMovesString.endsWith(line.substring(9))||aktion.blackRivalMovesString.endsWith(line.substring(9))))
         {
            line="";
            aktion.sendEngineCmd("white","quit");
            aktion.sendEngineCmd("black","quit");
            //chessClock.stop();
            new Msg_Thread(colorCE.toUpperCase()+" SAY: end game !");
         }
        if ( (line.contains("bestmove") && ( line.contains("can't move")||line.contains("nomove")||line.contains("0000")||line.contains("null")||line.contains("none")||line.contains("a1a1")) ) || line.contains("resign")||line.contains("mates")||line.contains("mated")||line.contains("black checkmate")||line.contains("white checkmate")||line.contains("computer wins")||line.contains("stalemate")||(line.contains("1/2")&&(line.contains("move rule")||line.contains("moves rule")||line.contains("repetition")))||line.contains("{checkmate}")||line.contains("game over"))
         {
            aktion.sendEngineCmd("white","quit");
            aktion.sendEngineCmd("black","quit");
            //chessClock.stop();
            new Msg_Thread(colorCE.toUpperCase()+" SAY: "+line);
         }
        aktion.enginePromotionType="";
        if (this.ceTip.equals("uci")) {
            if (line.contains("bestmove")) 
                line=line.replaceAll("bestmove","move");
            if (line.contains("move") && line.contains("ponder")) { 
                String[] bf = line.trim().split("\\s+");
                line=bf[0] + " " + bf[1];
            }
        }
        if (line.contains("my move is:")) line=line.replaceAll("my move is:","move");
        if (line.contains("move")) {
            if (line.endsWith("n") && line.length()==10)
                { 
                    aktion.enginePromotionType="n"; 
                    aktion.enginePromotionFig="n";
                    aktion.promotionCount=MoveListUI.count;
                }
            if (line.endsWith("b") && line.length()==10)
                { 
                    aktion.enginePromotionType="b"; 
                    aktion.enginePromotionFig="b";
                    aktion.promotionCount=MoveListUI.count;                    
                }
            if (line.endsWith("r") && line.length()==10)
                { 
                    aktion.enginePromotionType="r"; 
                    aktion.enginePromotionFig="r";
                    aktion.promotionCount=MoveListUI.count;                    
                }           
            if (line.endsWith("q") && line.length()==10)
                { 
                    aktion.enginePromotionType="q"; 
                    aktion.enginePromotionFig="q";
                    aktion.promotionCount=MoveListUI.count;                    
                }           
            // for Animats and Tiffanys
            if (line.endsWith("o-o") && !line.endsWith("o-o-o")) {
                if (colorCE.equals("black")) 
                    line="move e8g8";
                else 
                    line="move e1g1";
                System.out.println("KingSide Castle o-o = "+line);
            }
            if (line.endsWith("0-0") && !line.endsWith("0-0-0")) {
                if (colorCE.equals("black")) 
                    line="move e8g8";
                else 
                    line="move e1g1";
                System.out.println("KingSide Castle Zero-Zero = "+line);
            }
            if (line.endsWith("o-o-o")) {
                if (colorCE.equals("black")) 
                    line="move e8c8";
                else 
                    line="move e1c1";
                System.out.println("QueenSide Castle o-o-o = "+line);
            }
            if (line.endsWith("0-0-0")) {
                if (colorCE.equals("black")) 
                    line="move e8c8";
                else 
                    line="move e1c1";
                System.out.println("QueenSide Castle Zero-Zero-Zero = "+line);
            } 
        }
        fireDataPrinted(new EngineEvent(this, line));
        return line;
    }
    
    public void writeLine(String string) {
        frame.outputArea.append("<write to "+colorCE.toUpperCase()+">: "+string+"\n");
        fireDataEntered(new EngineEvent(this, string));
        if (this.colorCE.equals("white")) 
         {
            // black output transformation for white uci
            if (this.ceTip.equals("uci")&& MoveListUI.count%2!=1) 
              {
                if (aktion.enemyTip.equals("another")&&string.length()==4&&!string.equals("easy")&&!string.equals("hard")&&!string.equals("post")&&!string.startsWith("sd")) {
                    writeLine("position startpos moves" + aktion.uciAllMovesString);
                    if (aktion.UseClock.equals("true") && !goEngine.equals("Magnum"))
                        writeLine("go depth " + aktion.Depth + " wtime " + frame.chessClock.getTime(ChessClock.WHITE_TURN) + " btime " + frame.chessClock.getTime(ChessClock.BLACK_TURN) + " winc 0 binc 0");
                    else
                        writeLine("go depth " + aktion.Depth);
                    return;
                } 
                if (aktion.enemyTip.equals("another")&&string.length()==5&&(string.endsWith("r")||string.endsWith("n")||string.endsWith("b")||string.endsWith("q"))) {
                    writeLine("position startpos moves" + aktion.uciAllMovesString);
                    if (aktion.UseClock.equals("true") && !goEngine.equals("Magnum"))
                        writeLine("go depth " + aktion.Depth + " wtime " + frame.chessClock.getTime(ChessClock.WHITE_TURN) + " btime " + frame.chessClock.getTime(ChessClock.BLACK_TURN) + " winc 0 binc 0");
                    else
                        writeLine("go depth " + aktion.Depth);
                    return;
                }                
              } // white UCI
            // black output transformation for SPECIAL white xboard
            if ((aktion.whitePlayerCE.equals("FairyPrincess")||aktion.whitePlayerCE.equals("Talvmenni")||aktion.whitePlayerCE.equals("FrankWalter")||aktion.whitePlayerCE.equals("KennyClassIQ"))&&string.length()==4&&!string.equals("easy")&&!string.equals("hard")&&!string.equals("post")&&!string.startsWith("sd")){
                this.engineIOwhite.writeLine("usermove "+string); return;
            }
            else if ((aktion.whitePlayerCE.equals("FairyPrincess")||aktion.whitePlayerCE.equals("Talvmenni")||aktion.whitePlayerCE.equals("FrankWalter")||aktion.whitePlayerCE.equals("KennyClassIQ"))&&string.length()==5&&(string.endsWith("r")||string.endsWith("n")||string.endsWith("b")||string.endsWith("q"))){
                this.engineIOwhite.writeLine("usermove "+string); return;
            }            
            else  
                this.engineIOwhite.writeLine(string); 
            return;
         } // white
        // white xboard/uci output transformation for SPECIAL black xboard
        if (this.colorCE.equals("black")) {
            if (aktion.blackPlayerCE.equals("FairyPrincess")||aktion.blackPlayerCE.equals("Talvmenni")||aktion.blackPlayerCE.equals("FrankWalter")||aktion.blackPlayerCE.equals("KennyClassIQ"))
            {
                if (string.length()==4&&!string.equals("easy")&&!string.equals("hard")&&!string.equals("post")&&!string.startsWith("sd"))
                {
                    this.engineIOblack.writeLine("usermove "+string); 
                    return;
                } 
                else if (string.length()==5&&(string.endsWith("r")||string.endsWith("n")||string.endsWith("b")||string.endsWith("q")))
                {
                    this.engineIOblack.writeLine("usermove "+string); 
                    return;
                }                 
                else  {this.engineIOblack.writeLine(string); return;}
            }
            else  {this.engineIOblack.writeLine(string); return;}
        }
        /*
        if (this.colorCE.equals("white")) 
            this.engineIOwhite.writeLine(string);
        else 
            this.engineIOblack.writeLine(string); */       
    }
    
    abstract protected void parseCommand() throws IOException;
    
    abstract public void userMove(Move move);
    
    abstract public void quitEngine();
    
    @Override
    public void dispose() {
        super.dispose();
        parseThread = null;
        quitEngine();
        if (this.colorCE.equals("white")) 
            engineIOwhite.destroy();
        else 
            engineIOblack.destroy();
    }
    
}