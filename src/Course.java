import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Course extends DFSNode implements Comparable<Course>{ 

	public int id;
	public String title;
	public boolean compSysRequirement;
	public boolean infoSysRequirement;
	public boolean minorRequirement;
	
	public List<Course> dependencies = new ArrayList<Course>();
	
	public String toString() {
		return this.id + ": " + this.title + 
				" (requires: " + this.dependenciesToString() + ")" + 
				"\n      * computer systems=" + this.compSysRequirement + 
				"\n      * information systems=" + this.infoSysRequirement + 
				"\n      * minor=" + this.minorRequirement + "\n";
	}
	
	public String dependenciesToString() {
		if (this.dependencies.size() == 0) {
			return "No prereqs";
		}
		StringJoiner sj = new StringJoiner(", ");
		for (Course dependency : this.dependencies) {
			sj.add(((Integer)dependency.id).toString());
		}
		return sj.toString();
	}
	
	public int compareTo(Course b) {
		
		if (this.id == b.id) {
			return 0;
		} else if (this.id > b.id) {
			return 1;
		} else {
			return -1;
		}
	}
	
	// converts a JSON Object (from the JSON file) into a Java Course object
	public Course(JSONObject obj) {
		obj = (JSONObject)obj.get("data");
		Long id = (Long)obj.get("id");
		this.id = id.intValue();
		this.title = (String)obj.get("title");
		Object sys = (Object)obj.get("comp_sys_requirement");
		Object info = (Object)obj.get("info_sys_requirement");
		Object minor = (Object)obj.get("minor_requirement");
		this.compSysRequirement = (sys != null) ? (boolean)sys : false;
		this.infoSysRequirement = (info != null) ? (boolean)info : false;
		this.minorRequirement = (minor != null) ? (boolean)minor : false;
	}
	
	
	// converts a Java Course object to a JSON node object (to write to a file)
	@SuppressWarnings("unchecked")
	public JSONObject toNodeJSON() {
		JSONObject entry = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("id", this.id);
		data.put("title", this.title);
		data.put("comp_sys_requirement", this.compSysRequirement);
		data.put("info_sys_requirement", this.infoSysRequirement);
		data.put("minor_requirement", this.minorRequirement);
		entry.put("data", data);
		return entry;
	}
	
	// converts a Java Course object to a list of JSON edge objects (to write to a file)
	@SuppressWarnings("unchecked")
	public JSONArray toEdgesJSON() {
		JSONArray edgeList = new JSONArray();
		for (Course course : this.dependencies) {
			JSONObject entry = new JSONObject();
			JSONObject data = new JSONObject();
			data.put("target", this.id);
			data.put("source", course.id);
			entry.put("data", data);
			edgeList.add(entry);
		}
		return edgeList;
	}

}
