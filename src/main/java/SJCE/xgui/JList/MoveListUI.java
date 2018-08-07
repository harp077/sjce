package SJCE.xgui.JList;
import static SJCE.XChessFrame.SQUARES;
import static SJCE.XChessFrame.aktion;
import static SJCE.XChessFrame.frame;
import SJCE.more.Msg_Thread;
import SJCE.xgui.Move;
import SJCE.xgui.Notation;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;

public class MoveListUI extends JList {

 public static DefaultListModel listModel = new DefaultListModel();
 public static ArrayList<Move> moveList = new ArrayList<Move>();
 public static int count;

 public MoveListUI() {  setModel(listModel); }

 public void addMove(Move move) {
  moveList.add(move);
  String notation;
  count = listModel.size() + 1;
  if(count % 2 == 1) {
      if (move.getPiece()>5)
         {
            aktion.sendEngineCmd("white","quit");
            aktion.sendEngineCmd("black","quit"); 
            //frame.chessClock.stop();            
            new Msg_Thread("WHITE RESIGN !");
         }      
      notation = String.valueOf((count / 2) + 1)+") ";
      aktion.restColorWhite_moveColorBlack();
      if (aktion.useSound.equals("true") && (aktion.gameTip.equals("EH")||aktion.gameTip.equals("EE")))
          aktion.playWAV("ce/wav/honkhonk.wav");
      aktion.whiteLastMove=move;
      //if (aktion.whitePlayerCE.equals("Rival")||aktion.whitePlayerCE.equals("Eden"))
          aktion.whiteRivalMovesString=aktion.whiteRivalMovesString + " " + Notation.toString(move);
  }
  else {
      if (move.getPiece()<6)
         {
            aktion.sendEngineCmd("white","quit");
            aktion.sendEngineCmd("black","quit"); 
            //frame.chessClock.stop();            
            new Msg_Thread("BLACK RESIGN !");
         }          
      notation = "         |----------------> ";
      aktion.moveColorWhite_restColorBlack();
      if (aktion.useSound.equals("true") && (aktion.gameTip.equals("HE")||aktion.gameTip.equals("EE")))
          aktion.playWAV("ce/wav/sndMsg.wav");
      aktion.blackLastMove=move;
      //if (aktion.blackPlayerCE.equals("Rival")||aktion.blackPlayerCE.equals("Eden"))
         aktion.blackRivalMovesString=aktion.blackRivalMovesString + " " + Notation.toString(move);
  }
  if (aktion.promotionCount!=0&&count>aktion.promotionCount+1) {
     aktion.enginePromotionFig=""; 
     aktion.promotionCount=0;
     System.out.println("RESET PROMOTION TYPE !");
  }    
  switch (move.getPiece()) {
      case 0:  listModel.addElement(notation + " " + "wP_"+ Notation.toString(move)); break;
      case 6:  listModel.addElement(notation + " " + "bP_"+ Notation.toString(move)); break; 
      case 1:  listModel.addElement(notation + " " + "wN_"+ Notation.toString(move)); break;
      case 7:  listModel.addElement(notation + " " + "bN_"+ Notation.toString(move)); break;  
      case 2:  listModel.addElement(notation + " " + "wB_"+ Notation.toString(move)); break;
      case 8:  listModel.addElement(notation + " " + "bB_"+ Notation.toString(move)); break; 
      case 3:  listModel.addElement(notation + " " + "wR_"+ Notation.toString(move)); break;
      case 9:  listModel.addElement(notation + " " + "bR_"+ Notation.toString(move)); break; 
      case 4:  listModel.addElement(notation + " " + "wQ_"+ Notation.toString(move)); break;
      case 10: listModel.addElement(notation + " " + "bQ_"+ Notation.toString(move)); break; 
      case 5:  listModel.addElement(notation + " " + "wK_"+ Notation.toString(move)); break;
      case 11: listModel.addElement(notation + " " + "bK_"+ Notation.toString(move)); break;
      default: listModel.addElement(notation + " " + Notation.toString(move)); break;
  }
  if (Notation.toString(move).equals("a1a1"))
      aktion.uciAllMovesString=aktion.uciAllMovesString + " null";
  else
      aktion.uciAllMovesString=aktion.uciAllMovesString + " " + Notation.toString(move);
  aktion.enginePromotionType="";
  //System.out.println(aktion.uciAllMovesString);
  frame.boardUI.update(SQUARES);
  //frame.boardUI.repaint();
  //frame.boardUI.updateUI();
 }
 
 public ArrayList<Move> getMoveList() {  return moveList; }
 
 public void clear()                  {  moveList.clear(); listModel.clear(); }
 
}
