package men.brakh.oop1.model.figure.impl;

import men.brakh.oop1.model.Point;
import men.brakh.oop1.model.canvas.AbstractCanvas;
import men.brakh.oop1.model.figure.AbstractSquareFigure;

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
        canvas.drawOval(getLeftTopPoint(), getRightBottomPoint());
    }
}
