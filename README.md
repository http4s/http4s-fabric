# http4s-fabric
[![CI](https://github.com/http4s/http4s-fabric/actions/workflows/ci.yml/badge.svg)](https://github.com/http4s/http4s-fabric/actions/workflows/ci.yml)
[![Maven Central](https://img.shields.io/maven-central/v/org.http4s/http4s-fabric_2.13.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/org.http4s/http4s-fabric_2.13)

Provides `EntityEncoder` and `EntityDecoder` support using the [Fabric](https://github.com/typelevel/fabric) JSON library.

Cross-built for Scala 2.13 and 3.

## SBT Setup
```sbt
libraryDependencies += "org.http4s" %% "http4s-fabric" % "1.0.0-M48"
```

The milestone number tracks the `http4s-core` release this version is built against.

## Usage

A single import supplies an `EntityDecoder` and `EntityEncoder` for any type with a Fabric
`RW` instance — see [Convert](https://github.com/typelevel/fabric#convert):

```scala
import org.http4s.fabric._

case class Person(name: String, age: Int)

object Person {
  implicit val rw: RW[Person] = RW.gen[Person]
}

// Person now encodes to and decodes from application/json
```

Note that the derived instances are specialized to `cats.effect.IO`.

### Filters

Import the package object for the default behavior, or instantiate `FabricEntitySupport`
directly with [Fabric filters](https://github.com/typelevel/fabric) to transform JSON on the
way in and out — for example, to exchange `snake_case` JSON with `camelCase` Scala fields:

```scala
import fabric.filter._
import org.http4s.fabric.FabricEntitySupport

object SnakeCaseSupport
    extends FabricEntitySupport(SnakeToCamelFilter, CamelToSnakeFilter)

import SnakeCaseSupport._
```
