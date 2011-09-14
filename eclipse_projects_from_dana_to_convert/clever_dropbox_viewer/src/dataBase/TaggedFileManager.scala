package dataBase
import org.hibernate.Session


/**
 * TaggedFileManager
 * @param session 
 * provides API of save, update and delete tagged file from the DB
 */
class TaggedFileManager(session:Session){
  
  val mainSession ={ if(session == null) throw new RuntimeException("Invalid session object."); else session}
  
  
  /**
   * saveTaggedFile
   * @param taggedFile
   * save the given taggedFile
   */
  def saveTaggedFile(taggedFile:TaggedFile){
    mainSession.save(taggedFile)
  }  
  
  
  /**
   * updateTaggedFile
   * @param taggedFile
   * update the given taggedFile
   */
  def updateTaggedFile(taggedFile:TaggedFile){
    mainSession.update(taggedFile)
  }
    
  
  /**
   * saveOrUpdate
   * @param taggedFile
   * save or update the given taggedFile
   */
  def saveOrUpdate(taggedFile:TaggedFile){
    mainSession.saveOrUpdate(taggedFile)
  }
  
  
  /**
   * delete
   * @param taggedFile
   * delete the given taggedFile
   */
  def delete(taggedFile:TaggedFile){
     mainSession.delete(taggedFile)
  }
}