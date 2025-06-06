val scala213 = "2.13.12"
val scala3 = "3.3.6"

name := "http4s-fabric"

ThisBuild / scalaVersion := scala213
ThisBuild / crossScalaVersions := Seq(scala213, scala3)

libraryDependencies ++= Seq(
  "org.typelevel" %% "fabric-io" % "1.17.1",
  "org.http4s" %% "http4s-core" % "1.0.0-M40",
  "org.typelevel" %% "cats-effect" % "3.5.2"
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
