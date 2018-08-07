package SJCE.xgui.JPanel;

import SJCE.xgui.ChessTheme;
import SJCE.xgui.PiecesUI;
import SJCE.xgui.Utility;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.JPanel;

public class BoardUI extends JPanel {
 public static final int FILE_RANK = 8;
 public static final int SQUARE_COUNT = 64;
 public static final int OFF_BOARD = -10;
 public static final int NO_SQUARE = -1;
 public static final int SQUARE_WHITE = 0;
 public static final int SQUARE_BLACK = 1;
 public static final int HIGHLIGHT_NONE = -1;
 public static final int HIGHLIGHT_SELECT = 0;
 public static final int HIGHLIGHT_MOVE = 1;
 public ChessTheme chessTheme = ChessTheme.getChessTheme();
 private PiecesUI piecesUI = new PiecesUI();
 private Dimension squareSize = new Dimension();
 private HashMap<Integer, Integer> highlightMap = new HashMap<Integer, Integer>(10);
 public BufferedImage boardImage;
 public BoardUI() {
  addComponentListener(new ComponentAdapter() {
   @Override
   public void componentResized(ComponentEvent e) {
    squareSize.setSize((int) (getWidth() / (double) FILE_RANK),
      (int) (getHeight() / (double) FILE_RANK));
    chessTheme.adjustTheme(getSize());
    boardImage = new BufferedImage(getWidth(), getHeight(),
      BufferedImage.TYPE_INT_ARGB);
    offPaint();
   }
  });
  setPreferredSize(new Dimension(400, 400));
  setMinimumSize(new Dimension(400,400));
  setSize(400,400);
  setBoard(Utility.INITIAL_BOARD);
 }
 public void offPaint() {
  if(boardImage != null)
   offPaint(0, 0, boardImage.getWidth(), boardImage.getHeight());
 }
 private void offPaint(int x, int y, int width, int height) {
  if(boardImage == null) return;
  Graphics2D g2d = boardImage.createGraphics();
  g2d.clipRect(x, y, width, height);
  for (int i = 0; i < FILE_RANK; i++) {
   for (int j = 0; j < FILE_RANK; j++) {
    if ((i + j) % 2 == 0) g2d.drawImage(chessTheme.getSquareImage(SQUARE_BLACK),
      i * squareSize.width, (FILE_RANK - j - 1) * squareSize.height, null);
    else g2d.drawImage(chessTheme.getSquareImage(SQUARE_WHITE),
      i * squareSize.width, (FILE_RANK - j - 1) * squareSize.height, null);
   }
  }
  for(int key : highlightMap.keySet()) {
   Point point = getSquare(key);
   g2d.drawImage(chessTheme.getHighlight(highlightMap.get(key)),
     point.x, point.y, null);
  }
  int[] board = piecesUI.getBoard();
  for(int i = 0; i < board.length; i++) {
   if(board[i] != PiecesUI.NO_PIECE) {
    Point point = getSquare(i);
    g2d.drawImage(chessTheme.getPieceImage(board[i]), point.x, point.y, null);
   }
  }
  repaint(x, y, width, height);
 }
 @Override
 public void paint(Graphics g) {
  super.paint(g);
  g.drawImage(boardImage, 0, 0, this);
 }
 public void addHighlight(int highlight, int square) {
  highlightMap.put(square, highlight);
  update(square);
 }
 public void removeHighlight(int square) {
  highlightMap.remove(square);
  update(square);
 }
 public Point getSquare(int square) {
  return new Point((square % FILE_RANK) * squareSize.width,
  (FILE_RANK - square / FILE_RANK - 1) * squareSize.height);
 }
 public int getSquare(Point point) {
  double file = point.x / (double) squareSize.width;
  double rank = point.y / (double) squareSize.height;
  if(file < 0 || file > 8) return OFF_BOARD;
  if(rank < 0 || rank > 8) return OFF_BOARD;
  return  ((int) (file)) + (FILE_RANK - ((int) (rank)) - 1) * FILE_RANK;
 }
 public void update(int square) {
  Point point = getSquare(square);
  offPaint(point.x, point.y, squareSize.width, squareSize.height);
 }
 public void update(int[] squares) {
  for(int square : squares) update(square);
 }
 public int[] getBoard() {
  return piecesUI.getBoard();
 }
 public void setBoard(int[] board) {
  piecesUI.setBoard(board);
  offPaint();
 }
 public int getPiece(int square) {
  return piecesUI.getPiece(square);
 }
 public void setPiece(int piece, int square, boolean repaint) {
  piecesUI.setPiece(piece, square);
  if(repaint) update(square);
 }
 public void setPiece(int piece, int square) {
  setPiece(piece, square, true);
 }
 public void drawImage(Image image, Point point) {
  Graphics2D g2d = boardImage.createGraphics();
  g2d.drawImage(image, point.x, point.y, null);
  repaint(point.x, point.y, image.getWidth(null), image.getHeight(null));
 }
 public void clearImage(Rectangle rect) {
  offPaint(rect.x, rect.y, rect.width, rect.height);
 }
 public void removePiece(int square, boolean repaint) {
  piecesUI.removePiece(square);
  if(repaint) update(square);
 }
 public void removePiece(int square) {
  removePiece(square, true);
 }
 public static int getSquare(int file, int rank) {
  return rank * FILE_RANK + file;
 }
 public static int getFile(int square) {
  return square % FILE_RANK;
 }
 public static int getRank(int square) {
  return square / FILE_RANK;
 }
 public static int getRankDistance(int square1, int square2) {
  return getRank(square1) - getRank(square2);
 }
 public static int getFileDistance(int square1, int square2) {
  return getFile(square1) - getFile(square2);
 }
 public ChessTheme getChessTheme() {
  return chessTheme;
 }
}
