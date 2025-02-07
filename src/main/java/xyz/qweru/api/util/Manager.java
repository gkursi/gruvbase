package xyz.qweru.api.util;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> target type
 */
public class Manager<T> {
    protected ArrayList<T> itemList = new ArrayList<>();

    public void add(T item) {
        itemList.add(item);
    }

    public @Nullable T get(Class<? extends T> clazz) {
        for (T t : itemList) {
            if(t.getClass().equals(clazz)) {
                return t;
            }
        }
        return null;
    }

    public List<T> getAll() {
        return itemList;
    }
}

