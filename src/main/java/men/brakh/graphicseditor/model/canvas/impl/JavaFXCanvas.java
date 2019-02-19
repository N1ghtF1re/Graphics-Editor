package men.brakh.graphicseditor.model.canvas.impl;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.PointType;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;
import men.brakh.graphicseditor.model.figure.Figure;

import java.util.Optional;

public class JavaFXCanvas extends AbstractCanvas {

    private Canvas canvas;
    private GraphicsContext gc;

    final double noBorder = 0.0001;

    public JavaFXCanvas(Canvas canvas) {
        super();
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();

        setPenColor("#000");
        setBrushColor("#000");
        setPenWidth(4);
    }

    private void changePointForStroke(Point lt, Point rb) {
        int penWidth = getPenWidth();

        lt.add(new Point(penWidth/2, penWidth/2));
        rb.add(new Point(-penWidth/2, -penWidth/2));
    }

    @Override
    public void clear() {
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void changeCursor(Point point) {
        Optional<Figure> figureOptional = getFigureAtPoint(point);
        if(figureOptional.isPresent() && isSelected(figureOptional.get())) {
            PointType type = figureOptional.get().checkPoint(point);
            switch (type) {
                case POINT_INSIZE:
                    canvas.setCursor(Cursor.MOVE);
                    break;
                case LT_VERTEX:
                    canvas.setCursor(Cursor.NW_RESIZE);
                    break;
                case RT_VERTEX:
                    canvas.setCursor(Cursor.NE_RESIZE);
                    break;
                case LB_VERTEX:
                    canvas.setCursor(Cursor.SW_RESIZE);
                    break;
                case RB_VERTEX:
                    canvas.setCursor(Cursor.SE_RESIZE);
                    break;
                case LEFT_SIDE:
                    canvas.setCursor(Cursor.W_RESIZE);
                    break;
                case RIGHT_SIDE:
                    canvas.setCursor(Cursor.E_RESIZE);
                    break;
                case TOP_SIDE:
                    canvas.setCursor(Cursor.N_RESIZE);
                    break;
                case BOTTOM_SIDE:
                    canvas.setCursor(Cursor.S_RESIZE);
                    break;
                case POINT_NODE:
                    canvas.setCursor(Cursor.CLOSED_HAND);
                    break;
            }
        } else {
            canvas.setCursor(Cursor.DEFAULT);
        }
    }

    @Override
    public void drawLine(Point point1, Point point2) {
        gc.strokeLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
    }

    @Override
    public void drawRectangle(Point leftTop, Point rightBottom) {
        gc.fillRect(leftTop.getX(), leftTop.getY(), rightBottom.getX()-leftTop.getX(), rightBottom.getY()-leftTop.getY());

        changePointForStroke(leftTop, rightBottom);
        gc.strokeRect(leftTop.getX(), leftTop.getY(), rightBottom.getX()-leftTop.getX(), rightBottom.getY()-leftTop.getY());
    }

    @Override
    public void drawStrokeRectangle(Point leftTop, Point rightBottom) {
        gc.strokeRect(leftTop.getX(), leftTop.getY(), rightBottom.getX()-leftTop.getX(), rightBottom.getY()-leftTop.getY());
    }

    @Override
    public void drawOval(Point leftTop, Point rightBottom) {
        gc.fillOval(leftTop.getX(), leftTop.getY(), rightBottom.getX()-leftTop.getX(), rightBottom.getY()-leftTop.getY());
        changePointForStroke(leftTop, rightBottom);
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

        changePointForStroke(leftTop, rightBottom);
        drawStrokeRhombus(leftTop, rightBottom);
    }

    @Override
    public void drawStrokeRhombus(Point leftTop, Point rightBottom) {
        int leftX = leftTop.getX();
        int topAndBottomX = leftTop.getX() + (rightBottom.getX() - leftTop.getX()) / 2;
        int rightX = rightBottom.getX();

        int leftAndRightY = leftTop.getY() + (rightBottom.getY() - leftTop.getY()) / 2;
        int topY = leftTop.getY();
        int bottomY = rightBottom.getY();


        double xs[] = new double[]{leftX, topAndBottomX, rightX, topAndBottomX};
        double ys[] = new double[]{leftAndRightY, topY, leftAndRightY, bottomY};

        gc.strokePolygon(xs, ys, xs.length);
    }

    @Override
    public String getBrushColor() {
        return gc.getFill().toString();
    }

    @Override
    public void setBrushColor(String brushColor) {
        if(brushColor != null)
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
    public int getPenWidth() {
        return (int) gc.getLineWidth();
    }

    @Override
    public void setPenWidth(int borderSize) {
        if(borderSize != 0) {
            gc.setLineWidth(borderSize);
        } else {
            gc.setLineWidth(noBorder); // Ноль - низя, поэтому отправляем маленькое значенеи
        }
    }

}
