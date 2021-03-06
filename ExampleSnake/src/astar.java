 
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
 
class AStar {
    private final List<Node> open;
    private final List<Node> closed;
    private final List<Node> path;
    private final int[][] maze;
    private final boolean[][] maze2;
    private Node now;
    private final int xstart;
    private final int ystart;
    private int xend, yend;
    private final boolean diag;
 
    // Node class for convienience
   
    AStar(int[][] maze, int xstart, int ystart, boolean diag) {
        this.maze2 = new boolean[50][50];
		this.open = new ArrayList<>();
        this.closed = new ArrayList<>();
        this.path = new ArrayList<>();
        this.maze = maze;
        this.now = new Node(null, xstart, ystart, 0, 0);
        this.xstart = xstart;
        this.ystart = ystart;
        this.diag = diag;
        
    }
   
    /*
    ** Finds path to xend/yend or returns null
    **
    ** @param (int) xend coordinates of the target position
    ** @param (int) yend
    ** @return (List<Node> | null) the path
    */
    public List<Node> findPathTo(int xend, int yend) {
    	int len = 0;
        this.xend = xend;
        this.yend = yend;
        this.closed.add(this.now);
        addNeigborsToOpenList();
        int counter = 0;
        while (this.now.x != this.xend || this.now.y != this.yend) {
            if (this.open.isEmpty()) { // Nothing to examine
                return null;
            }
            
            if (counter>2500) { // Nothing to examine
                return null;
            }
            
            this.now = this.open.get(0); // get first node (lowest f score)
            this.open.remove(0); // remove it
            this.closed.add(this.now); // and add to the closed
            this.maze2[this.now.x][this.now.y] = true;
            addNeigborsToOpenList();
            counter++;
        }
        this.path.add(0, this.now);
        while (this.now.x != this.xstart || this.now.y != this.ystart) {
       // if(len<50) {
	            this.now = this.now.parent;           
	            this.path.add(0, this.now);
          //  }else {
          //  	return null;}
            //len++;
            
            }
         //  if(len<50) {
        return this.path;
//           }else {
//        	   return null;
//           }
    }
    /*
    ** Looks in a given List<> for a node
    **
    ** @return (bool) NeightborInListFound
    */
    private static boolean findNeighborInList(List<Node> array, Node node) {
        return array.stream().anyMatch((n) -> (n.x == node.x && n.y == node.y));
    }
    /*
    ** Calulate distance between this.now and xend/yend
    **
    ** @return (int) distance
    */
    private double distance(int dx, int dy) {
        if (this.diag) { // if diagonal movement is alloweed
            return Math.hypot(this.now.x + dx - this.xend, this.now.y + dy - this.yend); // return hypothenuse
        } else {
            return Math.abs(this.now.x + dx - this.xend) + Math.abs(this.now.y + dy - this.yend); // else return "Manhattan distance"
        }
    }
    private void addNeigborsToOpenList() {
        Node node;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (!this.diag && x != 0 && y != 0) {
                    continue; // skip if diagonal movement is not allowed
                }
                node = new Node(this.now, this.now.x + x, this.now.y + y, this.now.g, this.distance(x, y));
//                if ((x != 0 || y != 0) // not this.now
//                    && this.now.x + x >= 0 && this.now.x + x < this.maze[0].length // check maze boundaries
//                    && this.now.y + y >= 0 && this.now.y + y < this.maze.length
//                    && this.maze[this.now.y + y][this.now.x + x] != 1 // check if square is walkable
//                    && !findNeighborInList(this.open, node)  && !findNeighborInList(this.closed, node)) { // if not already done
//                        node.g = node.parent.g + 1.; // Horizontal/vertical cost = 1.0
//                        node.g += maze[this.now.y + y][this.now.x + x]; // add movement cost for this square                     
//                        this.open.add(node);
//                }
              if (x != 0 || y != 0){// not this.now
              	  if( this.now.x + x >= 0 && this.now.x + x < this.maze[0].length) {// check maze boundaries
              		 if(this.now.y + y >= 0 && this.now.y + y < this.maze.length) {
              	              if(this.maze[this.now.y + y][this.now.x + x] != 1 ) {// check if square is walkable
              	            	  if(!findNeighborInList(this.open, node) ) {
              	            		  if( !findNeighborInList(this.closed, node)) { // if not already done
		              	  	                  node.g = node.parent.g + 1.; // Horizontal/vertical cost = 1.0
		                	                  node.g += maze[this.now.y + y][this.now.x + x]; // add movement cost for this square                     
		                	                  this.open.add(node);
              	            		  }
              	            	  }
                
              	              }
              	              
              		 	}
              		 }
             
              	}
            }
        }
        Collections.sort(this.open);
    }
    
}