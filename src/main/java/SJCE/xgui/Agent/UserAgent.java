package SJCE.xgui.Agent;
import SJCE.xgui.EventObject.MoveEvent;
import SJCE.xgui.Interfaces.IChessContext;
import SJCE.xgui.JPanel.BoardUI;
import SJCE.xgui.JPanel.ChessClock;
import SJCE.xgui.Move;
import SJCE.xgui.PiecesUI;
import SJCE.xgui.Verification;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JOptionPane;

public class UserAgent extends Agent {

 private static final int HIGHLIGHT_NULL = -1;
 private static MouseEvent expireEvent;
 private int highlight = HIGHLIGHT_NULL;
 private int pressSquare = BoardUI.NO_SQUARE;
 private int dragPiece = PiecesUI.NO_PIECE;
 private Rectangle dragRect = new Rectangle();
 private Image dragImage;
 private int[] cloneBoard = new int[BoardUI.SQUARE_COUNT];
 private Verification verification = new Verification(cloneBoard);
 private MouseAdapter mouseAdapter;
 private MouseMotionAdapter mouseMotionAdapter;

 public UserAgent(IChessContext context, String goHuman, String colorCE, String ceTip) {
  super(context, goHuman, colorCE, ceTip);
  boardUI.addMouseListener(mouseAdapter = new MouseAdapter() {
   @Override
   public void mousePressed(MouseEvent e) {
    if(agentTurn == chessClock.getTurn() && expireEvent != e) {
     UserAgent.this.mousePressed(e);
     expireEvent = e;
    }
   }
   @Override
   public void mouseReleased(MouseEvent e) {
    if(agentTurn == chessClock.getTurn() && expireEvent != e) {
     UserAgent.this.mouseReleased(e);
     expireEvent = e;
    }
   }
  });
  boardUI.addMouseMotionListener(mouseMotionAdapter = new MouseMotionAdapter() {
   @Override
   public void mouseDragged(MouseEvent e) {
    if(agentTurn == chessClock.getTurn() && expireEvent != e) {
     UserAgent.this.mouseDragged(e);
     expireEvent = e;
    }
   }
  });
 }
 protected void mouseDragged(MouseEvent e) {
  if(dragPiece == PiecesUI.NO_PIECE) return;
  Point point = e.getPoint();
  boardUI.clearImage(dragRect);
  point.translate(-dragRect.width/2, -dragRect.height/2);
  dragRect.setLocation(point);
  boardUI.drawImage(dragImage, dragRect.getLocation());
 }
 private void mousePressed(MouseEvent e) {
  Point point = e.getPoint();
  pressSquare = boardUI.getSquare(point);
  dragPiece = boardUI.getPiece(pressSquare);
  if(dragPiece == PiecesUI.NO_PIECE || ChessClock.getTurn(dragPiece) != agentTurn) {
   dragPiece = PiecesUI.NO_PIECE;
   return;
  }
  dragImage = boardUI.getChessTheme().getPieceImage(dragPiece);
  dragRect.setSize(dragImage.getWidth(null), dragImage.getHeight(null));
  dragRect.setLocation(boardUI.getSquare(pressSquare));
  boardUI.removePiece(pressSquare, false);
 }
 private void mouseReleased(MouseEvent e) {
  int source = BoardUI.NO_SQUARE;
  int destination = boardUI.getSquare(e.getPoint());
  int piece = PiecesUI.NO_PIECE;
  if(dragPiece != PiecesUI.NO_PIECE) {
   boardUI.clearImage(dragRect);
   boardUI.setPiece(dragPiece, pressSquare);
  }
  if(destination == BoardUI.OFF_BOARD) return;
  if(highlight != HIGHLIGHT_NULL && pressSquare == destination) {
   source = highlight;
   piece = boardUI.getPiece(source);
   boardUI.removeHighlight(highlight);
   highlight = HIGHLIGHT_NULL;
   if (source == destination) return;
  }
  else if(dragPiece != PiecesUI.NO_PIECE) {
   boardUI.setPiece(dragPiece, pressSquare, false);
   source = pressSquare;
   piece = dragPiece;
   boardUI.clearImage(dragRect);
   if(source == destination) {
    if(dragRect.getLocation().equals(boardUI.getSquare(destination)))
     boardUI.addHighlight(BoardUI.HIGHLIGHT_SELECT,
       highlight = boardUI.getSquare(e.getPoint()));
    return;
   }
  } else return;
  System.arraycopy(boardUI.getBoard(), 0, cloneBoard, 0, cloneBoard.length);
  Move move = new Move(source, destination, piece);
  int type = move.doMove(boardUI.getBoard());
  boardUI.update(move.getAffectedSquares(type));
                            int result = verification.initialVerify(move, type);
  if (result == Verification.INVALID_MOVE) {
   move.undoMove(boardUI.getBoard(), type);
   boardUI.update(move.getAffectedSquares(type));
   JOptionPane.showMessageDialog(boardUI, verification.getMessage(result),
     "117 UserAgent: Illegal Move", JOptionPane.WARNING_MESSAGE);
   return;
  } else {
   System.arraycopy(boardUI.getBoard(), 0, cloneBoard, 0, cloneBoard.length);
                                        result = verification.finalVerify(move);
   if (result == Verification.INVALID_MOVE) {
    move.undoMove(boardUI.getBoard(), type);
    boardUI.update(move.getAffectedSquares(type));
    JOptionPane.showMessageDialog(boardUI, verification.getMessage(result),
      "126 UserAgent: Illegal Move", JOptionPane.WARNING_MESSAGE);
    return;
   }
  }
  moveListUI.addMove(move);
  chessClock.stop();
  chessClock.switchTurn();
  fireMovePerformed(new MoveEvent(this, move));
  if(highlight != HIGHLIGHT_NULL) {
   boardUI.removeHighlight(highlight);
   highlight = HIGHLIGHT_NULL;
  }
  dragPiece = PiecesUI.NO_PIECE;
 }
 @Override
 public void moveDeclared(Move move) {
  chessClock.start();
 }
 @Override
 public void newGame() {
 }
 @Override
 public void dispose() {
  super.dispose();
  boardUI.removeMouseListener(mouseAdapter);
  boardUI.removeMouseMotionListener(mouseMotionAdapter);
 }
}
