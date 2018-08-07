package SJCE.xgui.Interfaces;

import SJCE.xgui.JPanel.BoardUI;
import SJCE.xgui.JPanel.ChessClock;
import SJCE.xgui.JList.MoveListUI;

public interface IChessContext {
    
	public BoardUI getBoardUI();
        
	public ChessClock getChessClock();
        
	public MoveListUI getMoveListUI();
}
