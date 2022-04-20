ThisBuild / scalaVersion     := "3.1.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val core = (project in file("core"))
  .settings(
    name := "quill-experiment",
    libraryDependencies ++= Seq(
      "io.getquill"   %% "quill-jdbc-zio" % "3.16.3.Beta2.5",
      "org.postgresql" % "postgresql"     % "42.3.3"
    ),
    scalaVersion := "3.1.0",
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )

Global / onChangedBuildSource := ReloadOnSourceChanges
