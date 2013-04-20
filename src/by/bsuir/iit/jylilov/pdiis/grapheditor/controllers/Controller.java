package by.bsuir.iit.jylilov.pdiis.grapheditor.controllers;

import java.awt.event.MouseEvent;

public abstract class Controller implements ControllerInterface {
	
	protected abstract ControllerInterface getCurrentController();

	@Override
	public void mouseDragged(MouseEvent e) {
		ControllerInterface c = getCurrentController(); 
		if (c != null) getCurrentController().mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		ControllerInterface c = getCurrentController(); 
		if (c != null) c.mouseMoved(e);		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		ControllerInterface c = getCurrentController(); 
		if (c != null) c.mouseClicked(e);		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		ControllerInterface c = getCurrentController(); 
		if (c != null) c.mouseEntered(e);		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		ControllerInterface c = getCurrentController(); 
		if (c != null) c.mouseExited(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		ControllerInterface c = getCurrentController(); 
		if (c != null) c.mousePressed(e);	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		ControllerInterface c = getCurrentController(); 
		if (c != null) c.mouseReleased(e);		
	}	
	
}
