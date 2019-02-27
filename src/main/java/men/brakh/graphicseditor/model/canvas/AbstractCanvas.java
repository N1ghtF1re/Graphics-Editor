package men.brakh.graphicseditor.model.canvas;

import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.PointType;
import men.brakh.graphicseditor.model.figure.AbstractRectFigure;
import men.brakh.graphicseditor.model.figure.Figure;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * Абстракция поверх различных реализаций канваса (от swing, javafx и тд)
 */
public abstract class AbstractCanvas {
    private List<Figure> figures = new ArrayList<>();

    /**
     * Множество выделенных фигур. Hashcode && Equals у фигур стандартные, все сравнения - по ссылкам
     */
    private Set<Figure> selectedFigures = new HashSet<>();


    /**
     * Выполнение метода с сохранением цвета полотна
     * (Применяется цвет фигуры, после чего возвращается предыдущие цвета полотна)
     * @param brushColor Цвет кисти
     * @param penColor Цвет линий
     * @param penWidth Размер линий
     * @param func Функция, которую надо выполнить
     */
    public void withColorSaving(String brushColor, String penColor, int penWidth, Callable<Void> func) {
        String brushColorBackup = getBrushColor();
        String penColorBackup = getPenColor();
        int penWidthBackup = getPenWidth();

        setBrushColor(brushColor);
        setPenColor(penColor);
        setPenWidth(penWidth);


        try {
            func.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setBrushColor(brushColorBackup);
        setPenColor(penColorBackup);
        setPenWidth(penWidthBackup);
    }

    /**
     * Очистка полотна
     */
    public abstract void clear();

    /**
     * Перерисовка полотна
     */
    public void redraw() {
        clear();

        figures.forEach(Figure::draw); // Отрисовываем каждую фигуру
        selectedFigures.forEach(Figure::select); // Выделяем фигуры, которые нужно
    }

    /**
     * Меняет курсор на канвасе
     * @param point Точка, на которую наведен курсор
     */
    public abstract void changeCursor(Point point);

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
     * Удаление фигуры с полотна
     * @param figure Объект фигуры
     */
    public void removeFigure(Figure figure) {
        figures.remove(figure);
        redraw();
    }

    public void removeAll(List<Figure> removedFigures) {
        figures.removeAll(removedFigures);
        redraw();
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
     * Выделение
     */

    /**
     * Выделение фигуры на полотне
     * @param figure Объект фигуры
     */
    public void select(Figure figure) {
        selectedFigures.add(figure);
        figure.select();
    }

    /**
     * Выделение фигур на полотне
     * @param figures Коллекция фигур
     */
    public void selectAll(Collection<Figure> figures) {
        selectedFigures.addAll(figures);

        figures.forEach(Figure::select);
    }

    /**
     * Возвращает список фигур внутри прямоугольника
     */
    public List<Figure> getFiguresInside(AbstractRectFigure figure) {
        List<Figure> figuresInside = new ArrayList<>();

        figures.forEach(
                currFigure -> {
                    if(currFigure.isInside(figure)) {
                        figuresInside.add(currFigure);
                    }
                }
        );

        return figuresInside;
    }

    /**
     * Проверяет, выделена ли фигура
     * @param figure объект фигуры
     * @return true если фигура выделена
     */
    public boolean isSelected(Figure figure) {
        return selectedFigures.contains(figure);
    }

    /**
     * Снимает выделение фигуры
     * @param figure Объект фигуры
     */
    public void unSelect(Figure figure) {
        selectedFigures.remove(figure);
        redraw(); // После очистки перерисовываем
    }

    /**
     * Снимает выделение ВСЕХ фигур
     */
    public void unSelectAll() {
        selectedFigures.clear();
        redraw(); // После очистки перерисовываем
    }

    public List<Figure> getSelected() {
        return new ArrayList<>(selectedFigures);
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
     * @param leftTop Левая верхняя точка
     * @param rightBottom Правая нижняя точка
     */
    public abstract void drawRectangle(Point leftTop, Point rightBottom);

    /**
     * Отрисовка полого прямоугольника
     * @param leftTop Левая верхняя точка
     * @param rightBottom Правая нижняя точка
     */
    public abstract void drawStrokeRectangle(Point leftTop, Point rightBottom);

    /**
     * Отрисовка овала
     * @param leftTop Левая верхняя точка (Если вписать в прямоугольник)
     * @param rightBottom Правая нижняя точка (Если вписать в прямоугольник)
     */
    public abstract void drawOval(Point leftTop, Point rightBottom);

    /**
     * Отрисовка ромба
     * @param leftTop Левая верхняя точка (Если вписать в квадрат)
     * @param rightBottom Правая нижняя точка (Если вписать в квадрат)
     */
    public abstract void drawRhombus(Point leftTop, Point rightBottom);

    /**
     * Отрисовка полого ромба
     * @param leftTop Левая верхняя точка (Если вписать в квадрат)
     * @param rightBottom Правая нижняя точка (Если вписать в квадрат)
     */
    public abstract void drawStrokeRhombus(Point leftTop, Point rightBottom);

    /*
     * ПОЛЯ САМОГО КАНВАСА
     */

    public abstract String getBrushColor();
    public abstract void setBrushColor(String brushColor);
    public abstract String getPenColor();
    public abstract void setPenColor(String penColor);
    public abstract int getPenWidth();
    public abstract void setPenWidth(int borderSize);
}
