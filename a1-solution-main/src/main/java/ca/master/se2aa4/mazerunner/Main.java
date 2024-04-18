package ca.master.se2aa4.mazerunner;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        CommandLineParser parser = new DefaultParser();
        long baselineTime = 0;
        long methodTime=0;
        CommandLine cmd = null;
        try {
            cmd = parser.parse(getParserOptions(), args);
            String filePath = cmd.getOptionValue('i');
            MazeReader mazeReader = new MazeReader();
            MazeData mazeData = mazeReader.readMazeFromFile(filePath);
            Maze maze = Maze.createMaze(mazeData.getMazeStructure(), mazeData.getStart(), mazeData.getEnd());

            if (cmd.hasOption("baseline")) {
                String baselineMethod = cmd.getOptionValue("baseline");
                baselineTime = measureTime(() -> solveMaze(baselineMethod, maze));
                System.out.println("Baseline time: " + baselineTime + " ms");
            }
            
            if (cmd.hasOption("p")) {
                logger.info("Validating path");
                Path path = new Path(cmd.getOptionValue("p"));
                if (maze.validatePath(path)) {
                    System.out.println("correct path");
                } else {
                    System.out.println("incorrect path");
                }
            }

            else {
                String method = cmd.getOptionValue("method");
                Path path = solveMaze(method, maze);
                System.out.println(path.getFactorizedForm());
                
                methodTime = measureTime(() -> solveMaze(method, maze));
                System.out.println("Method time: " + methodTime + " ms");

                if (cmd.hasOption("baseline")) {
                    double improvement = (double) methodTime / baselineTime;
                    System.out.printf("Improvement on the path as a speedup: %.5f%n", improvement);
                }
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
    
    private static long measureTime(Runnable task) {
        long startTime = System.nanoTime();
        System.out.println("start time: " + startTime + " ms");
        task.run();
        long endTime = System.nanoTime();
        System.out.println("end time: " + endTime + " ms");
        return TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);
    }

    private static Options getParserOptions() {
        Options options = new Options();

        Option fileOption = new Option("i", true, "File that contains maze");
        fileOption.setRequired(true);
        options.addOption(fileOption);

        options.addOption(new Option("p", true, "Path to be verified in maze"));
        options.addOption(new Option("method", true, "Specify which path computation algorithm will be used"));
        
        options.addOption(new Option("baseline", true, "Comparison baseline method"));

        return options;
    }
}
