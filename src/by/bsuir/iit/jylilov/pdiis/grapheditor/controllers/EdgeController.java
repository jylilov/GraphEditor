package by.bsuir.iit.jylilov.pdiis.grapheditor.controllers;

import java.awt.event.MouseEvent;

import by.bsuir.iit.jylilov.pdiis.grapheditor.models.EdgeModel;
import by.bsuir.iit.jylilov.pdiis.grapheditor.views.EdgeView;

public class EdgeController implements ControllerInterface {
	
	private GraphController graphController;
	private ControllerInterface controllerInEdgeMode;
	private ControllerInterface controllerInVertexMode;
	
	private EdgeModel model;
	private EdgeView view;
	
	public EdgeController(EdgeModel model, GraphController graphController) {
		this.model = model;
		view = new EdgeView(model);
		
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
		
		this.graphController = graphController;
		controllerInEdgeMode = new EdgeControllerInEdgeMode(this, graphController);
		controllerInVertexMode = new EdgeControllerInVertexMode(this, graphController);
	}
	
	public EdgeView getView() {
		return view;
	}
	
	public EdgeModel getModel() {
		return model;
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

	@Override
	public void mouseDragged(MouseEvent e) {
		getCurrentController().mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		getCurrentController().mouseMoved(e);
	}

}
