package xyz.sigmalab.sbtutil

case class Context(
    organization : String,
    originName : String,
    originVersion : String,
    scalaVersions : Seq[String] = Seq("2.12.7"),
    modulesDir : String = "module",
    fork : Boolean = true // ?
) {

    // private val subs: Seq[sbt.Project] = Seq.empty

    private val settings : Seq[sbt.Def.Setting[_]] = {

        val artifactSettings = Seq(
            sbt.Keys.organization := organization,
            sbt.Keys.name := originName,
        )

        val compileSettings = Seq(
            sbt.Keys.scalacOptions ++= Seq(
                "-feature",
                "-deprecation",
                "-language:postfixOps"
            ),
            sbt.Keys.javacOptions ++= Seq(
                "--illegal-access=warn"
            )
        )

        val runSettings = Seq(
            sbt.Keys.fork := fork
        )

        val scalaVersionSettings = scalaVersions match {
            case Nil => throw new Exception("The 'scalaVersions' can't be an empty list!")
            case head :: Nil => Seq(sbt.Keys.scalaVersion := head)
            case head :: _ => Seq(
                sbt.Keys.scalaVersion := head,
                sbt.Keys.crossScalaVersions := scalaVersions
            )
        }

        artifactSettings ++
            compileSettings ++
            runSettings ++
            scalaVersionSettings
    }

    def newModule(
        name : String,
        artifact : String,
        version : String,
        dir : String
    ) : sbt.Project = {

        val prj = sbt.Project(
            name,
            sbt.file(s"${modulesDir}/${dir}")
        ).settings(
            settings : _*
        ).settings(
            sbt.Keys.name := name,
            sbt.Keys.version := version
        )

        prj

    }

    def newModule(name : String, artifact : String, version : String = null) : sbt.Project =
        newModule(name, artifact, Option(version).getOrElse(originVersion), name)

    def newModule(name : String) : sbt.Project =
        newModule(name, name, originVersion)

    def defRoot(subs : sbt.ProjectReference*) : sbt.Project =
        sbt.Project("root", sbt.file("."))
            .settings(settings : _*)
            .settings(
                sbt.Keys.version := originVersion,
                sbt.Keys.name := originName
            ).aggregate(subs : _*)


}
