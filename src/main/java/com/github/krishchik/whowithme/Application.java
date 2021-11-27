package com.github.krishchik.whowithme;;

import com.github.krishchik.whowithme.controller.EventControllerImpl;
import com.github.krishchik.whowithme.controller.PlaceControllerImpl;
import com.github.krishchik.whowithme.controller.UserControllerImpl;

import com.github.krishchik.whowithme.controller.dto.ProfileDto;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.github");
        PlaceControllerImpl pc = applicationContext.getBean(PlaceControllerImpl.class);
        EventControllerImpl ec = applicationContext.getBean(EventControllerImpl.class);
        UserControllerImpl uc = applicationContext.getBean(UserControllerImpl.class);

        ProfileDto profileDto = uc.getUsersProfile(1l);
        System.out.println("1");

    }
}

