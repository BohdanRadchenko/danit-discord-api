package com.danit.discord.utils;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NanoId {
    private static final Random random = new Random();
    private static final char[] alphabet = IntStream.rangeClosed('a', 'z')
            .mapToObj(c -> Character.toString((char) c))
            .collect(Collectors.joining())
            .toCharArray();
    private static final int size = 10;

    public static String generate() {
        return NanoIdUtils.randomNanoId(random, alphabet, size);
    }
}
