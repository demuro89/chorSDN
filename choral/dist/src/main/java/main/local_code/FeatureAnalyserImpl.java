package main.local_code;

import choreography.DataStream;
import choreography.FeatureAnalyser;
import choreography.Packet;
import choreography.PacketFeature;

// QUESTO È SA VNF!


public class FeatureAnalyserImpl implements FeatureAnalyser {
 @Override
 public PacketFeature extractFeaturesVOL( Packet p ) {

  return new PacketFeatureImpl("value4","value5","value6");
 }

 @Override
 public PacketFeature extractFeaturesML( Packet p ) {
  return new PacketFeatureImpl("value4","value5","value6");
 }

 @Override
 public PacketFeature extractFeaturesSIG( Packet p ) {
  return new PacketFeatureImpl("value4","value5","value6");

 }

 @Override
 public DataStream genStm() {
  return new DataStreamImpl();
 }
}
