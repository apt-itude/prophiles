package com.itude.apt.prophiles.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by athompson on 1/9/17.
 */

public class Profile extends RealmObject {

    public static final String ID = "id";

    @PrimaryKey
    private String id;

    private String name;

    public Profile() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
