package main.distributed_implementation;

import choral.runtime.SerializerChannel.SerializerChannel_A;
import choral.runtime.SerializerChannel.SerializerChannel_B;
import choreography.ChorSDN_ML;
import main.local_code.SignatureAnalyserImpl_ML;
import utils.SocketChannelClient;
import utils.SocketChannelServer;

import java.io.IOException;

public class ML implements Runnable {
  @Override
  public void run() {
    SerializerChannel_A ml_sa = SocketChannelClient.getChannel( "localhost", Location.SA_ML.port );
    SerializerChannel_A ml_vol = SocketChannelClient.getChannel( "localhost", Location.ML_VOL.port );
    SerializerChannel_A ml_sig = SocketChannelClient.getChannel( "localhost", Location.ML_SIG.port );
    new ChorSDN_ML( ml_sa, ml_vol, ml_sig, new SignatureAnalyserImpl_ML() ).analyseTraffic();
  }
}
