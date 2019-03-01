package men.brakh.graphicseditor.model.figure;

import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
        String currentPackage = Figure.class.getPackage().getName();

        String packageName = currentPackage + ".impl";

        List<Class<Figure>> figures = new ArrayList<>();
        URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));

        File[] files = new File(Objects.requireNonNull(root).getFile()).listFiles((dir, name) -> name.endsWith(".class"));


        for (File file : Objects.requireNonNull(files)) {
            String className = file.getName().replaceAll(".class$", "");
            Class<?> cls = null;
            try {
                cls = Class.forName(packageName + "." + className);
                if (Figure.class.isAssignableFrom(cls)) {
                    figures.add((Class<Figure>) cls);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return figures;
    }
}
