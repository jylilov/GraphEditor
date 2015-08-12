package by.jylilov.grapheditor.controllers;

import by.jylilov.grapheditor.models.VertexModel;
import by.jylilov.grapheditor.views.VertexView;

class VertexController extends Controller {
	
	private final ControllerInterface controllerInVertexMode;
	private final ControllerInterface controllerInEdgeMode;
	private final GraphController graphController;
	private final VertexModel model;
	private final VertexView view;

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
	
	@Override
	protected ControllerInterface getCurrentController() {
		switch(graphController.getMode()) {
		case EDGE_MODE:
			return controllerInEdgeMode;
		case VERTEX_MODE:
			return controllerInVertexMode;
		default:
			return null;
		}
	}
	
}
