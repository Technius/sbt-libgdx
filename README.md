# sbt-libgdx
This is an unofficial SBT plugin meant to make development with
[libGdx](http://libgdx.badlogicgames.com/) easier. At the moment, this plugin
is a work-in-progress.

Desktop projects are packaged using SBT Native Packager and Android projects
are packaged using the android-sdk-plugin.

**TODO: The Android plugin has not been tested yet.**

# Usage
Requirements:
* SBT 0.13.5 or newer
* Android SDK

Add the following to `project/plugins.sbt`:
```scala
resolvers += Resolver.sonatypeRepo("snaphots")

addSbtPlugin("co.technius" % "sbt-libgdx" % "0.0.1-SNAPSHOT")
```

To use this plugin, enable the `LibGdxAndroid` plugin on Android projects and
the `LibGdxDesktop` plugin on Desktop projects.

Sample `build.sbt`:
```scala
val projectName = """my-project"""

name := projectName

version := "1.0.0"

scalaVersion in Global := "2.11.5"

// Top level project
lazy val root = Project("root", file("."))
  .aggregate(android, desktop)

// Shared code
lazy val core = Project("core", file("core"))
  .settings(
    name := projectName + "-core",
    // 'libGdx' is an alias for the version of libGDX supported by the plugin
    libraryDependencies += libGdx
  )

lazy val android = Project("android", file("android"))
  .settings(
    name := projectName + "-android",
    platformTarget in Android := "android-17", // Set Android properties
    targetSdkVersion in Android := "19" // minSdkVersion defaults to 8
  )
  .dependsOn(core)
  .enablePlugins(LibGdxAndroid) // Enable Android plugin

lazy val desktop = Project("desktop", file("desktop"))
  .settings(
    name := projectName + "-desktop"
  )
  .dependsOn(core)
  .enablePlugins(LibGdxDesktop) // Enable desktop plugin
```

Assets go into the folder defined by the `assetDir` key, which defaults to
`assets` under the root project. Assets are automatically included as resources
in Android and desktop projects. They are also copied to the `bin` folder of
the desktop distribution.

Assuming the above build file, here are some of the commands:
* `desktop/run`: runs the desktop project
* `desktop/universal:packageBin`: creates a tarball distribution of the project
* `android/android:run`: install and run the project on an Android device
* `android/android:package-release`: creates a release APK and signs it

See SBT Native Packager and android-sdk-plugin for more options.

# Library Aliases

This plugin defines several alises for the extension libraries. These aliases
can be inserted into `libraryDependencies` in the shared code projects.

* `libGdxBox2d`
* `libGdxFreeType`
* `libGdxControllers`

To use them in desktop and Android projects, append them to libraryDependencies,
with `Desktop` or `Android` appended to their names.

An example is shown below:

```scala
// core
libraryDependencies ++= Seq(libGdxBox2d)

// desktop
libraryDependencies ++= Seq(
  // other dependencies
) ++ libGdxBox2dDesktop

// Android
libraryDependencies ++= Seq(
  // other dependencies
) ++ libGdxBox2dAndroid
```

# Other

This project is licensed under the Apache 2.0 License. See LICENSE for more
details.
