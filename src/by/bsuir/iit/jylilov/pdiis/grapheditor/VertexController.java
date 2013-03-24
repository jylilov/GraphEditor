package by.bsuir.iit.jylilov.pdiis.grapheditor;

import java.awt.Color;

import javax.swing.JComponent;

public class VertexController implements VertexControllerInterface {
	
	private VertexView view;
	private VertexModel model;
	
	public VertexController(VertexModel model) {
		view = new VertexView(this, model);
		this.model = model;		
	}
	
	public JComponent getView() {
		return view;
	}

	@Override
	public void setLocation(int x, int y) {
		view.setLocation(x, y);
	}

	@Override
	public void setColor(Color color) {
		view.setColor(color);		
	}

	@Override
	public void setBorderColor(Color color) {
		view.setBorderColor(color);
		
	}

	@Override
	public void setIdentifier(String idtf) {
		model.setIdentifier(idtf);		
	}

}
