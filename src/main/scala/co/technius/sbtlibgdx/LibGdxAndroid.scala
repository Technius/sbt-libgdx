package co.technius.sbtlibgdx

import android.Keys._
import android.Plugin.androidBuild
import sbt._
import sbt.Keys._
import LibGdxPlugin.{ androidDependency, gdxDependency }
import LibGdxPlugin.autoImport.assetDir

object LibGdxAndroid extends AutoPlugin {

  override def requires = LibGdxPlugin && android.AndroidPlugin
  
  override def projectSettings =
    LibGdxPlugin.projectSettings ++
    androidBuild ++
    Seq(
      libraryDependencies ++= Seq(
        gdxDependency("gdx-backend-android").value
      ) ++ androidDependency("gdx-platform").value,
      javacOptions ++= Seq("-source", "1.7", "-target", "1.7"),
      scalacOptions += "-target:jvm-1.7",
      minSdkVersion in Android := "8",
      useProguard in Android := true,
      proguardScala in Android := true,
      proguardOptions in Android <++= Def.task {
        val f = baseDirectory.value / "proguard-project.txt"
        if (f.exists) Seq(IO.read(f))
        else Seq()
      },
      collectJni <+= fetchNatives,
      extraAssetDirectories <+= assetDir
    )

  val fetchNatives = Def.task {
    val updReport = update.value
    val af = artifactFilter(classifier = "natives-*")
    val mf = moduleFilter(
      organization = "com.badlogicgames.gdx",
      name = "gdx-platform"
    )
    val jars = updReport.select(module = mf, artifact = af)
    val natives = Seq("armeabi", "armeabi-v7a", "x86")
    val outputDir = target.value / "libgdx-natives"
    val mappings = for {
      n <- natives
      j <- jars if j.getName.contains(n)
    } {
      val path = outputDir / n / "libgdx.so"
      if (!path.exists) {
        IO.unzip(j, outputDir / n, "libgdx.so")
      }
    }

    outputDir
  }
}
