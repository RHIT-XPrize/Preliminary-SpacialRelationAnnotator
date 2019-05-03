package SpatialRelationGenerator;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.threed.SphericalCoordinates;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Grapher {
	
	final double MAX_DISTANCE = 10.0;
	
	List<Block> blocks;
	
	public Grapher (List<Block> blocks){
		this.blocks = blocks;
	}
	
	public void makeGraph (){
		for(int i = 0; i < blocks.size(); i++){
			Block current = blocks.get(i);
			for(int j = i + 1; j < blocks.size(); j++){
				checkRelation(current, blocks.get(j));
			}
		}
	}
	
	public String generateAnnotationResponse() throws JsonProcessingException{
		String output = null;
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode jsonData = mapper.createArrayNode();
		for(Block b: blocks){
			ObjectNode block = mapper.createObjectNode();
			block.put("Id", b.id);
			block.put("Name" , b.name);
			block.put("X", b.x);
			block.put("Y", b.y);
			block.put("Z", b.z);
			addListsToObjectNode(block, b, mapper);
			jsonData.add(block);
		}

		output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonData);
		return output;
	}
	
	public void addListsToObjectNode(ObjectNode block, Block b, ObjectMapper mapper){
		ArrayNode left = mapper.createArrayNode();
		ArrayNode right = mapper.createArrayNode();
		ArrayNode front = mapper.createArrayNode();
		ArrayNode behind = mapper.createArrayNode();
		
		for(BlockWrapper innerBlock: b.behind){
			ObjectNode BID = mapper.createObjectNode();
			BID.put("id", innerBlock.block.id);
			behind.add(BID);
		}
		for(BlockWrapper innerBlock: b.front){
			ObjectNode BID = mapper.createObjectNode();
			BID.put("id", innerBlock.block.id);
			front.add(BID);	
		}
		for(BlockWrapper innerBlock: b.left){
			ObjectNode BID = mapper.createObjectNode();
			BID.put("id", innerBlock.block.id);
			left.add(BID);
		}
		for(BlockWrapper innerBlock: b.right){
			ObjectNode BID = mapper.createObjectNode();
			BID.put("id", innerBlock.block.id);
			right.add(BID);
		}
		
		block.putPOJO("left", left);
		block.putPOJO("right", right);
		block.putPOJO("front", front);
		block.putPOJO("behind", behind);
	}

	
	private void checkRelation(Block current, Block other) {
		Vector3D vector = new Vector3D(other.x-current.x, other.y-current.y, other.z - current.z);
		SphericalCoordinates po = new SphericalCoordinates(vector);
		
		
		//PolarCoordinates po = new PolarCoordinates(current.x, current.y, current.z, other.x, other.y, other.z);
		System.out.println("Current: " + current.name + " other: " + other.name+ "\n\tphi: " + po.getPhi() + " theta: " + po.getTheta());
		
		double distance = Math.abs(po.getR());
		BlockWrapper otherWrapper = new BlockWrapper(other, distance);
		BlockWrapper currentWrapper = new BlockWrapper(current, distance);
		
		if (distance > MAX_DISTANCE){
			System.out.println("out of range");
		}else if (po.getTheta() < Math.PI/2) {
			System.out.println("Hey");
			if(po.getPhi() < Math.PI/4){
				current.front.add(otherWrapper);
				other.behind.add(currentWrapper);
			}else if (po.getPhi() <= (3.0*Math.PI)/4.0){
				current.right.add(otherWrapper);
				other.left.add(currentWrapper);
			}else{
				current.behind.add(otherWrapper);
				other.front.add(currentWrapper);
			}
		}else{
			System.out.println("Hello");
			if(po.getPhi() < Math.PI/4){
				current.behind.add(otherWrapper);
				other.front.add(currentWrapper);
			}else if (po.getPhi() <= (3.0*Math.PI)/4.0){
				current.left.add(otherWrapper);
				other.right.add(currentWrapper);
			}else{
				current.front.add(otherWrapper);
				other.behind.add(currentWrapper);
			}
		}
	}

}
