package men.brakh.oop1.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import men.brakh.oop1.model.figure.FigureFactory;
import men.brakh.oop1.view.controls.FiguresListCell;
import men.brakh.oop1.model.Mode;
import men.brakh.oop1.model.Point;
import men.brakh.oop1.model.PointType;
import men.brakh.oop1.model.canvas.AbstractCanvas;
import men.brakh.oop1.model.canvas.impl.JavaFXCanvas;
import men.brakh.oop1.model.figure.Figure;
import men.brakh.oop1.model.figure.impl.Rectangle;

import java.util.List;
import java.util.Optional;

public class Controller {

    @FXML
    private Canvas canvasMain;

    @FXML
    private ListView<String> lwFigures;

    private AbstractCanvas canvas;

    private Mode mode; // Текущий режим работы

    private Point prevPoint; // Предыдущая обрабатываемая точка

    private Figure currentFigure; // Текущая фигура

    private PointType currPointType; // Текущий тип точки


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        canvas = new JavaFXCanvas(canvasMain);
        mode = Mode.MODE_VIEW;


        List<String> figuresNames = FigureFactory.getFiguresNames();

        ObservableList<String> items = FXCollections.observableArrayList(figuresNames);
        lwFigures.setItems(items);


        lwFigures.setCellFactory(param -> new FiguresListCell());
    }


    @FXML
    void canvasOnMouseDragged(MouseEvent event) {
        Point clickedPoint = new Point(event.getX(), event.getY());
        switch (mode) {
            case MODE_VIEW:
                break;
            case MODE_CREATE:
                PointType pointType = currentFigure.checkPoint(prevPoint);

                // Если при создании тип точки изменился => обновляем, иначе - оставляем предыдущий
                switch (pointType) {
                    case LT_VERTEX:
                    case RT_VERTEX:
                    case LB_VERTEX:
                    case RB_VERTEX:
                        currPointType = pointType;
                        break;
                }
                currentFigure.resize(currPointType, clickedPoint.delta(prevPoint));
                prevPoint = clickedPoint;
                break;
        }

        canvas.redraw();
    }

    @FXML // Только нажата
    void canvasOnMousePressed(MouseEvent event) {
        Point clickedPoint = new Point(event.getX(), event.getY());
        Optional<Figure> figure = canvas.getFigureAtPoint(clickedPoint);

        if(!figure.isPresent()) { // Фигуры в этой точке еще не существует => добавляем
            currentFigure = new Rectangle(canvas, clickedPoint);
            prevPoint = clickedPoint; // Обновляем предыдущие координаты
            currPointType = currentFigure.checkPoint(clickedPoint); // Тип точки при создании
            mode = Mode.MODE_CREATE; // Меняем режим на создание фигуры
        }
    }

    @FXML // Уже отпущена
    void canvasOnMouseReleased(MouseEvent event) {
        mode = Mode.MODE_VIEW; // Когда отпускаем кнопку - восстанавливаем режим просмотра
    }


}
