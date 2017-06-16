package in.dream_lab.goffish.giraph.master;

import in.dream_lab.goffish.giraph.aggregators.PartitionLoadingAggregator;
import in.dream_lab.goffish.giraph.aggregators.SubgraphPartitionMappingAggregator;
import in.dream_lab.goffish.giraph.conf.GiraphSubgraphConfiguration;
import in.dream_lab.goffish.giraph.graph.GiraphSubgraphComputation;
import in.dream_lab.goffish.giraph.graph.RemoteVerticesFinder;
import in.dream_lab.goffish.giraph.graph.RemoteVerticesFinder2;
import in.dream_lab.goffish.giraph.graph.RemoteVerticesFinder3;
import org.apache.giraph.master.DefaultMasterCompute;

/**
 * Created by anirudh on 17/03/17.
 */
public class SubgraphMasterCompute extends DefaultMasterCompute {
  public static final String ID = "SubgraphPartitionMappingAggregator";
  public static final String LOADID = "PartitionLoadingAggregator";

  @Override
  public void compute() {
    long superstep = getSuperstep();
    if (superstep == 0) {
      setComputation(RemoteVerticesFinder.class);
    } else if(superstep == 1) {
      setComputation(RemoteVerticesFinder2.class);
    } else if(superstep == 2) {
      setComputation(RemoteVerticesFinder3.class);
    } else {
//      return;
      setComputation(GiraphSubgraphComputation.class);
    }
  }

  @Override
  public void initialize() throws InstantiationException, IllegalAccessException {
    registerAggregator(ID, SubgraphPartitionMappingAggregator.class);
    registerAggregator(LOADID, PartitionLoadingAggregator.class);
    GiraphSubgraphConfiguration conf=new GiraphSubgraphConfiguration(getConf());



      //get file path and return the map object
//    setAggregatedValue();
  }
}
