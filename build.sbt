name := "play-json-test"

organization := "com.github.dnvriend"

version := "1.0.0"

scalaVersion := "2.11.8"
scalaOrganization := "org.typelevel"
scalacOptions += "-Ypartial-unification" // enable fix for SI-2712
scalacOptions += "-Yliteral-types"       // enable SIP-23 implementation


libraryDependencies += "org.typelevel" %% "cats" % "0.8.1"
libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.2"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.5.10"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0-M1" % Test

fork in Test := true
parallelExecution := false

licenses +=("Apache-2.0", url("http://opensource.org/licenses/apache2.0.php"))

// enable scala code formatting //
import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform
import sbtbuildinfo.BuildInfoKey.action

// Scalariform settings
SbtScalariform.autoImport.scalariformPreferences := SbtScalariform.autoImport.scalariformPreferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)

// enable updating file headers //
import de.heikoseeberger.sbtheader.license.Apache2_0

headers := Map(
  "scala" -> Apache2_0("2016", "Dennis Vriend"),
  "conf" -> Apache2_0("2016", "Dennis Vriend", "#")
)

buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion, action("lastCommitHash") { "git rev-parse HEAD".!!.trim })
buildInfoPackage := "com.github.dnvriend.component.health.version"
buildInfoKeys += buildInfoBuildNumber
buildInfoOptions += BuildInfoOption.ToMap
buildInfoOptions += BuildInfoOption.ToJson
buildInfoOptions += BuildInfoOption.BuildTime
buildInfoOptions += BuildInfoOption.Traits("com.github.dnvriend.component.health.version.VersionTrait")

enablePlugins(AutomateHeaderPlugin, SbtScalariform, PlayScala, BuildInfoPlugin)
