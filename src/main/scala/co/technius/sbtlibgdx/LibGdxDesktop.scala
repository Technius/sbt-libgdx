package co.technius.sbtlibgdx

import com.typesafe.sbt.SbtNativePackager.Universal
import com.typesafe.sbt.packager.MappingsHelper._
import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
import sbt._

import Keys._
import LibGdxPlugin.libGdxVersion

object LibGdxDesktop extends AutoPlugin {

  import LibGdxPlugin.autoImport._

  override def requires = LibGdxPlugin && JavaAppPackaging

  override def projectSettings = LibGdxPlugin.projectSettings ++ Seq(
    fork in run := true,
    libraryDependencies ++= Seq(
      "com.badlogicgames.gdx" % "gdx-backend-lwjgl" % libGdxVersion,
      "com.badlogicgames.gdx" % "gdx-platform" % libGdxVersion classifier "natives-desktop"
    ),
    mappings in Universal <++= assetDir map { dir =>
      dir.*** pair rebase(dir, "bin/")
    }
  )
}
