import Dependencies._

lazy val commonSettings = Seq(
  organization := "test.emnify",
  version := "0.1",
  parallelExecution := false
)

lazy val assemblySettings = Seq(
  test in assembly := {},
  aggregate in assembly := false
)

lazy val testActor = (project in file ("testActor"))
  .settings(
    inThisBuild(
      commonSettings
    ),
    name := "testActor",
    libraryDependencies ++= commonLibs
  )

