package com.adventofcode.day6;

import java.util.*;
import java.util.stream.Collectors;

public class OrbitGraph {
    private CelestialBody com;
    private Map<String, CelestialBody> cbMap;

    public OrbitGraph() {
        com = new CelestialBody("COM");
        cbMap = new HashMap<>();
        cbMap.put("COM", com);
    }

    public void addOrbit(String parentName, String childName) {
        CelestialBody parent = getCelestialBodyIfExists(parentName);
        CelestialBody child =  getCelestialBodyIfExists(childName);
        child.setParent(parent);
        cbMap.putIfAbsent(childName, child);
        cbMap.putIfAbsent(parentName, parent);
        parent.addOrbitingBody(child);
    }

    public void markAllUnvisited() {
        cbMap.values().forEach(cb -> cb.setVisited(false));
    }

    public int findShortestPath(String start, String target) {
        return findShortestPath(getCelestialBodyIfExists(start), getCelestialBodyIfExists(target));
    }

    private int findShortestPath(CelestialBody start, CelestialBody target) {
        Queue<CelestialBody> queue = new LinkedList<>();
        List<CelestialBody> nextLayer = new ArrayList<>();
        int numberOfTransfers = 0;
        queue.add(start.getParent());
        start.setVisited(true);

        while ( !(queue.isEmpty() && nextLayer.isEmpty()) ) {
            if (queue.isEmpty()) {
                queue.addAll(nextLayer);
                nextLayer.clear();
                numberOfTransfers++;
            }
            CelestialBody current = queue.remove();
            if (current.getOrbitingBodies().contains(target)) {
                return numberOfTransfers;
            }
            nextLayer.addAll(current.getAllUnvisitedNeighbours());
        }
        return -1; //Not found
    }

    private CelestialBody getCelestialBodyIfExists(String name) {
        return cbMap.containsKey(name) ? cbMap.get(name) : new CelestialBody(name);
    }

    public int countOrbits() {
        Stack<CelestialBody> stack = new Stack<>();
        stack.push(com);
        int orbits = 0;

        while (!stack.empty()) {
            CelestialBody current = stack.peek();
            CelestialBody next = current.getNextUnvisited();
            if (next != null) {
                orbits += stack.size();
                stack.push(next);
            } else {
                stack.pop();
            }
        }
        return orbits;
    }

    class CelestialBody {
        private List<CelestialBody> orbitingBodies;
        private CelestialBody parent;
        private String name;
        private boolean visited;

        private CelestialBody(String name) {
            orbitingBodies = new ArrayList<>();
            parent = null;
            this.name = name;
            visited = false;
        }

        private void addOrbitingBody(CelestialBody other) {
            orbitingBodies.add(other);
        }

        private List<CelestialBody> getOrbitingBodies() {
            return orbitingBodies;
        }

        private String getName() {
            return name;
        }

        public CelestialBody getParent() {
            return parent;
        }

        public void setParent(CelestialBody parent) {
            this.parent = parent;
        }

        public CelestialBody getNextUnvisited() {
            for (CelestialBody cb : orbitingBodies) {
                if (!cb.visited()) {
                    cb.setVisited(true);
                    return cb;
                }
            }
            return null;
        }

        public List<CelestialBody> getAllUnvisitedNeighbours() {
            List<CelestialBody> unvisitedNeighbours =
                     orbitingBodies.stream()
                    .filter(cb -> !cb.visited())
                    .collect(Collectors.toList());
            if (!parent.visited()) {
                unvisitedNeighbours.add(parent);
            }
            unvisitedNeighbours.forEach(cb -> cb.setVisited(true));
            return unvisitedNeighbours;
        }

        public boolean visited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof CelestialBody) {
                return this.getName().equals(((CelestialBody)other).getName());
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        public String toString() {
            return name + " -> " + orbitingBodies;
        }
    }
}
