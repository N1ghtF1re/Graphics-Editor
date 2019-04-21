package men.brakh.graphicseditor.model.figure;

import men.brakh.graphicseditor.config.GraphicEditorConfig;
import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Фабрика фигур
 */
public class FigureFactory {
    private static List<Class<Figure>> figuresClasses = getActualFigures();


    public static List<String> getFiguresNames() {
        return figuresClasses.stream()
                .map(figureClass -> figureClass.getName().replaceAll(".*\\.", ""))
                .collect(Collectors.toList());
    }

    /**
     * Создает фигуру
     * @param name Название фигуры
     * @param canvas Объект {@link AbstractCanvas}
     * @param startPoint Начальная точка фигуры
     * @return объект фигуры
     */
    public static Optional<Figure> getFigure(String name, AbstractCanvas canvas, Point startPoint) {
        for(Class<Figure> figureClass: figuresClasses) {
            if (figureClass.getName().endsWith(name)) {
                try {
                    Constructor constructor = figureClass.getConstructor(AbstractCanvas.class, Point.class);
                    Figure figure = (Figure) constructor.newInstance(canvas, startPoint);
                    return Optional.of(figure);
                } catch (Exception e) {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Метод, который возвращает все классы, идующие от интерфейса Figure
     */
    private static List<Class<Figure>> getActualFigures() {

        GraphicEditorConfig config = GraphicEditorConfig.getInstance();

        List<Class<Figure>> figures = new ArrayList<>();

        File folder = new File(Paths.get(config.getLibsPath()).toAbsolutePath().toString());
        File[] listOfFiles = folder.listFiles();

        if(listOfFiles == null) return figures;

        for (File listOfFile : listOfFiles) {
            if (listOfFile.isFile()) {
                try {
                    JarFile jarFile = new JarFile(listOfFile.getAbsolutePath());
                    Enumeration<JarEntry> e = jarFile.entries();

                    URL[] urls = {new URL("jar:file:" + listOfFile.getAbsolutePath() + "!/")};
                    URLClassLoader cl = URLClassLoader.newInstance(urls);

                    while (e.hasMoreElements()) {
                        JarEntry je = e.nextElement();
                        if (je.isDirectory() || !je.getName().endsWith(".class")) {
                            continue;
                        }
                        // -6 because of .class
                        String className = je.getName().substring(0, je.getName().length() - 6);
                        className = className.replace('/', '.');

                        Class cls = cl.loadClass(className);

                        if (Figure.class.isAssignableFrom(cls)) {
                            figures.add((Class<Figure>) cls);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        return figures;
    }
}
