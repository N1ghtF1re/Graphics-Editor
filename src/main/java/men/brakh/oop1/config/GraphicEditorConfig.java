package men.brakh.oop1.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Синглтон с конфигурацией графического редактора
 */
public class GraphicEditorConfig {
    private static GraphicEditorConfig instance;


    /**
     * Количество пикселей, в радиусе которых нажатие на точку будет засчитываться
     */
    private int pointAreaSize;

    /**
     * Минимальная высота окна редактора
     */
    private int minFrameHeight;

    /**
     * Минимальная ширина окна редактора
     */
    private int minFrameWidth;




    /*
     * ГЕТТЕРЫ, КОНСТРУКТОР
     */

    public int getPointAreaSize() {
        return pointAreaSize;
    }

    public int getMinFrameHeight() {
        return minFrameHeight;
    }

    public int getMinFrameWidth() {
        return minFrameWidth;
    }

    /**
     * Получение синглтона
     */
    public static synchronized GraphicEditorConfig getInstance() {
        if (instance == null) {
            instance = new GraphicEditorConfig();
        }
        return instance;
    }

    private GraphicEditorConfig() {
        String appConfigPath = "src/main/resources/editor.properties";

        Properties editorProps = new Properties();
        try {
            editorProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Заполняем значения
        pointAreaSize = Integer.valueOf(editorProps.getProperty("point.area.size"));
        minFrameHeight = Integer.valueOf(editorProps.getProperty("frame.minHeight"));
        minFrameWidth = Integer.valueOf(editorProps.getProperty("frame.minWidth"));

    }
}
