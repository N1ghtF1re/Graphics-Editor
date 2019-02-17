package men.brakh.graphicseditor.model;


public enum PointType {
    /*
      Общие типы:
     */
    POINT_INSIZE, // Точка внутри фигуры (Именно внутри, не по краям)
    UNKNOWN_POINT, // Непонятная точка

    /*
      Для точек внутри прямоугольных фигур
     */
    LT_VERTEX, // Левая верхняя вершина
    RT_VERTEX, // Правая верхняя вершина
    LB_VERTEX, // Левая нижняя вершина
    RB_VERTEX, // Правая нижняя вершина
    LEFT_SIDE,
    RIGHT_SIDE,
    TOP_SIDE,
    BOTTOM_SIDE,

    /*
      Для линий:
     */
    POINT_NODE,
}
