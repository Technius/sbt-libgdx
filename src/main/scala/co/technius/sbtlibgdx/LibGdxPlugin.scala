package co.technius.sbtlibgdx

import sbt._
import Keys._

object LibGdxPlugin extends AutoPlugin {

  import autoImport._

  override def projectSettings = baseAssetSettings

  object autoImport {
    val assetDir = settingKey[File]("The folder containing the assets.")

    val libGdx = gdxDependency("gdx")

    val libGdxBox2d = gdxDependency("gdx-box2d")

    val libGdxFreeType = gdxDependency("gdx-freetype")

    val libGdxControllers = gdxDependency("gdx-controllers")

    val libGdxBox2dDesktop = Seq(
      libGdxBox2d, desktopDependency("gdx-box2d-platform"))

    val libGdxBox2dAndroid =
      Seq(libGdxBox2d) ++ androidDependency("gdx-box2d-platform")

    val libGdxFreeTypeDesktop = Seq(
      libGdxFreeType, desktopDependency("gdx-freetype-platform"))

    val libGdxFreeTypeAndroid =
      Seq(libGdxFreeType) ++ androidDependency("gdx-freetype-platform")

    val libGdxControllersDesktop = Seq(
      libGdxControllers,
      gdxDependency("gdx-controllers-desktop"),
      desktopDependency("gdx-controllers-platform")
    )

    val libGdxControllersAndroid = Seq(
      libGdxControllers, gdxDependency("gdx-controllers-android"))

  }

  lazy val baseAssetSettings: Seq[Def.Setting[_]] = Seq(
    assetDir := baseDirectory.value / ".." / "assets",
    unmanagedResourceDirectories in Compile += assetDir.value
  )

  val libGdxVersion = "1.6.0"

  private[sbtlibgdx] def gdxDependency(name: String) =
    "com.badlogicgames.gdx" % name % libGdxVersion

  private [sbtlibgdx] def androidDependency(name: String) =
    Seq(
      gdxDependency(name) classifier "natives-armeabi",
      gdxDependency(name) classifier "natives-armeabi-v7",
      gdxDependency(name) classifier "natives-x86"
    )

  private [sbtlibgdx] def desktopDependency(name: String) =
    gdxDependency(name) classifier "natives-desktop"
}
