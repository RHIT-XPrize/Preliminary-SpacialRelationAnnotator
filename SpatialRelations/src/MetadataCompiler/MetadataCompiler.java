package MetadataCompiler;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import SpatialRelationGenerator.Block;
import SpatialRelationGenerator.BlockWrapper;
public class MetadataCompiler {
	
	public Block chooseBlock(List<String> relationKeywords, List<Integer> degrees, Block startBlock){
		Block block = null;
		
		//make queue with initial starting block
		Queue<BlockWrapper> initialBlockQueue = new PriorityQueue<BlockWrapper>();
		initialBlockQueue.add(new BlockWrapper(startBlock,0));
		
		//stack to track all that has been seen so far
		Stack<Queue<BlockWrapper>> stackOfSeenQueues = new Stack();
		stackOfSeenQueues.push(initialBlockQueue);
		
//		for(int i = 0; i < relationKeywords.size(); i++)
//		{
//			Queue<BlockWrapper> currentQueue = stackOfSeenQueues.pop();
//			
//			BlockWrapper currentBlock = currentQueue.poll();
//			
//			boolean looping = true;
//			while(looping)
//			{
//				try {
//					Queue<BlockWrapper> queueInDirection = useSpatialRelation(currentBlock.block,relationKeywords.get(i),degrees.get(i));
//					
//					stackOfSeenQueues.push(queueInDirection);
//					looping = false;
//				} catch (Exception e) {
//					currentBlock = currentQueue.poll();
//				}
//				
//				
//			}
//			
//		}
		int index = 0;
		Queue<BlockWrapper> returnedFinalList = recursivlySearchForBlock(initialBlockQueue,relationKeywords,degrees,index);
		
		if(returnedFinalList == null){
			//nothing found
		}
		else
		{
			block = returnedFinalList.poll().block;
		}
		
		return block;
	}
	
	private Queue<BlockWrapper> recursivlySearchForBlock(Queue<BlockWrapper> initialBlockQueue, List<String> relationKeywords, List<Integer> degrees, int index) {
		Queue<BlockWrapper> blocks = null;
		
		if(index == relationKeywords.size()-1)
		{
			return initialBlockQueue;
		}
		
		while(!initialBlockQueue.isEmpty() && blocks == null) 
		{
			BlockWrapper currentBlock = initialBlockQueue.poll();
			
			try {
				Queue<BlockWrapper> nextQueue = useSpatialRelation(currentBlock.block,relationKeywords.get(index),degrees.get(index));
				
				blocks = recursivlySearchForBlock(nextQueue,relationKeywords,degrees,index+1);
			} catch (Exception e) {
				//no blocks found in the given direction
			}
		}
		
		return blocks;
	}

	public Queue<BlockWrapper> useSpatialRelation (Block current, String spatialRelation,int degree) throws Exception{
		Queue<BlockWrapper> out = new PriorityQueue<BlockWrapper>();
		
		//Get corresponding 
		switch(spatialRelation){
			case "LEFT":
				out.addAll(current.left);
				break;
			case "RIGHT":
				out.addAll(current.right);
				break;
			case "FRONT":
				out.addAll(current.front);
				break;
			case "BEHIND":
				out.addAll(current.behind);
				break;
			case "ADJACENT":
				out.addAll(current.left);
				out.addAll(current.right);
				out.addAll(current.front);
				out.addAll(current.behind);
				break;
			default:
				throw new Exception("Unrecoginized spatial relation");
		}
		//Figure get to the xth block away, where x is the degree.
		int i = degree;
		while(i > 1){
			out.poll();
			i--;
			if(out.isEmpty()){
				throw new Exception("Invalid degree: " + degree);
			}
		}
		
		
		if(out.isEmpty()){
			throw new Exception("No blocks found");
		}
		
		
		return out;
	}

}
