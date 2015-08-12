package by.jylilov.grapheditor.views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import by.jylilov.grapheditor.models.VertexModel;
import by.jylilov.grapheditor.models.VertexModelEvent;

public class VertexView extends JComponent implements Observer {
	
	public static final int SIZE = 30;
	public static final int BORDER_SIZE = SIZE / 5;
	public static final Color DEFAULT_COLOR = Color.BLACK;
	
	private static final long serialVersionUID = 1292220919565527946L;
	
	private final Inscription inscription;
	private final int inscriptionX = SIZE - BORDER_SIZE;
	private final int inscriptionY = inscriptionX;
	private final VertexModel model;
	private final Color color = Color.WHITE;
	
	private Color borderColor = DEFAULT_COLOR;
	
	public VertexView(VertexModel model) {
		this.model = model;
		inscription = new Inscription(model.getIdentifier(), this);
		model.addObserver(this);	
		setOpaque(false);
		setBounds();
		setLocation(model.getLocation());
	}
	
	public VertexView(int x, int y) {
		this(new VertexModel(x, y));
	}
	
	public void setColor(Color color) {
		this.borderColor = color;
		repaint();
	}
	
	public VertexModel getModel() {
		return model;
	}
	
	private void setBounds() {
		Rectangle r1 = new Rectangle(getX(), getY(), SIZE, SIZE);
		Rectangle r2 = inscription.getRectangle();
		r2.setLocation(inscriptionX + getX(), inscriptionY + getY());
		setBounds(r1.union(r2));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(borderColor);
        g2d.fillOval(0, 0, SIZE, SIZE);
        g2d.setColor(color);
        g2d.fillOval(BORDER_SIZE, BORDER_SIZE, SIZE - 2 * BORDER_SIZE, SIZE - 2 * BORDER_SIZE);
        
        inscription.paint(g2d, inscriptionX, inscriptionY);
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
		if(arg instanceof VertexModelEvent) {
			VertexModelEvent e = (VertexModelEvent)arg;
			switch (e) {
			case CHANGED_IDENTIFIER:
				inscription.setText(model.getIdentifier());
				setBounds();
				repaint();
				break;
			case CHANGED_LOCATION:
				setLocation(model.getX(), model.getY());
				break;
			}
		}
	}
	
}
