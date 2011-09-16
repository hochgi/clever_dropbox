package main
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.ToolBar
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.ToolItem
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.widgets.Menu
import org.eclipse.swt.widgets.MenuItem
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.layout.FormLayout
import org.eclipse.swt.widgets.Group
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.layout.FormData
import org.eclipse.swt.layout.FormAttachment
import org.eclipse.swt.widgets.ExpandBar
import org.eclipse.swt.layout.RowLayout
import org.eclipse.swt.widgets.ExpandItem
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.layout.RowData
import org.eclipse.swt.widgets.Listener
import org.eclipse.swt.widgets.Table
import org.eclipse.swt.widgets.TableItem
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Text
import org.eclipse.swt.widgets.Event
import scala.collection.mutable.ListBuffer
import enumeration.TagEvent._
import shellsDialog.AddTagDialog
import shellsDialog.RemoveTagDialog
import shellsDialog.RenameTagDialog
import dataBase.DBConnector
import massages.Massage
import scala.util.Sorting



/**
 * FileSearchView class .<br>
 * @param display.<br>
 * @param shell.<br>
 * Create and initialize the shell's components of the search files application.<br>
 * Adding Listeners to the shell widgets and contains Listeners class to handle the events 
 */
class FileSearchView (display2:Display, shell2:Shell){

  val display:Display = display2
  val shell:Shell = shell2
  var toolBar:ToolBar = null
  var logIn:ToolItem = null
  var connection:ToolItem = null
  var sync:ToolItem = null
  var settings:ToolItem = null
  var logInlistener:DropdownSelectionListener = null
  var connectionListener:DropdownSelectionListener = null
  var syncListener:DropdownSelectionListener = null
  var settingsListener:DropdownSelectionListener = null
  var composite:Composite = null;
  var smallComposite:Composite = null;
  var menu:Group = null
  var bar:ExpandBar = null
  var sortBy:ExpandItem = null
  var soryByName:Button = null
  var soryBySize:Button = null
  var sortByDate:Button = null
  var showAllFiles:Button = null
  var addNewTag:Button = null
  var removeTag:Button = null
  var renameTag:Button = null
  var openFile:Button = null
  var trashCan:Button = null
  var fileDetail:Table = null
  var tagsTable:Table = null
  var filedetailsLabel:TableItem = null
  var addTag:Label = null
  var searchBut:Button = null
  var clean:Button = null
  var cleanTags:Button = null
  var AllTags:Button = null
  var searchBy:Label = null
  var returnFiles:Label = null
  var plusTag:Button = null
  var filesTable:Table = null
  var tagTextArea:Text= null
  var orButton:Button = null
  var andButton:Button = null
  var currFiles = new ListBuffer[String]

  //initialize shell 
  shell.setText("clever DropBox");
  shell.setSize(800, 600);
  initGUI();
  shell.open()
  while (!shell.isDisposed()) {
    if (!display.readAndDispatch())
      display.sleep();
    }
  display.dispose();
  
  
  
  /**
   * initGUI 
   * create and initialized the widgets of the shell 
   * create a Listeners for the those widgets
   * */
  def initGUI() {

    // initialize the Main Tool Bar ,with white background.. 
    // initialize Tool Bar
    toolBar = new ToolBar(shell, SWT.BORDER/*_DASH*/)
    toolBar.setSize(794,54)
	toolBar.setBackground(display.getSystemColor(SWT.COLOR_WHITE))
	
	//add the Log in tool item
    logIn = new ToolItem(toolBar, SWT.DROP_DOWN)
    logIn.setImage(new Image(display,"/home/giladhoch/workspace/clever_dropbox/clever_dropbox_viewer/src/main/resources/dropBox.png"))
  
    //add the connection drop down Tool item        
    connection = new ToolItem(toolBar, SWT.DROP_DOWN)
    connection.setImage(new Image(display,"/home/giladhoch/workspace/clever_dropbox/clever_dropbox_viewer/src/main/resources/world.png"))
	        
    //add the synchronize drop down Tool item  
    sync = new ToolItem(toolBar, SWT.DROP_DOWN)
    sync.setImage(new Image(display,"/home/giladhoch/workspace/clever_dropbox/clever_dropbox_viewer/src/main/resources/sync.png"))
	        
    //add the synchronize drop down Tool item
    settings = new ToolItem(toolBar, SWT.DROP_DOWN)
    settings.setImage(new Image(display,"/home/giladhoch/workspace/clever_dropbox/clever_dropbox_viewer/src/main/resources/setting.png"))
	    

     // add listeners to the tools items
  
    logInlistener = new DropdownSelectionListener(logIn)
    logInlistener.add("Subscribe to New account")
    // listenerLogIn.add("")
    logIn.addSelectionListener(logInlistener)
    
    connectionListener = new DropdownSelectionListener(connection)
    connectionListener.add("Connect to the Server")
    connectionListener.add("Disconnect from the Server")
    connection.addSelectionListener(connectionListener)
    
    syncListener = new DropdownSelectionListener(sync)
    syncListener.add("Synchronize with the Server")
    sync.addSelectionListener(syncListener)
    
   // settingsListener = new DropdownSelectionListener(settings)
   // settingsListener.add("change")
        
    // initialize the main composite of the view    
    composite = new Composite(shell, SWT.NONE)
    var thisLayout = new FormLayout
    composite.setLayout(thisLayout)
    composite.setSize(788,590);
    {
      // add group menu for the Buttons
      menu = new Group(composite, SWT.NONE)
      var menuLayout = new GridLayout()
      menuLayout.makeColumnsEqualWidth = true
      menu.setLayout(menuLayout)
      var menuLData = new FormData()
      menuLData.left =  new FormAttachment(0, 1000, 585)
      menuLData.top =  new FormAttachment(0, 1000, 159)
      menuLData.width = 183
      menuLData.height = 364
      menu.setLayoutData(menuLData);
	  {
        bar = new ExpandBar(menu, SWT.NONE)
		smallComposite = new Composite(bar, SWT.NONE)
		smallComposite.setLayout(new RowLayout(SWT.HORIZONTAL))

		sortBy = new ExpandItem (bar, SWT.NONE, 0)
		var barData = new GridData()
		barData.widthHint = 179
		barData.heightHint = 90
		bar.setLayoutData(barData)
		sortBy.setHeight(smallComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y)
		sortBy.setControl(smallComposite)
		sortBy.setText("Sory By :")
		sortBy.setImage(new Image(display, "/home/giladhoch/workspace/clever_dropbox/clever_dropbox_viewer/src/main/resources/sortIcon.jpg"))
		sortBy.setHeight(60)
		sortBy.setHeight(60);
		{
			soryByName = new Button(smallComposite, SWT.RADIO | SWT.LEFT);
			var soryByNameData = new RowData();
			soryByName.setLayoutData(soryByNameData);
			soryByName.setText("Name");
			soryByName.addListener(SWT.Selection, new SortFiles())
		}
		{
			soryBySize = new Button(smallComposite, SWT.RADIO | SWT.LEFT);
			var soryBySizeData = new RowData();
			soryBySize.setLayoutData(soryBySizeData);
			soryBySize.setText("Size");
		}
		{
			sortByDate = new Button(smallComposite, SWT.RADIO | SWT.LEFT);
			var sortByDateData = new RowData();
			sortByDate.setLayoutData(sortByDateData);
			sortByDate.setText("Date");
		}
			//bar.setSpacing(2);
			bar.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
			smallComposite.setBackground(display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
			
		}
  		{
			showAllFiles = new Button(menu, SWT.PUSH | SWT.CENTER);
			var shoeAllFilesLData = new GridData();
			shoeAllFilesLData.widthHint = 173;
			shoeAllFilesLData.heightHint = 32;
			showAllFiles.setLayoutData(shoeAllFilesLData);
			showAllFiles.setText("Show All Files");
			showAllFiles.setBackground(display.getSystemColor(SWT.COLOR_WHITE)); 
			showAllFiles.setImage(new Image(Display.getDefault(),"/home/giladhoch/workspace/clever_dropbox/clever_dropbox_viewer/src/main/resources/showFiles.png"));
			showAllFiles.addListener(SWT.Selection, new showAllFilesListener());
			
  		}
  		{
			addNewTag = new Button(menu, SWT.PUSH | SWT.CENTER)
			var newTagLData = new GridData()
			newTagLData.widthHint = 173
			newTagLData.heightHint = 32
			addNewTag.setLayoutData(newTagLData)
			addNewTag.setText("Add new Tag")
			addNewTag.setBackground(display.getSystemColor(SWT.COLOR_WHITE))
			addNewTag.setImage(new Image(Display.getDefault(),"/home/giladhoch/workspace/clever_dropbox/clever_dropbox_viewer/src/main/resources/addTag.png"))
			addNewTag.addListener(SWT.Selection, new AddNewFileTagListener())
		}
  		{
			removeTag = new Button(menu, SWT.PUSH | SWT.CENTER)
			var removeTagLData = new GridData()
			removeTagLData.widthHint = 173
			removeTagLData.heightHint = 32
			removeTag.setLayoutData(removeTagLData)
			removeTag.setText("Remove Tag")
			removeTag.setBackground(display.getSystemColor(SWT.COLOR_WHITE))
			removeTag.setImage(new Image(Display.getDefault(),"/home/giladhoch/workspace/clever_dropbox/clever_dropbox_viewer/src/main/resources/removeTag.png"))
			removeTag.addListener(SWT.Selection, new RemoveFileTagListener())
		}
		{
			renameTag = new Button(menu, SWT.PUSH | SWT.CENTER)
			var renameTagLData = new GridData()
			renameTagLData.widthHint = 173
			renameTagLData.heightHint = 32
			renameTag.setLayoutData(renameTagLData)
			renameTag.setText("Rename Tag")
			renameTag.setBackground(display.getSystemColor(SWT.COLOR_WHITE))
			renameTag.setImage(new Image(Display.getDefault(),"/home/giladhoch/workspace/clever_dropbox/clever_dropbox_viewer/src/main/resources/renameTag.png"))
			renameTag.addListener(SWT.Selection, new RenameFileTagListener())
		}
		{
			openFile = new Button(menu, SWT.PUSH | SWT.CENTER)
			var openFileLData = new GridData()
			openFileLData.widthHint = 173
			openFileLData.heightHint = 32
			openFile.setLayoutData(openFileLData)
			openFile.setText(" Open File  ")
			openFile.setBackground(display.getSystemColor(SWT.COLOR_WHITE))
			openFile.setImage(new Image(Display.getDefault(),"/home/giladhoch/workspace/clever_dropbox/clever_dropbox_viewer/src/main/resources/openFile.png"))
			openFile.addListener(SWT.Selection, new Listener(){
			    override def handleEvent(e:Event) {
			      new Massage(" not working yet ",shell)
			    }
			 })
		}
		{
			trashCan = new Button(menu, SWT.PUSH | SWT.CENTER);
			var trashImg = new Image(Display.getDefault(),"/home/giladhoch/workspace/clever_dropbox/clever_dropbox_viewer/src/main/resources/trash.png");
			var button5LData = new GridData();
			button5LData.widthHint = 173;
			button5LData.heightHint = 75
			trashCan.setLayoutData(button5LData);
			trashCan.setText(" Delete File ");
			trashCan.setImage(trashImg);
			trashCan.setBackground(display.getSystemColor(SWT.COLOR_WHITE));	
			trashCan.addListener(SWT.Selection, new Listener(){
			    override def handleEvent(e:Event) {
			      new Massage(" not working yet ",shell)
			    }
			 })
		}
		
      }// end of composite
	  menu.layout();
	  {
		var fileDetailLData = new FormData();
		fileDetailLData.left =  new FormAttachment(0, 1000, 585);
		fileDetailLData.top =  new FormAttachment(0, 1000, 60);
		fileDetailLData.width = 165;
		fileDetailLData.height = 74;
		fileDetail = new Table(composite , SWT.NONE | SWT.BORDER);
		fileDetail.setLayoutData(fileDetailLData);
		{
			filedetailsLabel = new TableItem(fileDetail, SWT.NONE);
			filedetailsLabel.setText(" File Details :");
		}
	  }
	  {
		addTag = new Label(composite, SWT.NONE);
		val addTagLData = new FormData();
		addTagLData.left =  new FormAttachment(0, 1000, 23);
		addTagLData.top =  new FormAttachment(0, 1000,98);
		addTagLData.width = 56;
		addTagLData.height = 17;
		addTag.setLayoutData(addTagLData);
		addTag.setText("Add Tag:");
	}
	{
		orButton = new Button(composite, SWT.RADIO | SWT.CENTER);
		val orButtonData = new FormData();
		orButtonData.left =  new FormAttachment(0, 1000, 209);
		orButtonData.top =  new FormAttachment(0, 1000, 120);
		orButtonData.width = 50;
		orButtonData.height = 29;
		orButton.setLayoutData(orButtonData);
		orButton.setText("OR");
	}
	{
		andButton = new Button(composite, SWT.RADIO | SWT.CENTER);
		val andButtonData = new FormData();
		andButtonData.left =  new FormAttachment(0, 1000, 259);
		andButtonData.top =  new FormAttachment(0, 1000, 120);
		andButtonData.width = 55;
		andButtonData.height = 29;
		andButton.setLayoutData(andButtonData);
		andButton.setText("AND");
	}
	{
		searchBut = new Button(composite, SWT.PUSH | SWT.CENTER);
		val searchButData = new FormData();
		searchButData.left =  new FormAttachment(0, 1000, 320);
		searchButData.top =  new FormAttachment(0, 1000, 120);
		searchButData.width = 70;
		searchButData.height = 29;
		searchBut.setLayoutData(searchButData);
		searchBut.setText("Search");
		searchBut.setBackground(display.getSystemColor(SWT.COLOR_WHITE))
		searchBut.addListener(SWT.Selection, new SearchFilesListener)
	}
	{
		clean = new Button(composite, SWT.PUSH | SWT.CENTER);
		var button7LData = new FormData();
		button7LData.left =  new FormAttachment(0, 1000, 400);
		button7LData.top =  new FormAttachment(0, 1000, 120);
		button7LData.width = 70;
		button7LData.height = 29;
		clean.setLayoutData(button7LData)
		clean.setText("Clear All")
		clean.setBackground(display.getSystemColor(SWT.COLOR_WHITE))
		clean.addListener(SWT.Selection, new Listener(){
		  override def handleEvent(e:Event) {
		    filesTable.removeAll()
		    fileDetail.removeAll()
		  }
		})
	}
	{
		cleanTags = new Button(composite, SWT.PUSH | SWT.CENTER)
		var cleanTagsLData = new FormData()
		cleanTagsLData.left =  new FormAttachment(0, 1000, 20)
		cleanTagsLData.top =  new FormAttachment(0, 1000, 530)
		cleanTagsLData.width = 87
		cleanTagsLData.height = 25
		cleanTags.setLayoutData(cleanTagsLData)
		cleanTags.setText("remove All")
		cleanTags.setBackground(display.getSystemColor(SWT.COLOR_WHITE))
		cleanTags.addListener(SWT.Selection, new Listener(){
		  override def handleEvent(e:Event) {
		    tagsTable.removeAll()
		  }
		})
	}
	{
		AllTags = new Button(composite, SWT.RADIO | SWT.LEFT);
		var AllTagsLData = new FormData();
		AllTagsLData.left =  new FormAttachment(0, 1000, 83);
		AllTagsLData.top =  new FormAttachment(0, 1000, 98);
		AllTagsLData.width = 75;
		AllTagsLData.height = 16;
		AllTags.setLayoutData(AllTagsLData);
		AllTags.setText("All Tags");
		AllTags.addListener(SWT.Selection, new showAllTagsListener);
	}
	{
		searchBy = new Label(composite, SWT.NONE);
		var searchByData = new FormData();
		searchByData.left =  new FormAttachment(0, 1000, 21);
		searchByData.top =  new FormAttachment(0, 1000, 60);
		searchByData.width = 107;
		searchByData.height = 20;
		searchBy.setLayoutData(searchByData);
		searchBy.setText("Search by Tags:");
	}
	{
		returnFiles = new Label(composite, SWT.NONE);
		var returnFilesData = new FormData();
		returnFilesData.left =  new FormAttachment(0, 1000, 172);
		returnFilesData.top =  new FormAttachment(0, 1000, 60);
		returnFilesData.width = 90;
		returnFilesData.height = 18;
		returnFiles.setLayoutData(returnFilesData);
		returnFiles.setText("Return Files :");
	}
	{
		var table2LData = new FormData();
		table2LData.left =  new FormAttachment(0, 1000, 20);
		table2LData.top =  new FormAttachment(0, 1000, 158);
		table2LData.width = 125;
		table2LData.height = 345;
		tagsTable = new Table(composite, SWT.CHECK | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		tagsTable.setLayoutData(table2LData);
	}
	{
		plusTag = new Button(composite, SWT.PUSH | SWT.CENTER);
		var button1LData = new FormData();
		button1LData.left =  new FormAttachment(0, 1000, 175);
		button1LData.top =  new FormAttachment(0, 1000, 120);
		button1LData.width = 29	;
		button1LData.height = 29;
		plusTag.setLayoutData(button1LData);
		plusTag.setImage(new Image(Display.getDefault(),"/home/giladhoch/workspace/clever_dropbox/clever_dropbox_viewer/src/main/resources/plus.png"));
		plusTag.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		plusTag.addListener(SWT.Selection, new Listener(){
		  override def handleEvent(e:Event) {
		    var tag = tagTextArea.getText();
		    if(tag==null || tag.trim().isEmpty())
		      return
		    var item = new TableItem(tagsTable, SWT.NONE);
		    item.setText(tag);
		    
		  }
		})
	}
	{
		var table1LData = new FormData();
		table1LData.left =  new FormAttachment(0, 1000, 172);
		table1LData.top =  new FormAttachment(0, 1000, 158);
		table1LData.width = 386;
		table1LData.height = 346;
		filesTable = new Table(composite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		filesTable.setLayoutData(table1LData);
		filesTable.addListener(SWT.Selection, new fileTableListener());
	
	}
	{
		var text1LData = new FormData();
		text1LData.left =  new FormAttachment(0, 1000, 20);
		text1LData.top =  new FormAttachment(0, 1000, 122);
		text1LData.width = 142;
		text1LData.height = 22;
		tagTextArea = new Text(composite, SWT.WRAP | SWT.BORDER);
		tagTextArea.setLayoutData(text1LData);
	}
	composite.layout();
    }//end of initGui()       
  
  
  
  /**
   * openShellDialog.<br>
   * @param tagEvent represent one of the set {add tag, remove tag, rename tag}.<br>
   * @param fileSelected represent the selected file
   * Create a shell dialog according to the tagEvent
   */
  def openShellDialog(tagEvent:TagEvent,fileSelected:String){
    tagEvent match {
      case ADD => println("add"); new AddTagDialog(shell,fileSelected)
      case REMOVE => println("remove"); new RemoveTagDialog(shell,fileSelected)
      case RENAME => println("rename"); new RenameTagDialog(shell,fileSelected)
    }
  }
  
  
  
  /**
   * handleTagsEvent.<br>
   * @param tagEvent represent one of the set {add tag, remove tag, rename tag}.<br>
   * call to openShellDialog with tagEvent and the selected file.<br>
   * or if there is no file selected then create an appropriate massage for the user
   */
  def handleTagsEvent(tagEvent:TagEvent){
  
       var selection = filesTable.getSelection();
        if(selection==null || selection.isEmpty){
          new Massage("No file selected",shell)
        }
        else{
          var fileSelected = "";
          for (i <-0 to selection.length-1)
            fileSelected += selection(i).getText() + " ";
          openShellDialog(tagEvent,fileSelected)
          println("File selected : "+fileSelected)
        }
  }
  
 
  
  def showFiles(files:ListBuffer[String]){
    
    if(files==null || files.isEmpty)
      return
    currFiles.clear()
    filesTable.removeAll()
    for(f<-files){
    	var file = new TableItem(filesTable, SWT.NONE)
		file.setText(f)
		currFiles+=f
    }
  }
  
	  
  /**
   * AddNewFileTagListener class.<br>
   * extends Listener.<br>
   * call handleTagsEvent with ADD value
   */
  class AddNewFileTagListener extends Listener{
      override def handleEvent(e:Event) {
       handleTagsEvent(ADD)
       }
  }
  
    
  
  /**
  * RemoveFileTagListener class.<br>
  * extends Listener.<br>
  * call handleTagsEvent with the REMOVE value
  */
  class RemoveFileTagListener extends Listener{
      override def handleEvent(e:Event) {
        handleTagsEvent(REMOVE)
       }
	}
 

  
  /**
  * RenameFileTagListener class.<br>
  * extends Listener.<br>
  * call handleTagsEvent with REMOVE value
  */
  class RenameFileTagListener extends Listener{
      override def handleEvent(e:Event) {
        handleTagsEvent(RENAME)
       }
	}
  
  
  
  /**
   * SortFiles class.<br>
   * extends Listener.<br>
   * sort the current files and show them in the files table
   */
  class SortFiles extends Listener {
    override def handleEvent(e:Event) {
        var files = currFiles.sortWith((e1, e2) => (e1 compareTo e2) < 0)
        showFiles(files)
       }
  }
  
  
  
  /**
   * showAllTagsListener class.<br>
   * extends Listener.<br>
   * gets all existing tags and print them in the tagsTable
   */
  class showAllTagsListener extends Listener{
    
    override def handleEvent(e:Event) {
		tagsTable.removeAll()
		var tags = DBConnector.getAllTags()
		if(tags==null)
		  return
		// for each tag create an item to the tags Table with the name of the tag
		tags.foreach{t=> 
		  {
		    var tag = new TableItem(tagsTable, SWT.NONE)
		    tag.setText(t)
		  }
		}
    }
  }
  
  
  
  /**
   * showAllFilesListener class.<br>
   * extends Listener.<br>
   * when the showAllFiles button is pushed, this class gets all the files from the DB 
   * and show the files in the filesTable
   */
  class showAllFilesListener extends Listener{
    
    override def handleEvent(e:Event) {
		//show all files in the directory
		filesTable.removeAll()
		var files = DBConnector.getAllFiles()
		if(files==null)
		  return
		// for each file create an item to the files Table with the name of the file
		showFiles(files)
	  }
  }
  
  
  
  /**
   * fileTableListener class.<br>
   * extends Listener.<br>
   * when file selected in the file table, this class gets the selected file's details from the DB 
   * and print its details to the fileDetail Table
   */
  class fileTableListener extends Listener{
    
    var selection:List[TableItem]=null ;
	var file_Name:TableItem=null;
	var file_Size:TableItem=null;
	var file_Date:TableItem=null;
	
	override def handleEvent(e:Event) {
	  
	  var fileName:String = "";
	  fileDetail.removeAll();
      var selection = filesTable.getSelection();
        
      //System.out.println(selection[0]);
      for (i <-0 to selection.length-1)
       fileName += selection(i).getText() + " ";    
        
      // took the file by its fileName
        
      // show file Details on the fileDetail table
      file_Name = new TableItem(fileDetail, SWT.NONE);
      file_Size = new TableItem(fileDetail, SWT.NONE);
      file_Date = new TableItem(fileDetail, SWT.NONE);
      file_Name.setText("File name : "+fileName);
      file_Size.setText("File size : 1.49 KB"); // for now its a fake size
      file_Date.setText("Date created : Wed 07 Sep 2011 06:10:25 PM IDT");// for now its a fake date
       
      }
	}//end of class fileTableListener
   
  
  
  /**
   * DropdownSelectionListener class
   * extends SelectionAdapter
   * @param dropdown2 ToolItem
   * this class handle the selected Tool's items
   */
  class DropdownSelectionListener(dropdown2:ToolItem) extends SelectionAdapter {
    
	var dropdown:ToolItem = dropdown2
	var menu:Menu = new Menu(dropdown.getParent().getShell());

	  def add(item:String) {
	    var menuItem = new MenuItem(menu, SWT.NONE);
	    menuItem.setText(item);
	    menuItem.addSelectionListener(new SelectionAdapter() {
	    override def widgetSelected(event:SelectionEvent) {
	        var selected = event.widget.asInstanceOf[MenuItem]
	        var selectionName = selected.getText()
	        selectionName match {
	          case "Subscribe to New account" => new Massage(" not working yet ",shell)
	          case "Connect to the Server" => new Massage(" not working yet ",shell)
	          case "Disconnect from the Server" => new Massage(" not working yet ",shell)
	          case "Synchronize with the Server"=> new Massage(" not working yet ",shell)
	        }  
	       // dropdown.setText(selected.getText())
	      }
	    });
	  }

	  override def widgetSelected(event:SelectionEvent ) {
	    if (event.detail == SWT.ARROW) {
	      var item = event.widget.asInstanceOf[ToolItem]
	      var rect = item.getBounds()
	      var pt = item.getParent().toDisplay(new Point(rect.x, rect.y))
	      menu.setLocation(pt.x, pt.y + rect.height)
	      menu.setVisible(true)
	    } else {
	      System.out.println(dropdown.getText() + " Pressed")
	      }
	    }
  }//end of DropdownSelectionListener
  
  
  
  /**
   * SearchFilesListener class 
   * extends Listener
   * this class handle the search of files by the chosen tags items in the tagsTable
   * send the chosen tags to the DBConnector with the chosen operator {AND,OR}
   * create new items in the filesTable that represent the files that return as a result from the DBConnector
   */
  class SearchFilesListener extends Listener{
    
    override def handleEvent(e:Event) {
      
      // remove the current items from the filesTable
      filesTable.removeAll()
      
      // get the chosen items in the tagsTable
      var selectedItems = tagsTable.getItems() 
      var selectedTags = new ListBuffer[String]
      for( item <- selectedItems){
        if(item.getChecked()){
	     selectedTags += item.getText()
	    }
      }
      // if there is no chosen items in the tagsTable then return
      if(selectedTags.isEmpty){
        new Massage(" Please select tags to search",shell)
        return
      }
      // get the operation of the search {AND,OR}
      var operator:String = null
      if(orButton.getSelection())
        operator = "OR"
      else if(andButton.getSelection())
        operator = "AND"
      else{
          new Massage("Please choose an operation : \n " +
         		"'AND' -> to receive the intersection of the files that meet the chosen tags\n " +
         		"'OR' -> to receive the union of the files that meet the chosen tags\n ",shell)
         		return
      }
      // send the chosen tags to the DBConnector with the chosen operator {AND,OR}
      var returnFiles = DBConnector.getFilesByTags(selectedTags,operator)
      if(returnFiles!=null){       
        showFiles(returnFiles)
        }
      }
    }

  
}
