package SJCE.xgui.Agent;
import SJCE.xgui.EventObject.MoveEvent;
import SJCE.xgui.Interfaces.IMoveListener;
import SJCE.xgui.Interfaces.IChessContext;
import SJCE.xgui.JList.MoveListUI;
import SJCE.xgui.JPanel.BoardUI;
import SJCE.xgui.JPanel.ChessClock;
import SJCE.xgui.Move;
import java.util.ArrayList;
public abstract class Agent {
 public static final int USER_AGENT = 0;
 public static final int ENGINE_AGENT = 1;
 protected BoardUI boardUI;
 protected MoveListUI moveListUI;
 protected ChessClock chessClock;
 protected int agentTurn;
 private Agent opponentAgent;
 private ArrayList<IMoveListener> listenerList = new ArrayList<IMoveListener>(2);
 private IMoveListener moveListener;
 public Agent(IChessContext context, String goEngine, String colorCE, String ceTip) {
  this.boardUI = context.getBoardUI();
  this.moveListUI = context.getMoveListUI();
  this.chessClock = context.getChessClock();
 }
 public static Agent createAgent(IChessContext context, int type, String goEngine, String colorCE, String ceTip) {
  switch(type) {
    case USER_AGENT:   return new UserAgent(context, goEngine, colorCE, ceTip);
    case ENGINE_AGENT: return EngineAgent.createEngine(context, goEngine, colorCE, ceTip);
  }
  return null;
 }
 public void addIMoveListener(IMoveListener l)    { listenerList.add(l); }
 public void removeIMoveListener(IMoveListener l) { listenerList.remove(l); }
 protected void fireMovePerformed(MoveEvent e) {
  int count = listenerList.size();
  for(int i = 0; i < count; i++) {
   listenerList.get(i).movePerformed(e);
  }
 }
 public Agent getOpponentAgent() { return opponentAgent; }
 public void setOpponentAgent(Agent agent) {
  this.opponentAgent = agent;
  agent.addIMoveListener(moveListener = new IMoveListener() {
   public void movePerformed(MoveEvent e) {
    Agent.this.moveDeclared(e.getMove());
   }
  });
 }
 public int  getTurn()          {  return agentTurn;  }
 public void setTurn(int turn)  {  this.agentTurn = turn; }
 public void dispose() {
  opponentAgent.removeIMoveListener(moveListener);
  opponentAgent = null;
 }
 public abstract void newGame();
 public abstract void moveDeclared(Move move);
}
