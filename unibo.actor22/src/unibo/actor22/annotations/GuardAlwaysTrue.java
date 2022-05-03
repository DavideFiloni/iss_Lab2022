package unibo.actor22.annotations;

 
import unibo.actor22comm.interfaces.IGurad;
import unibo.actor22comm.utils.ColorsOut;

public class GuardAlwaysTrue  implements IGurad{
	@Override
  	public boolean eval( ) {
 		//ColorsOut.outappl("GuardAlwaysTrue eval" , ColorsOut.ANSI_YELLOW);
 		return true;
	}

}
