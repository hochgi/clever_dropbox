package watchDirectory

import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.WatchEvent
import java.nio.file.WatchKey
import java.nio.file.WatchService
import java.nio.file.StandardWatchEventKinds._
import main.LocalEventHandler
import main.ServerUpdateListener


/**
 * ServiceManager is a Runnable class .<br>
 * @param directory path .<br>
 * Listens to events from the given directory.<br>
 * contain LocalEventHandler to handle the local events like new file or delete file.<br>
 * contain ServerUpdateListener that Listens to updates from the server
 */
class ServiceManager(dirPath: String) extends Runnable{
  
  var dir:Path=null
  var watcher:WatchService=null
  var key:WatchKey=null
  var stopWatch:Boolean=false
  var pauseWatch:Boolean = false
  var localEventHandler:LocalEventHandler = new LocalEventHandler
  var serverListener = new ServerUpdateListener
  var lock : AnyRef = new Object()


  /**
   * run.<br>
   * start the service that watch the current directory.<br>
   * start the LocalEventHandler actor.<br>
   * start the ServerUpdateListener actor
   */
  def run(){
    
    localEventHandler.start() //start the llocalEventHandler actor
    serverListener.start() // start the serverListener actor
    watcher = FileSystems.getDefault().newWatchService()
    dir =  FileSystems.getDefault().getPath(dirPath)
    
    try {
     if(watcher!=null)
      key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE);
 
    } catch{
      case x:IOException => System.err.println(x)
    }

    // watching the directory's events in a loop
    while(!stopWatch){
      // if the pauseWatch is true, then activate the wait method and pause the service
      if(pauseWatch){
        lock.synchronized{
			try {
			  lock.wait();
			} catch {case e:InterruptedException =>{}}
        }
      }
      
      
      try {
	      key = watcher.take() // wait until a new event is come
	      } catch {
	      case x:InterruptedException  => return
	      }
      
    val events = key.pollEvents(); // get the events from the container
  
    while(!events.isEmpty()){
      // get the file of the event
      val event = events.remove(0);
      val filename = event.context().asInstanceOf[Path];
      val kind = event.kind();
      val child = dir.resolve(filename);
      val currFile = child.toFile()
     
      // send to localEventHandler the type of the event and the file
      localEventHandler ! (kind.toString(),currFile)
  
      }
      // reset the key for continue getting more events
      val valid = key.reset();
      if (!valid) {
        stopWatch = true
        } 
    }
  }
    
  
  /**
   * pause the service that watch the current directory
   */
  def pause(){   	
	pauseWatch = true
  }
    
  
  /**
   * continue the service that watch the current directory
   */
  def continue(){  
	  	pauseWatch = false
	  	lock.synchronized{
    	lock.notifyAll()
    	}
  }
  
  
  /**
   * stop the service that watch the current directory
   */
  def stop(){
    localEventHandler ! "STOP"
    serverListener ! "STOP"
    stopWatch = true;
  }
}
