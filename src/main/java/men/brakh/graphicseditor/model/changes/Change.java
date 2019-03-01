package men.brakh.graphicseditor.model.changes;

public class Change<T> {
    private T changedObject;
    private T safeCopy;
    private ChangeType changeType;

    public Change(ChangeType changeType, T changedObject, T safeCopy) {
        this.changedObject = changedObject;
        this.changeType = changeType;
        this.safeCopy = safeCopy;
    }

    public T getChangedObject() {
        return changedObject;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public T getSafeCopy() {
        return safeCopy;
    }
}
