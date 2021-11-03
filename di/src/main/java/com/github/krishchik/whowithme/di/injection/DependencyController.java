package com.github.krishchik.whowithme.di.injection;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class DependencyController {

    public static void setDependency(Class<?> clazz)  {
        try {
            DependencyService.getInstance().setVariable(clazz);
            DependencyService.getInstance().initConstructor();
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException | FileNotFoundException e) {
        }
    }
}
