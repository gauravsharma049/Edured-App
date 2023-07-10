package com.edured.services.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UtilityClass {
    public static <T extends Identifiable> void sortById(List<T> objects) {
        Collections.sort(objects, new Comparator<T>() {
            @Override
            public int compare(T object1, T object2) {
                return Long.compare(object1.getId(), object2.getId());
            }
        });
    }
}
