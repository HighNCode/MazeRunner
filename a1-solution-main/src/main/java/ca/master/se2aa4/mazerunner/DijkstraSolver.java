package ca.master.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Path.expandFactorizedStringPath;

import java.util.*;

public class DijkstraSolver implements MazeSolver {
	
	private static final Logger logger = LogManager.getLogger();

    @Override
    public Path solve(Maze maze) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        Map<Position, Integer> distances = new HashMap<>();
        Map<Position, Position> parentMap = new HashMap<>();

        Position start = maze.getStart();
        queue.add(new Node(start, 0));
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            Position currentPosition = current.position;
            int currentDistance = current.distance;

            if (currentPosition.equals(maze.getEnd())) {
                return reconstructPath(parentMap, maze);
            }

            for (Position neighbor : getValidNeighbors(currentPosition, maze)) {
                int newDistance = currentDistance + 1; // Assuming unit weight for all edges
                if (!distances.containsKey(neighbor) || newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    queue.add(new Node(neighbor, newDistance));
                    parentMap.put(neighbor, currentPosition);
                }
            }
        }

        // No path found
        return new Path();
    }

    private List<Position> getValidNeighbors(Position position, Maze maze) {
        List<Position> neighbors = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            Position neighborPos = position.move(dir);
            if (isValidPosition(neighborPos, maze)) {
                neighbors.add(neighborPos);
            }
        }
        return neighbors;
    }

    private boolean isValidPosition(Position position, Maze maze) {
        return position.x() >= 0 && position.x() < maze.getSizeX() &&
                position.y() >= 0 && position.y() < maze.getSizeY() &&
                !maze.isWall(position);
    }

    private Path reconstructPath(Map<Position, Position> parentMap, Maze maze) {
        Position current = maze.getEnd();
        //List<Character> pathSteps = new ArrayList<>();
        Path pathSteps = new Path();

        while (parentMap.containsKey(current)) {
            Position parent = parentMap.get(current);
            pathSteps.addStep(getStepDirection(parent, current));
            current = parent;
        }
        //Collections.reverse(pathSteps);
        
        logger.info("** H{}", pathSteps.toString());
        
        return new Path(pathSteps.toString());
    }

    private char getStepDirection(Position from, Position to) {
        int dx = to.x() - from.x();
        int dy = to.y() - from.y();

        if (dx == 0 && dy == 1) {
            return '↑';
        } else if (dx == 0 && dy == -1) {
            return '↓';
        } else if (dx == 1 && dy == 0) {
            return '→';
        } else if (dx == -1 && dy == 0) {
            return '←';
        } else {
            throw new IllegalArgumentException("Invalid path: unexpected step direction.");
        }
    }

    private static class Node {
        Position position;
        int distance;

        Node(Position position, int distance) {
            this.position = position;
            this.distance = distance;
        }
    }
}
