package by.bsuir.iit.jylilov.pdiis.grapheditor.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

class Inscription {

	private static final int SIZE = 12;
	private static final Font FONT = new Font("", Font.PLAIN, SIZE);
	
	private FontMetrics metrics;
	
	private String text;
	
	public Inscription(String text, JComponent component) {
		setText(text);
		metrics = component.getFontMetrics(FONT);
	}
	
	public void paint(Graphics2D g, int x, int y) {
		if (!text.equals("")) {
			int width = metrics.stringWidth(text); 
			int height = metrics.getHeight();
			int ascent = metrics.getAscent();
			Color color = g.getColor();
			
			g.setColor(Color.BLACK);
			g.drawRoundRect(x - 1, y, width + 2, height, SIZE / 3, SIZE / 3);
			
			g.setColor(new Color(255, 255, 255, 200));
	        g.fillRoundRect(x - 1, y, width + 2, height, SIZE / 3, SIZE / 3);
	        
	        g.setColor(Color.BLACK);
	        g.setFont(FONT);
	        g.drawString(text, x, y + ascent);
	        
	        g.setColor(color);
		}
	}
		
	public Rectangle getRectangle() {
		if (text == "") 
			return new Rectangle(0, 0, 0, 0);
		else
			return new Rectangle(0, 
								 0,
								 metrics.stringWidth(text) + 2,
								 metrics.getHeight() + 2) ;
	}

	public void setText(String text) {
		this.text = text;
	}
}