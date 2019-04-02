package men.brakh.graphicseditor.model.stack;

import men.brakh.graphicseditor.model.exceptions.StackEmptyException;

import java.util.Stack;

/**
 * Стек с фиксированным максимальным числом элементов
 */
public final class FixedStack<T> extends Stack<T> {
    private final int maxSize;

    public FixedStack(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public T push(T el) {
        super.push(el);
        if(super.size() > maxSize) { // Если стек переполнен - выкидываем старые изменения
            super.remove(0);
        }
        return el;
    }

    @Override
    public T pop(){
        if(super.size() == 0) {
            throw new StackEmptyException();
        }
        return super.pop();
    }

}
