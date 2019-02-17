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
     * @param delta Новая координата - старая координата
     */
    @Override
    public void resize(PointType pointType, Point delta) {
        int xSign = delta.getX() < 0 ? -1 : 1;
        int ySign = delta.getY() < 0 ? -1 : 1;

        int newDelta = Math.max(Math.abs(delta.getX()), Math.abs(delta.getY()));
        super.resize(pointType, new Point(xSign * newDelta, ySign * newDelta));
    }

    @Override
    public abstract void draw();
}
