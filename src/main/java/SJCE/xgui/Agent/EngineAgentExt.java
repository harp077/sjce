package SJCE.xgui.Agent;
import static SJCE.XChessFrame.aktion;
import static SJCE.XChessFrame.frame;
import SJCE.xgui.Interfaces.IChessContext;
import SJCE.more.Actions;
import SJCE.xgui.EventObject.EngineEvent;
import SJCE.xgui.EventObject.MoveEvent;
import SJCE.xgui.JList.MoveListUI;
import SJCE.xgui.JPanel.ChessClock;
import SJCE.xgui.Move;
import SJCE.xgui.Notation;
import SJCE.xgui.PiecesUI;
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

    // parseCommand(Engine |-> EngineAgent.readLine |-> move) and userMove( move |-> EngineAgent.writeLine |-> Engine ) !!!!!!!!!

public class EngineAgentExt extends EngineAgent {
 Pattern patternMove = Pattern.compile("(my)?\\s*move\\s*(is)?\\s*[:>=\\-]?\\s*([a-h][1-8][a-h][1-8][nbrq]?)", Pattern.CASE_INSENSITIVE);
 Pattern patternIllegal = Pattern.compile("(Illegal move.+)|(Error.+)", Pattern.CASE_INSENSITIVE);
 public static String ceTip;
 public static String goEngine;
 public static String colorCE;
 public EngineAgentExt(IChessContext context, String goEngine, String colorCE, String ceTip) 
    {   
        super(context, goEngine, colorCE, ceTip);
        this.ceTip=ceTip;
        this.goEngine=goEngine;
        this.colorCE=colorCE;        
    }

 @Override
 public void initiate() 
    {  
        super.initiate();
        if (this.ceTip.equals("xboard"))
        {
            writeLine("xboard");
            if (goEngine.equals("Animats")||goEngine.equals("FrankWalter")||goEngine.equals("KennyClassIQ"))
                writeLine("protover 2");            
            writeLine("new");
            switch (this.goEngine) {
                case "Alf":
                    writeLine(aktion.Mode);
                    writeLine("sd " + aktion.Depth);
                    writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");                
                    writeLine("post");
                    break; 
                // Animats not support any cmd    
                case "ArabianKnight":
                    writeLine(aktion.Mode);
                    writeLine("sd " + aktion.Depth);
                    writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");                
                    writeLine("post");
                    break; 
                case "BremboCE":
                    //writeLine(aktion.Mode); - not support
                    writeLine("sd " + aktion.Depth);
                    writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");                
                    writeLine("post");
                    break;                     
                case "CupCake":
                    writeLine(aktion.Mode);
                    writeLine("sd " + aktion.Depth);
                    writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");                
                    writeLine("post");
                    writeLine("new");
                    break; 
                case "CaveChess":
                    //writeLine(aktion.Mode);
                    writeLine("sd " + aktion.Depth);
                    //writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");                
                    //writeLine("post");
                    break;
                // ChessBotX not support any cmd  
                case "DeepBrutePos":
                    writeLine(aktion.Mode);
                    writeLine("sd " + aktion.Depth);
                    writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");                
                    writeLine("post");
                    break;                    
                case "FairyPrincess":
                    //writeLine(aktion.Mode);
                    //writeLine("depth " + aktion.Depth);
                    writeLine(colorCE);
                    writeLine("time "+6000*aktion.Time);
                    //writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");
                    //writeLine("post");
                    break;                     
                case "Frittle":
                    writeLine(aktion.Mode);
                    writeLine("sd " + aktion.Depth);
                    writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");                
                    writeLine("post");
                    break;
                case "FrankWalter":
                    writeLine(aktion.Mode);
                    writeLine("sd " + aktion.Depth);
                    writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");                
                    writeLine("post");
                    break;
                case "Gladiator":
                    writeLine(aktion.Mode);
                    writeLine("sd " + aktion.Depth);
                    //writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");  // Gladiator error returned              
                    writeLine("post");
                    break;                    
                case "GNU Chess":
                    writeLine(aktion.Mode);
                    writeLine("depth " + aktion.Depth);
                    writeLine("time "+60*aktion.Time);
                    writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");
                    writeLine("post");
                    break;  
                case "Javalin":
                    writeLine(aktion.Mode);
                    writeLine("sd " + aktion.Depth);
                    writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");                
                    writeLine("post");
                    break; 
                case "Jchess":
                    // NOT SUPPORT writeLine(aktion.Mode);
                    // NOT SUPPORT writeLine("sd " + aktion.Depth);
                    writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");                
                    writeLine("post");
                    break; 
                case "jChecs":
                    writeLine(aktion.Mode); 
                    writeLine("sd " + aktion.Depth);
                    break;                    
                case "KingsOut":
                    writeLine(aktion.Mode);
                    writeLine("sd " + aktion.Depth);
                    writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");
                    //writeLine("time "+60*aktion.Time);                    
                    writeLine("post");
                    break;
                case "KennyClassIQ":
                    writeLine(aktion.Mode);
                    writeLine("sd " + aktion.Depth);
                    writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");
                    //writeLine("time "+6000*aktion.Time);
                    writeLine("post");
                    break; 
                case "OliThink":
                    writeLine(aktion.Mode);
                    writeLine("sd " + aktion.Depth);
                    writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");                
                    writeLine("post");
                    break;                    
                case "Tiffanys":
                    //writeLine("new");
                    writeLine(aktion.Mode);
                    break;
                case "Talvmenni":
                    writeLine(aktion.Mode);
                    writeLine("sd " + aktion.Depth);
                    writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");                
                    writeLine("post");
                    break;
                case "Tri-OS":
                    //writeLine(aktion.Mode); // don't support
                    //writeLine("sd " + aktion.Depth);
                    writeLine("level "+60*aktion.Time+" "+aktion.Time+" 0");                
                    writeLine("post");
                    break;
            }            
        }
        if (this.ceTip.equals("uci")) 
         {
            writeLine("uci");
            writeLine("isready");
            writeLine("ucinewgame");
            writeLine("isready");
            //writeLine("setoption name UCI_ShowCurrLine value false");
            //writeLine("setoption name UCI_ShowRefutations value false");
            //writeLine("setoption name Nullmove value true");
            if (aktion.Mode.equals("hard"))
              writeLine("setoption name Ponder value true");
            else
              writeLine("setoption name Ponder value false");            
         }
    }
 
 @Override
 public void newGame() {
   /*
   if (colorCE.equals("white") && ceTip.equals("xboard")) { 
          if (!goEngine.equals("ArabianKnight") && !goEngine.equals("OliThink")&& !goEngine.equals("Eden")) 
             writeLine("white");  
          writeLine("go"); 
   }
   if (colorCE.equals("white") && ceTip.equals("uci"))   { 
          writeLine("position startpos");
          writeLine("go depth " + aktion.Depth);
   }*/
 }
 
 @Override
 protected void parseCommand() throws IOException {
   //final 
   String line = readLine();
   if(line == null) return;
   Matcher matcher;
   matcher = patternMove.matcher(line);
   if(matcher.matches()) {
       Move move = Notation.toMove(matcher.group(3).substring(0, 4));
                        updateContext(move); // !!!!!!!!!!!!!!!!!!!!!!!!!!!!
       if ((move.getPiece()==6)&(aktion.Prohod_White_Event==1)&(Math.abs(move.getSource() - aktion.Prohod_White_Destination)==1)&(Math.abs(move.getDestination() - aktion.Prohod_White_Destination)==8)) {
            System.out.println("Black Pawn En-Passant !");            
            boardUI.setPiece(PiecesUI.NO_PIECE,aktion.Prohod_White_Destination);
            boardUI.update(aktion.Prohod_White_Destination);
            aktion.Prohod_White_Event=0;
            aktion.Prohod_White_Destination=-1;
       }  
       if ((move.getPiece()==0)&(aktion.Prohod_Black_Event==1)&(Math.abs(move.getSource() - aktion.Prohod_Black_Destination)==1)&(Math.abs(move.getDestination() - aktion.Prohod_Black_Destination)==8)) {
            System.out.println("White Pawn En-Passant !");            
            boardUI.setPiece(PiecesUI.NO_PIECE,aktion.Prohod_Black_Destination);
            boardUI.update(aktion.Prohod_Black_Destination);
            aktion.Prohod_Black_Event=0;
            aktion.Prohod_Black_Destination=-1;
       }         
       if ((move.getPiece()==6)&(move.getSource()>=8)&(move.getSource()<=15)) {
            System.out.println("Black Pawn Promotion !!!!");
            switch (aktion.enginePromotionFig) {
                case "n": 
                    move.setPiece(PiecesUI.BLACK_KNIGHT);
                    boardUI.setPiece(PiecesUI.BLACK_KNIGHT, move.getDestination());
                    break;
                case "b":
                    move.setPiece(PiecesUI.BLACK_BISHOP);
                    boardUI.setPiece(PiecesUI.BLACK_BISHOP, move.getDestination()); 
                    break;
                case "r":
                    move.setPiece(PiecesUI.BLACK_ROOK);
                    boardUI.setPiece(PiecesUI.BLACK_ROOK, move.getDestination());
                    break;
                case "q":
                    move.setPiece(PiecesUI.BLACK_QUEEN);
                    boardUI.setPiece(PiecesUI.BLACK_QUEEN, move.getDestination());
                    break;
                default:
                    move.setPiece(PiecesUI.BLACK_QUEEN);
                    boardUI.setPiece(PiecesUI.BLACK_QUEEN, move.getDestination());
                    break;                    
            }
            boardUI.update(move.getDestination());
            //aktion.enginePromotionFig="";
       }  
       if ((move.getPiece()==0)&(move.getSource()>=48)&(move.getSource()<=55)) {
            System.out.println("White Pawn Promotion !!!!");            
            switch (aktion.enginePromotionFig) {
                case "n": 
                    move.setPiece(PiecesUI.WHITE_KNIGHT);
                    boardUI.setPiece(PiecesUI.WHITE_KNIGHT, move.getDestination());
                    break;
                case "b":
                    move.setPiece(PiecesUI.WHITE_BISHOP);
                    boardUI.setPiece(PiecesUI.WHITE_BISHOP, move.getDestination()); 
                    break;
                case "r":
                    move.setPiece(PiecesUI.WHITE_ROOK);
                    boardUI.setPiece(PiecesUI.WHITE_ROOK, move.getDestination());
                    break;
                case "q":
                    move.setPiece(PiecesUI.WHITE_QUEEN);
                    boardUI.setPiece(PiecesUI.WHITE_QUEEN, move.getDestination());
                    break;
                default:
                    move.setPiece(PiecesUI.WHITE_QUEEN);
                    boardUI.setPiece(PiecesUI.WHITE_QUEEN, move.getDestination());
                    break;                    
            }
            boardUI.update(move.getDestination());
            //aktion.enginePromotionFig="";
       }         
    fireMovePrinted(new EngineEvent(this, matcher.group(3)));
    fireMovePerformed(new MoveEvent(this, move));
    //System.out.println("OPA");
   }
   matcher = patternIllegal.matcher(line);
   if(matcher.matches()) fireIllegalPrinted(new EngineEvent(this));
 }
 
 @Override
 public void userMove(Move move) 
   { 
       String usercmd=Notation.toString(move);
       if ((move.getPiece()==0)&(Math.abs(move.getDestination()-move.getSource())==16)) {
            System.out.println("White Pawn 2-Prohod !"); 
            aktion.Prohod_White_Event=1;    
            aktion.Prohod_White_Destination=move.getDestination();            
       } 
       if ((move.getPiece()==6)&(Math.abs(move.getDestination()-move.getSource())==16)) {
            System.out.println("Black Pawn 2-Prohod !"); 
            aktion.Prohod_Black_Event=1;    
            aktion.Prohod_Black_Destination=move.getDestination();            
       }        
       if ((move.getPiece()==0)&(move.getSource()>=48)&(move.getSource()<=55)) 
        {
          if (ceTip.equals("xboard"))  
           {
             switch (goEngine) {
                 case "Animats":      writeLine(usercmd); break;
                 case "FrankWalter":  writeLine("usermove "+usercmd+"q"); break;
                 case "KennyClassIQ": writeLine("usermove "+usercmd+"q"); break;
                 case "Talvmenni":    writeLine("usermove "+usercmd+"q"); break;
                 case "FairyPrincess":    writeLine("usermove "+usercmd+"q"); break;
                 default:             writeLine(usercmd+"q"); break;
             }                
           }
          else
           {
            aktion.uciAllMovesString = aktion.uciAllMovesString + "q";               
            writeLine("position startpos moves" + aktion.uciAllMovesString);
            if (aktion.UseClock.equals("true") && !goEngine.equals("Magnum"))
                writeLine("go depth " + aktion.Depth + " wtime " + frame.chessClock.getTime(ChessClock.WHITE_TURN) + " btime " + frame.chessClock.getTime(ChessClock.BLACK_TURN) + " winc 0 binc 0");
            else
                writeLine("go depth " + aktion.Depth);
           }
          System.out.println("White Pawn to Queen !");            
          move.setPiece(PiecesUI.WHITE_QUEEN);
          boardUI.setPiece(PiecesUI.WHITE_QUEEN, move.getDestination());
          boardUI.update(move.getDestination());          
          return;
        }
       if ((move.getPiece()==6)&(move.getSource()>=8)&(move.getSource()<=15)) 
        {
          if (ceTip.equals("xboard"))  
           { 
             switch (goEngine) {
                 case "Animats":      writeLine(usercmd); break;
                 case "FrankWalter":  writeLine("usermove "+usercmd+"q"); break;
                 case "KennyClassIQ": writeLine("usermove "+usercmd+"q"); break;
                 case "Talvmenni":    writeLine("usermove "+usercmd+"q"); break;
                 case "FairyPrincess":    writeLine("usermove "+usercmd+"q"); break;
                 default:             writeLine(usercmd+"q"); break;
             } 
           }
          else
           {
            aktion.uciAllMovesString = aktion.uciAllMovesString + "q";  
            writeLine("position startpos moves" + aktion.uciAllMovesString);
            if (aktion.UseClock.equals("true") && !goEngine.equals("Magnum"))
                writeLine("go depth " + aktion.Depth + " wtime " + frame.chessClock.getTime(ChessClock.WHITE_TURN) + " btime " + frame.chessClock.getTime(ChessClock.BLACK_TURN) + " winc 0 binc 0");
            else
                writeLine("go depth " + aktion.Depth);            
           } 
          System.out.println("Black Pawn to Queen !");            
          move.setPiece(PiecesUI.BLACK_QUEEN);
          boardUI.setPiece(PiecesUI.BLACK_QUEEN, move.getDestination());
          boardUI.update(move.getDestination());          
          return;
        }       
       if (goEngine.equals("FairyPrincess")||goEngine.equals("FrankWalter")||goEngine.equals("KennyClassIQ")||goEngine.equals("Talvmenni")) 
        { 
          if (aktion.gameTip.equals("EE"))
             writeLine(usercmd);
          else
             writeLine("usermove "+usercmd); 
          return;
        }
       if (ceTip.equals("xboard"))
               //&&!aktion.enemyTip.equals("another")) 
        {
          writeLine(usercmd); 
          return;
        }
       if (ceTip.equals("uci")&&!aktion.enemyTip.equals("another"))            
        {
          writeLine("position startpos moves" + aktion.uciAllMovesString);
          if (aktion.UseClock.equals("true") && !aktion.whitePlayerCE.equals("Magnum")&& !aktion.blackPlayerCE.equals("Magnum"))
            writeLine("go depth " + aktion.Depth + " wtime " + frame.chessClock.getTime(ChessClock.WHITE_TURN) + " btime " + frame.chessClock.getTime(ChessClock.BLACK_TURN) + " winc 0 binc 0");
          else
            writeLine("go depth " + aktion.Depth);
          return;
        }
       if (ceTip.equals("uci")&&colorCE.equals("black")&&aktion.enemyTip.equals("another")) {
           if (MoveListUI.count%2==1) {
                writeLine("position startpos moves" + aktion.uciAllMovesString);
                if (aktion.UseClock.equals("true") && !goEngine.equals("Magnum"))
                    writeLine("go depth " + aktion.Depth + " wtime " + frame.chessClock.getTime(ChessClock.WHITE_TURN) + " btime " + frame.chessClock.getTime(ChessClock.BLACK_TURN) + " winc 0 binc 0");
                else
                    writeLine("go depth " + aktion.Depth);
                return;
           }// if MoveListUI.count%2!=1
           else {
               if ((move.getPiece()==0)&(move.getSource()>=48)&(move.getSource()<=55))
                   writeLine(usercmd+"q");
               else if ((move.getPiece()==6)&(move.getSource()>=8)&(move.getSource()<=15)) 
                   writeLine(usercmd+"q");
               else
                   writeLine(usercmd);
               //System.out.println("OPA");
               return;
           }
       }
   }
 
 private String getTime(long time) { time /= 1000;return time/60+":"+(time%60)/1000; }

 @Override
 public void quitEngine() {  writeLine("quit"); }
 
}
