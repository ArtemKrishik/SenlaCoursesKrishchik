package com.github.krishchik.whowithme.di.injection;

import com.github.krishchik.whowithme.di.annotation.Autowired;
import com.github.krishchik.whowithme.di.annotation.Component;
import com.github.krishchik.whowithme.di.annotation.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class DependencyService {
    private static DependencyService instance;
    private final Map<String, Object> instanceClassMap;
    private Object instanceClass;

    private DependencyService() {
        this.instanceClassMap = new HashMap<>();
    }

    public static DependencyService getInstance() {
        if (instance == null) {
            instance = new DependencyService();
        }
        return instance;
    }

    public void putIntoContext(Class<?> clazz) throws IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException, FileNotFoundException {
        if (clazz.getAnnotation(Component.class) == null) {
            throw new IllegalArgumentException("Class " + clazz.getSimpleName() + " don't have 'Component' annotation");
        }
        final Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        this.instanceClass = constructor.newInstance();
        constructor.setAccessible(false);
        instanceClassMap.put(clazz.getName(), instanceClass);
        setProperty(clazz);
    }

    public void setProperty(Class<?> clazz) throws IllegalAccessException, FileNotFoundException {

        Class<?> implClass = clazz;
        if(clazz.isInterface()) {
            implClass = DependencyInject.getInstance().getImplClass(clazz);
        }

        final List<Field> annotatedFields = Arrays.stream(implClass.getDeclaredFields())
                .filter(i -> !i.getType().isPrimitive() && i.isAnnotationPresent(Value.class))
                .collect(Collectors.toList());

        for (Field field : annotatedFields) {
            Value annotation = field.getAnnotation(Value.class);
            String path = ClassLoader.getSystemClassLoader().getResource("application.properties").getPath();
            Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
            Map<String, String> propertiesMap = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));
            lines.close();
            if (annotation != null) {
                String value = propertiesMap.get(annotation.value());
                field.setAccessible(true);
                field.set(instanceClass, value);
            }
        }

    }

    public void initConstructor() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException, FileNotFoundException {

        final List<Field> annotatedFields = Arrays.stream(instanceClass.getClass().getDeclaredFields())
                .filter(i -> !i.getType().isPrimitive() && i.isAnnotationPresent(Autowired.class))
                .collect(Collectors.toList());

        for (Field field : annotatedFields) {
            final Class<?> implClass = DependencyInject.getInstance().injection(field);
            if (implClass.isAnnotationPresent(Component.class) && !instanceClassMap.containsKey(implClass.getName())) {
                final Object bufInstanceClass = this.instanceClass;
                putIntoContext(implClass);
                initConstructor();
                this.instanceClass = bufInstanceClass;
            }
            field.setAccessible(true);
            if (instanceClassMap.containsKey(implClass.getName())) {
                field.set(instanceClass, instanceClassMap.get(implClass.getName()));
                field.setAccessible(false);
            }
        }
    }

    public Map<String, Object> getInstanceClassMap() {
        return instanceClassMap;
    }

}