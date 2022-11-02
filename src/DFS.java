import java.util.ArrayList;
import java.util.List;

public class DFS {

	private int time = 0;
	private Graph graph;
	
	public DFS(Graph graph) {
		
		this.graph = graph;
	}
	
	//I give you a source node, and you print a list of prereqs to the screen
	//Use a DFS on the source node
	public void printPrequisites(Course source) {
		
	}
	
//	DFS(G) // G is a graph
//	for each vertex u in G // initialize vertices
//	   u.color = WHITE
//	   u.p = NULL
//	time = 0 // initialize time stamp GLOBAL variable (see p. 604)
//	for each vertex u in G // each iteration makes a tree of 1 component
//	   if u.color == WHITE
//	      DFS-VISIT(G, u)
	
	public void printGraphTopologically() {
		
		List<Course> arr = new ArrayList<Course>();
		
		//Loop through each node of the graph
		//pre-processing step: all course colors to while
		for(Course course : this.graph.getCourses()) {
			course.color = DFSNode.Colors.WHITE;
			course.parent = null;
		}
		this.time = 0;
		
		for(Course course : this.graph.getCourses()) {
			if(course.color == DFSNode.Colors.WHITE) {
				dfsVisit(course, arr);
				System.out.println(course);
			}
		}
	}
	
//	DFS-VISIT(G, u) // explore vertex u’s component. make a depth-first tree
//	time++
//	u.d = time // u is now being visited. first timestamp
//	u.color = GRAY
//	for each neighbor v of u
//	   if v.color == WHITE // only visit white neighbors u of v
//	      v.p = u // u is now the parent of v in the tree
//	      DFS-VISIT(G, v) // recursion: immediately visit newly found v
//	u.color = BLACK // loop done. all neighbors of u visited. backtrack.
//	time++
//	u.f = time
	
	public void dfsVisit(Course course, List<Course> prequisites) {
		
		this.time++;
		course.d = time;
		course.color = DFSNode.Colors.GRAY;
		
		for (Course preReq : course.dependencies) {
			if (preReq.color == DFSNode.Colors.WHITE) {
				preReq.parent = course;
				dfsVisit(preReq, prequisites);
			}
		}
		course.color = DFSNode.Colors.BLACK;
		this.time++;
		course.f = this.time;
		prequisites.add(course);
	}
	
	public void writeNodeDepenndancyTreeToJavaScript(Course course) {
		//pirate code from graph's save method, but only output the course sub-graph (dependency chain)
	}
	
	public void printEligibleCourses(int coursesTaken) {
		
	}
}
