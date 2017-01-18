package services.utils;

import java.util.UUID;

public class RandomAlphaNum {

    public static String generateId() {
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString();
    }
}