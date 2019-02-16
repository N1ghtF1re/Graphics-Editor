package men.brakh.oop1;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import men.brakh.oop1.config.GraphicEditorConfig;

import java.util.Objects;

public class Application extends javafx.application.Application {

    private GraphicEditorConfig config = GraphicEditorConfig.getInstance();


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view.fxml")));
        primaryStage.setTitle("Graphics editor");

        int height = config.getMinFrameHeight();
        int width = config.getMinFrameWidth();

        System.out.println(width);

        primaryStage.setScene(new Scene(root, width, height));
        primaryStage.setMinHeight(height);
        primaryStage.setMinWidth(width);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}