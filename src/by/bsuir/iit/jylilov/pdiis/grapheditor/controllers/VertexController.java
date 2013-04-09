package by.bsuir.iit.jylilov.pdiis.grapheditor.controllers;

import java.awt.event.MouseEvent;

import by.bsuir.iit.jylilov.pdiis.grapheditor.models.VertexModel;
import by.bsuir.iit.jylilov.pdiis.grapheditor.views.VertexView;

class VertexController implements ControllerInterface{
	
	private ControllerInterface controllerInVertexMode;
	private ControllerInterface controllerInEdgeMode;
	private GraphController graphController;
	
	private VertexModel model;
	private VertexView view;

	public VertexController(VertexModel model, GraphController graphController) {
		this.model = model;
		this.graphController = graphController;
		
		view = new VertexView(model);
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
		
		controllerInVertexMode = new VertexControllerInVertexMode(this, graphController);
		controllerInEdgeMode = new VertexControllerInEdgeMode(this, graphController);
	}
	
	public VertexModel getModel() {
		return model;
	}
	
	public VertexView getView() {
		return view;
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
