package testSupport;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphVisualization extends JPanel {
	public static Set<Object> values = new HashSet<Object>();
	public static ArrayList<Object> expectPattern = new ArrayList<Object>();
    public GraphVisualization() {
    	
    }


    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Debugging tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 810); // Adjust height dynamically
        frame.add(new GraphVisualization(values));
        frame.setVisible(true);
    }
    
    private static class Node {
        Object value;
        int x, y;

        Node(Object value, int x, int y) {
            this.value = value;
            this.x = x;
            this.y = y;
        }
    }

    private final List<Node> nodes = new ArrayList<>();
    private final List<int[]> edges = new ArrayList<>();

    public GraphVisualization(Set<Object> values) {
   
        // Parameters for layout
        int startX = 60;        // Starting x position
        int startY = 60;        // Starting y position
        int verticalSpacing = 120; // Vertical spacing between nodes 
        int horizontalStep = 120;  // Horizontal movement for each step
        int rightLimit = 1500;    // Rightmost limit
        int leftLimit = 0;      // Leftmost limit

        boolean movingRight = true; // Direction flag
        int currentX = startX;
        int currentY = startY;
        // Create nodes based on input array
        for (Object value : values) {
            nodes.add(new Node(value, currentX, currentY));

            // Update position for the next node
            if (movingRight) {
                currentX += horizontalStep;
                if (currentX >= rightLimit) { // Turn down and switch to left
                    currentX -= horizontalStep;
                    currentY += verticalSpacing ; // Move down extra before turning
                    movingRight = false;
                }
            } else {
                currentX -= horizontalStep;
                if (currentX <= leftLimit) { // Turn down and switch to right
                    currentX += horizontalStep;
                    currentY += verticalSpacing ; // Move down extra before turning
                    movingRight = true;
                }
            }

        }

        // Create edges between consecutive nodes
        for (int i = 0; i < nodes.size() - 1; i++) {
            edges.add(new int[]{i, i + 1});
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Anti-aliasing for smoother lines and circles
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int index = 0;
        // Draw edges
        for (int[] edge : edges) {
            Node from = nodes.get(edge[0]);
            Node to = nodes.get(edge[1]);
            /*
             ToDo : Implementing the code to comparing actual value need to be
                          */
            if (GraphVisualization.expectPattern.size() >index && !nodes.get(index).value.equals(GraphVisualization.expectPattern.get(index))) {
            	g2d.setColor(Color.red);
            }
            g2d.drawLine(from.x, from.y+8, to.x, to.y+8);
            g2d.setColor(Color.black);
            index++;
        }
        
        // Draw nodes
        for (Node node : nodes) {
            g2d.setColor(Color.BLUE);
            g2d.fillOval(node.x - 20, node.y - 20, 60, 60); // Draw node as a circle
            g2d.setColor(Color.WHITE);
            g2d.drawString(String.valueOf(node.value), node.x -10, node.y + 14); // Draw value in the circle
        }
    }


   
}

