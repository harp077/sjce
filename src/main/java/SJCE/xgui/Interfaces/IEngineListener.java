package SJCE.xgui.Interfaces;

import SJCE.xgui.EventObject.EngineEvent;
import java.util.EventListener;

public interface IEngineListener extends EventListener {
    
	public void movePrinted(EngineEvent e);
        
	public void illegalPrinted(EngineEvent e);
        
	public void dataPrinted(EngineEvent e);
        
	public void dataEntered(EngineEvent e);
}
