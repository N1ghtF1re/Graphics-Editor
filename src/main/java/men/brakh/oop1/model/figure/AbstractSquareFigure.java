package men.brakh.oop1.model.figure;

import men.brakh.oop1.model.Point;
import men.brakh.oop1.model.PointType;
import men.brakh.oop1.model.canvas.AbstractCanvas;

/**
 * Фигура, которую можно вписать в квадрат
 */
public abstract class AbstractSquareFigure extends AbstractRectFigure{
    /**
     * Конструктор фигуры, которую можно вписать в квадрат
     *
     * @param canvas     Объект канваса
     * @param startPoint Начальные координаты
     */
    public AbstractSquareFigure(AbstractCanvas canvas, Point startPoint) {
        super(canvas, startPoint);
    }

    /**
     * Изменение размеров квадратной фигуры
     * @param pointType Тип точки, которую тянем ({@link PointType})
     * @param deltaX Старый X - Новый X
     * @param deltaY Старый Y - Новый Y
     */
    @Override
    public void resize(PointType pointType, int deltaX, int deltaY) {
        int xSign = deltaX < 0 ? -1 : 1;
        int ySign = deltaY < 0 ? -1 : 1;

        int delta = Math.max(Math.abs(deltaX), Math.abs(deltaY));
        super.resize(pointType, xSign * delta, ySign * delta);
    }

    @Override
    public abstract void draw();
}
