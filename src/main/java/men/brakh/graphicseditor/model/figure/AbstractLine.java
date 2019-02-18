package men.brakh.graphicseditor.model.figure;

import men.brakh.graphicseditor.config.GraphicEditorConfig;
import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.PointType;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractLine implements Figure {
    private GraphicEditorConfig config = GraphicEditorConfig.getInstance();

    private String penColor;
    private int penWidth;

    protected AbstractCanvas canvas;

    protected List<Point> points = new ArrayList<>();

    public AbstractLine(AbstractCanvas canvas, Point startPoint) {
        this.canvas = canvas;
        points.add(startPoint);

        this.penColor = canvas.getPenColor();
        this.penWidth = canvas.getBorderSize();

        canvas.addFigure(this);
    }

    /**
     * Добавление вершины линии
     * @param point Координата вершины
     */
    public void addPoint(Point point) {
        points.add(point);
    }

    /**
     * Тип точки
     * @param point Точка
     * @return Тип точки
     */
    @Override
    public PointType checkPoint(Point point) {
        for (Point linePoint : points) {
            if(linePoint.equals(point)) {
                return PointType.POINT_NODE;
            }
        }

        if(points.size() < 2) return PointType.UNKNOWN_POINT;

        for(int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i-1);
            Point p2 = points.get(i);

            int dx1 = p2.getX() - p1.getX();
            int dy1 = p2.getY() - p1.getY();

            int dx = point.getX() - p1.getX();
            int dy = point.getY() - p1.getY();


            int S = dx1 * dy - dx * dy1;


            double ab = Math.sqrt(dx1 * dx1 + dy1 * dy1);

            double h = (double) S / ab;

            if (Math.abs(h) < config.getPointAreaSize()) {
                return PointType.POINT_INSIZE;
            }
        }
        return PointType.UNKNOWN_POINT;
    }

    /**
     * Получение точки по координатам
     * @param needPoint координаты
     */
    private Optional<Point> getPoint(Point needPoint) {
        for(Point point : points) {
            if(point.equals(needPoint)) {
                return Optional.of(point);
            }
        }

        return Optional.empty();
    }

    /**
     * Перемещние точки линии
     * @param pointType Не используется. Используется в {@link AbstractRectFigure}
     * @param fromPoint Координата точки линии
     * @param toPoint Координата, куда надо переместить эту точку
     */
    @Override
    public void resize(PointType pointType, Point fromPoint, Point toPoint) {
        getPoint(fromPoint).ifPresent(
                point -> {
                    point.assign(toPoint);
                }
        );
    }

    /**
     * Перемещение линии
     */
    @Override
    public void move(Point deltaPoint) {
        for(Point point : points) {
            point.add(deltaPoint);
        }
    }

    /**
     * Выделение линии
     */
    @Override
    public void select() {
        int padding = config.getPointAreaSize();
        String color = config.getSelectionColor();


        canvas.withColorSaving(color, color, config.getSelectionPenWidth(), () ->
                {
                    for(Point point : points) {
                        int x = point.getX();
                        int y = point.getY();
                        canvas.drawRectangle(new Point(x - padding, y - padding), new Point(x + padding, y + padding));
                    }

                    String penBackup = penColor;
                    penColor = color;
                    draw();
                    penColor = penBackup;
                    return null;
                }
        );
    }

    /**
     * Ортрисовка
     */
    @Override
    public abstract void draw();

    /*
     * ГЕТТЕРЫ И СЕТТЕРЫ
     */

    @Override
    public String getPenColor() {
        return penColor;
    }

    @Override
    public String getBushColor() {
        return null;
    }

    @Override
    public int getPenWidth() {
        return penWidth;
    }

    @Override
    public void setPenColor(String color) {
        penColor = color;
    }

    @Override
    public void setBrushColor(String color) {
        // NOTHING
    }

    @Override
    public void setPenWidth(int width) {
        penWidth = width;
    }

    /*
     * HASHCODE && EQUALS
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractLine that = (AbstractLine) o;
        return penWidth == that.penWidth &&
                Objects.equals(penColor, that.penColor) &&
                Objects.equals(canvas, that.canvas) &&
                Objects.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(penColor, penWidth, canvas, points);
    }
}