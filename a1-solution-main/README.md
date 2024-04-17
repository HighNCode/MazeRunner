# Assignment A3 - Maze Runner Take Two

* **Student**: [FIRSTNAME LASTNAME](MACID@mcmaster.ca)
* **Program**: B. Eng. In Software Engineering
* **Course code**: SWE 2AA4
* **Course Title**: Software Design - Introduction to Software Development
* Term: *Level II - Winter 2024*

## Business Logic Specification

This program explores a maze using the basic as well as the most optimal algorithm Dijkstra, finding a path from an entry point to an exit one. After that it also does benchmarking and comparisons among multiple algorithms.

- The maze is stored in a text file, with `#` representing walls and `␣` (_empty space_) representing passages.
- You’ll find examples of such mazes in the [`examples`](./examples) directory.
    - You can also use the [Maze Generator](https://github.com/ace-lectures/maze-gen) to generate others.
- The Maze is surrounded by walls on its four borders, except for its entry/exit points.
    - Entry and exit points are always located on the East and West border.
    - The maze is not directed. As such, exit and entry can be interchanged.
- At the beginning of the exploration, we're located on the entry tile, facing the opposite side (e.g., if entering by
  the eastern entry, you're facing West).
- The program generates a sequence of instructions to reach the opposite exit (i.e., a "path"):
    - `F` means 'move forward' according to your current direction
    - `R` means 'turn right' (does not move, just change direction), and `L` means ‘turn left’.
- A canonical path contains only `F`, `R` and `L` symbols
- A factorized path squashes together similar instructions (i.e., `FFF` = `3F`, `LL` = `2L`).
- Spaces are ignored in the instruction sequence (only for readability: `FFLFF` = `FF L FF`)
- The program takes as input a maze and print the path on the standard output.
    - For this assignment, the path does not have to be the shortest one.
- The program can take a path as input and verify if it's a legit one.

## How to run this software?

To build the program, simply package it with Maven:

```
imran@chowdi13 A3 % mvn -q clean package 
```

### Provided version (starter code)

The starter code assumes the maze file name is the first argument.

```
imran@chowdi13 A3 % java -jar ca.master.se2aa4.mazerunner.Main -i ./examples/small.maz.txt

** Starting Maze Runner
**** Reading the maze from file ./examples/small.maz.txt
WALL WALL WALL WALL WALL WALL WALL WALL WALL WALL WALL 
WALL PASS PASS PASS PASS PASS PASS PASS PASS PASS WALL 
WALL WALL WALL PASS WALL WALL WALL PASS WALL WALL WALL 
WALL PASS PASS PASS PASS PASS WALL PASS PASS PASS WALL 
WALL PASS WALL PASS WALL WALL WALL WALL WALL PASS WALL 
WALL PASS WALL PASS PASS PASS PASS PASS WALL PASS PASS 
WALL WALL WALL PASS WALL PASS WALL WALL WALL WALL WALL 
WALL PASS PASS PASS WALL PASS PASS PASS PASS PASS WALL 
PASS PASS WALL PASS WALL PASS WALL WALL WALL PASS WALL 
WALL PASS WALL PASS WALL PASS WALL PASS PASS PASS WALL 
WALL WALL WALL WALL WALL WALL WALL WALL WALL WALL WALL 
**** Computing path
PATH NOT COMPUTED
** End of MazeRunner
```

When called on a non-existing file. it prints an error message

```
imran@chowdi13 A3 % java -jar ca.master.se2aa4.mazerunner.Main -i ./examples/small.maz.txtd
** Starting Maze Runner
**** Reading the maze from file ./examples/small.maz.txtd
/!\ An error has occured /!\
**** Computing path
PATH NOT COMPUTED
** End of MazeRunner
```

#### Command line arguments

The delivered program uses the following flags:

- `-i MAZE_FILE`: specifies the filename to be used;
- `-p PATH_SEQUENCE`: activates the path verification mode to validate that PATH_SEQUENCE is correct for the maze
- `-method {tremaux, righthand, dijkstra}`: specifies which path computation method to use. (default is right hand)
- `-baseline {tremaux, righthand, dijkstra}`: used to specify XXX as the comparison baseline.
