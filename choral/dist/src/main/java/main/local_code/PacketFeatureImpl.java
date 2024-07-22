package main.local_code;

import choral.runtime.Serializers.KryoSerializable;
import choreography.PacketFeature;
@KryoSerializable
public class PacketFeatureImpl implements PacketFeature {

    private String value4;
    private String value5;
    private String value6;

    // Constructor
    public PacketFeatureImpl(String value4, String value5, String value6) {
        this.value4 = value4;
        this.value5 = value5;
        this.value6 = value6;
    }

    // Getters and setters
    public String getValue4() {
        return value4;
    }

    public void setValue4(String value4) {
        this.value4 = value4;
    }

    public String getValue5() {
        return value5;
    }

    public void setValue5(String value5) {
        this.value5 = value5;
    }

    public String getValue6() {
        return value6;
    }

    public void setValue6(String value6) {
        this.value6 = value6;
    }

    // Override toString method for easy printing
    @Override
    public String toString() {
        return "MyPacket{" +
                "value4='" + value4 + '\'' +
                ", value5='" + value5 + '\'' +
                ", value6='" + value6 + '\'' +
                '}';
    }

}
