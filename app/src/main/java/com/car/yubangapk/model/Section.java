package com.car.yubangapk.model;

import java.util.List;

/**
 * Created by aspsine on 15/9/4.
 *
 * 都是关于Adapter的  滑动刷新加载
 */
public class Section {
    private String name;
    private List<Character> characters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}
