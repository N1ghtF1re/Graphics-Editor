package men.brakh.graphicseditor.model.canvas.impl;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;

public class JavaFXCanvas extends AbstractCanvas {

    private Canvas canvas;
    private GraphicsContext gc;

    public JavaFXCanvas(Canvas canvas) {
        super();
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();

        setPenColor("#000");
        setBrushColor("#000");
        setBorderSize(4);
    }

    @Override
    public void clear() {
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void drawLine(Point point1, Point point2) {
        gc.strokeLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
    }

    @Override
    public void drawRectangle(Point leftTop, Point rightBottom) {
        gc.fillRect(leftTop.getX(), leftTop.getY(), rightBottom.getX()-leftTop.getX(), rightBottom.getY()-leftTop.getY());
        gc.strokeRect(leftTop.getX(), leftTop.getY(), rightBottom.getX()-leftTop.getX(), rightBottom.getY()-leftTop.getY());
    }

    @Override
    public void drawOval(Point leftTop, Point rightBottom) {
        gc.fillOval(leftTop.getX(), leftTop.getY(), rightBottom.getX()-leftTop.getX(), rightBottom.getY()-leftTop.getY());
        gc.strokeOval(leftTop.getX(), leftTop.getY(), rightBottom.getX()-leftTop.getX(), rightBottom.getY()-leftTop.getY());
    }

    @Override
    public void drawRhombus(Point leftTop, Point rightBottom) {
        int leftX = leftTop.getX();
        int topAndBottomX = leftTop.getX() + (rightBottom.getX() - leftTop.getX()) / 2;
        int rightX = rightBottom.getX();

        int leftAndRightY = leftTop.getY() + (rightBottom.getY() - leftTop.getY()) / 2;
        int topY = leftTop.getY();
        int bottomY = rightBottom.getY();


        double xs[] = new double[]{leftX, topAndBottomX, rightX, topAndBottomX};
        double ys[] = new double[]{leftAndRightY, topY, leftAndRightY, bottomY};

        gc.fillPolygon(xs, ys, xs.length);
        gc.strokePolygon(xs, ys, xs.length);
    }

    @Override
    public String getBrushColor() {
        return gc.getFill().toString();
    }

    @Override
    public void setBrushColor(String brushColor) {
        gc.setFill(Color.web(brushColor));
    }

    @Override
    public String getPenColor() {
        return gc.getStroke().toString();
    }

    @Override
    public void setPenColor(String penColor) {
        gc.setStroke(Color.web(penColor));
    }

    @Override
    public int getBorderSize() {
        return (int) gc.getLineWidth();
    }

    @Override
    public void setBorderSize(int borderSize) {
        gc.setLineWidth(borderSize);
    }

}
