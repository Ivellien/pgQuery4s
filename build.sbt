name := "pgQuery4s"

lazy val parser = project

lazy val liftable = project in file("macros/liftable")
lazy val macros = project dependsOn (parser, liftable)

lazy val core = project dependsOn (macros, parser)

lazy val root =
  project in file(".") aggregate (parser, macros, core)
