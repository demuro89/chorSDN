package main.distributed_implementation;

public enum Location {
  SW_SA( 10000 ),
  SA_ML( 10001 ),
  SA_VOL( 10002 ),
  SA_SIG( 10003 ),
  ML_VOL( 10004 ),
  SIG_VOL( 10005 ),
  ML_SIG( 10007 );


  public final int port;

  private Location( int port ) {
    this.port = port;
  }

}
