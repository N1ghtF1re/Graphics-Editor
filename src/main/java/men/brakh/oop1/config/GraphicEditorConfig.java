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
     * Получение синглтона
     */
    public static synchronized GraphicEditorConfig getInstance() {
        if (instance == null) {
            instance = new GraphicEditorConfig();
        }
        return instance;
    }

    /**
     * Количество пикселей, в радиусе которых нажатие на точку будет засчитываться
     */
    private double pointAreaSize;

    public double getPointAreaSize() {
        return pointAreaSize;
    }

    private GraphicEditorConfig() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "editor.properties";

        Properties editorProps = new Properties();
        try {
            editorProps.load(new FileInputStream(appConfigPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Заполняем значения
        pointAreaSize = Double.valueOf(editorProps.getProperty("point.area.size"));
    }
}
