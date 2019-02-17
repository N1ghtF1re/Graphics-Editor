package men.brakh.graphicseditor.model.figure.impl;

import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;
import men.brakh.graphicseditor.model.figure.AbstractSquareFigure;

public class Circle extends AbstractSquareFigure {
    /**
     * Конструктор круга
     *
     * @param canvas     Объект канваса
     * @param startPoint Начальные координаты
     */
    public Circle(AbstractCanvas canvas, Point startPoint) {
        super(canvas, startPoint);
    }

    @Override
    public void draw() {
        canvas.withColorSaving(getBushColor(), getPenColor(), getPenWidth(), () -> {
            canvas.drawOval(getLeftTopPoint(), getRightBottomPoint());
            return null;
        });
    }
}
