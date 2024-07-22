package utils;

import choral.runtime.Media.SocketByteChannel;
import choral.runtime.SerializerChannel.SerializerChannel_A;
import choral.runtime.Serializers.KryoSerializer;
import choral.runtime.WrapperByteChannel.WrapperByteChannel_A;

public class SocketChannelClient {

  public static SerializerChannel_A getChannel( String address, int server_port ) {
    return new SerializerChannel_A( KryoSerializer.getInstance(), new WrapperByteChannel_A( SocketByteChannel.connect( address, server_port ) ) );
  }

}
