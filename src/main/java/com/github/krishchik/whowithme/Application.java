package com.github.krishchik.whowithme;;
import com.github.krishchik.whowithme.controller.UserControllerImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.github");
        UserControllerImpl userController = applicationContext.getBean(UserControllerImpl.class);
        String user1 = "{\"id\":2,\"login\":\"Nikita\",\"password\":\"22222\"}";
        userController.createUser(user1);

    }
}

