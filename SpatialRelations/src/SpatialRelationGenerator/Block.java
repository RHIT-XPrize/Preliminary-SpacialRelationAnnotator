package SpatialRelationGenerator;
import java.util.PriorityQueue;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class Block {
	
	int id;	
	String name;
	double x;
	double y;
	double z;
	

	public PriorityQueue<BlockWrapper> left;
	public PriorityQueue<BlockWrapper> right;
	public PriorityQueue<BlockWrapper> front;
	public PriorityQueue<BlockWrapper> behind;
	
	
	public Block(int id, double x1, double y1, double z1, String name){
		this.id = id;
		this.x = x1;
		this.y = y1;
		this.z = z1;
		this.name = name;
		
		this.left = new PriorityQueue<>();
		this.right = new PriorityQueue<>();
		this.front = new PriorityQueue<>();
		this.behind = new PriorityQueue<>();
	}
	
	public String toString(){
		StringBuilder out = new StringBuilder();
		out.append("This is the block " + name + "\n");
		out.append("\tLeft: "+ left);
		out.append("\tRight: " + right);
		out.append("\tFront: " + front);
		out.append("\tBehind: " + behind);
		
		return out.toString();
	}
}
