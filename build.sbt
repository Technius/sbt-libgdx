name := """sbt-libgdx"""

organization := "co.technius"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.10.4"

sbtPlugin := true

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.0.0-M5")

addSbtPlugin("com.hanhuy.sbt" % "android-sdk-plugin" % "1.3.18")
