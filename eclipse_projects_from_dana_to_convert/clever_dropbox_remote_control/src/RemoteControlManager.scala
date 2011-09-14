import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell


 /**
 * RemoteControlManager application.<br>
 * create an instance of the GUI of the remote control
 */
object RemoteControlManager  {
  
  def main(args : Array[String]) : Unit = {
     val display = new Display();
	 val shell = new Shell(display);
     val view = new RemoteControlView(display,shell)
  }
}