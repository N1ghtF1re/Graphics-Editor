package men.brakh.oop1.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import men.brakh.oop1.model.Mode;
import men.brakh.oop1.model.Point;
import men.brakh.oop1.model.PointType;
import men.brakh.oop1.model.canvas.AbstractCanvas;
import men.brakh.oop1.model.canvas.impl.JavaFXCanvas;
import men.brakh.oop1.model.figure.Figure;
import men.brakh.oop1.model.figure.impl.Rectangle;

import java.util.Optional;

public class Controller {

    @FXML
    private Canvas canvasMain;

    private AbstractCanvas canvas;

    private Mode mode;

    private Point prevPoint;

    private Figure currentFigure;

    private PointType prevPointType;


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        canvas = new JavaFXCanvas(canvasMain);
        mode = Mode.MODE_VIEW;
        /*
        Figure figure = new Rectangle(canvas, new Point(0,0));
        figure.resize(PointType.RB_VERTEX, 30, 40);
        canvas.addFigure(figure);
        */
    }


    @FXML
    void canvasOnMouseDragged(MouseEvent event) {
        Point clickedPoint = new Point(event.getX(), event.getY());
        switch (mode) {
            case MODE_VIEW:
                break;
            case MODE_RESIZE:
                PointType pointType = currentFigure.checkPoint(prevPoint);
                switch (pointType) {
                    case LT_VERTEX:
                    case RT_VERTEX:
                    case LB_VERTEX:
                    case RB_VERTEX:
                        prevPointType = pointType;
                        break;
                }
                System.out.println(prevPointType);
                currentFigure.resize(prevPointType,clickedPoint.delta(prevPoint));
                prevPoint = clickedPoint;

                break;


        }

        canvas.redraw();
        System.out.println("KEK");
    }

    @FXML // Только нажата
    void canvasOnMousePressed(MouseEvent event) {
        Point clickedPoint = new Point(event.getX(), event.getY());
        Optional<Figure> figure = canvas.getFigureAtPoint(clickedPoint);
        if(!figure.isPresent()) {
            currentFigure = new Rectangle(canvas, clickedPoint);
            canvas.addFigure(currentFigure);
            prevPoint = clickedPoint;
            prevPointType = currentFigure.checkPoint(clickedPoint);
            mode = Mode.MODE_RESIZE;
        }
    }

    @FXML // Уже отпущена
    void canvasOnMouseReleased(MouseEvent event) {
        System.out.println("RELEASED");
        mode = Mode.MODE_VIEW;
    }


}
