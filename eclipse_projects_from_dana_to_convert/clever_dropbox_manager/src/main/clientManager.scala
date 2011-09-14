package main
import dataBase.DataBaseManager
import dataBase._
import filesTagSystem.DynamicClassLoader
import java.io.File
import scala.collection.mutable.ListBuffer
import watchDirectory.ServiceManager


/**
 * @author Dana Coller
 * the main object of the application .<br>
 * start the service in a new Thread.<br>
 * start the ExternalEventHandler Actor
 */
object clientManager {

  def main(args: Array[String]): Unit = 
  {
    val dirPath  = "/home/dana/cleverDropBox" // the path of the directory
    val service = new ServiceManager(dirPath) // an instance of the service
    val externalEventListener = new ExternalEventHandler(1122,service)
    val serverListener = new ServerUpdateListener
    val serviceThread = new Thread(service)
    serviceThread.start()
    externalEventListener.start()
    println("start Watching")
  }
}


