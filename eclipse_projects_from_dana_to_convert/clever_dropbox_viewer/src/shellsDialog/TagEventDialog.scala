package shellsDialog
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.layout.FormLayout
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt._
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.layout.FormData
import org.eclipse.swt.layout.FormAttachment
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Text
import org.eclipse.swt.widgets.Listener
import org.eclipse.swt.widgets.Event
import org.eclipse.swt.graphics.Font
import org.eclipse.swt.widgets.Display
import enumeration.TagEvent._


/**
 * AddTagShellDialog class
 * This class create a new shell which the user can add a new tag to the selected file
 * or if there is no selected file, then create a new file tag
 */
  class TagEventDialog(parent2:Shell,fileSelected2:String) {
  
	var parent:Shell = parent2
	var fileSelected:String = fileSelected2
	var cancel:Button = null
	var ok:Button = null
	var mainLabel:Label = null
	var fileName:Label = null
	var mainLabelData:FormData = null
	var fileNameLData:FormData = null
	var okLData:FormData = null
	var cancelLData:FormData = null
	
	//create shell
    val child = new Shell(parent);
    child.setSize(340,160);
    		var thisLayout = new FormLayout();
    		var composite = new Composite(child, SWT.NONE)
			composite.setLayout(thisLayout);
			composite.setSize(333, 136);
			//create the widgets
			{
				cancel = new Button(composite, SWT.PUSH | SWT.CENTER);
				cancelLData = new FormData();
				cancelLData.left =  new FormAttachment(0, 1000, 253);
				cancelLData.top =  new FormAttachment(0, 1000, 93);
				cancelLData.width = 59;
				cancelLData.height = 31;
				cancel.setLayoutData(cancelLData);
				cancel.setText("Cancel");
				cancel.addListener(SWT.Selection, new Listener(){
				  override def handleEvent(e:Event) {
				    child.close();
				  }
				})
			}
			{
				ok = new Button(composite, SWT.PUSH | SWT.CENTER);
				okLData = new FormData();
				okLData.left =  new FormAttachment(0, 1000, 197);
				okLData.top =  new FormAttachment(0, 1000, 93);
				okLData.width = 50;
				okLData.height = 31;
				ok.setLayoutData(okLData);
				ok.setText("OK");				
			}
			{
				mainLabel = new Label(composite, SWT.NONE);
				mainLabelData = new FormData();
				mainLabelData.left =  new FormAttachment(0, 1000, 12);
				mainLabelData.top =  new FormAttachment(0, 1000, 19);
				mainLabelData.width = 124;
				mainLabelData.height = 17;
				mainLabel.setLayoutData(mainLabelData);
			}
			
			//composite.layout();
    child.open();     
  }

