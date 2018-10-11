

A simple library (without any real plugin yet!) to simplize some works in SBT:

```scala

import xyz.sigmalab.sbtutil.Context

val context = Context(
    organization = "xyz.sigmalab.sbtutil-test",
    originName = "sbtutil-test",
    originVersion = "0.1.0-SNAPSHOT",
)


val subProject1 = context.newModule("name_in_sbt_console_?", "sub-one")

val subProject2 = context.newModule("name_in_sbt_console_?", "sbt-two").dependsOn(subProject1)

val root = context.defRoot(subProject2)

```