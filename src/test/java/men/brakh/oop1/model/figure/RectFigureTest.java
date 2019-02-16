package men.brakh.oop1.model.figure;

import men.brakh.oop1.config.GraphicEditorConfig;
import men.brakh.oop1.model.Point;
import men.brakh.oop1.model.PointType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RectFigureTest {
    private class RectFigureImpl extends RectFigure {
        private RectFigureImpl(Point startPoint) {
            super(null, startPoint);
        }

        public Point getLeftBottom() {
            return getLeftBottomPoint();
        }

        public Point getRightTop() {
            return getRightTopPoint();
        }

        public Point getLeftTop() {
            Point lb = getLeftBottom();
            Point rt = getRightTop();

            return new Point(lb.getX(), rt.getY());
        }

        public Point getRightBottom() {
            Point lb = getLeftBottom();
            Point rt = getRightTop();

            return new Point(rt.getX(), lb.getY());
        }


        public void select() {}

        public void draw() {}
    }

    GraphicEditorConfig config = GraphicEditorConfig.getInstance();

    @Test
    public void resizeTest() {
        RectFigureImpl figure = new RectFigureImpl(new Point(0,0));

        figure.resize(PointType.RT_VERTEX, 15, 12);
        Point rt = figure.getRightTop();

        assertEquals(rt.getX(), 15, 0);
        assertEquals(rt.getY(), 12, 0);

        figure.resize(PointType.RB_VERTEX, -5, 5);
        Point rb = figure.getRightBottom();

        assertEquals(rb.getX(), 10, 0);
        assertEquals(rb.getY(), 5, 0);

        figure.resize(PointType.LEFT_SIDE, 20, 0); // Должна выполниться нормализация

        Point lb = figure.getLeftBottom();

        assertEquals(lb.getX(), 10, 0);

        rb = figure.getRightBottom();
        assertEquals(rb.getX(), 20, 0);
    }

    @Test
    public void checkPointTest() {
        int delta = config.getPointAreaSize();

        RectFigureImpl figure = new RectFigureImpl(new Point(10, 120));
        figure.resize(PointType.RT_VERTEX, 220, 240);
        //      |------------(220, 240)
        //      |                 |
        //  (10, 120)-------------|

        assertEquals(figure.checkPoint(new Point(10 + delta + 10, 120 + delta + 20)), PointType.POINT_INSIZE);
        assertEquals(figure.checkPoint(new Point(10 - delta - 10, 120 - delta - 20)), PointType.UNKNOWN_POINT);
        assertEquals(figure.checkPoint(new Point(10 - delta + 1, 120 - delta - 20)), PointType.UNKNOWN_POINT);
        assertEquals(figure.checkPoint(new Point(10 - delta - 1, 120 - delta + 1)), PointType.UNKNOWN_POINT);

        assertEquals(figure.checkPoint(new Point(10 - delta + 1, 121)), PointType.LB_VERTEX);
        assertEquals(figure.checkPoint(new Point(10 - delta + 1, 200)), PointType.LEFT_SIDE);
    }

    @Test
    public void moveTest() {
        RectFigureImpl figure = new RectFigureImpl(new Point(10, 20));
        figure.resize(PointType.RT_VERTEX, 20, 40);
        figure.move(10, 15);

        Point lb = figure.getLeftBottom();
        Point rt = figure.getRightTop();

        assertEquals(lb.getX(), 20, 0);
        assertEquals(lb.getY(), 35, 0);

        assertEquals(rt.getX(), 40, 0);
        assertEquals(rt.getY(), 75, 0);

    }

}