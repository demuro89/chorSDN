package main.distributed_implementation;

import choral.runtime.SerializerChannel.SerializerChannel_A;
import choral.runtime.SerializerChannel.SerializerChannel_B;
import choreography.ChorSDN_SA;
import main.local_code.FeatureAnalyserImpl;
import utils.SocketChannelClient;
import utils.SocketChannelServer;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SA implements Runnable {

  @Override
  public void run() {
    try {
      Future< SerializerChannel_B > sa_sw;
      Future< SerializerChannel_B > ml_sa;
      ExecutorService executorService = Executors.newFixedThreadPool(5);
      try (Closeable nonserve = executorService::shutdown;) {
        sa_sw = executorService.submit( () -> SocketChannelServer.getChannel( "localhost", Location.SW_SA.port ) );
        ml_sa = executorService.submit( () -> SocketChannelServer.getChannel( "localhost", Location.SA_ML.port ) );
      }
      SerializerChannel_A sa_vol = SocketChannelClient.getChannel( "localhost", Location.SA_VOL.port );
      SerializerChannel_A sa_sig = SocketChannelClient.getChannel( "localhost", Location.SA_SIG.port );
      new ChorSDN_SA( sa_sw.get(), sa_vol, ml_sa.get(), sa_sig, new FeatureAnalyserImpl() ).analyseTraffic();
    } catch (ExecutionException | InterruptedException | IOException e ) {
      throw new RuntimeException( e );
    }
  }
}
