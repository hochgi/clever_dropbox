package main
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell


/**
 * FileSearchApplication .<br>
 * This is the file's search application .<br>
 * the user can search files by tags
 * also create, rename and remove tags to a specific file
 */
object FileSearchApplication {
  
  def main(args : Array[String]) : Unit = {
     val display = new Display();
	 val shell = new Shell(display);
     val view = new FileSearchView(display,shell)
  }
}
