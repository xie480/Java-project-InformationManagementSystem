package com.yilena.yilenastudysystem.utils;

public class CurrentHolder {

    private static final ThreadLocal<Integer> CURRENT_LOCAL = new ThreadLocal<>();

    public static void setCurrent(Integer employeeId) {
        CURRENT_LOCAL.set(employeeId);
    }

    public static Integer getCurrent() {
        return CURRENT_LOCAL.get();
    }

    public static void removeCurrent() {
        CURRENT_LOCAL.remove();
    }
}