package main.distributed_implementation;

import choral.channels.SymChannel_A;
import choral.channels.SymChannel_B;
import choral.utils.Pair;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import choreography.*;
import main.local_code.*;

public class TestLocal {

  public static void main( String[] args ) throws InterruptedException, IOException {

   ExecutorService executorService = Executors.newFixedThreadPool(5);
   try (Closeable nonserve = executorService::shutdown;) {
      System.out.println( "STARTING SIG" );
      executorService.submit( new SIG() );
      TimeUnit.SECONDS.sleep( 2 );
      System.out.println( "STARTING VOL" );
      executorService.submit( new VOL() );
      TimeUnit.SECONDS.sleep( 2 );
      System.out.println( "STARTING SA" );
      executorService.submit( new SA() );
      TimeUnit.SECONDS.sleep( 2 );
      System.out.println( "STARTING ML" );
      executorService.submit( new ML() );
      System.out.println( "STARTING SW" );
      executorService.submit( new SW() );
  }

}
}
