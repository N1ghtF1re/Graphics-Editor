package men.brakh.oop1.model.figure;

import men.brakh.oop1.config.GraphicEditorConfig;
import men.brakh.oop1.model.Point;
import men.brakh.oop1.model.PointType;
import men.brakh.oop1.model.canvas.AbstractCanvas;

/**
 * Фигура, которую можно вписать в прямоугольник (По факту, на данный момени, все кроме линии)
 */
public abstract class AbstractRectFigure implements Figure {
    private GraphicEditorConfig config = GraphicEditorConfig.getInstance();

    protected AbstractCanvas canvas;

    private int left; // Левая
    private int bottom; // нижняя точка

    private int right; // Правая
    private int top; // верхняя точка


    /**
     * Конструктор фигуры, которую можно вписать в прямоугольник
     * @param canvas Объект канваса
     * @param startPoint Начальные координаты
     */
    public AbstractRectFigure(AbstractCanvas canvas, Point startPoint) {
        this.canvas = canvas;
        this.left = startPoint.getX() - config.getMinFigureWidth();
        this.bottom = startPoint.getY();
        this.right = startPoint.getX() ;
        this.top = startPoint.getY() - config.getMinFigureHeight();
    }

    /**
     * Координаты левой нижней точки
     * @return Координаты левой нижней точки
     */
    protected Point getLeftTopPoint() {
        return new Point(left, top);
    }

    /**
     * Коордитаты правоый верхней точки
     * @return Коордитаты правоый верхней точки
     */
    protected Point getRightBottomPoint() {
        return new Point(right, bottom);
    }

    /**
     * Возвращает тип точки в пределах данной фигуры
     * @param point Точка
     * @return тип точки в пределах фигуры
     */
    public PointType checkPoint(Point point) {
        if(!point.yInRange(top, bottom) || !point.xInRange(left, right)) {
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

        if(point.xEquals(left) && point.yInRange(top, bottom)) {
            return PointType.LEFT_SIDE;
        }

        if(point.xEquals(right) && point.yInRange(top, bottom)) {
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
     * @param pointType Тип точки, которую тянем ({@link PointType})
     * @param deltaPoint Текущая точка - Пердыдущая точка
     */
    public void resize(PointType pointType, Point deltaPoint) {
        switch (pointType) {
            case LT_VERTEX: // Левый верхний
                left += deltaPoint.getX();
                top += deltaPoint.getY();
                break;
            case RT_VERTEX: // Правый верхний
                right += deltaPoint.getX();
                top += deltaPoint.getY();
                break;
            case LB_VERTEX: // Левый нижний
                left += deltaPoint.getX();
                bottom += deltaPoint.getY();
                break;
            case RB_VERTEX: // Правый нижний
                right += deltaPoint.getX();
                bottom += deltaPoint.getY();
                break;
            case LEFT_SIDE:
                left += deltaPoint.getX();
                break;
            case RIGHT_SIDE:
                right += deltaPoint.getX();
                break;
            case TOP_SIDE:
                top += deltaPoint.getY();
                break;
            case BOTTOM_SIDE:
                bottom += deltaPoint.getY();
                break;
            default:
                throw new UnsupportedOperationException();
        }
        normalzie(); // Выполняем нормализацию координат после перемещения
    }

    /**
     * Перемещение фигуры
     * @param deltaPoint delta перемещения
     */
    public void move(Point deltaPoint) {
        left += deltaPoint.getX();
        right += deltaPoint.getX();
        bottom += deltaPoint.getY();
        top += deltaPoint.getY();
    }

    /**
     * Нормализация координат (Левая координата должна быть левее правой и т.д.)
     * @return true если нормализация была применена
     */
    private boolean normalzie() {
        boolean isNormalized = false;

        if(bottom < top) { // На канвасе ось У сверху вниз
            int tmp = top;
            top = bottom;
            bottom = tmp + config.getMinFigureHeight();
            isNormalized = true;
        }

        if(left > right) {
            int tmp = left;
            left = right;
            right = tmp + config.getMinFigureWidth();
            isNormalized = true;
        }

        return isNormalized;
    }

    /**
     * Выделение фигуры на полотне
     */
    public void select() {

    }

    /**
     * Отрисовка фигуры на полотне
     */
    public abstract void draw();

}
