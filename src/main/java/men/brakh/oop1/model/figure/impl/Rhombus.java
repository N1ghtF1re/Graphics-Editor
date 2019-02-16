package men.brakh.oop1.model.figure.impl;

import men.brakh.oop1.model.Point;
import men.brakh.oop1.model.canvas.AbstractCanvas;
import men.brakh.oop1.model.figure.AbstractSquareFigure;

public class Rhombus extends AbstractSquareFigure {
    /**
     * Конструктор фигуры, которую можно вписать в квадрат
     *
     * @param canvas     Объект канваса
     * @param startPoint Начальные координаты
     */
    public Rhombus(AbstractCanvas canvas, Point startPoint) {
        super(canvas, startPoint);
    }

    @Override
    public void draw() {
        canvas.drawRhombus(getLeftTopPoint(), getRightBottomPoint());
    }
}
