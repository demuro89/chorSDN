package main.local_code;

import choral.channels.SymChannel_A;
import choral.channels.SymChannel_B;
import choral.utils.Pair;
import choreography.*;
import main.local_code.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TestSocket {
 public static void main( String[] args ) throws ExecutionException, InterruptedException {
  Pair< ? extends SymChannel_A<Object>, ? extends SymChannel_B<Object> > sa_sw = choral.choralUnit.testUtils.TestUtils.newSocketChannel();
  Pair< ? extends SymChannel_A<Object>, ? extends SymChannel_B<Object> > sa_vol = choral.choralUnit.testUtils.TestUtils.newSocketChannel();
  Pair< ? extends SymChannel_A<Object>, ? extends SymChannel_B<Object> > ml_sa = choral.choralUnit.testUtils.TestUtils.newSocketChannel();
  Pair< ? extends SymChannel_A<Object>, ? extends SymChannel_B<Object> > sa_sig = choral.choralUnit.testUtils.TestUtils.newSocketChannel();
  Pair< ? extends SymChannel_A<Object>, ? extends SymChannel_B<Object> > ml_vol = choral.choralUnit.testUtils.TestUtils.newSocketChannel();
  Pair< ? extends SymChannel_A<Object>, ? extends SymChannel_B<Object> > sig_vol = choral.choralUnit.testUtils.TestUtils.newSocketChannel();
  Pair< ? extends SymChannel_A<Object>, ? extends SymChannel_B<Object> > ml_sig = choral.choralUnit.testUtils.TestUtils.newSocketChannel();

  ChorSDN_SW sw = new ChorSDN_SW( sa_sw.left(), new SwitchImpl() );
  ChorSDN_SA sa = new ChorSDN_SA( sa_sw.right(), sa_vol.left(), ml_sa.right(), sa_sig.left(), new FeatureAnalyserImpl() );
  ChorSDN_VOL vol = new ChorSDN_VOL( sa_vol.right(), ml_vol.right(), sig_vol.left(), new SignatureAnalyserImpl_VOL() );
  ChorSDN_ML ml = new ChorSDN_ML( ml_sa.left(), ml_vol.left(), ml_sig.left(), new SignatureAnalyserImpl_ML() );
  ChorSDN_SIG sig = new ChorSDN_SIG( sa_sig.right(), sig_vol.right(), ml_sig.right(), new FlowLabellerAnalyserImpl()  );

  List< Thread > threadList = new ArrayList<>();
  threadList.add( new Thread( sw::analyseTraffic ) );
  threadList.add( new Thread( sa::analyseTraffic ) );
  threadList.add( new Thread( vol::analyseTraffic ) );
  threadList.add( new Thread( ml::analyseTraffic ) );
  threadList.add( new Thread( sig::analyseTraffic ) );

   threadList.forEach( Thread::start );
   threadList.forEach( t -> {
    try {
     t.join();
    } catch( InterruptedException e ) {
     e.printStackTrace();
    }
   } );

 }

}
