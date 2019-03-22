package men.brakh.graphicseditor.model.figure.intf;

import men.brakh.graphicseditor.model.Point;
import men.brakh.graphicseditor.model.PointType;

public interface Resizable {
    void resize(PointType pointType, Point fromPoint, Point toPoint);
}
