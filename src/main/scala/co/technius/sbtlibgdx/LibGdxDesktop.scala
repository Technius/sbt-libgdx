package co.technius.sbtlibgdx

import com.typesafe.sbt.SbtNativePackager.Universal
import com.typesafe.sbt.packager.MappingsHelper._
import com.typesafe.sbt.packager.archetypes.JavaAppPackaging
import sbt._

import Keys._
import LibGdxPlugin.{ desktopDependency, gdxDependency }

object LibGdxDesktop extends AutoPlugin {

  import LibGdxPlugin.autoImport._

  override def requires = LibGdxPlugin && JavaAppPackaging

  override def projectSettings = LibGdxPlugin.projectSettings ++
    baseProjectSettings

  lazy val baseProjectSettings: Seq[Def.Setting[_]] = Seq(
    fork in run := true,
    libraryDependencies ++= Seq(
      gdxDependency("gdx-backend-lwjgl").value,
      desktopDependency("gdx-platform").value
    ),
    mappings in Universal <++= assetMappingsTask
  )

  lazy val assetMappingsTask = Def.task {
    val dir = assetDir.value
    dir.*** pair rebase(dir, "bin/")
  }
}
