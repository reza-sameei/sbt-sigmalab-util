
import xyz.sigmalab.sbtutil.Context

val context = Context(
    organization = "xyz.sigmalab.sbtutil-test",
    originName = "sbtutil-test",
    originVersion = "0.1.0-SNAPSHOT",
)

val jsonext =
    context.newModule("jsonext", "play-template-json-ext")

val dba =
    context.newModule("dba", "play-template-dba-slick")

val webapi =
    context.newModule("webapi", "play-template-webapi")
        .dependsOn(jsonext)

val root = context.defRoot(webapi)

