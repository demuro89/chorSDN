package main.local_code;

import choreography.*;

import java.io.IOException;

public class FlowLabellerAnalyserImpl implements FlowLabellerAnalyser {
 @Override
 public Result analyse( PacketFeature pf ) {
  RunShellScript.main("/home/demuro89/workspace/chorsdn/vnf3/home/ids_detection.py");

  // mi metto a leggere dalla named pipe.
  try {
   Thread.sleep(6000);
  } catch (InterruptedException e) {
   throw new RuntimeException(e);
  }

  try {

   Result risultato= PipeReader.main("/tmp/test2");
   System.out.println(risultato + " Questo è dal ids_detection");

   return risultato;

  } catch (IOException e) {
   throw new RuntimeException(e);
  }
 }


  @Override
 public FlowLabel labelFlow( DataSignature s1, DataSignature s2 ) {
  return null;
 }
}
