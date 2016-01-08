package cs355.view;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Observable;

import cs355.GUIFunctions;
import cs355.model.drawing.*;
import cs355.model.scene.Instance;

public class View implements ViewRefresher {

	@Override
	public void update(Observable arg0, Object arg1) {
		GUIFunctions.refresh();
	}

	@Override
	public void refreshView(Graphics2D g2d) {
		ArrayList<Shape> shapes = (ArrayList<Shape>) Model.instance().getShapes();
		
		for(int i = 0; i < shapes.size(); i++) {
			Shape currentShape = shapes.get(i);
			
			g2d.setColor(currentShape.getColor());
			g2d.fill(shapeFactory(currentShape)); //Uses the factory to determine the current shape to set the fill
			g2d.draw(shapeFactory(currentShape)); //Uses the factory to determine the current shape to draw the image
		}
	}
	
	//Use a factory to determine what type is being dealt with
	//TODO Needs to add logic for the rest of the shapes
	public java.awt.Shape shapeFactory(Shape currentShape)
	{
		if(currentShape instanceof Line)
		{
			Line line = (Line)currentShape;
			Point2D.Double start = new Point2D.Double(line.getStart().x, line.getStart().y);		
			Point2D.Double end = new Point2D.Double(line.getEnd().x, line.getEnd().y);
			
			return new Line2D.Double(start.x, start.y, end.x, end.y);
		}
		
		return null;
	}
}