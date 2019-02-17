package men.brakh.graphicseditor.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import men.brakh.graphicseditor.model.Mode;
import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.PointType;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;
import men.brakh.graphicseditor.model.canvas.impl.JavaFXCanvas;
import men.brakh.graphicseditor.model.figure.Figure;
import men.brakh.graphicseditor.model.figure.FigureFactory;
import men.brakh.graphicseditor.view.controls.FiguresListCell;

import java.util.List;
import java.util.Optional;

public class Controller {

    @FXML
    private Canvas canvasMain;

    @FXML
    private ListView<String> lwFigures;

    @FXML
    private ColorPicker cpBrush;

    @FXML
    private ColorPicker cpPen;


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
        lwFigures.getSelectionModel().selectFirst();

        cpPen.setValue(Color.BLACK);
        cpBrush.setValue(Color.BLACK);
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
                currentFigure.resize(currPointType, clickedPoint);
                prevPoint = clickedPoint;
                break;
        }

        canvas.redraw();
    }

    @FXML // Только нажата
    void canvasOnMousePressed(MouseEvent event) {
        switch (event.getButton()) {
            case PRIMARY:
                Point clickedPoint = new Point(event.getX(), event.getY());
                Optional<Figure> clickedFigure = canvas.getFigureAtPoint(clickedPoint);

                if(!clickedFigure.isPresent()) { // Фигуры в этой точке еще не существует => добавляем
                    String selectedFigureName = lwFigures.getSelectionModel().getSelectedItem();

                    // Создаем фигуру
                    FigureFactory.getFigure(selectedFigureName, canvas, clickedPoint).ifPresent(
                            createdFigure -> {
                                prevPoint = clickedPoint; // Обновляем предыдущие координаты
                                currPointType = createdFigure.checkPoint(clickedPoint); // Тип точки при создании
                                mode = Mode.MODE_CREATE; // Меняем режим на создание фигуры
                                currentFigure = createdFigure;
                            }
                    );

                }
                break;
            case MIDDLE:
                break;
            case SECONDARY:
                break;
        }
    }

    @FXML // Уже отпущена
    void canvasOnMouseReleased(MouseEvent event) {
        mode = Mode.MODE_VIEW; // Когда отпускаем кнопку - восстанавливаем режим просмотра
    }

    @FXML
    void brushColorSelected(ActionEvent event) {
        canvas.setBrushColor(cpBrush.getValue().toString());
    }

    @FXML
    void penColorSelected(ActionEvent event) {
        canvas.setPenColor(cpPen.getValue().toString());
    }

}
