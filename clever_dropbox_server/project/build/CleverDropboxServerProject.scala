import sbt._
import de.element34.sbteclipsify._

class CleverDropboxServerProject(info: ProjectInfo) extends DefaultWebProject(info) with Eclipsify {

    val javanetDeps = "javanetDeps" at "http://download.java.net/maven/2/"
    val apacheSnap = "apache.snapshots" at "http://people.apache.org/repo/m2-snapshot-repository"
    val central = "central" at "http://repo1.maven.org/maven2"

//some might be needed repositories...
//    val snapshots = "jersey-server Snapshots" at "http://download.java.net/maven/2/"
//    val glassfish-repository = "Java.net Repository for Glassfish" at "http://download.java.net/maven/glassfish"

	val jersey_server = "com.sun.jersey" % "jersey-server" % "1.8"
	val jersey_core = "com.sun.jersey" % "jersey-core" % "1.8"
    val jersey_json = "com.sun.jersey" % "jersey-json" % "1.8"
    val jsr311 = "javax.ws.rs" % "jsr311-api" % "1.1.1"

//    val jetty6 = "org.mortbay.jetty" % "jetty" % "6.1.24" % "test"  // jetty is only need for testing (copied from: http://code.google.com/p/simple-build-tool/wiki/WebApplications)
    val jettyServer = "org.eclipse.jetty" % "jetty-server" % "8.0.0.M0"
    val jettyServlet = "org.eclipse.jetty" % "jetty-servlet" % "8.0.0.M0"

    val apacheCommons = "commons-io" % "commons-io" % "1.3.2"
    val servlet_api = "javax.servlet" % "servlet-api" % "2.5"
    val portlet_api= "portlet-api" % "portlet-api" % "1.0"

//some might be needed dependencies...

//    val jersey-multipart = "com.sun.jersey.contribs" % "jersey-multipart" % "1.8"
//
//    val jetty-plugin = "org.mortbay.jetty" % "jetty-plugin" % "6.1.24"
//    val = "" % "" % ""
}
