val scala213 = "2.13.16"

val scala3 = "3.3.6"

lazy val root = (project in file("."))
  .settings(
    name := "http4s-fabric",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "fabric-io" % "1.17.2",
      "org.http4s" %% "http4s-core" % "1.0.0-M44",
      "org.typelevel" %% "cats-effect" % "3.5.7"
    )
  )

ThisBuild / scalaVersion := scala213
ThisBuild / crossScalaVersions := Seq(scala213, scala3)

ThisBuild / developers := List(
  Developer(
    "darkfrog26",
    "Matt Hicks",
    "matt@matthicks.com",
    url("https://github.com/darkfrog26")
  )
)
ThisBuild / licenses := Seq(License.MIT)
ThisBuild / startYear := Some(2022)
ThisBuild / tlBaseVersion := "1.0"
