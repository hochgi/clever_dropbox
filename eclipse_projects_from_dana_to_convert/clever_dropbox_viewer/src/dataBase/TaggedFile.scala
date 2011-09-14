package dataBase


/**
 * TaggedFile class
 * extends Serializable
 * represent a bean class that contains : file and a file's tag
 */
class TaggedFile extends Serializable{
  
  private var tag = ""
  private var file = ""

    
  /**
   * getTag
   * return the tag field
   */
  def getTag:String={
    return tag;
  }
  
  
  /**
   * setTag
   * @param tag
   */
  def setTag(tag:String) {
	this.tag = tag;
  }
  
  
  /**
   * getFile
   * return the file field
   */
  def getFile:String={
	return file;
  }
  
 
  /**
   * setFile
   * @param file
   */
  def  setFile(file:String){
	this.file = file;
  }
  
  
  /**
   * equals
   * @param tf of type any
   * return true if this tag is equal to the given tagged file's tag
   * and this file is equal to the given tagged file's file
   */
  override def equals(tf:Any):Boolean = tf.isInstanceOf[TaggedFile] && {
		var taggedFile = tf.asInstanceOf[TaggedFile];
		this.tag.intern()==taggedFile.getTag.intern() && this.file.intern()==taggedFile.getFile.intern()
	}
}