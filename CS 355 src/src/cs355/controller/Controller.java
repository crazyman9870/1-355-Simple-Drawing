package cs355.controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Iterator;

import cs355.GUIFunctions;
import cs355.model.drawing.*;

public class Controller implements CS355Controller {

	private boolean shapeSelected = false;
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(shapeSelected) {
			
			if(Model.instance().getLastShape().getShapeType() == Shape.type.TRIANGLE) {
				
				Triangle triangle = (Triangle)Model.instance().getLastShape();
				
				if(triangle.getCornerCount() == 2) {
					triangle.setC(new Point2D.Double(arg0.getX(), arg0.getY()));
					triangle.increaseCornerCount();
					GUIFunctions.refresh();
					shapeSelected = false;
				}
				
				if(triangle.getCornerCount() == 1) {
					triangle.setB(new Point2D.Double(arg0.getX(), arg0.getY()));
					triangle.increaseCornerCount();
				}
			}
			else {
				shapeSelected = false;
			}
		}
		else {
			
			switch(Model.instance().getCurrentMode())
			{
			case LINE:
				Model.instance().addShape(new Line(Model.instance().getColor(), new Point2D.Double(arg0.getX(), arg0.getY()), new Point2D.Double(arg0.getX(), arg0.getY())));
				shapeSelected = true;
				break;
			case SQUARE: 
				Model.instance().addShape(new Square(Model.instance().getColor(), new Point2D.Double(arg0.getX(), arg0.getY()), 0));
				shapeSelected = true;
				break;
			case RECTANGLE: 
				Model.instance().addShape(new Rectangle(Model.instance().getColor(), new Point2D.Double(arg0.getX(), arg0.getY()),0, 0));
				shapeSelected = true;
				break;
			case CIRCLE: 
				Model.instance().addShape(new Circle(Model.instance().getColor(), new Point2D.Double(arg0.getX(), arg0.getY()),0));
				shapeSelected = true;
				break;
			case ELLIPSE: 
				Model.instance().addShape(new Ellipse(Model.instance().getColor(), new Point2D.Double(arg0.getX(), arg0.getY()),0, 0));
				shapeSelected = true;
				break;
			case TRIANGLE: 
				Model.instance().addShape(new Triangle(Model.instance().getColor(), new Point2D.Double(arg0.getX(), arg0.getY()), null, null));
				shapeSelected = true;
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if(shapeSelected) {
			Shape currentShape = Model.instance().getLastShape();
			
			switch(currentShape.getShapeType())
			{
			case LINE:
				handleActiveLine(arg0);
				break;
			case SQUARE:
				handleActiveSquare(arg0);
				break;
			case RECTANGLE:
				handleActiveRectangle(arg0);
				break;
			case CIRCLE:
				handleActiveCircle(arg0);
				break;
			case ELLIPSE:
				handleActiveEllipse(arg0);
				break;
			case TRIANGLE:
				return;
			default:
				break;
			}
			GUIFunctions.refresh();
		}
	}
		
	public void handleActiveLine(MouseEvent arg0)
	{		
		Line line = (Line) Model.instance().getLastShape();
		line.setEnd(new Point2D.Double(arg0.getX(), arg0.getY()));
		
		Model.instance().setLastShape(line);
	}
	
	public void handleActiveSquare(MouseEvent arg0)
	{
		
		Square square = (Square) Model.instance().getLastShape();
		//if the cursor is moving below the upper left corner
		if(arg0.getY() > square.getOrigin().y)
		{
			//if the cursor is moving to the bottom right quad
			if(arg0.getX() > square.getOrigin().x)
			{
				double lengthX = arg0.getX() - square.getOrigin().x;
				double lengthY = arg0.getY() - square.getOrigin().y;
				double newcorner = Math.min(lengthX, lengthY);
				
				square.setUpperLeft(square.getOrigin());
				square.setSize(newcorner);
			}

			//if the cursor is moving to the bottom left quad
			if(arg0.getX() < square.getOrigin().x)
			{
				double lengthX = square.getOrigin().x - arg0.getX();
				double lengthY = arg0.getY() - square.getOrigin().y;
				double newcorner = Math.min(lengthX, lengthY);
				
				square.setUpperLeft(new Point2D.Double(square.getOrigin().x - newcorner, square.getOrigin().y));
				square.setSize(newcorner);
			}
		}

		//if the cursor is moving above the upper left corner
		if(arg0.getY() < square.getOrigin().y)
		{
			//if the cursor is moving to the upper right quad
			if(arg0.getX() > square.getOrigin().x)
			{
				double lengthX = arg0.getX() - square.getOrigin().x;
				double lengthY = square.getOrigin().y - arg0.getY();
				double newcorner = Math.min(lengthX, lengthY);
				
				square.setUpperLeft(new Point2D.Double(square.getOrigin().x, square.getOrigin().y  - newcorner));
				square.setSize(newcorner);
			}

			//if the cursor is moving to the upper left quad
			if(arg0.getX() < square.getOrigin().x)
			{
				double lengthX = square.getOrigin().x - arg0.getX();
				double lengthY = square.getOrigin().y - arg0.getY();
				double newcorner = Math.min(lengthX, lengthY);
				
				square.setUpperLeft(new Point2D.Double(square.getOrigin().x - newcorner, square.getOrigin().y - newcorner));
				square.setSize(newcorner);
			}
		}
		Model.instance().setLastShape(square);
	}
	
	public void handleActiveRectangle(MouseEvent arg0)
	{
		
		Rectangle rectangle = (Rectangle) Model.instance().getLastShape();
		//if the cursor is moving below the upper left corner
		if(arg0.getY() > rectangle.getOrigin().y)
		{
			//if the cursor is moving to the bottom right quad
			if(arg0.getX() > rectangle.getOrigin().x)
			{
				double lengthX = arg0.getX() - rectangle.getOrigin().x;
				double lengthY = arg0.getY() - rectangle.getOrigin().y;
				
				rectangle.setUpperLeft(rectangle.getOrigin());
				rectangle.setHeight(lengthY);
				rectangle.setWidth(lengthX);
			}

			//if the cursor is moving to the bottom left quad
			if(arg0.getX() < rectangle.getOrigin().x)
			{
				double lengthX = rectangle.getOrigin().x - arg0.getX();
				double lengthY = arg0.getY() - rectangle.getOrigin().y;
				
				rectangle.setUpperLeft(new Point2D.Double(rectangle.getOrigin().x - lengthX, rectangle.getOrigin().y));
				rectangle.setHeight(lengthY);
				rectangle.setWidth(lengthX);
			}
		}

		//if the cursor is moving above the upper left corner
		if(arg0.getY() < rectangle.getOrigin().y)
		{
			//if the cursor is moving to the upper right quad
			if(arg0.getX() > rectangle.getOrigin().x)
			{
				double lengthX = arg0.getX() - rectangle.getOrigin().x;
				double lengthY = rectangle.getOrigin().y - arg0.getY();
				
				rectangle.setUpperLeft(new Point2D.Double(rectangle.getOrigin().x, rectangle.getOrigin().y  - lengthY));
				rectangle.setHeight(lengthY);
				rectangle.setWidth(lengthX);
			}

			//if the cursor is moving to the upper left quad
			if(arg0.getX() < rectangle.getOrigin().x)
			{
				double lengthX = rectangle.getOrigin().x - arg0.getX();
				double lengthY = rectangle.getOrigin().y - arg0.getY();
				
				rectangle.setUpperLeft(new Point2D.Double(rectangle.getOrigin().x - lengthX, rectangle.getOrigin().y - lengthY));
				rectangle.setHeight(lengthY);
				rectangle.setWidth(lengthX);
			}
		}
		Model.instance().setLastShape(rectangle);
	}
	
	public void handleActiveCircle(MouseEvent arg0)
	{
		
		Circle circle = (Circle) Model.instance().getLastShape();
		//if the cursor is moving below the upper left corner
		if(arg0.getY() > circle.getOrigin().y)
		{
			//if the cursor is moving to the bottom right quad
			if(arg0.getX() > circle.getOrigin().x)
			{
				double lengthX = arg0.getX() - circle.getOrigin().x;
				double lengthY = arg0.getY() - circle.getOrigin().y;
				double newcorner = Math.min(lengthX, lengthY);
				
				circle.setUpperLeft(circle.getOrigin());
				circle.setRadius(newcorner / 2);
			}

			//if the cursor is moving to the bottom left quad
			if(arg0.getX() < circle.getOrigin().x)
			{
				double lengthX = circle.getOrigin().x - arg0.getX();
				double lengthY = arg0.getY() - circle.getOrigin().y;
				double newcorner = Math.min(lengthX, lengthY);
				
				circle.setUpperLeft(new Point2D.Double(circle.getOrigin().x - newcorner, circle.getOrigin().y));
				circle.setRadius(newcorner / 2);
			}
		}

		//if the cursor is moving above the upper left corner
		if(arg0.getY() < circle.getOrigin().y)
		{
			//if the cursor is moving to the upper right quad
			if(arg0.getX() > circle.getOrigin().x)
			{
				double lengthX = arg0.getX() - circle.getOrigin().x;
				double lengthY = circle.getOrigin().y - arg0.getY();
				double newcorner = Math.min(lengthX, lengthY);
				
				circle.setUpperLeft(new Point2D.Double(circle.getOrigin().x, circle.getOrigin().y  - newcorner));
				circle.setRadius(newcorner / 2);
			}

			//if the cursor is moving to the upper left quad
			if(arg0.getX() < circle.getOrigin().x)
			{
				double lengthX = circle.getOrigin().x - arg0.getX();
				double lengthY = circle.getOrigin().y - arg0.getY();
				double newcorner = Math.min(lengthX, lengthY);
				
				circle.setUpperLeft(new Point2D.Double(circle.getOrigin().x - newcorner, circle.getOrigin().y - newcorner));
				circle.setRadius(newcorner / 2);
			}
		}
		Model.instance().setLastShape(circle);
	}
	
	public void handleActiveEllipse(MouseEvent arg0)
	{
		
		Ellipse ellipse = (Ellipse) Model.instance().getLastShape();
		//if the cursor is moving below the upper left corner
		if(arg0.getY() > ellipse.getOrigin().y)
		{
			//if the cursor is moving to the bottom right quad
			if(arg0.getX() > ellipse.getOrigin().x)
			{
				double lengthX = arg0.getX() - ellipse.getOrigin().x;
				double lengthY = arg0.getY() - ellipse.getOrigin().y;
				
				ellipse.setUpperLeft(ellipse.getOrigin());
				ellipse.setWidth(lengthX);
				ellipse.setHeight(lengthY);
			}

			//if the cursor is moving to the bottom left quad
			if(arg0.getX() < ellipse.getOrigin().x)
			{
				double lengthX = ellipse.getOrigin().x - arg0.getX();
				double lengthY = arg0.getY() - ellipse.getOrigin().y;
				
				ellipse.setUpperLeft(new Point2D.Double(ellipse.getOrigin().x - lengthX, ellipse.getOrigin().y));
				ellipse.setWidth(lengthX);
				ellipse.setHeight(lengthY);
			}
		}

		//if the cursor is moving above the upper left corner
		if(arg0.getY() < ellipse.getOrigin().y)
		{
			//if the cursor is moving to the upper right quad
			if(arg0.getX() > ellipse.getOrigin().x)
			{
				double lengthX = arg0.getX() - ellipse.getOrigin().x;
				double lengthY = ellipse.getOrigin().y - arg0.getY();
				
				ellipse.setUpperLeft(new Point2D.Double(ellipse.getOrigin().x, ellipse.getOrigin().y  - lengthY));
				ellipse.setWidth(lengthX);
				ellipse.setHeight(lengthY);
			}

			//if the cursor is moving to the upper left quad
			if(arg0.getX() < ellipse.getOrigin().x)
			{
				double lengthX = ellipse.getOrigin().x - arg0.getX();
				double lengthY = ellipse.getOrigin().y - arg0.getY();
				
				ellipse.setUpperLeft(new Point2D.Double(ellipse.getOrigin().x - lengthX, ellipse.getOrigin().y - lengthY));
				ellipse.setWidth(lengthX);
				ellipse.setHeight(lengthY);
			}
		}
		Model.instance().setLastShape(ellipse);
	}

	@Override
	public void colorButtonHit(Color c) {
		Model.instance().setColor(c);
		GUIFunctions.changeSelectedColor(c);
	}

	@Override
	public void lineButtonHit() {
		Model.instance().setCurrentMode(Shape.type.LINE);
	}

	@Override
	public void squareButtonHit() {
		Model.instance().setCurrentMode(Shape.type.SQUARE);
	}

	@Override
	public void rectangleButtonHit() {
		Model.instance().setCurrentMode(Shape.type.RECTANGLE);
	}

	@Override
	public void circleButtonHit() {
		Model.instance().setCurrentMode(Shape.type.CIRCLE);
	}

	@Override
	public void ellipseButtonHit() {
		Model.instance().setCurrentMode(Shape.type.ELLIPSE);
	}

	@Override
	public void triangleButtonHit() {
		Model.instance().setCurrentMode(Shape.type.TRIANGLE);
	}

	@Override
	public void selectButtonHit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void zoomInButtonHit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void zoomOutButtonHit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hScrollbarChanged(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void vScrollbarChanged(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void openScene(File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toggle3DModelDisplay() {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(Iterator<Integer> iterator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void openImage(File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveImage(File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toggleBackgroundDisplay() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveDrawing(File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void openDrawing(File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doDeleteShape() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doEdgeDetection() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSharpen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doMedianBlur() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doUniformBlur() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doGrayscale() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doChangeContrast(int contrastAmountNum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doChangeBrightness(int brightnessAmountNum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doMoveForward() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doMoveBackward() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSendToFront() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSendtoBack() {
		// TODO Auto-generated method stub

	}

}
