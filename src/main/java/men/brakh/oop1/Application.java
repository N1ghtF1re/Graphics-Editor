package men.brakh.oop1;

import men.brakh.oop1.config.GraphicEditorConfig;

public class Application {
    private static GraphicEditorConfig config = GraphicEditorConfig.getInstance();

    public static void main(String[] args) {
        System.out.println(config.getPointAreaSize());
    }
}
