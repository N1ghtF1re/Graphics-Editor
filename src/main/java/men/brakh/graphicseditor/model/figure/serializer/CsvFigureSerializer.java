package men.brakh.graphicseditor.model.figure.serializer;

import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.canvas.AbstractCanvas;
import men.brakh.graphicseditor.model.figure.Figure;
import men.brakh.graphicseditor.model.figure.FigureFactory;
import men.brakh.graphicseditor.model.figure.intf.TextSerializible;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CsvFigureSerializer implements FigureSerializer {
    @Override
    public String serialize(List<Figure> objects) {
        StringBuilder csv = new StringBuilder();

        objects.forEach(
                figure -> {
                    if(figure instanceof TextSerializible)
                        csv.append(((TextSerializible) figure).serialize());
                }
        );

        return csv.toString();
    }

    @Override
    public List<Figure> deserialize(AbstractCanvas canvas, List<String> rows) {
        List<Figure> figures = new ArrayList<>();
        for(String row : rows) {
            String[] cols = row.split(";");
            String className = cols[0];
            Optional<Figure> figureOptional = FigureFactory.getFigure(className, canvas, new Point(0,0 ));
            figureOptional.ifPresent(
                    figure -> {
                        if(figure instanceof TextSerializible) {
                            if(((TextSerializible) figure).deserialize(row)) {
                                figures.add(figure);
                            }
                        }
                    }
            );
        }
        return figures;
    }
}
