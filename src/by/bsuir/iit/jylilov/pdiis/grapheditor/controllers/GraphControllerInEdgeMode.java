package by.bsuir.iit.jylilov.pdiis.grapheditor.controllers;

import java.awt.event.MouseEvent;

class GraphControllerInEdgeMode implements ControllerInterface {
	
	GraphController graphController;

	public GraphControllerInEdgeMode(GraphController graphController) {
		this.graphController = graphController;
	}

	@Override
	public void mouseDragged(MouseEvent e) {	
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3)
			graphController.deselectAll();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}