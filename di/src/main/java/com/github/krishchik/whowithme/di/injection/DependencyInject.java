package com.github.krishchik.whowithme.di.injection;

import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Set;

public class DependencyInject {
    private static DependencyInject instance;
    private static Reflections scanner;

    public static DependencyInject getInstance() {
        if (instance == null) {
            instance = new DependencyInject();
            scanner = new Reflections("com.github");
        }
        return instance;
    }

    public Class<?> injection(Field field) throws NoSuchMethodException {
        if (field.getType().isInterface()) {
            Class<?> ifc = field.getType();
            Set<Class<?>> classes = (Set<Class<?>>) scanner.getSubTypesOf(ifc);
            if (classes.size() != 1) {
                throw new IllegalArgumentException(ifc + " has 0 or more than 1 impl");
            }
            return classes.iterator().next();
        }
        else {
            return field.getType();
        }
    }

    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);

        if (classes.size() != 1) {
            throw new IllegalArgumentException(ifc + " has 0 or more than 1 impl");
        }
        return classes.iterator().next();
    }
}
