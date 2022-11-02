import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CourseInfoDriver {

	private Graph graph;
	BufferedReader reader;

	public CourseInfoDriver() {
		this.graph = new Graph();
		this.reader = new BufferedReader(new InputStreamReader(System.in));
	}

	public static void printDashes(int numDashes) {
		for(int i = 0; i < numDashes; i++) {
			System.out.print("-");
		}
		System.out.println();
	}



	public static void showMenu() {
		System.out.println();
		printDashes(50);
		System.out.println("MENU (feel free to modify this)");
		printDashes(50);
		System.out.println("1. Print all courses sorted by course number");
		System.out.println("2. Print all of the courses sorted topologically.");
		System.out.println("3. Print all of the courses filtered by minor, computer sys, or info sys");
		System.out.println("4. Print the chain of prerequisites for any course.");
		System.out.println("5. Output the chain of prerequisites for any course as subgraph");
		System.out.println("6. Print list of your eligible courses");
		System.out.println("Q. Quit program");
		printDashes(50);
		System.out.println("");
	}

	private static int getCourseNumViaPrompt(BufferedReader reader) {
		while (true) {
			System.out.print("\n\nEnter a course ID and view the prereqs (e.g., 333). Enter M for main menu. ");

			try {
				String courseStr = reader.readLine().trim();
				int courseNum = Integer.parseInt(courseStr);
				return courseNum;
			} catch(Exception e) {
				// e.printStackTrace();
				System.out.println("Invalid number. Please try again.");
			}
		}
	}

	public static void option1(Graph graph) {
		System.out.println("\n\n1. TODO: Logic to print all of the courses sorted by course number\n");
		// outputting but not in the right order:
		graph.print();
	}

	public static void option2(Graph graph) {
		System.out.println("\n\n2. TODO: Logic to print all of the courses sorted topologically\n");
		DFS dfs = new DFS(graph);
		dfs.printGraphTopologically();
	}

	public static void option3() {
		System.out.println("\n\n3. TODO: Logic to print all of the courses filtered by minor, computer sys, or info sys\n");
	}

	public static void option4(CourseInfoDriver driver) {
		System.out.println("\n\n4. TODO: Logic to print the chain of prerequisites for any course.\n");
		int courseNum = getCourseNumViaPrompt(driver.reader);
		System.out.println("You selected: " + courseNum);
		Course course = driver.graph.get(courseNum);
		DFS dfs = new DFS(driver.graph);
		dfs.printPrequisites(course);
	}

	public static void option5(CourseInfoDriver searcher) {
		System.out.println("\n\n5. TODO: Output the chain of prerequisites for any course as subgraph to course-graph.js\n");
//		int courseNum = getCourseNumViaPrompt(searcher.reader);
//		System.out.println("You selected: " + courseNum); 
		
		searcher.graph.save();
	}

	public static void option6() {
		System.out.println("\n\n6. TODO: Print list of your eligible courses");
		System.out.println("For this one, prompt the user for a few course numbers and then check each course node's dependency tree.\n");
	}

	public static void main(String[] args) {

		CourseInfoDriver searcher = new CourseInfoDriver();
		while (true) {
			showMenu();

			System.out.print("\n\nPlease select an option (1-6) or Q to quit: ");

			try {
				String numStr = searcher.reader.readLine().trim();
				if (numStr.toUpperCase().equals("Q")) {
					System.out.println("Program ended");
					return;
				}

				int num = Integer.parseInt(numStr);

				if (num == 1) {
					option1(searcher.graph);
				} else if (num == 2) {
					option2(searcher.graph);
				} else if (num == 3) {
					option3();
				} else if (num == 4) {
					option4(searcher);
				} else if (num == 5) {
					option5(searcher);
				} else if (num == 6) {
					option6();
				}

				// code to pause loop:
				System.out.println("\n\nPress any key to continue.");
				searcher.reader.readLine();

			} catch(Exception e) {
				// e.printStackTrace();
				System.out.println("Invalid option. Please try again.");
			}
		}

	}

}