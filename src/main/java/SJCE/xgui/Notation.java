package SJCE.xgui;

import static SJCE.XChessFrame.aktion;
import SJCE.xgui.JPanel.BoardUI;

public class Notation {
    
 public static final char[] FILE_CHAR = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };
 public static final char[] RANK_CHAR = { '1', '2', '3', '4', '5', '6', '7', '8' };
 
 public Notation() { }

 public int getFile(char f) {  return f - 'a'; }
 public int getRank(char r) {  return r - '1'; }
 public char getFile(int f) {  return (char) ('a' + f); }
 public char getRank(int r) {  return (char) ('1' + r); }

 public static String toString(int square) {
  return new String(new char[] {FILE_CHAR[square % BoardUI.FILE_RANK],
      RANK_CHAR[(square / BoardUI.FILE_RANK)]});
 }
 
 public static int toSquare(String square) {
  return ((square.charAt(1) - '1')) * BoardUI.FILE_RANK + square.charAt(0) - 'a';
 }
 
 public static String toString(Move move) {
     String bufer="";
     bufer= toString(move.getSource()) + toString(move.getDestination()) + aktion.enginePromotionFig;
     return bufer;
 }
 
 public static Move toMove(String notation) {
  return new Move(toSquare(notation.substring(0, 2)),
    toSquare(notation.substring(2, 4)));
 }
 
}