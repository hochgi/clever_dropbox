package main
import scala.actors.remote.RemoteActor
import scala.actors.remote.RemoteActor._
import scala.actors.Actor
import scala.actors.Actor._
import watchDirectory.ServiceManager


/**
 * ExternalEventHandler class .<br>
 * Listens to external events .<br>
 * @param service a running service program
 * the ExternalEventHandler control the service operations
 * Works as a remote actor that listens to events on a given port
 */
class ExternalEventHandler(port:Int, s:ServiceManager) extends Actor {
  
  val service = s
  
   /**
   * the act method of the actor
   * receive acts of {PAUSE, CONTINUE, STOP, START} and activate the service
   */
  def act{
    // register as a remote actor with port and id name
    alive(port)
    register('externalEventListener, self)

    loop{
      react {
        
          case "PAUSE" => service.pause() 
	      case "CONTINUE" => service.continue()
	      case "STOP" => service.stop; exit()
	      case "START" => println("start service")
	      case _ => println("Unknown external event ")
	      
	    }
    }
  }
}