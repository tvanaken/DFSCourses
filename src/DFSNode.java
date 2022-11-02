
public abstract class DFSNode {

	public enum Colors {WHITE, GRAY, BLACK};
	public Colors color = Colors.WHITE;
	public Course parent;
	public int d;
	public int f;
}
