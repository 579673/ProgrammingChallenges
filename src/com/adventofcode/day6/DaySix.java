package com.adventofcode.day6;

import com.adventofcode.utils.FileUtils;

public class DaySix {
    public static void main(String[] args) {
        OrbitGraph orbitGraph = new OrbitGraph();

        String[] input = FileUtils.readFileAsLines("resources/daySixInput.txt");

        for (String line : input) {
            String[] names = line.split("\\)");
            String parentName = names[0];
            String childName = names[1];
            orbitGraph.addOrbit(parentName, childName);
        }
        System.out.println(orbitGraph.countOrbits());
        orbitGraph.markAllUnvisited();
        System.out.println(orbitGraph.findShortestPath("YOU", "SAN"));
    }
}
