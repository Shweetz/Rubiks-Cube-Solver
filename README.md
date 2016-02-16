Rubik's Cube Solver
==============

This is a <b>Java program that solves Rubik's Cubes</b>. 

*Work In Progress* You can see the Rubik's Cube and flip it around but can't do anything else at the moment.

### More information :

##### src/main 
Contains the main Java classes :

- <b>View</b> contains main class, the view and the controller (including the keyboard commands).
- <b>Model</b> refreshes the view when the user performs an action. Also contains the order of the steps to solve the Rubik's Cube in its method <b>solve</b>.
- <b>Solution</b> contains the information to solve the Rubik's cube.

##### src/solve
Contains the Java classes to solve the Rubik's Cube : <b>SolveX</b> solves step X (oh really ?).

##### src/resource
Contains the <b>fancy 3D cube model</b> that was submitted by 3dregenerator : http://tf3dm.com/3d-model/rubik39s-cube-79189.html

##### Rubik's Cube Database
Is just here for testing, contains .txt files of examples of scrambled Rubik's Cubes.