sbt githubWorkflowGenerate
sbt +headerCreate
sbt scalafixAll
sbt "+Test / headerCreate"
sbt +scalafmt
sbt "+Test / scalafmt"