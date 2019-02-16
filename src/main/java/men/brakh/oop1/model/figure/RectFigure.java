package men.brakh.oop1.model.figure;

import men.brakh.oop1.model.Point;
import men.brakh.oop1.model.PointType;
import men.brakh.oop1.model.canvas.Canvas;

/**
 * Фигура, которую можно вписать в прямоугольник (По факту, на данный момени, все кроме линии)
 */
public abstract class RectFigure implements Figure {

    protected Canvas canvas;

    private double x1; // Левая нижняя
    private double y1; // Точка

    private double x2; // Правая верхняя
    private double y2; // Точка


    /**
     * Конструктор фигуры, которую можно вписать в прямоугольник
     * @param canvas Объект канваса
     * @param startPoint Начальные координаты
     */
    public RectFigure(Canvas canvas, Point startPoint) {
        this.canvas = canvas;
        this.x1 = startPoint.getX();
        this.y1 = startPoint.getY();
        this.x2 = startPoint.getX();
        this.y2 = startPoint.getY();
    }

    /**
     * Координаты левой нижней точки
     * @return Координаты левой нижней точки
     */
    protected Point getLeftBottomPoint() {
        return new Point(x1, y1);
    }

    /**
     * Коордитаты правоый верхней точки
     * @return Коордитаты правоый верхней точки
     */
    protected Point getRightTopPoint() {
        return new Point(x2, y2);
    }

    public PointType checkPoint(Point point) {
        return null;
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
                x1 += deltaX;
                y2 += deltaY;
                break;
            case RT_VERTEX: // Правый верхний
                x2 += deltaX;
                y2 += deltaY;
                break;
            case LB_VERTEX: // Левый нижний
                x1 += deltaX;
                y1 += deltaY;
                break;
            case RB_VERTEX: // Правый нижний
                x2 += deltaX;
                y1 += deltaY;
                break;
            case LEFT_SIDE:
                x1 += deltaX;
                break;
            case RIGHT_SIDE:
                x2 += deltaX;
                break;
            case TOP_SIDE:
                y2 += deltaY;
                break;
            case BOTTOM_SIDE:
                y1 += deltaY;
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    /**
     * Перемещение фигуры
     * @param deltaX delta x перемещения
     * @param deltaY delta y перемещения
     */
    public void move(double deltaX, double deltaY) {
        x1 += deltaX;
        x2 += deltaX;
        y2 += deltaY;
        y1 += deltaY;
    }

    public abstract void select();

    public abstract void draw();
}
