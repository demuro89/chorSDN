package choreography;

import choral.channels.SymChannel_A;
import choral.lang.Unit;

public class ChorSDN_SW {
	SymChannel_A< Object > sa_sw;
	Switch sw;

	public ChorSDN_SW( SymChannel_A< Object > sa_sw, Switch sw ) {
		this.sa_sw = sa_sw;
		this.sw = sw;
	}

	public void analyseTraffic() {
		sa_sw.< Packet >com( sw.getPacket() );
		{
			switch( sa_sw.< Result >select( Unit.id ) ){
				case MALICIOUS -> {
					closure();
				}
				case BENIGN -> {
					closure();
				}
				case UNDECIDED -> {
					analyseTraffic();
				}
				default -> {
					throw new RuntimeException( "Received unexpected label from select operation" );
				}
			}
		}
	}
	
	void closure() {

	}

}
