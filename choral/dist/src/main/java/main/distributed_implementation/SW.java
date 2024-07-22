package main.distributed_implementation;

import choral.runtime.SerializerChannel.SerializerChannel_A;
import choreography.ChorSDN_SW;
import main.local_code.SwitchImpl;
import utils.SocketChannelClient;

public class SW implements Runnable {

  @Override
  public void run() {
    SerializerChannel_A channel = SocketChannelClient.getChannel( "localhost", Location.SW_SA.port );
    new ChorSDN_SW( channel, new SwitchImpl() ).analyseTraffic();
  }
}
