package co.technius.sbtlibgdx

import sbt._
import Keys._

object LibGdxPlugin extends AutoPlugin {

  import autoImport._

  override def projectSettings = baseAssetSettings

  object autoImport {
    val assetDir = settingKey[File]("The folder containing the assets.")

    val libGdxVersion = settingKey[String]("Version of libGDX to use.")

    val libGdx = gdxDependency("gdx")

    val libGdxBox2d = gdxDependency("gdx-box2d")

    val libGdxFreeType = gdxDependency("gdx-freetype")

    val libGdxControllers = gdxDependency("gdx-controllers")
    
    val libGdxTools = gdxDependency("gdx-tools")

    val libGdxBox2dDesktop = Def.setting {
      Seq(libGdxBox2d.value, desktopDependency("gdx-box2d-platform").value)
    }

    val libGdxBox2dAndroid = Def.setting {
      Seq(libGdxBox2d.value) ++ androidDependency("gdx-box2d-platform").value
    }

    val libGdxFreeTypeDesktop = Def.setting {
      Seq(
        libGdxFreeType.value,
        desktopDependency("gdx-freetype-platform").value
      )
    }

    val libGdxFreeTypeAndroid = Def.setting {
      Seq(libGdxFreeType.value) ++ androidDependency("gdx-freetype-platform").value
    }

    val libGdxControllersDesktop = Def.setting {
      Seq(
        libGdxControllers.value,
        gdxDependency("gdx-controllers-desktop").value,
        desktopDependency("gdx-controllers-platform").value
      )
    }

    val libGdxControllersAndroid = Def.setting {
      Seq(
        libGdxControllers.value,
        gdxDependency("gdx-controllers-android").value
      )
    }

  }

  lazy val baseAssetSettings: Seq[Def.Setting[_]] = Seq(
    assetDir := baseDirectory.value / ".." / "assets",
    unmanagedResourceDirectories in Compile += assetDir.value
  )

  private[sbtlibgdx] def gdxDependency(name: String) = Def.setting {
    "com.badlogicgames.gdx" % name % libGdxVersion.value
  }

  private [sbtlibgdx] def androidDependency(name: String) = Def.setting {
    Seq(
      // gdxDependency(name) classifier "natives-x86",
      // gdxDependency(name) classifier "natives-armeabi",
      gdxDependency(name).value classifier "natives-armeabi-v7a"
    )
  }

  private [sbtlibgdx] def desktopDependency(name: String) = Def.setting {
    gdxDependency(name).value classifier "natives-desktop"
  }
}
