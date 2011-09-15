package cleverDropboxLibrary

import java.io.File
import scala.collection.mutable.ListBuffer

trait FileLabeler {
 
  def labelFile(file:File):ListBuffer[String]

}

