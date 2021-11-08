package com.github.krishchik.whowithme;

import com.github.krishchik.whowithme.controller.EventControllerImpl;
import com.github.krishchik.whowithme.controller.PlaceControllerImpl;
import com.github.krishchik.whowithme.controller.UserControllerImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.github");
        UserControllerImpl userController = applicationContext.getBean(UserControllerImpl.class);
        EventControllerImpl eventController = applicationContext.getBean(EventControllerImpl.class);
        PlaceControllerImpl placeController = applicationContext.getBean(PlaceControllerImpl.class);

        String user1 = "{\"id\":1,\"login\":\"Artem\",\"password\":\"password\",\"phoneNumber\":6223755}";
        String user2 = "{\"id\":2,\"login\":\"Andrei\",\"password\":\"password\",\"phoneNumber\":6223755}";
        String user22 = "{\"id\":2,\"login\":\"Nikita\",\"password\":\"password\",\"phoneNumber\":6223755}";

        userController.createUser(user1);
        userController.createUser(user2);
        System.out.println("getAllUsers:\n");
        System.out.println(userController.getAll()+"\n");
        System.out.println("getUserById, id = 1:\n");
        System.out.println(userController.getUserById(1L)+"\n");
        System.out.println("deleteUser, updateUser:\n");
        userController.deleteUser(user1);
        userController.updateUser(user22);
        System.out.println(userController.getAll()+"\n");

        String event1 = "{\"id\":1,\"eventName\":\"firstEvent\",\"eventStatus\":\"planned\",\"numberOfPeople\":100,\"ageLimit\":15}";
        String event12 = "{\"id\":1,\"eventName\":\"anotherFirstEvent\",\"eventStatus\":\"planned\",\"numberOfPeople\":100,\"ageLimit\":15}";
        String event2 = "{\"id\":2,\"eventName\":\"secondEvent\",\"eventStatus\":\"planned\",\"numberOfPeople\":100,\"ageLimit\":15}";

        eventController.createEvent(event1);
        eventController.createEvent(event2);
        System.out.println("getAllEvents:\n");
        System.out.println(eventController.getAll()+"\n");
        System.out.println("getEventById, id = 1:\n");
        System.out.println(eventController.getEventById(1L)+"\n");
        System.out.println("deleteEvent, updateEvent:\n");
        eventController.deleteEvent(event2);
        eventController.updateEvent(event12);
        System.out.println(eventController.getAll()+"\n");

        String place1 = "{\"id\":1,\"placeName\":\"firstPlace\",\"capacity\":100,\"price\":250}";
        String place12 = "{\"id\":1,\"placeName\":\"anotherFirstPlace\",\"capacity\":100,\"price\":250}";
        String place2 = "{\"id\":1,\"placeName\":\"secondPlace\",\"capacity\":100,\"price\":250}";

        placeController.createPlace(place1);
        placeController.createPlace(place2);
        System.out.println("getAllPlaces:\n");
        System.out.println(placeController.getAll()+"\n");
        System.out.println("getPlaceById, id = 1:\n");
        System.out.println(placeController.getPlaceById(1L)+"\n");
        System.out.println("deletePlace, updatePlace:\n");
        placeController.deletePlace(place2);
        placeController.updatePlace(place12);
        System.out.println(placeController.getAll()+"\n");

    }
}

