package choreography;

import choral.channels.SymChannel_A;
import choral.channels.SymChannel_B;
import choral.lang.Unit;

public class ChorSDN_ML {
	SymChannel_A< Object > ml_sa;
	SymChannel_A< Object > ml_vol;
	SymChannel_A< Object > ml_sig;
	SignatureAnalyser ml_a;

	public ChorSDN_ML( SymChannel_A< Object > ml_sa, SymChannel_A< Object > ml_vol, SymChannel_A< Object > ml_sig, SignatureAnalyser ml_a ) {
		this.ml_sa = ml_sa;
		this.ml_vol = ml_vol;
		this.ml_sig = ml_sig;
		this.ml_a = ml_a;
	}

	public void analyseTraffic() {
		PacketFeature ml_f = ml_sa.< PacketFeature >com( Unit.id );
		Result ml_r = ml_a.analyse( ml_f );
		{
			switch( ml_vol.< Result >select( Unit.id ) ){
				case MALICIOUS -> {
					ml_sa.< Result >com( ml_a.filterAndAnalyse( ml_sa.< DataStream >com( Unit.id ) ) );
					{
						switch( ml_sa.< Signature >select( Unit.id ) ){
							case GEN_SIG -> {
								ml_sig.< DataSignature >com( ml_a.genSignature() );
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
