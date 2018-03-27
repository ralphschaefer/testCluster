import sbt._

object Dependencies {
    lazy val akkaVersion = "2.5.11"
    lazy val commonLibs = Seq (
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe" % "config" % "1.3.2",
      "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
      "junit" % "junit" % "4.12",
      "com.novocode" % "junit-interface" % "0.11" % "test"
    )
}
