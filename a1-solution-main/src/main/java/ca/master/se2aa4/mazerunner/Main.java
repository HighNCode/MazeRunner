package ca.master.se2aa4.mazerunner;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        CommandLineParser parser = new DefaultParser();
        
        CommandLine cmd = null;
        try {
            cmd = parser.parse(getParserOptions(), args);
            String filePath = cmd.getOptionValue('i');
            MazeReader mazeReader = new MazeReader(); // Create an instance of MazeReader
            MazeData mazeData = mazeReader.readMazeFromFile(filePath); // Call readMazeFromFile on the instance
            Maze maze = Maze.createMaze(mazeData.getMazeStructure(), mazeData.getStart(), mazeData.getEnd());

            if (cmd.hasOption("p")) {
                logger.info("Validating path");
                Path path = new Path(cmd.getOptionValue("p"));
                if (maze.validatePath(path)) {
                    System.out.println("correct path");
                } else {
                    System.out.println("incorrect path");
                }
            } else {
                String method = cmd.getOptionValue("method");
                Path path = solveMaze(method, maze);
                System.out.println(path.getFactorizedForm());
            }
        } catch (Exception e) {
            System.err.println("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("PATH NOT COMPUTED");
        }

        logger.info("End of MazeRunner");
    }

    private static Path solveMaze(String method, Maze maze) {
        try {
            MazeSolver solver = MazeSolverFactory.createSolver(method);
            logger.info(method + " algorithm chosen.");
            return solver.solve(maze);
        } catch (Exception e) {
            System.err.println("Maze solving method '" + method + "' failed. Reason: " + e.getMessage());
            logger.error("Maze solving method '" + method + "' failed. Reason: " + e.getMessage());
            return new Path();
        }
    }

    private static Options getParserOptions() {
        Options options = new Options();

        Option fileOption = new Option("i", true, "File that contains maze");
        fileOption.setRequired(true);
        options.addOption(fileOption);

        options.addOption(new Option("p", true, "Path to be verified in maze"));
        options.addOption(new Option("method", true, "Specify which path computation algorithm will be used"));

        return options;
    }
}
