package men.brakh.graphicseditor.model.figure.impl;

import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.PointType;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;
import men.brakh.graphicseditor.model.figure.AbstractRectFigure;
import men.brakh.graphicseditor.model.figure.intf.Movable;
import men.brakh.graphicseditor.model.figure.intf.Resizable;
import men.brakh.graphicseditor.model.figure.intf.Selectable;

public class Rectangle extends AbstractRectFigure implements Resizable, Movable, Selectable {
    /**
     * Конструктор прямоугольника
     *
     * @param canvas     Объект канваса
     * @param startPoint Начальные координаты
     */
    public Rectangle(AbstractCanvas canvas, Point startPoint) {
        super(canvas, startPoint);
    }

    @Override
    public void draw() {
        canvas.withColorSaving(getBushColor(), getPenColor(), getPenWidth(), () -> {
            canvas.drawRectangle(getLeftTopPoint(), getRightBottomPoint());
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
}
