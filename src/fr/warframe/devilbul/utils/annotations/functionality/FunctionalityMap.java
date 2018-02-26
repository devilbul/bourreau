package fr.warframe.devilbul.utils.annotations.functionality;

import java.lang.reflect.Method;

import static fr.warframe.devilbul.Bourreau.functionalities;

public class FunctionalityMap {

    public static void registerFunctionalities(Object... objects) {
        for (Object object : objects)
            registerFunctionality(object);
    }

    public static void registerFunctionality(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Functionality.class)) {
                Functionality functionality = method.getAnnotation(Functionality.class);
                functionalities.put(functionality.name(), functionality.description());
            }
        }
    }
}
