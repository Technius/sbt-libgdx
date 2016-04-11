name := """sbt-libgdx"""

organization := "co.technius"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.4"

sbtPlugin := true

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.0")

addSbtPlugin("com.hanhuy.sbt" % "android-sdk-plugin" % "1.5.20")

// Publish settings

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>http://github.com/Technius/sbt-libgdx</url>
  <licenses>
    <license>
      <name>Apache License 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:Technius/sbt-libgdx.git</url>
    <connection>scm:git:git@github.com:Technius/sbt-libgdx.git</connection>
  </scm>
  <developers>
    <developer>
      <id>technius</id>
      <name>Bryan Tan</name>
      <url>http://technius.co</url>
    </developer>
  </developers>)

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots") 
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}


