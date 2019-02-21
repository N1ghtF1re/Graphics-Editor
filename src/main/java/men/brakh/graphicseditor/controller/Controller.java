package men.brakh.graphicseditor.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import men.brakh.graphicseditor.config.GraphicEditorConfig;
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

    @FXML
    private Label lblPenWidth;

    @FXML
    private Slider sliderPenWidth;



    private GraphicEditorConfig config = GraphicEditorConfig.getInstance();

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

        figuresNames.add(0, config.getFigureNoneName());

        ObservableList<String> items = FXCollections.observableArrayList(figuresNames);
        lwFigures.setItems(items);
        lwFigures.setCellFactory(param -> new FiguresListCell());
        lwFigures.getSelectionModel().selectFirst();

        cpPen.setValue(Color.BLACK);
        cpBrush.setValue(Color.BLACK);
    }

    /**
     * Выделение фигуры с обновлением color picker
     */
    private void selectWithColorUpdating(Figure figure) {
        canvas.select(figure);
        if(canvas.getSelected().size() == 1) {
            if(figure.getBushColor() != null)
                cpBrush.setValue(Color.web(figure.getBushColor()));
            cpPen.setValue(Color.web(figure.getPenColor()));
            lblPenWidth.setText(Integer.toString(figure.getPenWidth()));
            sliderPenWidth.setValue(figure.getPenWidth());
        }
    }

    /**
     * Снятие выделения обновлением color picker
     */
    private void unselectAllWithColorUpdating() {
        canvas.unSelectAll();
        cpBrush.setValue(Color.web(canvas.getBrushColor()));
        cpPen.setValue(Color.web(canvas.getPenColor()));
        lblPenWidth.setText(Integer.toString(canvas.getPenWidth()));
        sliderPenWidth.setValue(canvas.getPenWidth());
    }


    @FXML
    void canvasOnMouseDragged(MouseEvent event) {
        Point clickedPoint = new Point(event.getX(), event.getY());
        switch (mode) {
            case MODE_VIEW:
                break;
            case MODE_RESIZE:
                currentFigure.resize(currPointType, prevPoint, clickedPoint);
                break;
            case MODE_MOVE:
                currentFigure.move(clickedPoint.delta(prevPoint));
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
                currentFigure.resize(currPointType, prevPoint, clickedPoint);
                break;
        }

        prevPoint = clickedPoint; // Обновляем предыдущие координаты
        canvas.redraw();
    }

    /**
     * Произошел клик по пустому пространству
     */
    private void blankAreaClick(Point clickedPoint) {
        // Снимаем существующее выделение
        unselectAllWithColorUpdating();

        String selectedFigureName = lwFigures.getSelectionModel().getSelectedItem();

        if(selectedFigureName.equals(config.getFigureNoneName())) // Если рисовать не надо - выходим
            return;


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

    @FXML
    void onKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case DELETE:
                List<Figure> selectedFigure = canvas.getSelected();
                canvas.removeAll(selectedFigure);
                unselectAllWithColorUpdating();
        }
    }

    @FXML
    void canvasOnMouseMoved(MouseEvent event) {
        canvas.changeCursor(new Point(event.getX(), event.getY()));
    }

    /**
     * Обработка клика по фигуре
     */
    private void figureClick(Point clickedPoint, Figure clickedFigure) {
        currentFigure = clickedFigure; // Нажатая фигура - текущая
        prevPoint = clickedPoint; // Нажатые коориданты - уже предыдущие

        if(canvas.isSelected(clickedFigure)) { // Фигура выделена
            PointType pointType = clickedFigure.checkPoint(clickedPoint);

            if(pointType == PointType.POINT_INSIDE) {
                mode = Mode.MODE_MOVE; // Точка внутри -> перемещаем
            } else {
               mode = Mode.MODE_RESIZE; // По краем -> ресайзим
            }
            currPointType = pointType;

        } else { // Фигура не выделена -> надо выделить
            unselectAllWithColorUpdating();
            selectWithColorUpdating(clickedFigure);
        }
    }

    @FXML // Только нажата
    void canvasOnMousePressed(MouseEvent event) {
        switch (event.getButton()) {
            case PRIMARY:
                Point clickedPoint = new Point(event.getX(), event.getY());
                Optional<Figure> clickedFigureOptional = canvas.getFigureAtPoint(clickedPoint);

                if(!clickedFigureOptional.isPresent()) { // Фигуры в этой точке еще не существует => добавляем
                    blankAreaClick(clickedPoint);
                } else {
                    figureClick(clickedPoint, clickedFigureOptional.get());
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
        canvas.redraw();
    }

    @FXML
    void brushColorSelected(ActionEvent event) {
        List<Figure> selected = canvas.getSelected();
        String color = cpBrush.getValue().toString();
        if(selected.size() == 0) {
            canvas.setBrushColor(color);
        } else { // Если есть выделенные фигуры, то меняем цвет для всех этих выделенных фигур
            selected.forEach(figure -> figure.setBrushColor(color));
            canvas.redraw();
        }
    }

    @FXML
    void penColorSelected(ActionEvent event) {
        List<Figure> selected = canvas.getSelected();
        String color = cpPen.getValue().toString();
        if(selected.size() == 0) {
            canvas.setPenColor(color);
        } else { // Если есть выделенные фигуры, то меняем цвет для всех этих выделенных фигур
            selected.forEach(figure -> figure.setPenColor(color));
            canvas.redraw();
        }
    }

    @FXML
    void sliderPenWidthChanged(Event event) {
        List<Figure> selected = canvas.getSelected();
        int penWidth = (int) Math.round(sliderPenWidth.getValue());
        if(selected.size() == 0){
            canvas.setPenWidth(penWidth);
        } else {
            selected.forEach(figure -> figure.setPenWidth(penWidth));
            canvas.redraw();
        }
        lblPenWidth.setText(Integer.toString(penWidth));
    }

}
