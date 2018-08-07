package SJCE.xgui;

public class Utility {
    
 public static final int[] INITIAL_BOARD = {
   3,  1,  2,  4,  5,  2,  1,  3,
   0,  0,  0,  0,  0,  0,  0,  0,
  -1, -1, -1, -1, -1, -1, -1, -1,
  -1, -1, -1, -1, -1, -1, -1, -1,
  -1, -1, -1, -1, -1, -1, -1, -1,
  -1, -1, -1, -1, -1, -1, -1, -1,
   6,  6,  6,  6,  6,  6,  6,  6,
   9,  7,  8, 10, 11,  8,  7,  9,
 };
 
 public static int compare(int value, int p, int z, int n) {
  if(value > 0) return p;
  else if(value < 0) return n;
  else return z;
 }
 
 public static <T extends Number> boolean equalOr(T value, T alt, T... alts) {
  boolean result = (value == alt);
  for(T a : alts) {
   result |= (value == a);
  }
  return result;
 }
 
 public static <T extends Number> boolean equalAnd(T value, T alt, T... alts) {
  boolean result = (value == alt);
  for(T a : alts) {
   result &= (value == a);
  }
  return result;
 }
 
}