resolvers ++= Seq(
  "Spray" at "http://repo.spray.cc",
  "retronym-releases" at "http://retronym.github.com/repo/releases"
)

addSbtPlugin("cc.spray" % "sbt-revolver" % "0.6.0")

addSbtPlugin("com.github.retronym" % "sbt-onejar" % "0.6")
