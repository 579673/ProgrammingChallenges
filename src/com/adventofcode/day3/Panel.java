package com.adventofcode.day3;

import java.util.List;
import java.awt.Point;

public class Panel {
    List<Point> wire;
    List<Point> otherWire;

    public Panel(List<Point> wire, List<Point> otherWire) {
        this.wire = wire;
        this.otherWire = otherWire;
    }
    /*
    public Panel(String inputFilePath) {
        String[] lines = FileUtils.readFileAsLines("resources/dayThreeInput.txt");

    }*/
}
