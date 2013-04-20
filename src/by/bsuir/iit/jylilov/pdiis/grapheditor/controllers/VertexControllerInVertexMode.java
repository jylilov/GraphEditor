package by.bsuir.iit.jylilov.pdiis.grapheditor.controllers;

import java.awt.event.MouseEvent;

import by.bsuir.iit.jylilov.pdiis.grapheditor.views.VertexView;

class VertexControllerInVertexMode implements ControllerInterface {
	
	private final GraphController graphController;
	private final VertexController controller;
	
	private int dx;
	private int dy;

	public VertexControllerInVertexMode(VertexController controller, GraphController graphController) {
		this.controller = controller;
		this.graphController = graphController;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		graphController.moveSelected(e.getX() - dx, e.getY() - dy);
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
		VertexView vertex = controller.getView();
		switch(e.getButton()) {
		case MouseEvent.BUTTON1:
			dx = e.getX();
			dy = e.getY();
			if (e.isControlDown()) 
				graphController.addToSelection(vertex);
			else
				graphController.select(vertex);
			break;
		case MouseEvent.BUTTON3:
			if (e.isControlDown())
				graphController.deselect(vertex);
			else
				graphController.deselectAll();
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
