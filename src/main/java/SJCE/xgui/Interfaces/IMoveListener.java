package SJCE.xgui.Interfaces;

import SJCE.xgui.EventObject.MoveEvent;
import java.util.EventListener;

public interface IMoveListener extends EventListener {
    
    public void movePerformed(MoveEvent e);
 
}
