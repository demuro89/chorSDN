package main.distributed_implementation;

import choral.runtime.SerializerChannel.SerializerChannel_A;
import choral.runtime.SerializerChannel.SerializerChannel_B;
import choreography.ChorSDN_VOL;
import main.local_code.SignatureAnalyserImpl_VOL;
import utils.SocketChannelClient;
import utils.SocketChannelServer;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VOL implements Runnable {
  @Override
  public void run() {
    try {
      Future< SerializerChannel_B > sa_vol;
      Future< SerializerChannel_B > ml_vol;
      ExecutorService executorService = Executors.newFixedThreadPool(5);
      try (Closeable nonserve = executorService::shutdown;) {
        sa_vol = executorService.submit( () -> SocketChannelServer.getChannel( "localhost", Location.SA_VOL.port ) );
        ml_vol = executorService.submit( () -> SocketChannelServer.getChannel( "localhost", Location.ML_VOL.port ) );
      }
      SerializerChannel_A sig_vol = SocketChannelClient.getChannel( "localhost", Location.SIG_VOL.port );
      new ChorSDN_VOL( sa_vol.get(), ml_vol.get(), sig_vol, new SignatureAnalyserImpl_VOL() ).analyseTraffic();
    } catch (ExecutionException | InterruptedException | IOException e ) {
      throw new RuntimeException( e );
    }
  }
}
