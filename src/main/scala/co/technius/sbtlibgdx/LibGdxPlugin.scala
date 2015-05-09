package co.technius.sbtlibgdx

import sbt._
import Keys._

object LibGdxPlugin extends AutoPlugin {

  import autoImport._

  override def projectSettings = baseAssetSettings

  object autoImport {
    val assetDir = settingKey[File]("The folder containing the assets.")

    val libGdx = "com.badlogicgames.gdx" % "gdx" % libGdxVersion
  }

  lazy val baseAssetSettings: Seq[Def.Setting[_]] = Seq(
    assetDir := baseDirectory.value / ".." / "assets",
    unmanagedResourceDirectories in Compile += assetDir.value
  )

  val libGdxVersion = "1.6.0"
}
