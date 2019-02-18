package men.brakh.graphicseditor.view.controls;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListCell;
import men.brakh.graphicseditor.config.GraphicEditorConfig;
import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.PointType;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;
import men.brakh.graphicseditor.model.canvas.impl.JavaFXCanvas;
import men.brakh.graphicseditor.model.figure.*;

public class FiguresListCell extends ListCell<String> {
    private final int canvasSize = 50;
    private final int padding = 5;

    private GraphicEditorConfig config = GraphicEditorConfig.getInstance();

    private Canvas canvasInCell = new Canvas(canvasSize + 10, canvasSize);
    private AbstractCanvas canvas = new JavaFXCanvas(canvasInCell);

    @Override
    public void updateItem(String name, boolean empty) {
        canvas.setBrushColor("#000");

        super.updateItem(name, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            FigureFactory.getFigure(name, canvas, new Point(padding, padding)).ifPresent(
                    figure -> {
                        figure.move(new Point(config.getMinFigureWidth(), config.getMinFigureHeight()));
                        if (figure instanceof AbstractSquareFigure) {
                            figure.resize(PointType.RB_VERTEX, new Point(padding, padding), new Point(canvasSize - padding, canvasSize - padding));
                        } else if (figure instanceof AbstractRectFigure) {
                            figure.move(new Point(0, padding));
                            figure.resize(PointType.RB_VERTEX, new Point(padding, padding), new Point(canvasSize - padding, canvasSize - padding * 2));
                        } else if (figure instanceof AbstractLine) {
                            figure.resize(PointType.POINT_NODE, new Point(padding, padding), new Point(canvasSize - padding, canvasSize - padding * 2));
                        }
                    }
            );

            canvas.redraw();
            setText(name);
            setGraphic(canvasInCell);
        }
    }
}
