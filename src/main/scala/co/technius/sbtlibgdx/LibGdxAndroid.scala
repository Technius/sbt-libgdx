package co.technius.sbtlibgdx

import android.Keys._
import android.Plugin.androidBuild
import sbt._
import sbt.Keys._
import LibGdxPlugin.{ androidDependency, gdxDependency }

object LibGdxAndroid extends AutoPlugin {

  override def requires = LibGdxPlugin && android.AndroidPlugin
  
  override def projectSettings =
    LibGdxPlugin.projectSettings ++
    androidBuild ++
    Seq(
      libraryDependencies ++= Seq(
        gdxDependency("gdx-backend-android")
      ) ++ androidDependency("gdx-platform"),
      javacOptions ++= Seq("-source", "1.7", "-target", "1.7"),
      scalacOptions += "-target:jvm-1.7",
      minSdkVersion in Android := "8"
    )
}
