package com.practice.phonepe.questions.bookmyshowv2.entities;

import com.practice.phonepe.questions.bookmyshow.entities.Screen;

import java.util.ArrayList;
import java.util.List;

public class Theater {
    private final String id;
    private final String name;
    private final String city;
    private List<Screen> screens = new ArrayList<>();

    public Theater(String id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public List<Screen> getScreens() {
        return screens;
    }

    public void addScreens(Screen screen) {
        screens.add(screen);
    }
}
