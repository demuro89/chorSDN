package choreography;

import choral.channels.SymChannel_B;
import choral.lang.Unit;

public class ChorSDN_SIG {
	SymChannel_B< Object > sa_sig;
	SymChannel_B< Object > sig_vol;
	SymChannel_B< Object > ml_sig;
	FlowLabellerAnalyser sig_a;

	public ChorSDN_SIG( SymChannel_B< Object > sa_sig, SymChannel_B< Object > sig_vol, SymChannel_B< Object > ml_sig, FlowLabellerAnalyser sig_a ) {
		this.sa_sig = sa_sig;
		this.sig_vol = sig_vol;
		this.ml_sig = ml_sig;
		this.sig_a = sig_a;
	}

	public void analyseTraffic() {
		PacketFeature sig_f = sa_sig.< PacketFeature >com( Unit.id );
		Result sig_r = sig_a.analyse( sig_f );
		{
			switch( sig_vol.< Result >select( Unit.id ) ){
				case MALICIOUS -> {
					{
						switch( sa_sig.< Signature >select( Unit.id ) ){
							case GEN_SIG -> {
								DataSignature s1 = ml_sig.< DataSignature >com( Unit.id );
								DataSignature s2 = sig_vol.< DataSignature >com( Unit.id );
								sig_a.labelFlow( s1, s2 );
							}
							case NO_SIG -> {
								
							}
							default -> {
								throw new RuntimeException( "Received unexpected label from select operation" );
							}
						}
					}
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
