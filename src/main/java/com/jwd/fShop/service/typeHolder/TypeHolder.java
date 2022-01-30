package com.jwd.fShop.service.typeHolder;

import java.util.HashMap;

import static java.util.Objects.nonNull;

public class TypeHolder {
    private final HashMap<Integer, String> idName;
    private final HashMap<String, Integer> nameId;

    public TypeHolder() {
        idName = new HashMap<>();
        nameId = new HashMap<>();
    }

    public void add(Integer id, String name) {
        if (nonNull(id) && nonNull(name)) {
            idName.put(id, name);
            nameId.put(name, id);
        }
    }

    public String getName(Integer id) {
        return idName.get(id);
    }

    public Integer getId(String name) {
        return nameId.get(name);
    }

    public String[] getNames() {
        return idName.values().toArray(new String[0]);
    }
}
