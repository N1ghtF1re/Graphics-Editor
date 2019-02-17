package men.brakh.graphicseditor.model.figure.impl;

import men.brakh.graphicseditor.config.GraphicEditorConfig;
import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;
import men.brakh.graphicseditor.model.figure.AbstractLine;

public class StraightLine extends AbstractLine {
    private GraphicEditorConfig config = GraphicEditorConfig.getInstance();

    public StraightLine(AbstractCanvas canvas, Point startPoint) {
        super(canvas, startPoint.coppy());
        move(new Point(-config.getMinFigureWidth(), -config.getMinFigureHeight()));
        super.addPoint(startPoint);
    }

    @Override
    public void addPoint(Point point) {
        // Для прямой линии все - достаточно
    }

    @Override
    public void draw() {
        if(points.size() < 2) {
            return;
        }

        Point point1 = points.get(0);
        Point point2 = points.get(1);

        canvas.withColorSaving(getBushColor(), getPenColor(), getPenWidth(), () -> {
            canvas.drawLine(point1, point2);
            return null;
        });

    }
}
