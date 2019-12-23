package com.github.dant3.squirrel.utils;

import java.util.concurrent.CopyOnWriteArrayList;

public class ObservableSupport implements Observable {

    private CopyOnWriteArrayList<Event> objects;

    public ObservableSupport() {
        objects = new CopyOnWriteArrayList<>();
    }

    @Override
    public void addObserver(Object object, String methodName, Object... args) {
        objects.add(new Event(object, methodName, args));
    }

    //简化处理，只判断object(多数情况足够)
    @Override
    public void removeObserver(Object object) {
        for (Event event : objects) {
            if (event.object == object) {
                objects.remove(event);
                break;
            }
        }
    }

    @Override
    public void removeObservers() {
        objects.clear();
    }

    @Override
    public void notifyX() {
        try {
            for (Event event : objects) {
                event.invoke();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
