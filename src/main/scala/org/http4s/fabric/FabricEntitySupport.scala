package org.http4s.fabric

import cats.MonadThrow
import cats.data.EitherT
import cats.effect.IO
import cats.implicits.catsSyntaxEitherId
import fabric.parse.Json
import fabric.rw._
import fabric.filter._
import fs2.Chunk
import org.http4s.headers.`Content-Type`
import org.http4s.{DecodeFailure, EntityDecoder, EntityEncoder, MediaType}

case class FabricEntitySupport(decodeFilter: ValueFilter, encodeFilter: ValueFilter) {
  implicit def decoder[T](implicit writer: Writer[T]): EntityDecoder[IO, T] =
    EntityDecoder.decodeBy(MediaType.application.json) { media =>
      EitherT {
        media.as[String](implicitly[MonadThrow[IO]], EntityDecoder.text).map { jsonString =>
          val json = Json.parse(jsonString).filter(decodeFilter).get
          val t = writer.write(json)
          t.asRight[DecodeFailure]
        }
      }
    }

  implicit def encoder[T](implicit reader: Reader[T]): EntityEncoder[IO, T] = EntityEncoder
    .Pure[Chunk[Byte]]
    .contramap[T] { t =>
      val value = t.toValue.filter(encodeFilter).get
      val string = Json.format(value)
      val bytes = string.getBytes("UTF-8")
      Chunk.array(bytes)
    }.withContentType(`Content-Type`(MediaType.application.json))
}