package by.bsuir.iit.jylilov.pdiis.grapheditor.controllers;

import java.awt.event.MouseEvent;

import by.bsuir.iit.jylilov.pdiis.grapheditor.views.VertexView;

class VertexControllerInVertexMode implements ControllerInterface {
	
	private int dx, dy;
	private GraphController graphController;

	public VertexControllerInVertexMode(GraphController graphController) {
		this.graphController = graphController;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (graphController.isSelected((VertexView)e.getComponent()))
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
		VertexView vertex = (VertexView)e.getComponent();
		if (e.getButton() == MouseEvent.BUTTON1) {
			dx = e.getX();
			dy = e.getY();
			if (!e.isControlDown() && !graphController.isSelected(vertex)) graphController.deselectAll();
			graphController.select(vertex);
		} 
		else if (e.getButton() == MouseEvent.BUTTON3) {
			if (e.isControlDown())
				graphController.deselect(vertex);
			else
				graphController.deselectAll();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
