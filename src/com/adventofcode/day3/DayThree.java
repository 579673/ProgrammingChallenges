package com.adventofcode.day3;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DayThree {

    public static void main(String[] args) {
        List<Point> intersections = findIntersections(readDirectionsFromFile());
        int shortestDistance = intersections.stream()
                                    .map(DayThree::calcManhattanDistance)
                                    .min(Integer::compareTo)
                                    .get();
        System.out.println(shortestDistance);
    }

    private static int calcManhattanDistance(Point other) {
        return Math.abs(other.x) + Math.abs(other.y);
    }

    private static List<Point> findIntersections(Map<String, List<Line>> directions) {
        List<Point> intersections = new ArrayList<>();
        List<Line> firstWire = directions.get("first");
        Map<String, List<Line>> secondWire = directions.get("second")
                .stream()
                .collect(Collectors.groupingBy(line -> line.isHorizontal() ? "horizontal" : "vertical"));
        for (Line line : firstWire) {
            if (line.isHorizontal()) {
                intersections.addAll(secondWire.get("vertical").stream()
                        .filter(l -> l.intersects(line) != null)
                        .map(l -> l.intersects(line))
                        .collect(Collectors.toList()));
            } else {
                intersections.addAll(secondWire.get("horizontal").stream()
                        .filter(l -> l.intersects(line) != null)
                        .map(l -> l.intersects(line))
                        .collect(Collectors.toList()));
            }
        }
        return intersections;
    }

    private static Map<String, List<Line>> readDirectionsFromFile() {
        Map<String, List<Line>> input = new HashMap<>();
        String first = "";
        String second = "";
        try (BufferedReader reader = new BufferedReader(
                new FileReader(
                        new File("resources/dayThreeInput.txt")))) {
            first = reader.readLine();
            second = reader.readLine();
        } catch (IOException e) {
            System.out.println("File not found");
        }
        input.put("first", interpretDirections(first));
        input.put("second", interpretDirections(second));
        return input;
    }

    private static List<Line> interpretDirections(String input) {
        Map<Character, Point> directions = new HashMap<>();
        directions.put('U', new Point(0, 1));
        directions.put('D', new Point(0, -1));
        directions.put('R', new Point(1, 0));
        directions.put('L', new Point(-1, 0));

        List<Line> result = new ArrayList<Line>();
        Point currentLocation = new Point(0, 0);
        for (String direction : input.split(",")) {
            Point directionVector = directions.get(direction.charAt(0));
            int distance = 0;
            try {
                distance = Integer.parseInt(direction.substring(1));
            } catch (NumberFormatException e) {
                System.out.println("Error parsing input.");
            }
            Point newLocation = new Point(currentLocation.x + directionVector.x * distance,
                                          currentLocation.y + directionVector.y * distance);
            result.add(new Line(currentLocation, newLocation));
            currentLocation = newLocation;
        }
        return result;
    }
    //groupingBy isHorizontal


    /*
     * Helper class for this challenge, only intended to work for horizontal and vertical lines.
     */
    static class Line {
        Point start;
        Point end;

        Line(Point start, Point end) {
            this.start = start;
            this.end = end;
        }

        /*
         * Check if day2 lines intersect.
         *     Since this task only deals with horizontal or vertical lines, the equation for any line is
         *     simply a constant, defined by the x value of vertical lines or the y value of horizontal lines.
         *     The intersection would be the point defined by these x and y values.
         */
        public Point intersects(Line other) {
            if (this.isHorizontal() == other.isHorizontal()) {
                return null;
            }
            Point intersection = this.getIntersection(other);
            return this.contains(intersection) && other.contains(intersection) ? intersection : null;
        }

        private boolean contains(Point point) {
            if (this.isHorizontal()) {
                int max = Math.max(start.x, end.x);
                int min = Math.min(start.x, end.x);
                return point.y == start.y && point.x > min && point.x < max;
            } else {
                int max = Math.max(start.y, end.y);
                int min = Math.min(start.y, end.y);
                return point.x == start.x && point.y > min && point.y < max;
            }
        }

        //Should only be used privately in the class, can therefore assume lines are not parallel.
        private Point getIntersection(Line other) {
            if (this.isHorizontal()) {
                return new Point(other.getEquation(), this.getEquation());
            } else {
                return new Point(this.getEquation(), other.getEquation());
            }
        }

        public boolean isHorizontal() {
            return start.y == end.y;
        }

        private int getEquation() {
            if (this.isHorizontal()) {
                return start.y;
            } else {
                return start.x;
            }
        }
    }
}
