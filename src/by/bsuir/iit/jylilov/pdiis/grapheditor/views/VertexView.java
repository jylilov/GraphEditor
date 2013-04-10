package by.bsuir.iit.jylilov.pdiis.grapheditor.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import by.bsuir.iit.jylilov.pdiis.grapheditor.models.VertexModel;
import by.bsuir.iit.jylilov.pdiis.grapheditor.models.VertexModelEvent;

public class VertexView extends JComponent implements Observer {
	
	private static final long serialVersionUID = 1292220919565527946L;
	public static final int SIZE = 30;
	public static final int BORDER_SIZE = SIZE / 5;
	
	private VertexModel model;
	private Color color = Color.WHITE;
	private Color borderColor = Color.BLACK;
	private static Font font = new Font("VertexFont", Font.PLAIN, SIZE / 2);
	
	public VertexView(VertexModel model) {
		this.model = model;
		model.addObserver(this);
	
		setOpaque(false);
		setBounds();
		setLocation(model.getLocation());
	}
	
	public VertexView(int x, int y) {
		this(new VertexModel(x, y));
	}
	
	public VertexModel getVertexModel() {
		return model;
	}
	
	public void setColor(Color color) {
		this.color = color;
		repaint();
	}

	public void setBorderColor(Color color) {
		borderColor = color;
		repaint();
	}
	
	public VertexModel getModel() {
		return model;
	}
	
	public void setBounds() {
		FontMetrics metrics = getFontMetrics(font);
		int adv = metrics.stringWidth(model.getIdentifier());
		int desent = metrics.getDescent();
		Rectangle size = new Rectangle(getX(), getY(), adv + SIZE + 2, SIZE + desent + 2);
		setBounds(size);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(borderColor);
        g2d.fillOval(0, 0, SIZE, SIZE);
        g2d.setColor(color);
        g2d.fillOval(BORDER_SIZE, BORDER_SIZE, SIZE - 2 * BORDER_SIZE, SIZE - 2 * BORDER_SIZE);
          
        g2d.setFont(font);
        
        if (!model.getIdentifier().equals("")) {
	        FontMetrics metrics = getFontMetrics(font);
			int adv = metrics.stringWidth(model.getIdentifier());
			int hgt = metrics.getHeight();
			int asc = metrics.getAscent();
			g2d.setColor(Color.BLACK);
			g2d.drawRoundRect(SIZE, SIZE - asc, adv, hgt, BORDER_SIZE, BORDER_SIZE);
			g2d.setColor(new Color(255, 255, 255, 200));
	        g2d.fillRoundRect(SIZE, SIZE - asc, adv, hgt, BORDER_SIZE, BORDER_SIZE);
        }
        
        
        g2d.setColor(Color.BLACK);
        g2d.drawString(model.getIdentifier(), SIZE, SIZE);
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
		if(!(arg instanceof VertexModelEvent)){
			return;
		}
		VertexModelEvent e = (VertexModelEvent)arg;
		
		switch (e) {
		case CHANGED_IDENTIFIER:
			setBounds();
			repaint();
			break;
		case CHANGED_LOCATION:
			setLocation(model.getX(), model.getY());
			break;
		}
	}
	
}
