import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

public class Graph {
	public Map<Integer, Course> courseLookup;
	
	public Graph() {
		// note: using a dictionary here so that the Graph can easily access
		// individual nodes on demand.
		this.courseLookup = new HashMap<Integer, Course>();
		this.load();
	}
	
	public void addCourse(Course course) {
		// only add to the graph if the page is new:
		if (this.get(course.id) == null) {
			this.courseLookup.put(course.id, course);
    		}
	}
	
	public List<Course> getCourses() {
		
		List<Course> courses = new ArrayList<Course>();
		courses.addAll(this.courseLookup.values());
		courses.sort(null);
		return courses;
	}
	
	public Course get(int id) {
		return this.courseLookup.get(id);
	}
	
	public int size() {
		return this.courseLookup.size();
	}
	

	public void print() {
		// print nodes:
		List<Course> courses = new ArrayList<Course>();
		courses.addAll(this.courseLookup.values());
		courses.sort(null);
    	for (Course course : courses) {
    		System.out.println(course);
    	}
    }
	
	
	private String getInputFilePath() {
		String dir = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        return dir + separator + "src/courses.json";
	}
	
	private String getOutputFilePath() {
		String dir = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        return dir + separator + "src/visualizer/course-graph.js";
	}
	
	// Loads each node / edge of the graph from a JSON file:
	public void load() {
		// loads the graph from the JSON file (if it exists):
		String filePath = this.getInputFilePath();
		File tempFile = new File(filePath);
		if (!tempFile.exists()) {
			System.out.println("Can't find courses.json file");
			return;
		}
		JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(filePath)) {

        		JSONObject data = (JSONObject)parser.parse(reader);
        		
        		JSONArray nodes = (JSONArray)data.get("nodes");
        		this.loadNodes(nodes);
        		
        		JSONArray edges = (JSONArray)data.get("edges");
        		this.loadEdges(edges);
	        

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadNodes(JSONArray nodes) {
		for (Object node : nodes) {
        		this.addCourse(new Course((JSONObject)node));
		}
	}
	
	private void loadEdges(JSONArray edges) throws Exception {
		for (Object edge : edges) {
			// convert generic data object to a JSON Object
			JSONObject obj = (JSONObject)((JSONObject)edge).get("data");
			
			// get the source and target ids:
			int targetId = ((Long)obj.get("target")).intValue();
			int sourceId = ((Long)obj.get("source")).intValue();
			
			// get nodes from the Graph's dictionary:
			Course target = this.get(targetId);
			Course source = this.get(sourceId);
			if (target == null || source == null) {
				throw new Exception("Invalid edge!");
			}
			
			// Add dependency to the node's adjacency list:
			target.dependencies.add(source);
		}
	}
	
	
	
	// Writes the graph to a JavaScript file (so it can be visualized):
	@SuppressWarnings("unchecked")
	public void save() {
		JSONObject object = new JSONObject();
		JSONArray nodes = new JSONArray();
		JSONArray edges = new JSONArray();
		for (int key: this.courseLookup.keySet()) {
			Course course = this.courseLookup.get(key);
			nodes.add(course.toNodeJSON());
			edges.addAll(course.toEdgesJSON());
    		}
		object.put("nodes", nodes);
		object.put("edges", edges);
		
        try (FileWriter file = new FileWriter(this.getOutputFilePath())) {
            file.write("const graphData = " + object.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	

}
