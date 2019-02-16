package men.brakh.oop1.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import men.brakh.oop1.model.Point;
import men.brakh.oop1.model.canvas.AbstractCanvas;
import men.brakh.oop1.model.canvas.impl.JavaFXCanvas;

public class Controller {

    @FXML
    private Canvas canvasMain;

    private AbstractCanvas canvas;


    @FXML
    void canvasOnMousePressed(MouseEvent event) {
        canvas = new JavaFXCanvas(canvasMain);
        System.out.println("KEK");
        System.out.println(canvasMain.getWidth());

        /*
        GraphicsContext gc = canvasMain.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        */
        canvas.clear();
        canvas.drawLine(new Point(20, 20), new Point(40, 40));
        canvas.drawRectangle(new Point(20, 20), new Point(40, 40));

        canvas.drawOval(new Point(120, 120), new Point(140, 140));


    }

}
