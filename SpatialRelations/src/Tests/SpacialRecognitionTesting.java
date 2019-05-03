package Tests;

import java.util.ArrayList;
import java.util.List;

import MetadataCompiler.MetadataCompiler;
import SpatialRelationGenerator.Block;
import SpatialRelationGenerator.Grapher;

public class SpacialRecognitionTesting {

	public static void main(String[] args){
		Block origin = new Block(0,0,0,0, "origin");
		
		Block front = new Block(1,0,0,10, "front");
		
		Block behind = new Block(2, 0, 0, -5, "behind");
		
		Block right = new Block(3, 5, 0, 1, "right");
		
		Block left = new Block(4, -7, 0, -1, "left");
		
		Block testing = new Block(5, 5, 0, 13, "testing");
		
		List<Block> blocks  = new ArrayList<>();
		
		blocks.add(origin);
		blocks.add(front);
		blocks.add(behind);
		blocks.add(right);
		blocks.add(left);
		blocks.add(testing);
		
		Grapher grapher = new Grapher(blocks);
		
		grapher.makeGraph();
		
		
		//using a list of string(key words of relations) find the block the user wants
		
		
		MetadataCompiler compiler = new MetadataCompiler();
		
		
	}
}
