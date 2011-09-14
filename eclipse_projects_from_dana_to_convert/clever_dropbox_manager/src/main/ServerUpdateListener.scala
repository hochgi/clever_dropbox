package main
import scala.actors.Actor


/**
 * ServerUpdateListener 
 * Connect to the server and listens to updates 
 */
class ServerUpdateListener extends Actor{
  
  def act(){
    println("ServerUpdateListener start")
    loop{
      react{
        
        // not implement because there is no real server..
        case "STOP" => exit
      }
    }  
  }
}