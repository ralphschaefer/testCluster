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

lazy val common = (project in file ("common"))
  .settings(
    inThisBuild(
      commonSettings
    ),
    assemblyJarName in assembly := "common.jar",
    assemblySettings,
    name := "common",
    libraryDependencies ++= commonLibs
  )

lazy val testActor = (project in file ("testActor"))
  .settings(
    inThisBuild(
      commonSettings
    ),
    assemblyJarName in assembly := "testActor.jar",
    assemblySettings,
    name := "testActor",
    libraryDependencies ++= commonLibs
  )

lazy val root = (project in file("."))
  .aggregate(testActor, common)

