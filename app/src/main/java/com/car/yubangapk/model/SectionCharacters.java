package com.car.yubangapk.model;

import java.util.List;

/**
 * Created by aspsine on 15/9/4.
 * * 都是关于Adapter的  滑动刷新加载
 */
public class SectionCharacters {

    private List<Character> characters;

    private List<Section> sections;

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
