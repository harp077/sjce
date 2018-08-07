package SJCE.xgui.JPanel;

import SJCE.more.Actions;
import SJCE.xgui.PiecesUI;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

public class ChessClock extends JPanel {

 public static final int ON_TURN = 2;
 public static final int OFF_TURN = 3;
 public static final int WHITE_TURN = PiecesUI.COLOR_WHITE;
 public static final int BLACK_TURN = PiecesUI.COLOR_BLACK;
 private static final int ONE_SECOND = 1000;
 public JLabel[] label = new JLabel[4];
 private Timer timer = new Timer("Timer", true);
 private ClockTask clockTask;
 private long[] time = new long[2];
 private long systemTime = 0;
 private int turn = WHITE_TURN;

 public ChessClock() {
  super();
  initGUI();
        // for lighting timers !!!
        label[0].setOpaque(false);
        label[1].setOpaque(false);
        label[2].setOpaque(false);
        label[3].setOpaque(false);        
  setTime(WHITE_TURN, Actions.Time*60000);
  setTime(BLACK_TURN, Actions.Time*60000);
 }

 public synchronized void start() {
  long remainder = Math.abs(time[turn] % 1000);
  timer.scheduleAtFixedRate(clockTask = new ClockTask(),
    (remainder == 0)? 1000 : remainder, ONE_SECOND);
  systemTime = System.currentTimeMillis();
 }
 
 public synchronized void stop() {
  if(clockTask != null) clockTask.cancel();
 }
 
 private void initGUI() {
  try {
   this.setLayout(new GridLayout(2,2));
   setPreferredSize(new Dimension(210, 110));
   for(int i : new int[] {WHITE_TURN, BLACK_TURN,2,3}) {
    label[i] = new JLabel();
    this.add(label[i]);
    label[i].setHorizontalAlignment(SwingConstants.CENTER);
    label[i].setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
    label[i].setFont(label[i].getFont().deriveFont(Font.BOLD, 16));
    label[i].setSize(105,55);
   }
  } catch (Exception e) {   e.printStackTrace();  }
 }
 
 public int getTurn()                 {  return turn;  }
 
 public static int getTurn(int piece) {  return PiecesUI.getColor(piece); }
 
 public void setTurn(int turn)        {  this.turn = turn;      }
 
 public void switchTurn()             {  turn = (turn + 1) % 2; }

 private class ClockTask extends TimerTask {
                        //{systemTime = System.currentTimeMillis();}
  @Override
  public synchronized void run() {
   systemTime = System.currentTimeMillis();
   time[turn] = time[turn] - 1000;
   label[turn].setText(ChessClock.this.toString(time[turn]));
  }
  @Override
  public synchronized boolean cancel() {
   time[turn] = time[turn] - (System.currentTimeMillis() - systemTime);
   label[turn].setText(ChessClock.this.toString(time[turn]));
        //time[0]=0;
        //time[1]=0;
        //systemTime =0;
   return super.cancel();
  }
 }

 private String toString(long time) {
  String sign = new String();
  int timeSecond = (int)(time / 1000);
  if(timeSecond < 0) {
   timeSecond = -timeSecond;
   sign = "-";
  }
  int hour = timeSecond / 3600;
  int minute = (timeSecond % 3600) / 60;
  int second = timeSecond % 60;

  return (hour == 0)? String.format(sign + "%1$02d:%2$02d", minute, second)
    : String.format(sign + "%1$02d:%2$02d:%3$02d", hour, minute, second);
 }

 public long getTime(int turn) {   return time[turn]; }
 
 public void setTime(int turn, long time) {
  this.time[turn] = time;
  label[turn].setText(toString(this.time[turn]));
 }
 
 public void setTime(long time) {
  setTime(WHITE_TURN, time);   
  setTime(BLACK_TURN, time);
 }
}
