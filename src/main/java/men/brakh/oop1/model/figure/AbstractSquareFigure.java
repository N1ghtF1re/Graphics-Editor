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
     * Сравниваем стороны
     * @param pointType Тип перемещения
     */
    private void equalizeSides(PointType pointType) {
        Point lt = getLeftTopPoint();
        Point rb = getRightBottomPoint();

        int left = lt.getX();
        int right = rb.getX();
        int top = lt.getY();
        int bottom = rb.getY();

        int height = bottom - top;
        int width = right - left;

        int newSize = Math.max(height, width);

        switch (pointType) {

            case LT_VERTEX:
            case LEFT_SIDE:
            case TOP_SIDE:
                setLeft(right - newSize);
                setTop(bottom - newSize);
                break;
            case RT_VERTEX:
            case RIGHT_SIDE:
                setRight(left + newSize);
                setTop(bottom - newSize);
                break;
            case LB_VERTEX:
            case BOTTOM_SIDE:
                setLeft(right - newSize);
                setBottom(top + newSize);
                break;
            case RB_VERTEX:
                setBottom(top + newSize);
                setRight(left + newSize);
                break;
        }
    }

    /**
     * Изменение размеров квадратной фигуры
     * @param pointType Тип точки, которую тянем ({@link PointType})
     * @param toPoint Точка, в которую переместили координату
     */
    @Override
    public void resize(PointType pointType, Point toPoint) {
        super.resize(pointType, toPoint);
        equalizeSides(pointType);
    }

    @Override
    public abstract void draw();
}
