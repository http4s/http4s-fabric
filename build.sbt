val scala213 = "2.13.10"
val scala3 = "3.3.0"

name := "http4s-fabric"

ThisBuild / scalaVersion := scala213
ThisBuild / crossScalaVersions := Seq(scala213, scala3)

libraryDependencies ++= Seq(
  "org.typelevel" %% "fabric-io" % "1.11.1",
  "org.http4s" %% "http4s-core" % "1.0.0-M39",
  "org.typelevel" %% "cats-effect" % "3.5.0"
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
