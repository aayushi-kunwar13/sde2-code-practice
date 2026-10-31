package com.practice.phonepe.questions.bookmyshow.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Theatre {

    private final String id;
    private final String name;
    private final String city;
    private final List<Screen> screens = new ArrayList<>();

    public Theatre(String name, String city) {
        this.id = UUID.randomUUID().toString();
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

    public void addScreen(Screen screen) {
        screens.add(screen);
    }
}
