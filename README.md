Rubik's Cube Solver (Java)
==========================

.exe to solve Rubik's cube coming soon here !

### How to make a Java app into an .exe

##### 1. Create a Runnable JAR File

In VSC, run your java app. The console will tell you where is the folder where it executes. Open it (it should contains .class files).

In this folder, create a file "manifest.mf" containing the text "Main-Class: YourMainClass". Then run in cmd "jar cfm MyApplication.jar manifest.mf *.class

##### 2. Convert JAR to EXE

Use Launch4J for ease of use, jpackage for native modern approach

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