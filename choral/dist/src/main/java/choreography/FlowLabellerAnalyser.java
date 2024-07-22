package choreography;

public interface FlowLabellerAnalyser extends Analyser {
	FlowLabel labelFlow( DataSignature s1, DataSignature s2 );
}
