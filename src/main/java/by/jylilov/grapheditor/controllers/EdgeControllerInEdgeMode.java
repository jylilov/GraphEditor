package by.jylilov.grapheditor.controllers;

import java.awt.event.MouseEvent;

class EdgeControllerInEdgeMode implements ControllerInterface {
	
	private final GraphController graphController;
	private final EdgeController controller;

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
				graphController.select(controller.getView());
				break;
			case MouseEvent.BUTTON3:
				graphController.deselectAll();
				break;
			default:
				break;
			}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
