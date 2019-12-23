package com.github.dant3.squirrel.utils;

public interface Observable {
    void addObserver(Object object, String methodName, Object... args);

    void removeObserver(Object object);

    void removeObservers();

    void notifyX();
}
