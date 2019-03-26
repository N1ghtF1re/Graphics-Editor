package men.brakh.graphicseditor.model.figure.intf;

public interface TextSerializible {
    String serialize();
    boolean deserialize(String text);
}
