package men.brakh.oop1.model.figure;

import men.brakh.oop1.model.Point;
import men.brakh.oop1.model.PointType;
import men.brakh.oop1.model.canvas.Canvas;

/**
 * Фигура, которую можно вписать в прямоугольник (По факту, на данный момени, все кроме линии)
 */
public abstract class RectFigure implements Figure {
    protected Canvas canvas;

    private double left; // Левая
    private double bottom; // нижняя точка

    private double right; // Правая
    private double top; // верхняя точка


    /**
     * Конструктор фигуры, которую можно вписать в прямоугольник
     * @param canvas Объект канваса
     * @param startPoint Начальные координаты
     */
    public RectFigure(Canvas canvas, Point startPoint) {
        this.canvas = canvas;
        this.left = startPoint.getX();
        this.bottom = startPoint.getY();
        this.right = startPoint.getX();
        this.top = startPoint.getY();
    }

    /**
     * Координаты левой нижней точки
     * @return Координаты левой нижней точки
     */
    protected Point getLeftBottomPoint() {
        return new Point(left, bottom);
    }

    /**
     * Коордитаты правоый верхней точки
     * @return Коордитаты правоый верхней точки
     */
    protected Point getRightTopPoint() {
        return new Point(right, top);
    }

    /**
     * Возвращает тип точки в пределах данной фигуры
     * @param point Точка
     * @return тип точки в пределах фигуры
     */
    public PointType checkPoint(Point point) {
        if(!point.yInRange(bottom, top) || !point.xInRange(left, right)) {
            return PointType.UNKNOWN_POINT; // Точка вне фигуры
        }

        Point leftTop = new Point(left, top);
        Point rightTop = new Point(right, top);
        Point leftBottom = new Point(left, bottom);
        Point rightBottom = new Point(right, bottom);

        if(point.equals(leftTop)) {
            return PointType.LT_VERTEX;
        }

        if(point.equals(rightTop)) {
            return PointType.RT_VERTEX;
        }

        if(point.equals(leftBottom)) {
            return PointType.LB_VERTEX;
        }

        if(point.equals(rightBottom)) {
            return PointType.RB_VERTEX;
        }

        if(point.xEquals(left) && point.yInRange(bottom, top)) {
            return PointType.LEFT_SIDE;
        }

        if(point.xEquals(right) && point.yInRange(bottom, top)) {
            return PointType.RIGHT_SIDE;
        }

        if(point.yEquals(top) && point.xInRange(left, right)) {
            return PointType.TOP_SIDE;
        }

        if(point.yEquals(bottom) && point.xInRange(left, right)) {
            return PointType.BOTTOM_SIDE;
        }

        return PointType.POINT_INSIZE; // Остался только один вариант - точка внутри фигуры
    }

    /**
     * Изменение размера фигуры
     * @param pointType Тип точку, которую тянем ({@link PointType})
     * @param deltaX Старый X - Новый X
     * @param deltaY Старый Y - Новый Y
     */
    public void resize(PointType pointType, double deltaX, double deltaY) {
        switch (pointType) {
            case LT_VERTEX: // Левый верхний
                left += deltaX;
                top += deltaY;
                break;
            case RT_VERTEX: // Правый верхний
                right += deltaX;
                top += deltaY;
                break;
            case LB_VERTEX: // Левый нижний
                left += deltaX;
                bottom += deltaY;
                break;
            case RB_VERTEX: // Правый нижний
                right += deltaX;
                bottom += deltaY;
                break;
            case LEFT_SIDE:
                left += deltaX;
                break;
            case RIGHT_SIDE:
                right += deltaX;
                break;
            case TOP_SIDE:
                top += deltaY;
                break;
            case BOTTOM_SIDE:
                bottom += deltaY;
                break;
            default:
                throw new UnsupportedOperationException();
        }
        normalzie(); // Выполняем нормализацию координат после перемещения
    }

    /**
     * Перемещение фигуры
     * @param deltaX delta x перемещения
     * @param deltaY delta y перемещения
     */
    public void move(double deltaX, double deltaY) {
        left += deltaX;
        right += deltaX;
        bottom += deltaY;
        top += deltaY;
    }

    /**
     * Нормализация координат (Левая координата должна быть левее правой и т.д.)
     * @return true если нормализация была применена
     */
    private boolean normalzie() {
        boolean isNormalized = false;

        if(bottom > top) {
            double tmp = top;
            top = bottom;
            bottom = tmp;
            isNormalized = true;
        }

        if(left > right) {
            double tmp = left;
            left = right;
            right = tmp;
            isNormalized = true;
        }

        return isNormalized;
    }

    /**
     * Выделение фигуры на полотне
     */
    public abstract void select();

    /**
     * Отрисовка фигуры на полотне
     */
    public abstract void draw();
}
