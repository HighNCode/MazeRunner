package ca.master.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MazeReader {
	
	private final List<List<Boolean>> maze = new ArrayList<>();
    /**
     * Read the maze from a file and return the maze structure along with start and end positions.
     *
     * @param filePath File path of the maze file
     * @return Maze structure and start/end positions
     * @throws Exception If maze cannot be read, or maze has no start or end
     */
    public MazeData readMazeFromFile(String filePath) throws Exception {
        
        Position start = null;
        Position end = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                List<Boolean> rowList = new ArrayList<>();
                for (int col = 0; col < line.length(); col++) {
                    if (line.charAt(col) == '#') {
                        rowList.add(true);
                    } else if (line.charAt(col) == ' ') {
                        rowList.add(false);
                    }
                }
                maze.add(rowList);
                row++;
            }
        }
        
        start = findStart();
        end = findEnd();

        if (start == null || end == null) {
            throw new Exception("Invalid maze (missing start or end position)");
        }

        return new MazeData(maze, start, end);
    }
    
    /**
     * Find start position of the maze.
     *
     * @return The start position
     * @throws Exception If no valid start position exists
     */
    private Position findStart() throws Exception {
        for (int i = 0; i < maze.size(); i++) {
            Position pos = new Position(0, i);
            if (!isWall(pos)) {
                return pos;
            }
        }
        throw new Exception("Invalid maze (no start position available)");
    }

    /**
     * Find start end of the maze.
     *
     * @return The end position
     * @throws Exception If no valid end position exists
     */
    private Position findEnd() throws Exception {
        for (int i = 0; i < maze.size(); i++) {
            Position pos = new Position(maze.getFirst().size() - 1, i);
            if (!isWall(pos)) {
                return pos;
            }
        }
        throw new Exception("Invalid maze (no end position available)");
    }
    
    public Boolean isWall(Position pos) {
        return maze.get(pos.y()).get(pos.x());
    }
}
