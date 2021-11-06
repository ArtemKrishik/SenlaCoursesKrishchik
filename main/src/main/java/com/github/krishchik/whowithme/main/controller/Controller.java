package com.github.krishchik.whowithme.main.controller;



import com.github.krishchik.whowithme.di.annotation.Autowired;
import com.github.krishchik.whowithme.di.annotation.Component;
import com.github.krishchik.whowithme.main.service.ServiceInterface;


@Component
public class Controller {

    @Autowired
    private ServiceInterface service;

    public void doTask(){
        System.out.println(service.doTask());
    }

}
