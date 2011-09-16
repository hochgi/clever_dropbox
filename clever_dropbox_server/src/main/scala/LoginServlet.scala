package cleverdropbox

import java.io.IOException
import javax.servlet.http.{HttpServlet,HttpServletResponse,HttpServletRequest}
import javax.servlet.ServletException

class LoginServlet extends HttpServlet {
  
  // TODO: connect to DB
  
  override def doPost(req : HttpServletRequest, res : HttpServletResponse) = res.getWriter().println("""<html><title>scala</title><body><a href="http://www.google.com">dana</a></body></html>""")

}