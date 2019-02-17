package men.brakh.graphicseditor.model.figure.impl;

import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;
import men.brakh.graphicseditor.model.figure.AbstractRectFigure;

public class Rectangle extends AbstractRectFigure {
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
}
