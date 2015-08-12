package by.jylilov.grapheditor.controllers;

import java.awt.event.MouseEvent;

import by.jylilov.grapheditor.views.VertexView;

class GraphControllerInVertexMode implements ControllerInterface {
	
	private final GraphController graphController;

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
		switch(e.getButton()) {
		case MouseEvent.BUTTON1:
			if (e.getClickCount() == 2) { 
				graphController.addVertex(e.getX() - VertexView.SIZE / 2, 
						                  e.getY() - VertexView.SIZE / 2);
			} else {
				graphController.deselectAll();
			}
			break;
		case MouseEvent.BUTTON3:
			graphController.deselectAll();
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
