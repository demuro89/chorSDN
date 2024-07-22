package main.local_code;

import choreography.Packet;
import choreography.Result;
import choreography.Switch;

import java.io.IOException;

public class SwitchImpl implements Switch {
    @Override
    public Packet getPacket() {
        while (true){
            try {

                String risultato= PipeReaderGetPacket.main("/tmp/test3");
                //System.out.println(risultato);
                if (risultato != null && risultato.equals("START")) {
                    PacketImpl NuovoPacchetto=new PacketImpl("pippo","pluto","paperino");
                    System.out.println(NuovoPacchetto);

                    return NuovoPacchetto;
                    // Qui arriva il pacchetto allo switch
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
