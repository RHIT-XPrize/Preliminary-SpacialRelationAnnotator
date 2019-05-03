package SpatialRelationGenerator;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Main {
	
	
	
	

	public static void main(String[] args) throws JsonProcessingException{
		
		Block origin = new Block(0,0,0,0, "origin");
		
		Block front = new Block(1,0,0,10, "front");
		
		Block behind = new Block(2,0, 0, -5, "behind");
		
		Block right = new Block(3,5, 0, 1, "right");
		
		Block left = new Block(4,-7, 0, -1, "left");
		
		Block farAway = new Block(5, -1000, 0, 1, "faraway");
		
		List<Block> blocks  = new ArrayList<>();
		
		blocks.add(origin);
		blocks.add(front);
		blocks.add(behind);
		blocks.add(right);
		blocks.add(left);
		blocks.add(farAway);
		
		Grapher grapher = new Grapher(blocks);
		
		grapher.makeGraph();
		
		for(Block b: blocks){
			System.out.println(b);
		}
		
		System.out.println("\n\n\nYEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEET\n\n\n\n");
		
		System.out.println(grapher.generateAnnotationResponse());
		
				
	}
}
