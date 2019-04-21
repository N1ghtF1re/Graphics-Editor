package men.brakh.graphicseditor.config;

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

    /**
     * Минимальная ширина фигуры
     */
    private int minFigureWidth;

    /**
     * Минимальная высота фигуры
     */
    private int minFigureHeight;

    /**
     * Название режима, где фигуры не рисуются
     */
    private String figureNoneName;

    /**
     * Цвет выделения фигуры
     */
    private String selectionColor;

    /**
     * Размер пера при выделении
     */
    private int selectionPenWidth;

    /**
     * Максимальный размер стека изменений
     */
    private int changesStackSize;

    /**
     * Путь к библиотекам с фигурами
     */
    private String libsPath;

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

    public int getMinFigureWidth() {
        return minFigureWidth;
    }

    public int getMinFigureHeight() {
        return minFigureHeight;
    }

    public String getFigureNoneName() {
        return figureNoneName;
    }

    public String getSelectionColor() {
        return selectionColor;
    }

    public int getSelectionPenWidth() {
        return selectionPenWidth;
    }

    public int getChangesStackSize() {
        return changesStackSize;
    }

    public String getLibsPath() {
        return libsPath;
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
        minFigureWidth = Integer.valueOf(editorProps.getProperty("figure.minWidth"));
        minFigureHeight = Integer.valueOf(editorProps.getProperty("figure.minHeight"));
        figureNoneName = editorProps.getProperty("figure.none.name");
        selectionColor = editorProps.getProperty("selection.color");
        selectionPenWidth = Integer.valueOf(editorProps.getProperty("selection.pen.width"));
        changesStackSize = Integer.valueOf(editorProps.getProperty("changesStack.size"));
        libsPath = editorProps.getProperty("figures.lib.path");
    }
}
