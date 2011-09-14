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
 * AddTagDialog class .<br>
 * extends TagEventDialog.<br>
 * @param parent shell parent.<br>
 * @param fileSelected file that selected by the user.<br>
 * create a shell dialog to add a new tag to the selected file
 */
class AddTagDialog(parent2:Shell,fileSelected2:String) extends TagEventDialog(parent2,fileSelected2){
  
	var textfield:Text = null
	var shell = parent2
	var textfieldLData = new FormData();
	textfieldLData.left =  new FormAttachment(0, 1000, 12);
	textfieldLData.top =  new FormAttachment(0, 1000, 48);
	textfieldLData.width = 289;
	textfieldLData.height = 16;
	textfield = new Text(composite, SWT.BORDER);
	textfield.setLayoutData(textfieldLData);

	if(fileSelected!=null){
		// add the file name that was chosen 
		fileName = new Label(composite, SWT.NONE);
		fileNameLData = new FormData();
		fileNameLData.left =  new FormAttachment(0, 1000, 142);
		fileNameLData.top =  new FormAttachment(0, 1000, 14);
		fileNameLData.width = 211;
		fileNameLData.height = 17;
		fileName.setLayoutData(fileNameLData);
		fileName.setText(fileSelected);
		fileName.setFont( new Font(Display.getDefault(),"Arial", 14, SWT.BOLD ) );
		
		mainLabel.setText("Add a new Tag to :")
	}
	else
	  mainLabel.setText("Add a new file Tag :")
	    
	/**
	 * add listener to the OK button
	 */
	ok.addListener(SWT.Selection, new addNewTag())	
	// set the layout
    composite.layout();
	
	
	
    /**
     * addNewTag class.<br>
     * extends Listener.<br>
     * this class when getting event, extract the tag name,
     * and connect to the DB for adding the tag to the selected file
     */
    class addNewTag extends Listener{
      
    	/**
    	 * handleEvent.<br>
    	 * @param e an Event.<br>
    	 * the handle method of the listener.<br>
    	 * get the tag name from the text field of this shell.<br>
    	 * if the tag name is not an empty string then check if the tag is already exist in the DB for
    	 * the selected file, if not then create a new tagged file in the DB, else create a massage box
    	 * to inform the user
    	 */
		override def handleEvent(e:Event) {
		  
		  var tagName = textfield.getText()
		  if(tagName==null || tagName.trim().isEmpty()){
		    new Massage("Please fill all the fields", shell)
		    return
		  }
		  if(!DBConnector.isTaggedFileExist(fileSelected,tagName)){
			  DBConnector.addTaggedFile(fileSelected,tagName)
			  new Massage("Add tag: "+tagName+"\n"+"to the file: "+fileSelected, shell)
		  }
		  else
		    new Massage("The tag: "+tagName+" is already exist to the file: "+fileSelected, shell)
		  
		  child.close();
		}
	} 
}