package com.danit.discord.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Palette {
    private static Random rnd = new Random();
    private static List<String> defaultPalette = Arrays.asList("#fca41c", "#fce8c2", "#fc1ce6", "#1c76fc", "#1cfc32");

    public static String getRandomColor() {
        int result = rnd.nextInt(defaultPalette.size() - 1);
        return defaultPalette.get(result);
    }
}
