package main.local_code;

import choreography.*;

import java.io.IOException;


public class SignatureAnalyserImpl_ML implements SignatureAnalyser {
 @Override
 public Result analyse( PacketFeature pf ) {

  // In futuro estrarre feature da pf e metterle nei valori della POST

  /*HttpClientExample.main("http://127.0.0.1:5000/verify","{\"Model\": \"value1\", \"Data_Set_Type\": \"value2\"}");

  // mi metto a leggere dalla named pipe.
  try {
   Thread.sleep(3000);
  } catch (InterruptedException e) {
   throw new RuntimeException(e);
  }

  try {
   return PipeReader.main("/tmp/test");

  } catch (IOException e) {
   throw new RuntimeException(e);
  }*/
  return Result.BENIGN;
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
