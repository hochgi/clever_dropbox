import cleverDropboxLibrary.FileLabeler
import scala.collection.mutable.ListBuffer
import java.io.File
import java.nio.file.Files


class TypeLabeler extends FileLabeler{
  
  override def labelFile(file:File):ListBuffer[String]={
   
    var list = new ListBuffer[String]
    var suffix = ""
    var fileName = file.getName()
    var cut = fileName.lastIndexOf(".")
    if(cut>0)
      suffix = fileName.substring(cut+1,fileName.length())
    if(suffix!=null && !suffix.trim().isEmpty()){
      list+=suffix
    }
    
    var path = file.toPath()
    var fileType = Files.probeContentType(path)
    list+=fileType
       
    return list;
  }
}
