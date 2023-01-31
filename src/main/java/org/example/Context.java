package org.example;

import java.util.HashMap;

public class Context {
    private static Context context = null;
    private HashMap<Class, Object> objects = new HashMap<>();

    private Context() {
    }

    public static Context getContext() {
        if (context == null)
            context = new Context();

        return context;
    }

    public void put(Class clazz, Object object) {
        objects.put(clazz, object);
    }

    public <T> T get(Class<T> clazz) {
        var result = objects.get(clazz);
        if (result != null)
            return (T) result;
        else
            return null;
    }

}
