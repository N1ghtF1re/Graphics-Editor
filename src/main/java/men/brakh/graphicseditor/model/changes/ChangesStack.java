package men.brakh.graphicseditor.model.changes;

import men.brakh.graphicseditor.model.canvas.AbstractCanvas;
import men.brakh.graphicseditor.model.exceptions.StackEmptyException;
import men.brakh.graphicseditor.model.figure.Figure;
import men.brakh.graphicseditor.stack.FixedStack;

import java.util.Stack;

public class ChangesStack {
    private final Stack<Change<Figure>> undoStack;
    private final Stack<Change<Figure>> redoStack;
    private final AbstractCanvas canvas;

    public ChangesStack(AbstractCanvas canvas, int maxSize) {
        undoStack = new FixedStack<>(maxSize);
        redoStack = new FixedStack<>(maxSize);
        this.canvas = canvas;
    }

    public synchronized void add(ChangeType changeType, Figure figure) {
        undoStack.push(new Change<>(changeType, figure, figure.copy()));
    }

    public synchronized void undo() {
        try {
            Change<Figure> change = undoStack.pop();
            switch (change.getChangeType()) {
                case CHANGE_RESIZE:
                case CHANGE_MOVE:
                case CHANGE_RECOLOR:
                    Figure figure = change.getChangedObject();
                    figure.assign(change.getSafeCopy());
                    break;
                case CHANGE_ADD:
                    canvas.removeFigure(change.getChangedObject());
                    break;
                case CHANGE_REMOVE:
                    canvas.addFigure(change.getChangedObject());
                    break;
            }
        } catch (StackEmptyException ignored){}
    }
}
