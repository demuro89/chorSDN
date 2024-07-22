package choreography;

import choral.channels.SymChannel_A;
import choral.channels.SymChannel_B;
import choral.lang.Unit;

public class ChorSDN_SA {
	SymChannel_B< Object > sa_sw;
	SymChannel_A< Object > sa_vol;
	SymChannel_B< Object > ml_sa;
	SymChannel_A< Object > sa_sig;
	FeatureAnalyser sa_a;

	public ChorSDN_SA( SymChannel_B< Object > sa_sw, SymChannel_A< Object > sa_vol, SymChannel_B< Object > ml_sa, SymChannel_A< Object > sa_sig, FeatureAnalyser sa_a ) {
		this.sa_sw = sa_sw;
		this.sa_vol = sa_vol;
		this.ml_sa = ml_sa;
		this.sa_sig = sa_sig;
		this.sa_a = sa_a;
	}

	public void analyseTraffic() {
		Packet p = sa_sw.< Packet >com( Unit.id );
		sa_vol.< PacketFeature >com( sa_a.extractFeaturesVOL( p ) );
		ml_sa.< PacketFeature >com( sa_a.extractFeaturesML( p ) );
		sa_sig.< PacketFeature >com( sa_a.extractFeaturesSIG( p ) );
		{
			switch( sa_vol.< Result >select( Unit.id ) ){
				case MALICIOUS -> {
					sa_sw.< Result >select( Result.MALICIOUS );
					Result d = ml_sa.< Result >com( ml_sa.< DataStream >com( sa_a.genStm() ) );
					if( d == Result.MALICIOUS ){
						sa_vol.< Signature >select( Signature.GEN_SIG );
						ml_sa.< Signature >select( Signature.GEN_SIG );
						sa_sig.< Signature >select( Signature.GEN_SIG );
					} else { 
						sa_vol.< Signature >select( Signature.NO_SIG );
						ml_sa.< Signature >select( Signature.NO_SIG );
						sa_sig.< Signature >select( Signature.NO_SIG );
					}
					closure();
				}
				case BENIGN -> {
					sa_sw.< Result >select( Result.BENIGN );
					closure();
				}
				case UNDECIDED -> {
					sa_sw.< Result >select( Result.UNDECIDED );
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
