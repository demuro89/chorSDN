package choreography;

import choral.channels.SymChannel_A;
import choral.channels.SymChannel_B;
import choral.lang.Unit;

public class ChorSDN_VOL {
	SymChannel_B< Object > sa_vol;
	SymChannel_B< Object > ml_vol;
	SymChannel_A< Object > sig_vol;
	SignatureAnalyser vol_a;

	public ChorSDN_VOL( SymChannel_B< Object > sa_vol, SymChannel_B< Object > ml_vol, SymChannel_A< Object > sig_vol, SignatureAnalyser vol_a ) {
		this.sa_vol = sa_vol;
		this.ml_vol = ml_vol;
		this.sig_vol = sig_vol;
		this.vol_a = vol_a;
	}

	public void analyseTraffic() {
		PacketFeature vol_f = sa_vol.< PacketFeature >com( Unit.id );
		Result vol_r = vol_a.analyse( vol_f );
		switch( vol_r ){
			case MALICIOUS -> {
				sa_vol.< Result >select( Result.MALICIOUS );
				ml_vol.< Result >select( Result.MALICIOUS );
				sig_vol.< Result >select( Result.MALICIOUS );
				{
					switch( sa_vol.< Signature >select( Unit.id ) ){
						case GEN_SIG -> {
							sig_vol.< DataSignature >com( vol_a.genSignature() );
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
				sa_vol.< Result >select( Result.BENIGN );
				ml_vol.< Result >select( Result.BENIGN );
				sig_vol.< Result >select( Result.BENIGN );
				closure();
			}
			default -> {
				sa_vol.< Result >select( Result.UNDECIDED );
				ml_vol.< Result >select( Result.UNDECIDED );
				sig_vol.< Result >select( Result.UNDECIDED );
				analyseTraffic();
			}
		}
	}
	
	void closure() {
	}

}
