package SJCE.xgui;

import SJCE.xgui.JPanel.BoardUI;
import java.util.Arrays;

public class PiecesUI {
    
 public static final int NO_PIECE = -1;
 public final static int PIECE_ACCUMULATED = 32;
 public final static int PIECE_TOTAL = 12;
 public final static int PIECE_SIDE = 6;
 public static final int[] PAWN = {0, 6};
 public static final int[] KNIGHT = {1, 7};
 public static final int[] BISHOP = {2, 8};
 public static final int[] ROOK = {3, 9};
 public static final int[] QUEEN = {4, 10};
 public static final int[] KING = {5, 11};
 public static final int WHITE_PAWN = 0;
 public static final int WHITE_KNIGHT = 1;
 public static final int WHITE_BISHOP = 2;
 public static final int WHITE_ROOK = 3;
 public static final int WHITE_QUEEN = 4;
 public static final int WHITE_KING = 5;
 public static final int BLACK_PAWN = 6;
 public static final int BLACK_KNIGHT = 7;
 public static final int BLACK_BISHOP = 8;
 public static final int BLACK_ROOK = 9;
 public static final int BLACK_QUEEN = 10;
 public static final int BLACK_KING = 11;
 public static final int WHITE_START = 0;
 public static final int WHITE_END = 5;
 public static final int BLACK_START = 6;
 public static final int BLACK_END = 11;
 public static final int PIECE_MIN = 0;
 public static final int PIECE_MAX = 11;
 public static final int COLOR_WHITE = 0;
 public static final int COLOR_BLACK = 1;
 
 private int[] board = new int[BoardUI.SQUARE_COUNT];

 public PiecesUI() {  Arrays.fill(board, NO_PIECE); }

 public static boolean isPiece(int[] expected, int unknown) {
  if (unknown % BLACK_START == expected[COLOR_WHITE]) return true;
  return false;
 }
 
 public static int getColor(int piece) {
  if (piece >= WHITE_START && piece <= WHITE_END) {
   return COLOR_WHITE;
  } else if (piece >= BLACK_START && piece <= BLACK_END) {
   return COLOR_BLACK;
  }
  throw new IllegalArgumentException("Invalid piece: " + piece);
 }
 
 public static boolean sameColor(int piece1, int piece2) {
  if(PiecesUI.getColor(piece1) == PiecesUI.getColor(piece2)) return true;
  else return false;
 }
 
 public int[] getBoard()                     {  return board; }
 public void setBoard(int[] board) 
    {  System.arraycopy(board, 0, this.board, 0, board.length);  }
 public int getPiece(int square)             {  return board[square]; }
 public void setPiece(int piece, int square) {  board[square] = piece; }
 public void removePiece(int square)         {  board[square] = NO_PIECE; }
 
 public int indexOf(int piece) {
  for (int i = 0; i < board.length; i++) {
   if (board[i] == piece) return i;
  }
  return BoardUI.NO_SQUARE;
 }
 
 public static int indexOf(int[] board, int piece) {
  for (int i = 0; i < board.length; i++) {
   if (board[i] == piece) return i;
  }
  return BoardUI.NO_SQUARE;
 }
 
 public static int switchColor(int color) {  return (color + 1) % 2; }
 
}
