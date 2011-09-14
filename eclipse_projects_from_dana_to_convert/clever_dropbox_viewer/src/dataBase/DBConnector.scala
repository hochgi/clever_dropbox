package dataBase
import org.hibernate.SessionFactory
import org.hibernate.Session
import scala.collection.mutable.ListBuffer
import org.hibernate.cfg.Configuration


/**
 * DBConnector object.<br>
 * this object connect to the DB and provides an API for searching Files,
 * add ,remove and rename tag to a given file
 */
object DBConnector {
  
  var sessionFactory = null.asInstanceOf[SessionFactory] 
  var session = null.asInstanceOf[Session]
  var files:ListBuffer[String] = new ListBuffer[String]
  
  
  /**
   * getFilesByTags 
   * gets list of tags and operation , create a session and query for the DB
   * to get the files that meeting the tags by the given operation
   * @param tags the list of the chosen tags
   * @param operation the operation of the search {AND,OR}
   * return list of String that each string represent the file that return from the query
   */
  def getFilesByTags(tags:ListBuffer[String],operation:String):ListBuffer[String]={
    
    // create a session factory and session 
    sessionFactory = new Configuration().configure().buildSessionFactory()
    session = sessionFactory.openSession()
    
    // if there is no tags selected or the operation is null -> return null
    if(tags==null || tags.isEmpty || operation==null)
    	return null
    
    operation match{
      case "AND" =>
        {
		    var query = "Select file from TaggedFile tf where tf.tag='"+tags.first+"'"
		    var firstFiles = new ListBuffer[String]
		    var secondFiles = new ListBuffer[String]
		    // get the files that return from the first tag to the firstFiles variable
		    var firstQuery = session.createQuery(query).list.toArray   
		    for(q <- firstQuery){
		      firstFiles+=q.asInstanceOf[String].trim()
		    }
		    var res = firstFiles 
		    res.foreach{ f => println(f+" ,")}
		    for(i<-1 to tags.size-1){
		      secondFiles.clear
		      // get the files from the second tag(i) of the list to the secondFiles variable
		      var secondQuery = session.createQuery("Select file from TaggedFile tf where tf.tag='"+tags(i)+"'").list.toArray     
		      for(q <- secondQuery){
		        secondFiles+=q.asInstanceOf[String].trim()
		      }
		      println("second are : ");secondFiles.foreach{ f => println(f+" ,")}
		      // get the intersection of the files in the firstFiles with the secondFiles
		      res = firstFiles.intersect(secondFiles)		       
		      
		      // if the result is an empty set then return cause the next iteration will be empty again
		      if(res==null || res.isEmpty)
		    	  return null
		      // change the firstFiles to be the result of the intersection for the next iteration
		      firstFiles = res
		    }
		    res.foreach{ f => println(f+" ,")}
		    res // return the result
		}
        
      case "OR" =>
        {
          var res = new ListBuffer[String]
          // create a query from the chosen tags with the OR operation
          var buildQuery = new StringBuilder("tf.tag='"+tags.first+"'")
          for(i<-1 to tags.size-1){
            buildQuery.append(" OR tf.tag='"+tags(i)+"'")
          }
          // get the return files from the query
          var query = session.createQuery("Select file from TaggedFile tf where "+buildQuery).list.toArray
          for(q <- query){
            res+=q.asInstanceOf[String].trim()
          }
          // make a set of the list to avoid the same files
          val set = res.toSet
          res.clear()
          // return the files elements of the set to the result
          for(s <- set){
            res+=s
            println(s)
          }
          res // return the result
        }   
    } 
  }// end of getFilesByTags
  
  
  /**
   * getAllFiles
   * return all files in the DB as a ListBuffer of type String
   */
  def getAllFiles():ListBuffer[String]={
    
    // create a session factory and session 
    sessionFactory = new Configuration().configure().buildSessionFactory()
    session = sessionFactory.openSession()
    
    // create a query to get all files from the DB
    var query = session.createQuery("Select file from TaggedFile").list.toArray
    if(query==null)
      return null
      
    // make a set of the list to avoid the same files
    var fileSet = query.toSet
    var res = new ListBuffer[String]
    // return the files elements of the set to the result
    for(s <- fileSet)
      res+=s.asInstanceOf[String]
    res   
  }
  
  
  /**
   * getAllTags
   * return all tags in the DB as a ListBuffer of type String
   */
  def getAllTags():ListBuffer[String]={ 
    // create a session factory and session 
    sessionFactory = new Configuration().configure().buildSessionFactory()
    session = sessionFactory.openSession()
    
    // create a query to get all files from the DB
    var query = session.createQuery("Select tag from TaggedFile").list.toArray
    if(query==null)
      return null
      
    // make a set of the list to avoid the same files
    var fileSet = query.toSet
    var res = new ListBuffer[String]
    // return the files elements of the set to the result
    for(s <- fileSet)
      res+=s.asInstanceOf[String]
    res 
  }
  
  
  /**
   * addTaggedFile
   * @param tag 
   * @param fileName
   * add a new tag to the file
   */
  def addTaggedFile(fileName:String,tag:String){
    
    sessionFactory = new Configuration().configure().buildSessionFactory()
    session = sessionFactory.openSession()
    var taggedFileManager = new TaggedFileManager(session)
    var taggedFile = new TaggedFile
    taggedFile.setTag(tag)
    taggedFile.setFile(fileName)   
    taggedFileManager.saveOrUpdate(taggedFile)
    session.flush()
    
  }
   
  
  /**
   * removeTaggedFile
   * @param tag 
   * @param fileName
   * remove tag from file
   */
  def removeTaggedFile(fileName:String,tag:String){
    
    sessionFactory = new Configuration().configure().buildSessionFactory()
    session = sessionFactory.openSession()
    var taggedFileManager = new TaggedFileManager(session)
    var taggedFile = new TaggedFile
    taggedFile.setTag(tag)
    taggedFile.setFile(fileName)
    	taggedFileManager.delete(taggedFile)
    session.flush()
    
  }
    
  
  /**
   * renameTaggedFile
   * @param oldTag
   * @param newTag 
   * @param fileName
   * remove the old tag and add the new tag to the file
   */
  def renameTaggedFile(fileName:String,oldTag:String, newTag:String){
    
    removeTaggedFile(fileName,oldTag)
    addTaggedFile(fileName,newTag)  
  }
  
  
  /**
   * isTaggedFileExist
   * @param tag 
   * @param fileName
   * check if there is the file tagged with the given tag
   */
  def isTaggedFileExist(file:String,tag:String):Boolean={
    
    sessionFactory = new Configuration().configure().buildSessionFactory()
    session = sessionFactory.openSession()
    var query = session.createQuery("from TaggedFile tf where tf.file ='"+file.trim()+"'"+" and tf.tag='"+tag.trim()+"'").list
    if(query.isEmpty())
       false
    else
       true
    
  }
}