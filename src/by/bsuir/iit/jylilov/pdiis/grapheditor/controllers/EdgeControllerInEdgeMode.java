package by.bsuir.iit.jylilov.pdiis.grapheditor.controllers;

import java.awt.event.MouseEvent;

class EdgeControllerInEdgeMode implements ControllerInterface {
	
	GraphController graphController;
	EdgeController controller;

	public EdgeControllerInEdgeMode(EdgeController controller, GraphController graphController) {
		this.controller = controller;
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
				graphController.deselectAll();
				graphController.select(controller.getView());
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
