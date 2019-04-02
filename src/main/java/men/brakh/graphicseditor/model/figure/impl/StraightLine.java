package men.brakh.graphicseditor.model.figure.impl;

import men.brakh.graphicseditor.config.GraphicEditorConfig;
import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.PointType;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;
import men.brakh.graphicseditor.model.figure.AbstractLine;
import men.brakh.graphicseditor.model.figure.intf.Movable;
import men.brakh.graphicseditor.model.figure.intf.Resizable;
import men.brakh.graphicseditor.model.figure.intf.Selectable;

public class StraightLine extends AbstractLine implements Resizable, Movable, Selectable {
    private GraphicEditorConfig config = GraphicEditorConfig.getInstance();

    public StraightLine(AbstractCanvas canvas, Point startPoint) {
        super(canvas, startPoint.copy());
        move(new Point(-config.getMinFigureWidth(), -config.getMinFigureHeight()));
        super.addPoint(startPoint);
    }

    @Override
    public void addPoint(Point point) {
        // Для прямой линии все - достаточно
    }

    @Override
    public void draw() {
        if(points.size() < 2) {
            return;
        }

        Point point1 = points.get(0);
        Point point2 = points.get(1);

        canvas.withColorSaving(getBushColor(), getPenColor(), getPenWidth(), () -> {
            canvas.drawLine(point1, point2);
            return null;
        });

    }


    @Override
    public void resize(PointType pointType, Point fromPoint, Point toPoint) {
        super.resize(pointType, fromPoint, toPoint);
    }

    @Override
    public void move(Point deltaPoint) {
        super.move(deltaPoint);
    }

    @Override
    public void select() {
        super.select();
    }

    @Override
    public boolean deserialize(String text) {
        if(text.split(";").length != 7) {
            return false;
        } else {
            return super.deserialize(text);
        }
    }
}
