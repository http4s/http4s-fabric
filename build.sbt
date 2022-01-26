enablePlugins(TypelevelCiReleasePlugin)

val scala213 = "2.13.8"
val scala212 = "2.12.15"
val scala3 = "3.1.1"

name := "http4s-fabric"

scalaVersion := scala213
crossScalaVersions := Seq(scala213, scala212, scala3)

libraryDependencies ++= Seq(
  "com.outr" %% "fabric-parse" % "1.2.4",
  "org.http4s" %% "http4s-core" % "1.0.0-M30",
  "org.typelevel" %% "cats-effect" % "3.3.4"
)

developers := List(
  Developer(
    "darkfrog26",
    "Matt Hicks",
    "matt@matthicks.com",
    url("https://github.com/darkfrog26"))
)
startYear := Some(2022)
ThisBuild / tlBaseVersion := "0.11"