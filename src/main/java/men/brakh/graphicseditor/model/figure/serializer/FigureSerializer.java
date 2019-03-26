package men.brakh.graphicseditor.model.figure.serializer;

import men.brakh.graphicseditor.model.canvas.AbstractCanvas;
import men.brakh.graphicseditor.model.figure.Figure;

import java.util.List;

public interface FigureSerializer {
    String serialize(List<Figure> object);
    List<Figure> deserialize(AbstractCanvas canvas, List<String> rows);
}
