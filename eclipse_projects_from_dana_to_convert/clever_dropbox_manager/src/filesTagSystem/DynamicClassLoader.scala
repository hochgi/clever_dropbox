package filesTagSystem
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import scala.collection.mutable.ListBuffer
import java.nio.file.Path
import labels.FileLabeler


/**
 * DynamicClassLoader object.<br>
 * responsible for load classes dynamically from a given directory.<br>
 * contain list of FileLabeler's instances and Provides API for label a given file
 */
object DynamicClassLoader {
  
   var pluginPath:String = "/home/dana/workspace/clever_dropbox_manager/src/tagsPluginsDir"
   var tagsList:ListBuffer[String]=new ListBuffer()
   var fileLabelers:ListBuffer[FileLabeler]=new ListBuffer()
   val url= new java.io.File(pluginPath).toURI().toURL()
   val urls= Array[URL](url)
   val classLoader = new URLClassLoader(urls)
   // get all the children files of the plugins directory
   var dir = new File(pluginPath)
   var children = dir.list.toArray
   var list = new ListBuffer[String]
   println(children.length);
   // create a list of classe's names that contains in the directory
   for(c <- children){
     print("the file :"+c+"\n")
     if(c.endsWith(".class") && !c.contains("FileLabeler")){
         var cut = c.lastIndexOf(".")
         var file = c.substring(0,cut)
    	 if(file!=null) list+=file
     }
   }
   var arr = list.toArray
   // load the classes
   loadClasses(arr)
   

   
   /**
    * set a new path to the directory that holds the plugins
    */
   def setPluginPath(pluginPath2:String){
     pluginPath = pluginPath2
   }
   
   
   
   /**
    * loadClasses .<br>
    * @param - Array of String - the names of the classes that implements FileLabeler.<br>
    * loads the classes from the plugins directory.<br>
    * save them in the fileLabelers listBuffer field 
    */
   def loadClasses(labelers:Array[String]){
     
     if(fileLabelers!=null)
       fileLabelers.clear()
     else
       fileLabelers = new ListBuffer()
       
     // Add the new classes of FileLabeler in the fileLabelers ListBuffer
     for(i<-0 to labelers.length-1){
       
       println("here")
       println(labelers(i))
       var currClass = classLoader.loadClass(labelers(i))
       println("here")
       val currlabeler = currClass.newInstance().asInstanceOf[FileLabeler]
       fileLabelers+=currlabeler     
     } 
   }
   
   
   
   /**
    * label.<br>
    * @param file of type File.<br>
    * For each FileLabeler class, send the file to the labelFile method that returns list of tags.<br>
    * and for each tag add it to the main lists of tags.<br>
    * return ListBuffer of String that represent the tags of this file 
    */
   def label(file:File):ListBuffer[String]={
     
     if(tagsList!=null)
       tagsList.clear()
     else
       tagsList = new ListBuffer()
     
     /* for each FileLabeler class in the fileLabelers ListBuffer execute the
        labelFile method of the given file and get the tags ListBuffer of this file
        for each tag in the given tags list add the tag in the tagsList buffer   */
     fileLabelers.foreach{ f=> f.labelFile(file).foreach{ s=> tagsList+=s}}
     return tagsList
   
   }
}