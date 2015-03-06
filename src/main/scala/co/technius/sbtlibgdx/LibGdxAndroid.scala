package co.technius.sbtlibgdx

import android.Keys._
import android.Plugin.androidBuild
import sbt._
import sbt.Keys._
import LibGdxPlugin.libGdxVersion

object LibGdxAndroid extends AutoPlugin {

  override def requires = LibGdxPlugin && android.AndroidPlugin
  
  override def projectSettings =
    LibGdxPlugin.projectSettings ++
    androidBuild ++
    Seq(
      libraryDependencies ++= Seq(
        "com.badlogicgames.gdx" % "gdx-backend-android" % libGdxVersion,
        "com.badlogicgames.gdx" % "gdx-platform" % libGdxVersion classifier "natives-armeabi",
        "com.badlogicgames.gdx" % "gdx-platform" % libGdxVersion classifier "natives-armeabi-v7a"
      ),
      javacOptions ++= Seq("-source", "1.7", "-target", "1.7"),
      scalacOptions += "-target:jvm-1.7",
      minSdkVersion in Android := "8"
    )
}
