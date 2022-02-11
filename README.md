# http4s-fabric
[![CI](https://github.com/http4s/http4s-fabric/actions/workflows/ci.yml/badge.svg)](https://github.com/http4s/http4s-fabric/actions/workflows/ci.yml)

Provides `EntityEncoder` and `EntityDecoder` support using [Fabric](https://github.com/outr/fabric) JSON library

## SBT Setup
```sbt
libraryDependencies += "org.http4s" %% "http4s-fabric" % "1.0.0-M31"
```

## Usage
```scala
import org.http4s.fabric._

// Use implicits for any type with `ReaderWriter` support see: https://github.com/outr/fabric#convert
```