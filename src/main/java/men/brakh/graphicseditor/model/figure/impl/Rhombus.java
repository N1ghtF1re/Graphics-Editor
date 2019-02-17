package men.brakh.graphicseditor.model.figure.impl;

import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;
import men.brakh.graphicseditor.model.figure.AbstractSquareFigure;

public class Rhombus extends AbstractSquareFigure {
    /**
     * Конструктор ромба
     *
     * @param canvas     Объект канваса
     * @param startPoint Начальные координаты
     */
    public Rhombus(AbstractCanvas canvas, Point startPoint) {
        super(canvas, startPoint);
    }

    @Override
    public void draw() {
        canvas.withColorSaving(getBushColor(), getPenColor(), getPenWidth(), () -> {
            canvas.drawRhombus(getLeftTopPoint(), getRightBottomPoint());
            return null;
        });
    }
}