package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class MyScrollBarUI extends BasicScrollBarUI
{
	private Color color;
	
	public ScrollBarUI setColor(Color color)
	{
		this.color = color;
		return this;
	}
	
	
	protected JButton createZeroButton() {
	    JButton button = new JButton("zero button");
	    Dimension zeroDim = new Dimension(0,0);
	    button.setPreferredSize(zeroDim);
	    button.setMinimumSize(zeroDim);
	    button.setMaximumSize(zeroDim);
	    return button;
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
	    return createZeroButton();
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
	    return createZeroButton();
	}
	 
	 protected void setThumbBounds(int x, int y,int width,int height)
	  {
	      super.setThumbBounds(x, y, 30, 140);
	  }
	  protected Rectangle getThumbBounds()
	  {
	      return new Rectangle(super.getThumbBounds().x,super.getThumbBounds().y,30,14);
	  }  
	  protected Dimension getMinimumThumbSize()
	  {
	      return new Dimension(14,14);
	  }
	  protected Dimension getMaximumThumbSize()
	  {
	     return new Dimension(14,14);
	  }
	 
	    @Override
	    protected void paintDecreaseHighlight(Graphics g)
	    {
	    Insets insets = scrollbar.getInsets();
	    Rectangle thumbR = getThumbBounds();
	    g.setColor(new Color(137,144,144));

	    if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
	        int x = insets.left+decrButton.getWidth()/2-2;
	        int y = decrButton.getY() + decrButton.getHeight();
	        int w = 4;
	        int h = thumbR.y - y;
	        g.fillRect(x, y, w, h);
	    } 
	    else    {
	        int x, w;
	        if (scrollbar.getComponentOrientation().isLeftToRight()) {
	        x = decrButton.getX() + decrButton.getWidth();
	        w = thumbR.x - x;
	        } else {
	        x = thumbR.x + thumbR.width;
	        w = decrButton.getX() - x;
	        }
	        int y = insets.top;
	        int h = scrollbar.getHeight() - (insets.top + insets.bottom);
	        g.fillRect(x, y, w, h);
	    }
	    }

	    @Override
	    protected void paintIncreaseHighlight(Graphics g) {
	            Insets insets = scrollbar.getInsets();
	            Rectangle thumbR = getThumbBounds();
	            g.setColor(new Color(202,207,203));

	            if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
	                int x = insets.left+decrButton.getWidth()/2-2;
	                int y = thumbR.y;
	                int w = 4;
	                int h = incrButton.getY() - y;
	                g.fillRect(x, y, w, h);
	            }
	        }


	    @Override
	    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds)  
	    {
	        g.setColor(color);
	        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
	        paintDecreaseHighlight(g);
	        paintIncreaseHighlight(g);

	    }

	  @Override
	  protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds)
	  {
	      if(thumbBounds.isEmpty() || !scrollbar.isEnabled())     {
	          return;
	      }

	      int w = 16;
	      int h = 16;

	      Graphics2D g2 = (Graphics2D) g;
	      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	          RenderingHints.VALUE_ANTIALIAS_ON);

	      g.translate(thumbBounds.x-1, thumbBounds.y);

	      GradientPaint gr = new GradientPaint(2, 0, Color.BLACK, 18, 16, new Color(96,99,98));
	      g2.setPaint(gr);
	      g2.fill(new Ellipse2D.Double(2, 0, w-1, h-1));

	      g2.setPaint(new Color(203,207,203));
	      g2.fill(new Ellipse2D.Double(6, 4, 7, 7));

	      g.translate(-thumbBounds.x, -thumbBounds.y);
	  }
}
