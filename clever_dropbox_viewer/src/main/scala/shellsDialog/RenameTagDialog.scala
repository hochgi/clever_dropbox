package shellsDialog
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.layout.FormLayout
import org.eclipse.swt.layout.FormData
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
import massages.Massage
import dataBase.DBConnector



/**
 * RenameTagDialog
 * extends TagEventDialog.<br>
 * Create a shell dialog to rename tag to the selected file
 */
class RenameTagDialog(parent:Shell,fileSelected2:String) extends TagEventDialog(parent,fileSelected2){

   
	child.setSize(350,240)
	composite.setSize(338,240)
	var currentTagText:Text = null
	var currentTagLabel:Label = null
	var newTagText:Text = null
	var newTagLabel:Label = null
	var shell = parent
	var currentTagtextData = new FormData()
	currentTagtextData.left =  new FormAttachment(0, 1000, 24)
	currentTagtextData.top =  new FormAttachment(0, 1000, 64)
	currentTagtextData.width = 272
	currentTagtextData.height = 20
	currentTagText = new Text(composite, SWT.BORDER)
	currentTagText.setLayoutData(currentTagtextData)
	
	currentTagLabel = new Label(composite, SWT.NONE)
	var currentTagLData = new FormData()
	currentTagLData.left =  new FormAttachment(0, 1000, 24)
	currentTagLData.top =  new FormAttachment(0, 1000, 47)
	currentTagLData.width = 121
	currentTagLData.height = 17
	currentTagLabel.setLayoutData(currentTagLData)
	currentTagLabel.setText("Current Tag Name:")
			
	newTagLabel= new Label(composite, SWT.NONE)
	var newTagLabelData = new FormData()
	newTagLabelData.left =  new FormAttachment(0, 1000, 24)
	newTagLabelData.top =  new FormAttachment(0, 1000, 107)
	newTagLabelData.width = 106
	newTagLabelData.height = 17
	newTagLabel.setLayoutData(newTagLabelData)
	newTagLabel.setText("New Tag Name :")	
	
	var newTagTextData = new FormData()
	newTagTextData.left = new FormAttachment(0, 1000, 24)
	newTagTextData.top = new FormAttachment(0, 1000, 128)
	newTagTextData.width = 272
	newTagTextData.height = 20
	newTagText = new Text(composite, SWT.BORDER)
	newTagText.setLayoutData(newTagTextData)

	mainLabelData.left =  new FormAttachment(0, 1000, 28)
	mainLabelData.top =  new FormAttachment(0, 1000, 12)
	mainLabelData.width = 203
	mainLabelData.height = 17
		
	okLData.left =  new FormAttachment(0, 1000, 215)
	okLData.top =  new FormAttachment(0, 1000, 166)
	okLData.width = 50
	okLData.height = 31
	ok.setLayoutData(okLData)
	
	cancelLData.left =  new FormAttachment(0, 1000, 271)
	cancelLData.top =  new FormAttachment(0, 1000, 166)
	cancelLData.width = 59
	cancelLData.height = 31
	cancel.setLayoutData(cancelLData)

	if(fileSelected!=null){
		// add the file name that was chosen 
		mainLabelData.width = 200
		fileName = new Label(composite, SWT.NONE)
		fileNameLData = new FormData()
		fileNameLData.left =  new FormAttachment(0, 1000, 230)
		fileNameLData.top =  new FormAttachment(0, 1000, 12)
		fileNameLData.width = 100
		fileNameLData.height = 30
		fileName.setLayoutData(fileNameLData)
		fileName.setText(fileSelected)
		fileName.setFont( new Font(Display.getDefault(),"Arial", 14, SWT.BOLD ))
		
		mainLabel.setText("Choose tag to rename to file :")
	}
	else
		mainLabel.setText("Choose tag to rename :")

	//add listener to the OK button
	ok.addListener(SWT.Selection, new RenameTag())
	  
	// set the layout
    composite.layout()
	
	
	
	/**
	 * RenameTag class
	 * extends Listener
	 */
	class RenameTag extends Listener {
      
		override def handleEvent(e:Event) {
		  
		  var oldTag = currentTagText.getText()
		  var newTag = newTagText.getText()
		  if(oldTag.isEmpty() || newTag.isEmpty()){
		    new Massage("Please fill all the fields", shell)
		    return
		  }
		  if(!DBConnector.isTaggedFileExist(fileSelected,oldTag)){
		    new Massage("The tag: "+oldTag+" is not exist to the file: "+fileSelected, shell)
		    return
		  }
		  if(DBConnector.isTaggedFileExist(fileSelected,newTag)){
		    new Massage("The tag: "+newTag+" is already exist to the file: "+fileSelected, shell)
		    return
		  }
		  DBConnector.renameTaggedFile(fileSelected,oldTag,newTag)
		  new Massage("The tag: "+oldTag+"\n"+"of the file: "+fileSelected+"\nchanged to: "+newTag, shell)
		  
		  child.close();
		}
	} 
}