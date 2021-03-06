package in.dream_lab.goffish.giraph.examples;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anirudh on 10/02/17.
 */
public class ShortestPathSubgraphValue implements Writable
{
  public Map<Long, Short> shortestDistanceMap;

  @Override
  public void write(DataOutput dataOutput) throws IOException {
    if (shortestDistanceMap == null) {
      shortestDistanceMap = new HashMap<>();
    }
    dataOutput.writeInt(shortestDistanceMap.size());
    for (Map.Entry<Long, Short> entry : shortestDistanceMap.entrySet()) {
      dataOutput.writeLong(entry.getKey());
      dataOutput.writeShort(entry.getValue());
    }
  }

  @Override
  public void readFields(DataInput dataInput) throws IOException {
    int size = dataInput.readInt();
    shortestDistanceMap = new HashMap<>(size);
    for (int i = 0; i < size; i++) {
      shortestDistanceMap.put(dataInput.readLong(), dataInput.readShort());
    }
  }
}
