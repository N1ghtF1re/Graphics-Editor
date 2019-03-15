package men.brakh.graphicseditor.model.figure.impl;

import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;
import men.brakh.graphicseditor.model.figure.AbstractRectFigure;

public class Treeangle extends AbstractRectFigure {
    /**
     * Конструктор фигуры, которую можно вписать в прямоугольник
     *
     * @param canvas     Объект канваса
     * @param startPoint Начальные координаты
     */
    public Treeangle(AbstractCanvas canvas, Point startPoint) {
        super(canvas, startPoint);
    }

    @Override
    public void draw() {
        canvas.withColorSaving(getBushColor(), getPenColor(), getPenWidth(), () -> {
            canvas.drawTreangle(getLeftTopPoint(), getRightBottomPoint());
            return null;
        });
    }
}
