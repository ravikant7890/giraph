package in.dream_lab.goffish.giraph;

import com.google.common.primitives.Longs;
import in.dream_lab.goffish.AbstractSubgraphComputation;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;

import java.io.IOException;

/**
 * Created by anirudh on 21/11/16.
 */
public class SubgraphConnectedComponents extends AbstractSubgraphComputation<LongWritable, NullWritable, NullWritable, BytesWritable, LongWritable, NullWritable, LongWritable
    > {

  @Override
  public void compute(Iterable<IMessage<LongWritable,BytesWritable>> messages) throws IOException {
    Subgraph<LongWritable, NullWritable, NullWritable, LongWritable, NullWritable, LongWritable> subgraph = getSubgraph();
    if (getSuperstep() == 0) {
      LongWritable sid = subgraph.getSubgraphId();
      subgraph.getSubgraphVertices().setSubgraphValue(sid);

      sendToNeighbors(new BytesWritable(Longs.toByteArray(sid.get())));
    } else {
      long myMin = subgraph.getSubgraphVertices().getSubgraphValue().get();
      long currentMin = myMin;
      //System.out.println("My Min: " + myMin);
      for (IMessage<LongWritable,BytesWritable> message : messages) {
        long neighborMin = Longs.fromByteArray(message.getMessage().getBytes());
        //System.out.println("Message from neighbor: " + neighborMin);
        if (neighborMin < currentMin) {
          currentMin = neighborMin;
        }
      }
      if (currentMin < myMin) {
        subgraph.getSubgraphVertices().setSubgraphValue(new LongWritable(currentMin));
        sendToNeighbors( new BytesWritable(Longs.toByteArray(currentMin)));
      }
    }

    voteToHalt();
  }
}
//TODO : we cant have member variables here( which wont be shared with other subgraphs)



