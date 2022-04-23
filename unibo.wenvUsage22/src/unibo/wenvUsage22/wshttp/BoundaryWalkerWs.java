package unibo.wenvUsage22.wshttp;

import java.util.Observable;

import org.json.JSONObject;

import unibo.actor22comm.interfaces.IObserver;
import unibo.actor22comm.interfaces.Interaction2021;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;
import unibo.actor22comm.ws.WsConnection;
import unibo.wenvUsage22.common.ApplData;

public class BoundaryWalkerWs implements IObserver {
	private Interaction2021 conn;
	private  int boundary;
	private  boolean collision;
	
	public BoundaryWalkerWs() {
		boundary = 0;
		collision = false;
//		conn = WsConnection.create("localhost:8091" ); // Variante Fabio
//		((WsConnection)conn).addObserver(this);
	}
	
	
	
	protected void doBasicMoves() throws Exception {
		conn = WsConnection.create("localhost:8091" );
		((WsConnection)conn).addObserver(this);
		//boundary = 0;
		//collision = false;
//		if(boundary != 4) { //Variante Fabio
//			if (! collision) {
//				conn.forward( ApplData.moveForward(900) );
//			}
//			else {
//				collision = false;
//				conn.forward( ApplData.turnLeft(300) );
//			}
//		}
		while (boundary != 4) {
			while (! collision) {
			conn.forward( ApplData.moveForward(900) );
			CommUtils.delay( 1000 );
			}
			collision = false;
			conn.forward( ApplData.turnLeft(300) );
		}
		
		conn.close();
		
		
	}
	
	@Override
	public void update(Observable source, Object data) {
		ColorsOut.out("ClientUsingWs update/2 receives:" + data);
		JSONObject d = new JSONObject(""+data);
		ColorsOut.outappl("ClientUsingWs update/2 collision=" + d.has("collision"), ColorsOut.MAGENTA);
		if (d.has("collision")) {
			boundary++;
			collision = true;	
		}
//		try { // Variante Fabio
//			doBasicMoves();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	@Override
	public void update(String data) {
		ColorsOut.out("ClientUsingWs update receives:" + data);	
	}
	
	/*
	MAIN
	 */
		public static void main(String[] args) throws Exception   {
			CommUtils.aboutThreads("Before start - ");
	 		new BoundaryWalkerWs().doBasicMoves();
			CommUtils.aboutThreads("At end - ");
		}



}
