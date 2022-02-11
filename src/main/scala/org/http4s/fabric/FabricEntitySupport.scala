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

/**
 * Used to generate EntityDecoder and EntityEncoder for http4s using Fabric. Importing org.http4s.fabric._ should be
 * sufficient for most use-cases, but you can optionally instantiate with decoding and encoding filters to enhance your
 * use-case with features like snake-case conversions.
 *
 * @param decodeFilter used when decoding from JSON into Fabric
 * @param encodeFilter used when encoding from Fabric into JSON
 */
class FabricEntitySupport(decodeFilter: ValueFilter, encodeFilter: ValueFilter) {
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
      val string = t match {
        case s: String => s // Ignore String
        case _ =>
          val value = t.toValue.filter(encodeFilter).get
          Json.format(value)
      }
      val bytes = string.getBytes("UTF-8")
      Chunk.array(bytes)
    }.withContentType(`Content-Type`(MediaType.application.json))
}