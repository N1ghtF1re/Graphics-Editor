package men.brakh.oop1.model.figure.impl;

import men.brakh.oop1.model.Point;
import men.brakh.oop1.model.canvas.AbstractCanvas;
import men.brakh.oop1.model.figure.AbstractSquareFigure;

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
