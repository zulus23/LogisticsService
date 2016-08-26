name := "play-java"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava,PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.webjars" %% "webjars-play" % "2.5.0",
  "org.webjars" % "bootstrap" % "3.3.7",
  "org.webjars" % "underscorejs" % "1.8.3",
  "org.webjars" % "jquery" % "2.2.4",
  "org.webjars" % "font-awesome" % "4.6.3"

)
