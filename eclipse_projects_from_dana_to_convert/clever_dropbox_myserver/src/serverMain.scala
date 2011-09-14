


object ServerMain extends App{
  
  override def main(args : Array[String]) : Unit = {
    
    val port : Int = 1122
    val addNewFile = new AddNewFile(port)
    addNewFile.start

  }
}
