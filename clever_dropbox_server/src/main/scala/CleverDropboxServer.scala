package cleverDropboxServer

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import javax.servlet.ServletException
import java.io.IOException
import org.eclipse.jetty.server.{Server, Request}
import org.eclipse.jetty.server.handler.AbstractHandler
import org.eclipse.jetty.server.nio.SelectChannelConnector
import org.eclipse.jetty.servlet.{ServletHolder, ServletContextHandler}
import com.sun.jersey.spi.container.servlet.ServletContainer
import javax.ws.rs.{GET, Produces, Path, PathParam}


object WebRunner extends App{
  override def main(args: Array[String]) {

    val server = new Server(8080)
    val connector = new SelectChannelConnector()
    server.addConnector(connector)
    
    val holder:ServletHolder = new ServletHolder(classOf[ServletContainer])
    holder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig")
    holder.setInitParameter("com.sun.jersey.config.property.packages", "cleverDropboxServer")
    val context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS)
    context.addServlet(holder, "/*")
    server.start
    server.join
  }
}


@Path("/helloworld")
class TestResource {
    @GET
    //@produces("text/html")
    def hello() = {
        "<html><title>GilaDana Inc.</title>" +
        """<body><img src="http://www.h-online.com/imgs/43/5/5/4/4/8/6/a484cda3f9839fe6.png"></img> RULEZZZZZZZ!!!</body></html>"""
    }
}


@Path("/user/{username}")
class SomethingElse {
    @GET
    //@Produces("text/html")
    def goodbye(@PathParam("username") user:String):String = {
        "<html><title>GilaDana Inc.</title>" +
        "<body>" + user + " ROCKSSSSSSSS!!!</body></html>"
    }
}
