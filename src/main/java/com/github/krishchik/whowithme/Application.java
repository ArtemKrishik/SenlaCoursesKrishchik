package com.github.krishchik.whowithme;;

import com.github.krishchik.whowithme.controller.EventControllerImpl;
import com.github.krishchik.whowithme.controller.Mapper.Mapper;
import com.github.krishchik.whowithme.controller.PlaceControllerImpl;
import com.github.krishchik.whowithme.controller.UserControllerImpl;

import com.github.krishchik.whowithme.controller.dto.EventDto;
import com.github.krishchik.whowithme.controller.dto.PlaceDto;
import com.github.krishchik.whowithme.controller.dto.UserDto;
import com.github.krishchik.whowithme.model.Place;
import com.github.krishchik.whowithme.model.Profile;
import com.github.krishchik.whowithme.model.Role;
import com.github.krishchik.whowithme.repository.PlaceRepositoryImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.github");
        PlaceControllerImpl pc = applicationContext.getBean(PlaceControllerImpl.class);
        EventControllerImpl ec = applicationContext.getBean(EventControllerImpl.class);
        UserControllerImpl uc = applicationContext.getBean(UserControllerImpl.class);

        List<UserDto> u = uc.getAll();
        System.out.println("1");





    }
}

