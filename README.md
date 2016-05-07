# sbt-libgdx
This is an unofficial SBT plugin meant to make development with
[libGdx](http://libgdx.badlogicgames.com/) easier. At the moment, this plugin
is a work-in-progress.

Desktop projects are packaged using SBT Native Packager and Android projects
are packaged using the android-sdk-plugin.

# Usage
Requirements:
* SBT 0.13.5 or newer
* Android SDK

Add the following to `project/plugins.sbt`:
```scala
resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("co.technius" % "sbt-libgdx" % "0.0.1-SNAPSHOT")
```

To use this plugin, enable the `LibGdxAndroid` plugin on Android projects and
the `LibGdxDesktop` plugin on Desktop projects.

A sample `build.sbt` file can be viewed
[here](https://github.com/Technius/activator-libgdx-scala-seed/blob/master/build.sbt).

Assets go into the folder defined by the `assetDir` key, which defaults to
`assets` under the root project. Assets are automatically included as resources
in Android and desktop projects. They are also copied to the `bin` folder of
the desktop distribution.

For Android projects, you will need to configure proguard. Either specify the
proguard options with `proguardOptions in Android ++= Seq(...)` or create an
appropriate `proguard-project.txt` located in the Android project folder. If
both are used, the contents of `proguard-project.txt` will be appended to the
existing configuration.

Assuming the sample build file, here are some of the commands:
* `desktop/run`: runs the desktop project
* `desktop/universal:packageBin`: creates a tarball distribution of the project
* `android/android:run`: install and run the project on an Android device
* `android/android:package-release`: creates a release APK and signs it

See [SBT Native Packager](https://github.com/sbt/sbt-native-packager) and
[sbt-android](https://github.com/scala-android/sbt-android)
for more options.

# Library Aliases

This plugin defines several alises for the libGdx libraries. These aliases
are defined as settings, so the actual values must be retrieved by calling
the `value` method on the aliases. They will use the version of libGDX as set
in the `libGdxVersion` key. To include just the libGdx library, add the
following to `libraryDependencies`:

```scala
libraryDependencies += libGdx.value
```

Do not forget to set the version for each platform:
```scala
libGdxVersion := "1.9.2"
```
Once the `libGdxVersion` key is set, all aliases will use the specified version.

## Extension Libraries
To use the extension libraries, first insert the aliases into
`libraryDependencies` in the shared code projects.

* `libGdxBox2d`
* `libGdxFreeType`
* `libGdxControllers`
* `libGdxTools`

Then, append the corresponding `Desktop` or `Android` versions to
`libraryDependencies` in the corresponding projects.

An example is shown below:

```scala
// shared settings or in global
libGdxVersion := "1.9.2"

// core
libraryDependencies ++= Seq(libGdxBox2d.value)

// desktop
libraryDependencies ++= Seq(
  // other dependencies
) ++ libGdxBox2dDesktop.value

// Android
libraryDependencies ++= Seq(
  // other dependencies
) ++ libGdxBox2dAndroid.value
```

# Other

This project is licensed under the Apache 2.0 License. See LICENSE for more
details.
