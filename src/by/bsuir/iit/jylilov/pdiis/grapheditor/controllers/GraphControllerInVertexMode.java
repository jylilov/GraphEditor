package by.bsuir.iit.jylilov.pdiis.grapheditor.controllers;

import java.awt.event.MouseEvent;

import by.bsuir.iit.jylilov.pdiis.grapheditor.views.VertexView;

public class GraphControllerInVertexMode implements ControllerInterface {
	
	GraphController graphController;

	public GraphControllerInVertexMode(GraphController graphController) {
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
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
			graphController.addVertex(e.getX() - VertexView.SIZE / 2, e.getY() - VertexView.SIZE / 2);
		else
			graphController.deselectAll();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
