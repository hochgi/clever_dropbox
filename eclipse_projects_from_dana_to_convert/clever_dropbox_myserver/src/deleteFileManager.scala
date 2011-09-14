import scala.actors.remote.RemoteActor
import scala.actors.remote.RemoteActor._
import scala.actors.Actor
import scala.actors.Actor._
import java.io.File



class DeleteFileManager(port: Int) extends Actor {
def act() {
    alive(port)
    register('deleteFileManager, self)

    loop{
      react {
        case file:File => println("the file : "+file.getName()+" received to the server")
      
      }
      }
  }
}