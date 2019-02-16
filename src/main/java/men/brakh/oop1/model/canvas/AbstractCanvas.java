package men.brakh.oop1.model.canvas;

import men.brakh.oop1.model.Point;
import men.brakh.oop1.model.PointType;
import men.brakh.oop1.model.figure.Figure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Абстракция поверх различных реализаций канваса (от swing, javafx и тд)
 */
public abstract class AbstractCanvas {
    private List<Figure> figures = new ArrayList<>();
    private String brushColor = "#000000";
    private String penColor = "#000000";
    private int borderSize = 2;

    /**
     * Очистка полотна
     */
    public abstract void clear();

    /**
     * Перерисовка полотна
     */
    public void redraw() {
        clear();
        for(Figure figure : figures) {
            figure.draw();
        }
    }

    /**
     * Добавление фигуры на полотно
     * @param figure объект фигуры
     * @return добавленная фигура
     */
    public Figure addFigure(Figure figure) {
        figures.add(figure);
        figure.draw();
        return figure;
    }

    /**
     * Метод возвращает фигуру, расположенную в точке
     * @param point Точка
     * @return Опционал фигуры (Если в точке нет фигуры - опционал пустой)
     */
    public Optional<Figure> getFigureAtPoint(Point point) {
        for(Figure figure : figures) {
            PointType type = figure.checkPoint(point);
            if(type != PointType.UNKNOWN_POINT) {
                return Optional.of(figure);
            }
        }
        return Optional.empty();
    }

    /*
     * РИСОВАНИЕ
     */

    /**
     * Отрисовка линии из точки1 в точку2
     * @param point1 Точка 1
     * @param point2 Точка 2
     */
    public abstract void drawLine(Point point1, Point point2);

    /**
     * Отрисовка прямоугольника
     * @param leftBottom Левая нижняя точка
     * @param rightTop Правая верхняя точка
     */
    public abstract void drawRectangle(Point leftBottom, Point rightTop);

    /**
     * Отрисовка овала
     * @param leftBottom Левая нижняя точка (Если вписать в прямоугольник)
     * @param rightTop Правая верхняя точка (Если вписать в прямоугольник)
     */
    public abstract void drawOval(Point leftBottom, Point rightTop);

    /*
     * ГЕТЕРЫ И СЕТТЕРЫ
     */

    public String getBrushColor() {
        return brushColor;
    }

    public void setBrushColor(String brushColor) {
        this.brushColor = brushColor;
    }

    public String getPenColor() {
        return penColor;
    }

    public void setPenColor(String penColor) {
        this.penColor = penColor;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }
}
