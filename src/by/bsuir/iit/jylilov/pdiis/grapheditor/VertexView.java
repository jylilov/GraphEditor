package by.bsuir.iit.jylilov.pdiis.grapheditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

public class VertexView extends JComponent implements Observer {
	
	private int SIZE = 75;
	private int BORDER_SIZE = SIZE / 6;
	
	private VertexModel model;
	private VertexControllerInterface controller;
	private Color color = Color.WHITE;
	private Color borderColor = Color.BLACK;
	
	public VertexView(VertexController controller, VertexModel model) {
		this.controller = controller;
		this.model = model;
		model.addObserver(this);
		setOpaque(false);
		setBounds(0, 0, SIZE, SIZE);
		setLocation(model.getX(), model.getY());
	}
	
	public void setColor(Color color) {
		this.color = color;		
	}

	public void setBorderColor(Color color) {
		borderColor = color;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(borderColor);
        g2d.fillOval(0, 0, SIZE, SIZE);
        g2d.setColor(color);
        g2d.fillOval(BORDER_SIZE, BORDER_SIZE, SIZE - 2 * BORDER_SIZE, SIZE - 2 * BORDER_SIZE);
	}
	
	@Override
	public boolean contains(int x, int y) {
		return contains(new Point(x, y));
	}
	
	@Override
	public boolean contains(Point p) {
		return p.distance(new Point(SIZE / 2, SIZE / 2)) < SIZE / 2;
	}

	@Override
	public void update(Observable o, Object arg) {
		VertexModelEvent e = (VertexModelEvent)arg;
		
		switch (e) {
		case CHANGED_IDENTIFIER:
			repaint();
			break;
		case CHANGED_LOCATION:
			setLocation(model.getX(), model.getY());
			break;
		}
	}
	
}
