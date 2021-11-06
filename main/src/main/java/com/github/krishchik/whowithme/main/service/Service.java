package com.github.krishchik.whowithme.main.service;


import com.github.krishchik.whowithme.di.annotation.Autowired;
import com.github.krishchik.whowithme.di.annotation.Component;
import com.github.krishchik.whowithme.main.dao.DatabaseInterface;


@Component
public class Service implements ServiceInterface{

    @Autowired
    private DatabaseInterface database;

    @Override
    public String doTask() {
        return database.doTask();
    }
}
