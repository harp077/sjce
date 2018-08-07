package SJCE.xgui.EventObject;

import SJCE.xgui.Move;
import java.util.EventObject;

public class MoveEvent extends EventObject {
    
 private Move move;
 
 public MoveEvent(Object source, Move move) {  super(source); this.move = move; }
 
 public Move getMove()                      {  return move; }
 
 public void setMove(Move move)             {  this.move = move; }
 
}
