package ca.master.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DijkstraSolverTest {
    @Test
    void solve_Should_ReturnCorrectPath_ForSimpleMaze() {
        // Given
         // Provide the path to a simple maze file
        try {
        MazeReader mazeReader = new MazeReader();
        MazeData mazeData = mazeReader.readMazeFromFile("./examples/small.maz.txt");
        Maze maze = Maze.createMaze(mazeData.getMazeStructure(), mazeData.getStart(), mazeData.getEnd());
        
        // When
        DijkstraSolver solver = new DijkstraSolver();
        Path path = solver.solve(maze);
        
        // Then
        // Add assertions based on the expected path in the maze
        assertEquals("F L F R 2F L 6F R 4F R 2F L 2F R 2F L F", path.getFactorizedForm());
        }
        catch(Exception e){
        	System.err.println("MazeSolverTest failed.  Reason: " + e.getMessage());
        }
    }

    @Test
    void solve_Should_ReturnCorrectPath_ForComplexMaze() {
        // Given
    	try {
            MazeReader mazeReader = new MazeReader();
            MazeData mazeData = mazeReader.readMazeFromFile("./examples/large.maz.txt");
            Maze maze = Maze.createMaze(mazeData.getMazeStructure(), mazeData.getStart(), mazeData.getEnd());
            
            // When
            DijkstraSolver solver = new DijkstraSolver();
            Path path = solver.solve(maze);
            
            // Then
            // Add assertions based on the expected path in the maze
            assertEquals("15F R 2F L 8F R 2F L 4F R 2F L 4F R 4F L 6F R 2F L 2F R 2F L 2F R 2F L 2F R 2F L 2F R 2F L 2F R 6F L 2F L 2F R F", path.getFactorizedForm());
            }
            catch(Exception e){
            	System.err.println("MazeSolverTest failed.  Reason: " + e.getMessage());
            }
    }
}
