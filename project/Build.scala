import sbt._
import sbt.Keys._
import xerial.sbt.Sonatype.sonatypeSettings
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbtbuildinfo.BuildInfoPlugin
import sbtbuildinfo.BuildInfoPlugin.autoImport._

object Build extends sbt.Build {
  object Dependencies {
    val ScalaTest = "3.0.0-M15"
    val MetaDocs  = "0.1.1"  // TODO Check at maven central
    val MetaRx    = "0.1.6"  // TODO Check at maven central
  }

  val SharedSettings = Seq(
    name := "MetaTime",
    organization := "pl.metastack",
    scalaVersion := "2.11.7",
    scalacOptions := Seq(
      "-unchecked",
      "-deprecation",
      "-encoding", "utf8"
    )
  )

  lazy val root = project.in(file("."))
    .aggregate(js, jvm)
    .settings(SharedSettings: _*)
    .settings(publishArtifact := false)

  lazy val metaTime = crossProject.in(file("."))
    .settings(SharedSettings: _*)
    .settings(sonatypeSettings: _*)
    .settings(
      pomExtra :=
        <url>https://github.com/MetaStack-pl/MetaTime</url>
        <licenses>
          <license>
            <name>Apache-2.0</name>
            <url>https://www.apache.org/licenses/LICENSE-2.0.html</url>
          </license>
        </licenses>
        <scm>
          <url>git@github.com:MetaStack-pl/MetaTime.git</url>
        </scm>
        <developers>
          <developer>
            <id>tindzk</id>
            <name>Tim Nieradzik</name>
            <url>http://github.com/tindzk/</url>
          </developer>
        </developers>,

      autoAPIMappings := true,
      apiMappings +=
        (scalaInstance.value.libraryJar -> url(s"http://www.scala-lang.org/api/${scalaVersion.value}/"))
    )
    .jsSettings(
      libraryDependencies +=
        "org.scalatest" %%% "scalatest" % Dependencies.ScalaTest % "test",
      libraryDependencies +=
        "pl.metastack" %%% "metarx" % Dependencies.MetaRx % "test",

      /* Use io.js for faster compilation of test cases */
      scalaJSStage in Global := FastOptStage
    )
    .jvmSettings(
      libraryDependencies +=
        "org.scalatest" %% "scalatest" % Dependencies.ScalaTest % "test",
      libraryDependencies +=
        "pl.metastack" %% "metarx" % Dependencies.MetaRx % "test"
    )

  lazy val js = metaTime.js
  lazy val jvm = metaTime.jvm

  lazy val manual = project.in(file("manual"))
    .dependsOn(jvm)
    .enablePlugins(BuildInfoPlugin)
    .settings(SharedSettings: _*)
    .settings(
      publishArtifact := false,
      libraryDependencies ++= Seq(
        "pl.metastack" %% "metadocs" % Dependencies.MetaDocs,
        "org.eclipse.jgit" % "org.eclipse.jgit" % "4.1.1.201511131810-r"),
      buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
      buildInfoPackage := "pl.metastack.metatime",
      name := "MetaTime manual")
}
