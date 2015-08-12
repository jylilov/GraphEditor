package by.jylilov.grapheditor.controllers;

import by.jylilov.grapheditor.models.EdgeModel;
import by.jylilov.grapheditor.views.EdgeView;

class EdgeController extends Controller {
	
	private final GraphController graphController;
	private final ControllerInterface controllerInEdgeMode;
	private final ControllerInterface controllerInVertexMode;
	private final EdgeModel model;
	private final EdgeView view;
	
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
