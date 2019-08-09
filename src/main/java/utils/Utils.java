package utils;

import java.util.Random;

public class Utils {









    public static String generateId(){
        Random random = new Random();
        int number = random.nextInt(99999);
        return "set"+number;
    }
}
