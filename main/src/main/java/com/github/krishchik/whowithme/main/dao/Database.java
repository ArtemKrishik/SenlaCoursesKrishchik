package com.github.krishchik.whowithme.main.dao;

import com.github.krishchik.whowithme.di.annotation.Component;
import com.github.krishchik.whowithme.di.annotation.Value;


@Component
public class Database implements DatabaseInterface{

    @Value("my.param.db")
    private String someText;
    @Override
    public String doTask() {
        return someText;
    }
}
