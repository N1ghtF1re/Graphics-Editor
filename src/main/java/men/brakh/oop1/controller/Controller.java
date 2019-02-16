package men.brakh.oop1.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML
    private Canvas canvasMain;


    @FXML
    void canvasOnMousePressed(MouseEvent event) {
        System.out.println("KEK");
        System.out.println(canvasMain.getWidth());

    }

}
