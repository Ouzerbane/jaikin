🌀 Chaikin’s Algorithm – Step-by-Step Animation (Java Swing)
🎯 Objective

The goal of this project is to implement Chaikin’s algorithm and create a step-by-step animation of the curve smoothing process using a graphical canvas built with Java Swing.

The program allows users to draw control points, visualize the algorithm’s iterations, and see how the initial polyline becomes progressively smoother after each Chaikin iteration.

🧩 Project Description

This application provides an interactive graphical environment where users can:

Draw one or more control points with the mouse.

Animate the smoothing process step-by-step (up to 7 iterations).

Visualize each stage of the Chaikin algorithm in real time.

The canvas updates automatically at 60 frames per second (FPS) using a javax.swing.Timer.

🖱️ Instructions
Action	Description
🖱️ Left Mouse Click	Add a control point at the clicked position.
⏎ Enter Key	Start or restart the Chaikin smoothing animation (if at least 2 points exist).
␣ Space Bar	Clear all control points and restart the drawing.
⎋ Escape Key	Exit the program.
⚙️ Implementation Details
1. Canvas Setup

A custom JPanel (ChaikinPanel) is used as the main drawing surface.
It handles:

Rendering with paintComponent(Graphics g).

Keyboard and mouse input (KeyListener, MouseListener).

Real-time animation with Timer.

2. Mouse Input

When the user left-clicks on the panel, a new control point (Vec2(x, y)) is created and added to the list positions.
Each point is represented visually by a small white circle.

3. Animation Control

When the Enter key is pressed:

If there are at least two control points, the animation begins.

The algorithm is applied step-by-step until the 7th iteration.

After the 7th iteration, the animation resets automatically.

If there are:

0 points: Nothing happens.

1 point: The point is drawn alone.

2 points: A straight line is drawn between them.

4. Chaikin’s Algorithm

For each iteration:

The algorithm replaces every segment [p0, p1] by two new points:

Q = 0.75*p0 + 0.25*p1
R = 0.25*p0 + 0.75*p1


The new set of points becomes smoother with each iteration.

After 7 iterations, the system resets to the original control points.

5. Timer and Repainting

The animation is updated by a Timer running at 60 FPS.

Every tick calls actionPerformed(), which triggers repaint().

Inside paintComponent(Graphics g), the algorithm:

Draws the control points.

Draws connecting lines.

Updates and displays the current animation step.

🧠 Bonus Features (Optional)

Clear the canvas: Press the Space Bar to remove all control points.

Draggable points (optional): Implement real-time movement of control points by dragging with the mouse.

🖥️ Technologies Used

Language: Java (JDK 17+)

GUI Library: Swing (AWT)

Frame Rate: 60 FPS (javax.swing.Timer)

🚀 How to Run

Ensure you have Java 17+ installed.

Compile and run the project:

javac ChaikinPanel.java Main.java
java Main


(replace Main.java with the name of your main file if different)

Click to add points → Press Enter → Enjoy the animation!

🧾 Example Behavior
With 1 point:

Only one dot appears.

With 2 points:

A straight line is drawn between them.

With 3+ points:

A Chaikin curve starts to smooth step-by-step for 7 iterations, then restarts.

🧰 Files
File	Description
ChaikinPanel.java	Main drawing panel – handles rendering, input, and animation.
Vec2.java	Small vector class for representing (x, y) coordinates.
Main.java	Entry point (creates the window and starts the program).