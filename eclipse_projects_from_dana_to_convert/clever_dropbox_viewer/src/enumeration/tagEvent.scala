package enumeration


/**
 * TagEvent object
 * extends Enumeration
 * represent tag event. the values are: ADD, REMOVE, RENAME
 */
object TagEvent extends Enumeration{
  type TagEvent = Value
  val ADD, REMOVE, RENAME = Value
}