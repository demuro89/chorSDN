package main.distributed_implementation;

import choral.runtime.SerializerChannel.SerializerChannel_B;
import choreography.ChorSDN_SIG;
import main.local_code.FlowLabellerAnalyserImpl;
import utils.SocketChannelServer;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.*;

public class SIG implements Runnable {
  @Override
  public void run() {
    try {
      Future< SerializerChannel_B > sa_sig;
      Future< SerializerChannel_B > sig_vol;
      Future< SerializerChannel_B > ml_sig;
      ExecutorService executorService = Executors.newFixedThreadPool(5);
      try (Closeable nonserve = executorService::shutdown;) {
        sa_sig = executorService.submit( () -> SocketChannelServer.getChannel( "localhost", Location.SA_SIG.port ) );
        sig_vol = executorService.submit( () -> SocketChannelServer.getChannel( "localhost", Location.SIG_VOL.port ) );
        ml_sig = executorService.submit( () -> SocketChannelServer.getChannel( "localhost", Location.ML_SIG.port ) );
      }
      new ChorSDN_SIG( sa_sig.get(), sig_vol.get(), ml_sig.get(), new FlowLabellerAnalyserImpl() ).analyseTraffic();
    } catch (ExecutionException | InterruptedException | IOException e ) {
      throw new RuntimeException( e );
    }
  }
}
