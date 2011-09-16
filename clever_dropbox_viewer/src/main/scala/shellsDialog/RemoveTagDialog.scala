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
import dataBase.DBConnector
import massages.Massage



/**
 * RemoveTagDialog class.<br>
 * extends TagEventDialog.<br>
 * @param parent a shell parent
 * @param fileSelected file that selected by the user
 * Create a shell dialog to remove tag from the selected file
 */
class RemoveTagDialog(parent2:Shell,fileSelected2:String) extends TagEventDialog(parent2,fileSelected2){
	
  	var textfield:Text = null
	var shell = parent2
	var textfieldLData = new FormData();
	textfieldLData.left =  new FormAttachment(0, 1000, 12);
	textfieldLData.top =  new FormAttachment(0, 1000, 48);
	textfieldLData.width = 289;
	textfieldLData.height = 16;
	textfield = new Text(composite, SWT.BORDER);
	textfield.setLayoutData(textfieldLData);		
	mainLabelData.width = 200;
	mainLabelData.height = 17;
	fileName = new Label(composite, SWT.NONE);
	fileNameLData = new FormData();
	fileNameLData.left =  new FormAttachment(0, 1000, 220);
	fileNameLData.top =  new FormAttachment(0, 1000, 14);
	fileNameLData.width = 211;
	fileNameLData.height = 30;
	fileName.setLayoutData(fileNameLData);
	fileName.setText(fileSelected);
	fileName.setFont( new Font(Display.getDefault(),"Arial", 14, SWT.BOLD ) );		
	mainLabel.setText(" Choose tag to remove from: ")		
	// add listener to the OK button
	ok.addListener(SWT.Selection, new RemoveTag())	  
	// set the layout
    composite.layout();
	
	
	
	/**
    * RemoveTag class.<br>
    * extends Listener.<br>
    * this class when getting event, extract the tag name,
    * and connect to the DB for removing the tag from the selected file.<br>
    */
    class RemoveTag extends Listener{
      
        /**
         * handleEvent
         * @param e an Event.<br>
    	 * the handle method of the listener.<br>
    	 * get the tag name from the text field of this shell.<br>
         * if the tag name is not an empty string then check if the tag exist in the DB for the selected file.<br>
         * if the tag exist then remove it from the file, else create a massage box to inform the user
         */
		override def handleEvent(e:Event) {
		  
		  var tagName = textfield.getText()
		  if(tagName.isEmpty()){
		    new Massage("Please fill all the fields",shell)
		    return
		  }
		  
		  if(DBConnector.isTaggedFileExist(fileSelected,tagName)){
		    DBConnector.removeTaggedFile(fileSelected,tagName)
		    new Massage("The tag: "+tagName+" removed from the file: "+fileSelected+" successfully", shell)
		  }
		  else
		    new Massage("The tag: "+tagName+" isn't exist to the file: "+fileSelected, shell)	
		  child.close();
		}
	} 
}