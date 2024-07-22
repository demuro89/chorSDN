package choreography;

import choral.runtime.Serializers.KryoSerializable;

@KryoSerializable
public enum Result {
	MALICIOUS,
	BENIGN,
	UNDECIDED

}
