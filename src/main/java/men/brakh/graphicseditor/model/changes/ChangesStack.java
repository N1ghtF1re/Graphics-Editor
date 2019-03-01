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

    public boolean undoEmpty() {
        return undoStack.isEmpty();
    }

    public boolean redoEmpty() {
        return redoStack.isEmpty();
    }

    public synchronized void add(ChangeType changeType, Figure figure) {
        undoStack.push(new Change<>(changeType, figure, figure.copy()));
        redoStack.clear();
    }

    public synchronized void clear() {
        undoStack.clear();
        redoStack.clear();
    }

    public synchronized void undo() {
        restore(undoStack, redoStack);
    }

    public synchronized void redo() {
        restore(redoStack, undoStack);
    }

    private void restore(Stack<Change<Figure>> srcStack, Stack<Change<Figure>> destStack) {
        try {
            Change<Figure> change = srcStack.pop();
            Figure figureToRedo = change.getChangedObject().copy();

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
            ChangeType newChangeType = change.getChangeType();
            if(change.getChangeType() == ChangeType.CHANGE_ADD)
                newChangeType = ChangeType.CHANGE_REMOVE;

            if(change.getChangeType() == ChangeType.CHANGE_REMOVE)
                newChangeType = ChangeType.CHANGE_ADD;


            destStack.push(new Change<>(newChangeType, change.getChangedObject(), figureToRedo));
        } catch (StackEmptyException ignored){}
    }

}
