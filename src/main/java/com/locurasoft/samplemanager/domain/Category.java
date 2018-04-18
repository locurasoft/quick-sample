package com.locurasoft.samplemanager.domain;

import static java.util.Arrays.stream;

public enum Category {
    Kicks("bd", "bassdrum", "bass drum"),
    Snares("sd"),
    Toms("tom"),
    Hihats("hh"),
    Cymbals("cym"),
    Claps("cp");

    private final String[] matches;

    Category(String... matchesList) {
        this.matches = matchesList;
    }

    public String[] getMatches() {
        return matches.clone();
    }

    public static String[] getNames() {
        return stream(Category.class.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}
