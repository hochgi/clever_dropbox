package enumeration


/**
 * Operation object
 * extends Enumeration
 * represent operation. the values are: AND - intersection, OR - union
 */
object Operation extends Enumeration{
  type Operation = Value
  val AND, OR = Value
}

