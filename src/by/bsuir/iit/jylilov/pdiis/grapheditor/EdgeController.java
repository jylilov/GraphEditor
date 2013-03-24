package by.bsuir.iit.jylilov.pdiis.grapheditor;

import java.awt.Color;

import javax.swing.JComponent;

public class EdgeController implements EdgeControllerInterface {
	
	private EdgeModel model;
	private EdgeView view;
	
	public EdgeController(EdgeModel model) {
		this.model = model;
		view = new EdgeView(this, model);
	}
	
	public JComponent getView() {
		return view;
	}

	@Override
	public void setWeight(int w) {
		model.setWeight(w);		
	}

	@Override
	public void setColor(Color color) {
		view.setColor(color);		
	}

}
