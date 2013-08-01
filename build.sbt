name := "Casbah Test"

version := "0.1"

scalaVersion := "2.10.0"

libraryDependencies += "org.mongodb" %% "casbah" % "2.6.3-SNAPSHOT"

libraryDependencies += "com.novus" %% "salat" % "1.9.2-SNAPSHOT"

libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "0.2.0"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
