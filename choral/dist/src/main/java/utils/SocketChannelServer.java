package utils;

import choral.runtime.Media.ServerSocketByteChannel;
import choral.runtime.SerializerChannel.SerializerChannel_B;
import choral.runtime.Serializers.KryoSerializer;
import choral.runtime.WrapperByteChannel.WrapperByteChannel_B;

import java.io.IOException;

public class SocketChannelServer {

  public static SerializerChannel_B getChannel( String address, int server_port ) throws IOException {
    ServerSocketByteChannel serverListener = ServerSocketByteChannel.at( address, server_port );
    SerializerChannel_B channel = new SerializerChannel_B( KryoSerializer.getInstance(), new WrapperByteChannel_B( serverListener.getNext() ) );
    serverListener.close();
    return channel;
  }

}
