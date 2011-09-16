package dataBase
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import org.hibernate.cfg.Configuration
import scala.actors.Actor


/**
 * DataBaseManager object.<br>
 * provides an API to add and remove tagged file from the DB
 */
object DataBaseManager{
 
  var sessionFactory = null.asInstanceOf[SessionFactory] 
  var session = null.asInstanceOf[Session]
  
  
  /**
   * addTaggedFile.<br>
   * @param fileName the name of the file .<br>
   * @param tag the name of the tag.<br>
   * Create a new sessionFactory and session.<br>
   * Create a new TaggedFile with the given params and save in DB
   */
  def addTaggedFile(fileName:String, tag:String){
    
    sessionFactory = new Configuration().configure().buildSessionFactory()
    session = sessionFactory.openSession()
    var taggedFileManager = new TaggedFileManager(session)
    var taggedFile = new TaggedFile
    taggedFile.setTag(tag)
    taggedFile.setFile(fileName)   
    taggedFileManager.saveOrUpdate(taggedFile)
    session.flush()
    session.close()
    
  }
  
  
  /**
   * removeTaggedFile.<br>
   * @param fileName the name of the file .<br>
   * @param tag the name of the tag.<br>
   * Create a new sessionFactory and session.<br>
   * Create a new TaggedFile with the given params and delete from DB
   */
  def removeTaggedFile(fileName:String,tagName:String){
    
    sessionFactory = new Configuration().configure().buildSessionFactory()
    session = sessionFactory.openSession()
    val tf = new TaggedFile
    tf.setFile(fileName)
    tf.setTag(tagName)
    var taggedFileManager = new TaggedFileManager(session)
    taggedFileManager.delete(tf)
    session.flush()
    session.close()
    
  }
  
  
  /**
   * deleteFile.<br>
   * @param fileName the name of the file.<br>
   * Create a new sessionFactory and session.<br>
   * Delete each tagged file in the DB where file name fetch the given fileName
   */
  def deleteFile(fileName:String){

  sessionFactory = new Configuration().configure().buildSessionFactory()
  session = sessionFactory.openSession()
  var deleteTransaction = session.beginTransaction()
  var hqlDelete = "delete TaggedFile where file = :fileName"
  var deletedEntities = session.createQuery( hqlDelete ).setString( "fileName", fileName ).executeUpdate()
   deleteTransaction.commit()  
   session.flush()
   session.close()

  }
}