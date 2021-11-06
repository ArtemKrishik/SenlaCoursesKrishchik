package com.github.krishchik.whowithme.di.injection;


import java.util.Map;

public final class ApplicationContext {


    private final Map<String, Object> applicationContext;

    public ApplicationContext(Map<String, Object> applicationContext) {
        this.applicationContext = applicationContext;
    }



    public Object getBean(String beanName) {
        return applicationContext.get(beanName);
    }



}
