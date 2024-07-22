package main.local_code;

import choreography.*;

import java.io.IOException;

public class SignatureAnalyserImpl_VOL implements SignatureAnalyser {
 @Override
 public Result analyse( PacketFeature pf ) {

  // Lancia il monitoraggio per 30 secondi. Lancia lo script read.sh
  RunShellScript.main("/home/demuro89/workspace/chorsdn/vnf1/home/read.sh");

  // mi metto a leggere dalla named pipe.
  try {
   Thread.sleep(6000);
  } catch (InterruptedException e) {
   throw new RuntimeException(e);
  }

  try {

   Result risultato= PipeReader.main("/tmp/test1");
   System.out.println(risultato + " questo è del read.sh");

   return risultato;

  } catch (IOException e) {
   throw new RuntimeException(e);
  }
 }

 @Override
 public Result filterAndAnalyse( DataStream ds ) {

  return Result.BENIGN;
 }

 @Override
 public DataSignature genSignature() {
  // Chiamata a REST API di signature che inserisce la nuova regola dell IDS

  return new DataSignatureImpl();
 }
}
