package com.github.krishchik.whowithme.main;



import com.github.krishchik.whowithme.main.controller.Controller;

import java.util.HashMap;


public class Main {
    public static void main(String[] args) {
        HashMap<String, Object> applicationContext = (HashMap<String, Object>) ApplicationContext.getApplicationContext(Controller.class);
        Controller controller = (Controller) applicationContext.get(Controller.class.getName());
        controller.doTask();
    }
}


