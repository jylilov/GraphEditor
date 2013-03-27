package by.bsuir.iit.jylilov.pdiis.grapheditor.controllers;

import java.awt.event.MouseEvent;

public class VertexController implements ControllerInterface{
	
	private ControllerInterface controllerInVertexMode;
	private ControllerInterface controllerInEdgeMode;
	private GraphController graphController;

	public VertexController(GraphController graphController) {
		this.graphController = graphController;
		controllerInVertexMode = new VertexControllerInVertexMode(graphController);
		controllerInEdgeMode = new VertexControllerInEdgeMode(graphController);
	}
	
	private ControllerInterface getCurrentController() {
		switch(graphController.getMode()) {
		case EDGE_MODE:
			return controllerInEdgeMode;
		case VERTEX_MODE:
			return controllerInVertexMode;
		default:
			return null;
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		getCurrentController().mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		getCurrentController().mouseMoved(e);		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		getCurrentController().mouseClicked(e);		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		getCurrentController().mouseEntered(e);		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		getCurrentController().mouseExited(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		getCurrentController().mousePressed(e);	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		getCurrentController().mouseReleased(e);		
	}	
	
}
