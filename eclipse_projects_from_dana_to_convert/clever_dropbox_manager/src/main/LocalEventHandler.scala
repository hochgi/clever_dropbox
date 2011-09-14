package main
import scala.actors.Actor
import java.io.File
import filesTagSystem.DynamicClassLoader
import dataBase.DataBaseManager
import scala.actors._
import scala.actors.Actor._
import scala.actors.remote._
import scala.actors.remote.RemoteActor._


/**
 * LocalEventHandler class.<br>
 * extends Actor.<br>
 * Listens to local events that receive from the directory .<br>
 * send the event to the remote actors of the server 
 */
class LocalEventHandler extends Actor{

  // an instance of a remote actor of the main server that handle new file 
  lazy val newFileServerConnector = select(Node("localhost", 1122), 'newFileManager)
  // an instance of a remote actor of the main server that handle file delete
  lazy val deleteFileServerConnector = select(Node("localhost", 1122), 'deleteFileManager)

  
  /**
   * The act method of the actor.<br>
   * handle the cases of new file and delete file.<br>
   */
  def act(){
    loop{
      react{
	      case ("ENTRY_CREATE",file:File) => 
	        { 
	          newFileHandle(file)
	          println("file :" +  file.getAbsolutePath()+ " added to the directory")	        
	        }
	      case ("ENTRY_DELETE",file:File) => 
	        {
	          deleteFileHandle(file)
	          println("file :" + file.getAbsolutePath()+" delete from the directory")
	        }
	      case "STOP" => exit
	      case _ => println("Unknown command")
      }
    }
  }
  
  
  
 /**
  * newFileHandle.<br>
  * @param file.<br>
  * label the new file.<br>
  * for each tag add a new tagged file in the DB.<br>
  * send the file to the newFileServerConnector remote actor
  */
  def newFileHandle(file:File)
  {
    
    var tagsList = DynamicClassLoader.label(file) // label the file
    if(tagsList==null || tagsList.isEmpty)
      return
    
    tagsList.foreach{ tag => if(tag!=null || tag.trim().isEmpty()) DataBaseManager.addTaggedFile(file.getName(),tag) }
    // send the file to the server
    newFileServerConnector ! file
  }
  
  
  
  /**
  * deleteFileHandle.<br>
  * @param file.<br>
  * delete the file from the DB
  * send the file to the newFileServerConnector remote actor
  */
  def deleteFileHandle(file:File)
  {
    DataBaseManager.deleteFile(file.getName())
    // send the file to the server
    deleteFileServerConnector ! file
  }
}