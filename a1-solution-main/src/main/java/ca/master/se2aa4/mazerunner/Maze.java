package ca.master.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Maze {
    private static final Logger logger = LogManager.getLogger();

    private final List<List<Boolean>> maze = new ArrayList<>();
    private final Position start;
    private final Position end;

    // Constructor for internal use
    private Maze(List<List<Boolean>> maze, Position start, Position end) {
        this.maze.addAll(maze);
        this.start = start;
        this.end = end;
    }

    /**
     * Create a Maze instance from a maze structure and start/end positions.
     *
     * @param maze  Maze structure
     * @param start Start position
     * @param end   End position
     * @return Maze instance
     */
    public static Maze createMaze(List<List<Boolean>> maze, Position start, Position end) {
        return new Maze(maze, start, end);
    }

    /**
     * Get horizontal (X) size of Maze.
     *
     * @return Horizontal size
     */
    public int getSizeX() {
        return this.maze.get(0).size();
    }

    /**
     * Get vertical (Y) size of Maze.
     *
     * @return Vertical size
     */
    public int getSizeY() {
        return this.maze.size();
    }

    /**
     * Check if position of Maze is a wall.
     *
     * @param pos The position to check
     * @return If position is a wall
     */
    public Boolean isWall(Position pos) {
        return maze.get(pos.y()).get(pos.x());
    }

    /**
     * Get start position.
     *
     * @return Start position
     */
    public Position getStart() {
        return start;
    }

    /**
     * Get end position.
     *
     * @return End position
     */
    public Position getEnd() {
        return end;
    }

    /**
     * Check if path is valid for Maze.
     *
     * @param path The path to valid
     * @return If path is valid
     */
    public Boolean validatePath(Path path) {
        return validatePathDir(path, getStart(), Direction.RIGHT, getEnd()) || validatePathDir(path, getEnd(), Direction.LEFT, getStart());
    }

    /**
     * Check if path is valid from starting to end position.
     *
     * @param path Path
     * @param startPos Starting position
     * @param startDir Starting direction
     * @param endPos Ending position
     * @return If path is valid
     */
    private Boolean validatePathDir(Path path, Position startPos, Direction startDir, Position endPos) {
        Position pos = startPos;
        Direction dir = startDir;
        for (char c : path.getPathSteps()) {
            switch (c) {
                case 'F' -> {
                    pos = pos.move(dir);

                    if (pos.x() >= getSizeX() || pos.y() >= getSizeY() || pos.x() < 0 || pos.y() < 0) {
                        return false;
                    }
                    if (isWall(pos)) {
                        return false;
                    }
                }
                case 'R' -> dir = dir.turnRight();
                case 'L' -> dir = dir.turnLeft();
            }
            logger.debug("Current Position: " + pos);
        }

        return pos.equals(endPos);
    }
}
