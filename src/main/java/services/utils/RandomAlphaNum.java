package services.utils;

import java.util.Random;
import java.util.UUID;

public class RandomAlphaNum {

    public static String generateId() {
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString();
    }

    public static int generateNumber(int aStart, int aEnd) {

        Random random = new Random();
        if (aStart > aEnd) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        //get the range, casting to long to avoid overflow problems
        long range = (long)aEnd - (long)aStart + 1;
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * random.nextDouble());

        return (int)(fraction + aStart);
    }
}