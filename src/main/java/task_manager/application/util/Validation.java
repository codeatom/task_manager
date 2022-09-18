package task_manager.application.util;

public class Validation {

    public static boolean isNullOrEmpty(String s){
        return s == null || s.trim().isEmpty();
    }
}
