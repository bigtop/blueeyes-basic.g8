import sbt._
import sbt.Keys._
import cc.spray.revolver.RevolverPlugin._
import com.github.retronym.SbtOneJar._

object AppBuild extends Build {

  val appResolvers = Seq(
    "Sonatype"     at "http://nexus.scala-tools.org/content/repositories/",
    "Scala Tools"  at "http://scala-tools.org/repo-snapshots/",
    "JBoss"        at "http://repository.jboss.org/nexus/content/groups/public/",
    "Akka"         at "http://akka.io/repository/",
    "ReportGrid"   at "http://nexus.reportgrid.com/content/repositories/public-snapshots",
    "GuiceyFruit"  at "http://guiceyfruit.googlecode.com/svn/repo/release/"
  )

  val scalaz        = "org.scalaz"              %% "scalaz-core"       % "7.0-SNAPSHOT"
  val blueeyesCore  = "com.reportgrid"          %% "blueeyes-core"     % "0.6.0-SNAPSHOT"
  val blueeyesMongo = "com.reportgrid"          %% "blueeyes-mongo"    % "0.6.0-SNAPSHOT"
  val blueeyesJson  = "com.reportgrid"          %% "blueeyes-json"     % "0.6.0-SNAPSHOT"
  val specs2        = "org.specs2"              %% "specs2"            % "1.8.1"
  val configgy      = "net.lag"                 %  "configgy"          % "2.0.0"
  val jodaTime      = "joda-time"               %  "joda-time"         % "2.0"
  val jodaConvert   = "org.joda"                %  "joda-convert"      % "1.1"
  val logback       = "ch.qos.logback"          %  "logback-classic"   % "1.0.0"

  def developmentConfigFile: File =
    new File("src/main/resources/development.conf")

  lazy val blueeyesSettings = Seq(
    resolvers := appResolvers,
    libraryDependencies ++= Seq(
      blueeyesCore,
      blueeyesMongo,
      blueeyesJson,
      specs2 % "test",
      configgy % "compile" intransitive(),
      logback
    ),
    exportJars := true,
    Revolver.reStartArgs := Seq("--configFile", developmentConfigFile.getCanonicalPath)
  )

  lazy val app = Project(
    id = "app",
    base = file(".")
  ).settings(
    Project.defaultSettings ++
    blueeyesSettings ++
    oneJarSettings ++
    Revolver.settings ++
    Seq(
      organization := "$organization;format="lower"$",
      version := "$version$"
    ) : _*
  )

}
