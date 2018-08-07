package SJCE.xgui;

import SJCE.xgui.JPanel.BoardUI;

public class Move {
 private static byte[][] CASTLE = {{7, 5}, {0, 3}, {63, 61}, {56, 59}};
 public static int NORMAL_MOVE = 100;
 public static int CASTLE_MOVE = 101;
 public static int PROMOTION_MOVE = 102;
 public static int ENPASSANT_MOVE = 103;
 public static int WOO_CASTLE = 0;
 public static int WOOO_CASTLE = 1;
 public static int BOO_CASTLE = 2;
 public static int BOOO_CASTLE = 3;
 public static int ENPASSANT_CAPTURE = 80;
 private short source;
 private short destination;
 private byte piece;
 private byte captured;
 private byte promoted;
 public Move() {
  this(BoardUI.NO_SQUARE, BoardUI.NO_SQUARE, PiecesUI.NO_PIECE);
 }
 public Move(int source, int destination) {
  this(source, destination, PiecesUI.NO_PIECE);
 }
 public Move(int source, int destination, int piece) {
  this.source = (short) source;
  this.destination = (short) destination;
  this.piece = (byte) piece;
  this.captured = PiecesUI.NO_PIECE;
  this.promoted = PiecesUI.NO_PIECE;
 }
 public int getSource() {  return source; }
 public int getDestination() {  return destination; }
 public int getPiece() {  return piece; }
 public void setPiece(int piece) {  this.piece = (byte) piece; }
 public int getCaptured() {  return captured; }
 public void setCaptured(int captured) {  this.captured = (byte) captured; }
 public int getPromoted() {  return promoted; }
 public void setPromoted(int promoted) {  this.promoted = (byte) promoted; }
 public int doMove(int[] board) {
  setPiece(board[source]);
  setCaptured(board[destination]);
  board[destination] = board[source];
  board[source] = PiecesUI.NO_PIECE;
  Move castle = castleMove();
  if(castle != null) {
   board[castle.getDestination()] = board[castle.getSource()];
   board[castle.getSource()] = PiecesUI.NO_PIECE;
   return CASTLE_MOVE;
  }
  int square = enPassant(board);
  if(square != BoardUI.NO_SQUARE) {
   setCaptured(ENPASSANT_CAPTURE + board[square]);
   board[square] = PiecesUI.NO_PIECE;
   return ENPASSANT_MOVE;
  }
  return NORMAL_MOVE;
 }
 public void undoMove(int[] board, int type) {
  board[source] = board[destination];
  if(type == ENPASSANT_MOVE) {
   board[BoardUI.getSquare(BoardUI.getFile(getDestination()),
     BoardUI.getRank(getSource()))] = captured - ENPASSANT_CAPTURE;
   board[destination] = PiecesUI.NO_PIECE;
  } else board[destination] = captured;
  if(type == CASTLE_MOVE) {
   int[] squares = castleRookMove();
   board[squares[0]] = board[squares[1]];
   board[squares[1]] = PiecesUI.NO_PIECE;
  }
  if(type == PROMOTION_MOVE)
   throw new UnsupportedOperationException("Not Implemented");
 }
 public int undoMove(int[] board) {
  int type = NORMAL_MOVE;
  if(isCastleMove()) type = CASTLE_MOVE;
  if(isEnPassant()) type = ENPASSANT_MOVE;
  undoMove(board, type);
  return type;
 }
 public int[] getAffectedSquares(int type) {
  if(type == ENPASSANT_MOVE) {
   return new int[] {source, destination,
     BoardUI.getSquare(BoardUI.getFile(destination), BoardUI.getRank(source))};
  } else if(type == CASTLE_MOVE) {
   int[] rookMove = castleRookMove();
   return new int[] {source, destination, rookMove[0], rookMove[1]};
  } else return new int[] {source, destination};
 }
 private int[] castleRookMove() {
  switch(source + destination) {
  case 10: return new int[] {CASTLE[WOO_CASTLE][0], CASTLE[WOO_CASTLE][1],
    PiecesUI.WHITE_ROOK};
  case 6: return new int[] {CASTLE[WOOO_CASTLE][0], CASTLE[WOOO_CASTLE][1],
    PiecesUI.WHITE_ROOK};
  case 122: return new int[] {CASTLE[BOO_CASTLE][0], CASTLE[BOO_CASTLE][1],
    PiecesUI.BLACK_ROOK};
  case 118: return new int[] {CASTLE[BOOO_CASTLE][0], CASTLE[BOOO_CASTLE][1],
    PiecesUI.BLACK_ROOK};
  }
  return null;
 }
 private Move castleMove() {
  if(!isCastleMove()) return null;
  int[] rookMove = castleRookMove();
  return new Move(rookMove[0], rookMove[1], rookMove[2]);
 }
 private boolean isCastleMove() {
  if(!PiecesUI.isPiece(PiecesUI.KING, piece)) return false;
  if(BoardUI.getRankDistance(destination, source) != 0) return  false;
  if(Math.abs(BoardUI.getFileDistance(destination, source)) != 2) return false;
  return true;
 }
 private boolean isEnPassant() {
  if(captured >= ENPASSANT_CAPTURE) return true;
  else return false;
 }
 private int enPassant(int[] board) {
  if(!PiecesUI.isPiece(PiecesUI.PAWN, piece)) return BoardUI.NO_SQUARE;
  int distance = Math.abs(BoardUI.getFileDistance(destination, source));
if(distance != 1 || board[destination] != PiecesUI.NO_PIECE) return BoardUI.NO_SQUARE;
  return BoardUI.getSquare(BoardUI.getFile(destination), BoardUI.getRank(source));
 }
}
