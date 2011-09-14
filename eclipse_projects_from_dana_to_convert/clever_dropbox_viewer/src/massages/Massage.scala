package massages
import org.eclipse.swt.widgets.MessageBox
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.SWT


/**
 * Massage .<br>
 * @param massage of type String.<br>
 * @param shell.<br>
 * open a MassageBox shell with the coming massage
 */
class Massage(msg:String,s:Shell) {
  
  var massage = msg
  var shell = s
  var messageBox = new MessageBox(shell,SWT.OK);
        
        messageBox.setText("massage");
        messageBox.setMessage(massage);
        messageBox.open();
        
}