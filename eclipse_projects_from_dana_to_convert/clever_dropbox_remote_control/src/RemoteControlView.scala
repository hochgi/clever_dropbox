import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.layout.FormLayout
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.layout.FormData
import org.eclipse.swt.layout.FormAttachment
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.graphics.Font
import org.eclipse.swt.widgets.Listener
import org.eclipse.swt.widgets.Event
import scala.actors.remote.RemoteActor._
import scala.actors.remote._

/**
 * RemoteControlView class
 * Contains and responsible for creating the RemoteControl gui's components.<br>
 * Gives a possibility to start, stop, pause and continue the Service.<br>
 */
class RemoteControlView(d:Display, s:Shell) {
  
  var shell = s
  var display = d
  var continueBut:Button = null
  var pauseBut:Button = null
  var startBut:Button = null
  var stopBut:Button = null
  lazy val service = select(Node("localhost", 1122), 'externalEventListener)
  
    //initialize shell 
  shell.setText("cleverDropBox")
  shell.setSize(235, 225)
  initGUI
  shell.open()
  while (!shell.isDisposed()) {
    if (!display.readAndDispatch())
      display.sleep();
    }
  display.dispose();
  
  
  
  /**
   * initGUI
   * initialize the shell with all the components of the remote control
   */
  def initGUI(){
    	try {
	  		var composite = new Composite(shell, SWT.NONE)
		    var thisLayout = new FormLayout
			composite.setLayout(thisLayout);
			composite.setSize(226, 205);
			{
				var title = new Label(composite, SWT.NONE);
				var titleLData = new FormData();
				titleLData.left =  new FormAttachment(0, 1000, 50);
				titleLData.top =  new FormAttachment(0, 1000, 7);
				titleLData.width = 200;
				titleLData.height = 17;
				title.setLayoutData(titleLData);
				title.setText("Remote Control");
				title.setFont( new Font(display,"Arial", 14, SWT.BOLD ) );
			}
			{
				var label = new Label(composite, SWT.NONE);
				var labelLData = new FormData();
				labelLData.left =  new FormAttachment(0, 1000, 6);
				labelLData.top =  new FormAttachment(0, 1000, 36);
				labelLData.width = 260;
				labelLData.height = 33;
				label.setLayoutData(labelLData);
				label.setText("Choose an option for the service:");
			}
			{
				continueBut = new Button(composite, SWT.PUSH | SWT.CENTER);
				var continueButLData = new FormData();
				continueButLData.left =  new FormAttachment(0, 1000, 114);
				continueButLData.top =  new FormAttachment(0, 1000, 137);
				continueButLData.width = 79;
				continueButLData.height = 50;
				continueBut.setLayoutData(continueButLData);
				continueBut.setText("Continue");
				continueBut.addListener(SWT.Selection, new Listener(){
			    override def handleEvent(e:Event) {
			      service ! "CONTINUE"
			      stopBut.setEnabled(true)
			      startBut.setEnabled(false)
			      pauseBut.setEnabled(true)
			      continueBut.setEnabled(false)
			      println("send CONTINUE to service")
			    }
			 })
			}
			{
				pauseBut = new Button(composite, SWT.PUSH | SWT.CENTER);
				var pauseButLData = new FormData();
				pauseButLData.left =  new FormAttachment(0, 1000, 29);
				pauseButLData.top =  new FormAttachment(0, 1000, 137);
				pauseButLData.width = 79;
				pauseButLData.height = 50;
				pauseBut.setLayoutData(pauseButLData);
				pauseBut.setText("Pause")
				pauseBut.addListener(SWT.Selection, new Listener(){
			    override def handleEvent(e:Event) {
			      service ! "PAUSE"
			      startBut.setEnabled(false)
			      pauseBut.setEnabled(false)
			      stopBut.setEnabled(false)
			      continueBut.setEnabled(true)
			      println("send PAUSE to service")
			    }
			 })
			}
			{
				stopBut = new Button(composite, SWT.PUSH | SWT.CENTER);
				var stopButLData = new FormData();
				stopButLData.left =  new FormAttachment(0, 1000, 114);
				stopButLData.top =  new FormAttachment(0, 1000, 81);
				stopButLData.width = 79;
				stopButLData.height = 50;
				stopBut.setLayoutData(stopButLData);
				stopBut.setText("Stop")
				stopBut.addListener(SWT.Selection, new Listener(){
			    override def handleEvent(e:Event) {
			      service ! "STOP"
			      stopBut.setEnabled(false)
			      startBut.setEnabled(true)
			      pauseBut.setEnabled(false)
			      continueBut.setEnabled(false)
			      println("send STOP to service")
			    }
			 })
			}
			{
				startBut = new Button(composite, SWT.PUSH | SWT.CENTER);
				var startButLData = new FormData();
				startButLData.left =  new FormAttachment(0, 1000, 29);
				startButLData.top =  new FormAttachment(0, 1000, 81);
				startButLData.width = 79;
				startButLData.height = 50;
				startBut.setLayoutData(startButLData);
				startBut.setText("Start")
				startBut.addListener(SWT.Selection, new Listener(){
			    override def handleEvent(e:Event) {
			      service ! "START"
			      stopBut.setEnabled(true)
			      startBut.setEnabled(false)
			      pauseBut.setEnabled(true)
			      continueBut.setEnabled(false)
			      println("send START to service")
			    }
			 })
			}
			stopBut.setEnabled(false)
			startBut.setEnabled(true)
			pauseBut.setEnabled(false)
			continueBut.setEnabled(false)
			composite.layout();
			
		} catch{case e:Exception => e.printStackTrace()}
	}//END OF initGUI
  }



