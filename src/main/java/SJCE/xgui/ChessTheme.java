package SJCE.xgui;
import static SJCE.XChessFrame.aktion;
import SJCE.xgui.JPanel.BoardUI;
import java.awt.Dimension;
import java.awt.Image;
import java.io.*;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ChessTheme {

 private String themePathFig;
 private String themePathFon; 
 private static ChessTheme chessTheme = new ChessTheme();
 private HashMap<String, String> themeMap = new HashMap<String, String>();
 private Image[] squareImages = new Image[2];
 private Image[] pieceImages = new Image[PiecesUI.PIECE_TOTAL];
 private Image[] highlightImages = new Image[2];

 public ChessTheme() 
    {    
        themePathFig="/SJCE/img/themes/fig/"+aktion.BoardThemeFig+"/";
        themePathFon="/SJCE/img/themes/fon/"+aktion.BoardThemeFon+"/";        
        loadTheme();
    }

 public void loadTheme() {
        themePathFig="/SJCE/img/themes/fig/"+aktion.BoardThemeFig+"/";
        themePathFon="/SJCE/img/themes/fon/"+aktion.BoardThemeFon+"/";
  squareImages[BoardUI.SQUARE_WHITE] = 
          new ImageIcon(getClass().getResource(themePathFon+"ws.png")).getImage();
  squareImages[BoardUI.SQUARE_BLACK] = 
          new ImageIcon(getClass().getResource(themePathFon+"bs.png")).getImage();
  //////////////////////////
  pieceImages[PiecesUI.WHITE_PAWN] = 
          new ImageIcon(getClass().getResource(themePathFig+"wp.png")).getImage();
  pieceImages[PiecesUI.WHITE_KNIGHT] = 
          new ImageIcon(getClass().getResource(themePathFig+"wk.png")).getImage();
  pieceImages[PiecesUI.WHITE_BISHOP] = 
          new ImageIcon(getClass().getResource(themePathFig+"wo.png")).getImage();
  pieceImages[PiecesUI.WHITE_ROOK] = 
          new ImageIcon(getClass().getResource(themePathFig+"wl.png")).getImage();
  pieceImages[PiecesUI.WHITE_QUEEN] = 
          new ImageIcon(getClass().getResource(themePathFig+"wf.png")).getImage();
  pieceImages[PiecesUI.WHITE_KING] = 
          new ImageIcon(getClass().getResource(themePathFig+"wg.png")).getImage();
  pieceImages[PiecesUI.BLACK_PAWN] = 
          new ImageIcon(getClass().getResource(themePathFig+"bp.png")).getImage();
  pieceImages[PiecesUI.BLACK_KNIGHT] = 
          new ImageIcon(getClass().getResource(themePathFig+"bk.png")).getImage();
  pieceImages[PiecesUI.BLACK_BISHOP] = 
          new ImageIcon(getClass().getResource(themePathFig+"bo.png")).getImage();
  pieceImages[PiecesUI.BLACK_ROOK] = 
          new ImageIcon(getClass().getResource(themePathFig+"bl.png")).getImage();
  pieceImages[PiecesUI.BLACK_QUEEN] = 
          new ImageIcon(getClass().getResource(themePathFig+"bf.png")).getImage();
  pieceImages[PiecesUI.BLACK_KING] = 
          new ImageIcon(getClass().getResource(themePathFig+"bg.png")).getImage();
  highlightImages[BoardUI.HIGHLIGHT_SELECT] = 
    new ImageIcon(getClass().getResource("/SJCE/img/themes/highlight-select.png")).getImage();
  highlightImages[BoardUI.HIGHLIGHT_MOVE] = 
    new ImageIcon(getClass().getResource("/SJCE/img/themes/highlight-move.png")).getImage();
  themeMap.clear();
 }
 public static ChessTheme getChessTheme()   {  return chessTheme;       }
 public Image getSquareImage(int id)        {  return squareImages[id]; }
 public Image getPieceImage(int id)         {  return pieceImages[id];  }
 public Image getHighlight(int id)          {  return highlightImages[id]; }
 public void adjustTheme(Dimension dimension) {
  loadTheme();
  int swidth = (int) (dimension.width / (double) BoardUI.FILE_RANK);
  int sheight = (int) (dimension.height / (double) BoardUI.FILE_RANK);
  squareImages[BoardUI.SQUARE_WHITE]
               = scaleImage(squareImages[BoardUI.SQUARE_WHITE], swidth, sheight);
  squareImages[BoardUI.SQUARE_BLACK]
               = scaleImage(squareImages[BoardUI.SQUARE_BLACK], swidth, sheight);
  for(int i = 0; i < PiecesUI.PIECE_TOTAL; i++) {
   pieceImages[i] = scaleImage(pieceImages[i], swidth, sheight);
  }
  highlightImages[BoardUI.HIGHLIGHT_SELECT]
          = scaleImage(highlightImages[BoardUI.HIGHLIGHT_SELECT], swidth, sheight);
  highlightImages[BoardUI.HIGHLIGHT_MOVE]
          = scaleImage(highlightImages[BoardUI.HIGHLIGHT_MOVE], swidth, sheight);
 }
 private Image scaleImage(Image image, int swidth, int sheight) {
    return image.getScaledInstance(swidth, sheight, Image.SCALE_SMOOTH);
 }
}
