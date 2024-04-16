package ca.master.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class DijkstraSolver implements MazeSolver {
	
	private static final Logger logger = LogManager.getLogger();
	Direction prv;

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
        Direction dir = Direction.LEFT;
        prv = Direction.LEFT;
        Path path = new Path();

        while (parentMap.containsKey(current)) {
            Position parent = parentMap.get(current);
            String c = getStepDirection(parent, current, dir);
            
            if (c == "LF")
            {
            	dir = dir.turnRight();
            }
            
            else if (c == "RF")
            {
            	dir = dir.turnLeft();
            }
            
            path.addStep(c.charAt(0));
            
            if (c != "F")
            	path.addStep(c.charAt(1));
            
            current = parent;
        }

        path.reversePath();
        return path;
    }

    private String getStepDirection(Position from, Position to, Direction dir) {
//    	logger.info("Reading the maze from file {}", dir);
        int dx = to.x() - from.x();
        int dy = to.y() - from.y();
        
        if (dx == 1 && dy == 0)
        	dir = Direction.LEFT;
        else if (dx == -1 && dy == 0)
        	dir = Direction.RIGHT;
        else if (dx == 0 && dy == 1)
        	dir = Direction.UP;
        else if (dx == 0 && dy == -1)
        	dir = Direction.DOWN;
        
        
        if (prv != dir.turnLeft() && prv != dir.turnRight())
        {
            return "F";
        }
        
        else if (prv == dir.turnLeft())
        {
        	prv = dir;
            return "LF";
        }
        
        else if (prv == dir.turnRight())
        {
        	prv = dir;
            return "RF";
        }
        
        else
        {
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










//public String convertPath(Path path) {
//
//StringBuilder convertedPath = new StringBuilder();
//String pathString = path.getPath();
//
//// Start direction assumed to be up
//Direction currentDirection = Direction.RIGHT;
//
//for (int i = 0; i < pathString.length(); i++) {
//    char step = pathString.charAt(i);
//    if (step == '↑' || step == '↓' || step == '←' || step == '→') {
//        // Change current direction
//        currentDirection = step;
//    } else if (step == 'F') {
//        convertedPath.append('F');
//    } else if (step == 'R') {
//        currentDirection = turnRight(currentDirection);
//    } else if (step == 'L') {
//        currentDirection = turnLeft(currentDirection);
//    }
//}
//
//return convertedPath.toString();
//}
//
//private char turnRight(char currentDirection) {
//switch (currentDirection) {
//    case '↑':
//        return '→';
//    case '→':
//        return '↓';
//    case '↓':
//        return '←';
//    case '←':
//        return '↑';
//    default:
//        throw new IllegalArgumentException("Invalid direction");
//}
//}
//
//private char turnLeft(char currentDirection) {
//switch (currentDirection) {
//    case '↑':
//        return '←';
//    case '←':
//        return '↓';
//    case '↓':
//        return '→';
//    case '→':
//        return '↑';
//    default:
//        throw new IllegalArgumentException("Invalid direction");
//}
//}
