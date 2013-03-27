package by.bsuir.iit.jylilov.pdiis.grapheditor.controllers;

import java.awt.event.MouseEvent;

import by.bsuir.iit.jylilov.pdiis.grapheditor.views.EdgeView;

public class EdgeControllerInEdgeMode implements ControllerInterface {
	
	GraphController graphController;

	public EdgeControllerInEdgeMode(GraphController graphController) {
		this.graphController = graphController;
	}

	@Override
	public void mouseDragged(MouseEvent e) {	
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		graphController.mouseMoved(e);
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
		graphController.deselectAll();
		if (e.getButton() == MouseEvent.BUTTON1)
			graphController.select((EdgeView)e.getComponent());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
