package men.brakh.oop1.model.figure;

import men.brakh.oop1.model.ResizeType;
import men.brakh.oop1.model.canvas.Canvas;

/**
 * Фигура, которую можно вписать в прямоугольник (По факту, на данный момени, все кроме линии)
 */
public abstract class RectFigure implements Figure {

    private Canvas canvas;

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
     * @param resizeType Тип перемещения ({@link ResizeType})
     * @param deltaX Старый X - Новый X
     * @param deltaY Старый Y - Новый Y
     */
    public void resize(ResizeType resizeType, double deltaX, double deltaY) {
        switch (resizeType) {
            case RESIZE_LT_VERTEX: // Левый верхний
                x1 += deltaX;
                y2 += deltaY;
                break;
            case RESIZE_RT_VERTEX: // Правый верхний
                x2 += deltaX;
                y2 += deltaY;
                break;
            case RESIZE_LB_VERTEX: // Левый нижний
                x1 += deltaX;
                y1 += deltaY;
                break;
            case RESIZE_RB_VERTEX: // Правый нижний
                x2 += deltaX;
                y1 += deltaY;
                break;
            case RESIZE_L_SIDE:
                x1 += deltaX;
                break;
            case RESIZE_R_SIDE:
                x2 += deltaX;
                break;
            case RESIZE_T_SIDE:
                y2 += deltaY;
                break;
            case RESIZE_B_SIDE:
                y1 += deltaY;
                break;
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
