package men.brakh.graphicseditor.model.figure.impl;

import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;
import men.brakh.graphicseditor.model.figure.AbstractSquareFigure;

public class Square extends AbstractSquareFigure {
    /**
     * Конструктор квадрата
     *
     * @param canvas     Объект канваса
     * @param startPoint Начальные координаты
     */
    public Square(AbstractCanvas canvas, Point startPoint) {
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
