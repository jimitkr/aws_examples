name := "mediaconvertexample"

version := "0.1"

scalaVersion := "2.13.1"

// https://mvnrepository.com/artifact/com.amazonaws/aws-java-sdk
// AWS Java SDK
libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.11.692"

// https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
// Jackson for Json processing
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.10.1"

// https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-scala
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.10.1"

// https://mvnrepository.com/artifact/ch.qos.logback/logback-classic
// logger
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3" % Runtime
// https://mvnrepository.com/artifact/com.typesafe.scala-logging/scala-logging
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"

