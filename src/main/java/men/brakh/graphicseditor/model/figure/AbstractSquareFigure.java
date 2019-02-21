package men.brakh.graphicseditor.model.figure;

import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.PointType;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;

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
                setLeft(right - newSize);
                setTop(bottom - newSize);
                break;
            case LEFT_SIDE:
                setLeft(right - width);
                setTop(bottom - width);
                break;
            case TOP_SIDE:
                setLeft(right - height);
                setTop(bottom - height);
                break;
            case RT_VERTEX:
                setRight(left + newSize);
                setTop(bottom - newSize);
                break;
            case RIGHT_SIDE:
                setRight(left + width);
                setTop(bottom - width);
                break;
            case LB_VERTEX:
                setLeft(right - newSize);
                setBottom(top + newSize);
                break;
            case BOTTOM_SIDE:
                setLeft(right - height);
                setBottom(top + height);
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
    public void resize(PointType pointType, Point fromPoint, Point toPoint) {
        super.resize(pointType, fromPoint,  toPoint);
        equalizeSides(pointType);
    }

    @Override
    public abstract void draw();
}
