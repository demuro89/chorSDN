interface Packet@A {}
interface PacketFeature@A {}
interface DataStream@A {}
interface DataSignature@A {}
interface FlowLabel@A {}
interface Switch@A {
   Packet@A getPacket();
}
interface FeatureAnalyser@A {
 PacketFeature@A extractFeaturesVOL( Packet@A p );
 PacketFeature@A extractFeaturesML( Packet@A p );
 PacketFeature@A extractFeaturesSIG( Packet@A p );
 DataStream@A genStm();
}
interface Analyser@A {
 Result@A analyse( PacketFeature@A pf );
}
interface FilterAnalyser@A extends Analyser@A {
 Result@A filterAndAnalyse( DataStream@A ds );
}
interface SignatureAnalyser@A extends FilterAnalyser@A { // QUI FILTER NO VIENE USATO DA VOL
 DataSignature@A genSignature();
}
interface FlowLabellerAnalyser@A extends Analyser@A {
 FlowLabel@A labelFlow( DataSignature@A s1, DataSignature@A s2 );
}

enum Result@A {
 MALICIOUS, BENIGN, UNDECIDED
}

enum Signature@A {
 GEN_SIG, NO_SIG
}

interface Channel@( A, B ) {
 < T@X > T@B com( T@A m );
 < T@X > T@A com( T@B m );
 @SelectionMethod
 < T@X extends Enum@X< T > > T@A select( T@B m );
 @SelectionMethod
 < T@X extends Enum@X< T > > T@B select( T@A m );
}

public class ChorSDN@( SW, SA, VOL, ML, SIG ){

	// "Network topology", stored as fields or method args
	Channel@(SW,SA)     sa_sw;
	Channel@(SA,VOL)    sa_vol;
	Channel@(SA,ML)     ml_sa;
	Channel@(SA,SIG)    sa_sig;
	Channel@(VOL,ML)    ml_vol;
	Channel@(VOL,SIG)   sig_vol;
	Channel@(ML,SIG)    ml_sig;

	Switch@( SW ) sw;
	FeatureAnalyser@SA sa_a;
	SignatureAnalyser@VOL vol_a;
	SignatureAnalyser@ML ml_a;
	FlowLabellerAnalyser@SIG sig_a;
	// WRITE THE CONSTRUCTOR

	void analyseTraffic() {
		Packet@SA p = sa_sw.< Packet >com( sw.getPacket() );
		PacketFeature@VOL vol_f = sa_vol.< PacketFeature >com( sa_a.extractFeaturesVOL( p ) );
		PacketFeature@ML  ml_f = ml_sa.< PacketFeature >com( sa_a.extractFeaturesML( p ) );
		PacketFeature@SIG sig_f = sa_sig.< PacketFeature >com( sa_a.extractFeaturesSIG( p ) );

		Result@VOL vol_r = vol_a.analyse( vol_f );
		Result@ML  ml_r = ml_a.analyse( ml_f );
		Result@SIG sig_r = sig_a.analyse( sig_f );

		switch( vol_r ) {
			case MALICIOUS -> {
				sa_vol.< Result >select( Result@VOL.MALICIOUS );  // SA
				ml_vol.< Result >select( Result@VOL.MALICIOUS );  // SIG
				sig_vol.< Result >select( Result@VOL.MALICIOUS ); // ML
				sa_sw.< Result >select( Result@SA.MALICIOUS );    // SW
				Result@SA d = sa_a.genStm() >> ml_sa::< DataStream >com
											>> ml_a::filterAndAnalyse
											>> ml_sa::< Result >com;
				if ( d == Result@SA.MALICIOUS ) {
					sa_vol.< Signature >select( Signature@SA.GEN_SIG ); // VOL
					ml_sa.< Signature >select( Signature@SA.GEN_SIG );     // ML
					sa_sig.< Signature >select( Signature@SA.GEN_SIG );     // SIG
					DataSignature@SIG s1 = ml_a.genSignature() >> ml_sig::< DataSignature >com;
					DataSignature@SIG s2 = vol_a.genSignature() >> sig_vol::< DataSignature >com;
					sig_a.labelFlow( s1, s2 );
					// finish the protocol
				} else {
					sa_vol.< Signature >select( Signature@SA.NO_SIG );    // VOL
					ml_sa.< Signature >select( Signature@SA.NO_SIG );     // ML
					sa_sig.< Signature >select( Signature@SA.NO_SIG );    // SIG
				}
				closure();
			}
			case BENIGN -> {
				sa_vol.< Result >select( Result@VOL.BENIGN );  // SA
				ml_vol.< Result >select( Result@VOL.BENIGN );  // SIG
				sig_vol.< Result >select( Result@VOL.BENIGN ); // ML
				sa_sw.< Result >select( Result@SA.BENIGN );    // SW
				closure();
			}
			default -> {
				sa_vol.< Result >select( Result@VOL.UNDECIDED );  // SA
				ml_vol.< Result >select( Result@VOL.UNDECIDED );  // SIG
				sig_vol.< Result >select( Result@VOL.UNDECIDED ); // ML
				sa_sw.< Result >select( Result@SA.UNDECIDED );    // SW
				analyseTraffic();
			}
		}
	}

	void closure(){
	 // TO DO
	}
}