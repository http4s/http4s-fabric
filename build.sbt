val scala213 = "2.13.8"
val scala3 = "3.1.3"

name := "http4s-fabric"

ThisBuild / scalaVersion := scala213
ThisBuild / crossScalaVersions := Seq(scala213, scala3)

libraryDependencies ++= Seq(
  "com.outr" %% "fabric-parse" % "1.2.9",
  "org.http4s" %% "http4s-core" % "1.0.0-M37",
  "org.typelevel" %% "cats-effect" % "3.3.14"
)

developers := List(
  Developer(
    "darkfrog26",
    "Matt Hicks",
    "matt@matthicks.com",
    url("https://github.com/darkfrog26")
  )
)
startYear := Some(2022)
ThisBuild / tlBaseVersion := "1.0"
ThisBuild / tlSonatypeUseLegacyHost := false
