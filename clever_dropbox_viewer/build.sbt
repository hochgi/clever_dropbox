name := "clever_dropbox_viewer"

version := "0.1"

organization := "GilaDana"

scalaVersion := "2.9.0-1"


    resolvers ++= Seq(
        "eclipse repo" at "http://repo1.maven.org/eclipse/",
        "eclipse repo2" at "http://repo1.maven.org/maven2/",
        "mvn Repo" at "http://mvnrepository.com/",
        "mvn Repo2" at "http://mvnrepository.com/artifact/",
        "JavaNet1Repository" at "http://download.java.net/maven/1/",
        "DefaultMavenRepository" at "http://repo1.maven.org/maven2/",
        "plugin" at "https://github.com/hochgi/clever_dropbox/tree/master/clever_dropbox_library/",
        "Scala-Tools Maven2 Repository" at "http://scala-tools.org/repo-releases/",
        "Java.net Repository for Maven" at "http://download.java.net/maven/2/",
        "Akka Repository" at "http://akka.io/repository/",
        "jarvana" at "http://www.jarvana.com/jarvana/browse/",
        "grepcode Repo" at "http://grepcode.com/snapshot/repo1.maven.org/maven2/"
    )

    libraryDependencies ++= Seq(
        "GilaDana" % "clever_dropbox_library_2.9.0-1" % "0.1",
        "org.eclipse.swt.gtk.linux" % "x86" % "3.3.0-v3346",
        "org.eclipse.swt.gtk.linux" % "x86_64" % "3.3.0-v3346",
        "org.eclipse.swt.gtk.linux" % "ppc" % "3.3.0-v3346",
        "mysql" % "mysql-connector-java" % "5.1.17",
        "commons-dbcp" % "commons-dbcp" % "1.4",
        "hibernate" % "antlr" % "2.7.5H3",
        "commons-collections" % "commons-collections" % "3.1",
        "commons-lang" % "commons-lang" % "2.4",
        "commons-logging" % "commons-logging" % "1.1.1",
        "org.eclipse.jdt" % "core" % "3.1.1",
        "dom4j" % "dom4j" % "1.6.1",
        "org.springframework" % "spring-hibernate3" % "2.0.5",
        "org.hibernate" % "hibernate-commons-annotations" % "3.2.0.Beta1",
        "org.hibernate" % "hibernate-core" % "3.6.2.Final",
        "javassist" % "javassist" % "3.9.0.GA",
        "org.ow2.spec.ee" % "ow2-jta-1.1-spec" % "1.0.2",
        "org.apache.lucene" % "lucene-core" % "3.1.0",
        "org.slf4j" % "slf4j-api" % "1.6.2",
        "org.slf4j" % "slf4j-jdk14" % "1.6.2"
    )

//        "org.eclipse.swt.motif.aix" % "ppc" % "3.3.0-v3346",
//        "mysql" % "mysql-connector-java" % "5.1.16",

