package by.bsuir.iit.jylilov.pdiis.grapheditor.views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import by.bsuir.iit.jylilov.pdiis.grapheditor.models.EdgeModel;
import by.bsuir.iit.jylilov.pdiis.grapheditor.models.EdgeModelEvent;
import by.bsuir.iit.jylilov.pdiis.grapheditor.models.VertexModel;

public class EdgeView extends JComponent implements Observer{

	private static final long serialVersionUID = 6159972103656262134L;
	
	private EdgeModel model;
	private Color color = Color.BLACK;
	
	public EdgeView(VertexModel v1, VertexModel v2) {
		setOpaque(false);
		model = new EdgeModel(v1, v2);
		model.addObserver(this);
		
		setBounds();
	}
	
	public EdgeModel getModel() {
		return model;
	}
	
	public void setColor(Color color) {
		this.color = color;
		repaint();
	}
	
	public void setBounds() {
		int x1, y1, x2, y2;
		x1 = Math.min(model.getVertex1().getX(), model.getVertex2().getX());
		y1 = Math.min(model.getVertex1().getY(), model.getVertex2().getY());
		x2 = Math.max(model.getVertex1().getX(), model.getVertex2().getX());
		y2 = Math.max(model.getVertex1().getY(), model.getVertex2().getY());
		setBounds(x1 - VertexView.SIZE, y1 - VertexView.SIZE, x2 - x1 + 2 * VertexView.SIZE, y2 - y1 + 2 * VertexView.SIZE);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		int x1, y1, x2, y2;
		x1 = model.getVertex1().getX() - getX() + VertexView.SIZE / 2;
		y1 = model.getVertex1().getY() - getY() + VertexView.SIZE / 2;
		x2 = model.getVertex2().getX() - getX() + VertexView.SIZE / 2;
		y2 = model.getVertex2().getY() - getY() + VertexView.SIZE / 2;
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(VertexView.BORDER_SIZE));
		g2d.setColor(color);
		g2d.drawLine(x1, y1, x2, y2);
	}
	
	@Override
	public boolean contains(int x, int y) {
		double a, b, c;
		double s, r1, r2, r;
		int x1, x2, y1, y2, x0, y0;
		
		x1 = model.getVertex1().getX() + VertexView.SIZE / 2;
		x2 = model.getVertex2().getX() + VertexView.SIZE / 2;
		y1 = model.getVertex1().getY() + VertexView.SIZE / 2;
		y2 = model.getVertex2().getY() + VertexView.SIZE / 2;
		
		a = y1 - y2; b = x2 - x1; c = x1 * y2 - x2 * y1;
		x0 = x + getX(); y0 = y + getY();
		s = Math.abs((a * x0 + b * y0 + c) / Math.sqrt(a * a + b * b));
		r = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow(y1 - y2, 2));
		r1 = Math.sqrt(Math.pow((x1 - x0), 2) + Math.pow(y1 - y0, 2));
		r2 = Math.sqrt(Math.pow((x2 - x0), 2) + Math.pow(y2 - y0, 2));
		r1 = Math.sqrt(Math.pow(r1, 2) - Math.pow(s, 2));
		r2 = Math.sqrt(Math.pow(r2, 2) - Math.pow(s, 2));

		return s <= 1.25 * VertexView.BORDER_SIZE  && r1 <= r && r2 <= r;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg != null) {
			EdgeModelEvent e = (EdgeModelEvent)arg;
			switch (e) {
			case CHANGED_LOCATION:
				setBounds();
			}
		}
		repaint();
	}

}
