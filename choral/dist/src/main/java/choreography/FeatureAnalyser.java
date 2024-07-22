package choreography;

public interface FeatureAnalyser {
	PacketFeature extractFeaturesVOL( Packet p );
	PacketFeature extractFeaturesML( Packet p );
	PacketFeature extractFeaturesSIG( Packet p );
	DataStream genStm();
}
