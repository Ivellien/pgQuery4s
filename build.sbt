name := "pgQuery4s"

lazy val parser = project

lazy val macros = project dependsOn parser

lazy val core = project dependsOn (macros, parser)

lazy val root =
  project in file(".") aggregate (parser, macros, core)
