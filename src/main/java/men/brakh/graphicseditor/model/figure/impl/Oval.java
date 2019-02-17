package men.brakh.graphicseditor.model.figure.impl;

import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;
import men.brakh.graphicseditor.model.figure.AbstractRectFigure;

public class Oval extends AbstractRectFigure {
    /**
     * Конструктор овала
     *
     * @param canvas     Объект канваса
     * @param startPoint Начальные координаты
     */
    public Oval(AbstractCanvas canvas, Point startPoint) {
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
