package men.brakh.oop1.model.figure;

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
     * @param x0 Начальная координата х
     * @param y0 Начальная координата y
     */
    public RectFigure(Canvas canvas, double x0, double y0) {
        this.canvas = canvas;
        this.x1 = x0;
        this.y1 = y0;
        this.x2 = x0;
        this.y2 = y0;
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
