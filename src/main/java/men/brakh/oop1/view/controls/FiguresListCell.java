package men.brakh.oop1.view.controls;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListCell;
import men.brakh.oop1.model.Point;
import men.brakh.oop1.model.PointType;
import men.brakh.oop1.model.canvas.AbstractCanvas;
import men.brakh.oop1.model.canvas.impl.JavaFXCanvas;
import men.brakh.oop1.model.figure.AbstractSquareFigure;
import men.brakh.oop1.model.figure.Figure;
import men.brakh.oop1.model.figure.FigureFactory;

public class FiguresListCell extends ListCell<String> {
    private final int canvasSize = 50;
    private final int padding = 5;


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
            Figure figure = FigureFactory.getFigure(name, canvas, new Point(padding,padding)).orElseThrow(RuntimeException::new);
            if(figure instanceof AbstractSquareFigure) {
                figure.resize(PointType.RB_VERTEX, new Point(canvasSize - padding*2, canvasSize - padding*2));
            } else {
                figure.move(new Point(0, padding));
                figure.resize(PointType.RB_VERTEX, new Point(canvasSize - padding*2, canvasSize - padding*4));
            }

            canvas.redraw();
            setText(name);
            setGraphic(canvasInCell);
        }
    }
}
