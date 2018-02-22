package com.es.jointexpensetracker.service;

import java.util.*;
import java.util.stream.Collectors;

final class ChartColorService {
    private static final ChartColorService instance = new ChartColorService();
    private List<Integer> colors = new ArrayList<>();
    private final Object lock = new Object();

    private ChartColorService() {
        colors.addAll(Arrays.asList(0xF7464A, 0x46BFBD, 0xFDB45C, 0x949FB1, 0x4D5360));
    }

    List<Integer> getColors(int number) {
        // todo: improve somehow. it still allows similar (and even same, probably) colors, and any unpleasant colors, depending on random
        if (colors.size() < number) {
            synchronized (lock) {
                if (colors.size() < number)
                    addColors(number);
            }
        }
        return Collections.unmodifiableList(colors.subList(0, number));
    }

    List<Integer> getHighlightedColors(int number) {
        if (colors.size() < number) {
            synchronized (lock) {
                if (colors.size() < number)
                    addColors(number);
            }
        }
        return Collections.unmodifiableList(colors.subList(0, number).stream().map(i -> {
            i = (i + 0x140000 > 0xffffff) ? i | 0xff0000 : i + 0x140000;
            i = (i + 0x001400 > 0xffffff) ? i | 0x00ff00 : i + 0x001400;
            i = (i + 0x000014 > 0xffffff) ? i | 0x0000ff : i + 0x000014;
            return i;
        }).collect(Collectors.toList()));
    }

    List<String> getColorsAsHexStrings(int number) {
        return getColors(number).stream().map(num -> String.format("#%X", num)).collect(Collectors.toList());
    }

    List<String> getHighlightedColorsAsHexStrings(int number) {
        return getHighlightedColors(number).stream().map(num -> String.format("#%X", num)).collect(Collectors.toList());
    }

    private void addColors(int number) {
        Random random = new Random();
        while(colors.size() < number)
            colors.add(random.nextInt(0xfeeeee - 0x222222) + 0x222222);
    }

    static ChartColorService getInstance() {
        return instance;
    }
}
