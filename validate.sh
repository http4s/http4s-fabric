sbt +githubWorkflowGenerate
sbt +headerCreate
sbt scalafixAll
sbt +scalafmt
sbt "Test / scalafmt"