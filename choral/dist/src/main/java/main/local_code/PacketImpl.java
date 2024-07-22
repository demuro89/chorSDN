package main.local_code;

import choral.runtime.Serializers.KryoSerializable;
import choreography.Packet;
@KryoSerializable
public class PacketImpl implements Packet {

    private String value1;
    private String value2;
    private String value3;

    // Constructor
    public PacketImpl(String value1, String value2, String value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    // Getters and setters
    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    // Override toString method for easy printing
    @Override
    public String toString() {
        return "MyPacket{" +
                "value1='" + value1 + '\'' +
                ", value2='" + value2 + '\'' +
                ", value3='" + value3 + '\'' +
                '}';
    }

}
