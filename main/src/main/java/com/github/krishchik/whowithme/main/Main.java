package com.github.krishchik.whowithme.main;

import com.github.krishchik.whowithme.di.injection.ApplicationContext;
import com.github.krishchik.whowithme.di.injection.DependencyController;
import com.github.krishchik.whowithme.main.controller.Controller;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = DependencyController.createContext(Controller.class);
        Controller controller = (Controller) applicationContext.getBean(Controller.class.getName());
        controller.doTask();
    }
}


