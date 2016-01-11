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
					//add second corner
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
//				handleActiveRectangle(arg0)
				break;
			case CIRCLE:
//				handleActiveCircle(arg0);
				break;
			case ELLIPSE:
//				handleActiveEllipse(arg0);
				break;
			case TRIANGLE:
				break;
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
//		System.out.println("instance: Square");
		
		Square square = (Square) Model.instance().getLastShape();
		//if the cursor is moving below the upper left corner
		if(arg0.getY() > square.getUpperLeft().y)
		{
			//if the cursor is moving to the bottom right quad
			if(arg0.getX() > square.getUpperLeft().x)
			{
				double lengthX = arg0.getX() - square.getUpperLeft().x;
				double lengthY = arg0.getY() - square.getUpperLeft().y;
				double newlength = Math.min(lengthX, lengthY);
				
				square.setUpperLeft(square.getUpperLeft());
				square.setSize(newlength);
			}

			//if the cursor is moving to the bottom left quad
			if(arg0.getX() < square.getUpperLeft().x)
			{
				double lengthX = square.getUpperLeft().x - arg0.getX();
				double lengthY = arg0.getY() - square.getUpperLeft().y;
				double newlength = Math.min(lengthX, lengthY);
				
				square.setUpperLeft(new Point2D.Double(square.getUpperLeft().x - newlength, square.getUpperLeft().y));
				square.setSize(newlength);
			}
		}

		//if the cursor is moving above the upper left corner
		if(arg0.getY() < square.getUpperLeft().y)
		{
			//if the cursor is moving to the upper right quad
			if(arg0.getX() > square.getUpperLeft().x)
			{
				double lengthX = arg0.getX() - square.getUpperLeft().x;
				double lengthY = square.getUpperLeft().y - arg0.getY();
				double newlength = Math.min(lengthX, lengthY);
				
				square.setUpperLeft(new Point2D.Double(square.getUpperLeft().x, square.getUpperLeft().y  - newlength));
				square.setSize(newlength);
			}

			//if the cursor is moving to the upper left quad
			if(arg0.getX() < square.getUpperLeft().x)
			{
				double lengthX = square.getUpperLeft().x - arg0.getX();
				double lengthY = square.getUpperLeft().y - arg0.getY();
				double newlength = Math.min(lengthX, lengthY);
				
				square.setUpperLeft(new Point2D.Double(square.getUpperLeft().x - newlength, square.getUpperLeft().y - newlength));
				square.setSize(newlength);
			}
		}
		Model.instance().setLastShape(square);
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
