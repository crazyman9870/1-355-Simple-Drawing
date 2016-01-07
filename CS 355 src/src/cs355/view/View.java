package cs355.view;

import java.awt.Graphics2D;
import java.util.Observable;

import cs355.GUIFunctions;

public class View implements ViewRefresher {

	@Override
	public void update(Observable arg0, Object arg1) {
		GUIFunctions.refresh();
	}

	@Override
	public void refreshView(Graphics2D g2d) {
		//ArrayList<Shape> shapes = 
	}
}