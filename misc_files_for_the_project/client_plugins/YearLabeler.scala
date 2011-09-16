import cleverDropboxLibrary.FileLabeler
import scala.collection.mutable.ListBuffer
import java.util.Date
import java.io.File

class YearLabeler extends FileLabeler{
  
  override def labelFile(file:File):ListBuffer[String]={
    
    var date = file.lastModified()
    var fileDate = new Date(date)

    var list = new ListBuffer[String]
    list+=(fileDate.getYear()+1900).toString()
    return list;
  }
}
