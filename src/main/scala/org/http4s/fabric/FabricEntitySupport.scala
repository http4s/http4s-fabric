/*
 * Copyright (c) 2022 http4s.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.http4s.fabric

import cats.MonadThrow
import cats.data.EitherT
import cats.effect.IO
import cats.implicits.catsSyntaxEitherId
import fabric.filter._
import fabric.io.JsonFormatter
import fabric.io.JsonParser
import fabric.rw._
import fs2.Chunk
import org.http4s.DecodeFailure
import org.http4s.EntityDecoder
import org.http4s.EntityEncoder
import org.http4s.MediaType
import org.http4s.headers.`Content-Type`

/** Used to generate EntityDecoder and EntityEncoder for http4s using Fabric.
  * Importing org.http4s.fabric._ should be sufficient for most use-cases, but
  * you can optionally instantiate with decoding and encoding filters to enhance
  * your use-case with features like snake-case conversions.
  *
  * @param decodeFilter
  *   used when decoding from JSON into Fabric
  * @param encodeFilter
  *   used when encoding from Fabric into JSON
  */
class FabricEntitySupport(
    decodeFilter: JsonFilter,
    encodeFilter: JsonFilter
) {
  implicit def decoder[T](implicit writer: Writer[T]): EntityDecoder[IO, T] =
    EntityDecoder.decodeBy(MediaType.application.json) { media =>
      EitherT {
        media.as[String](implicitly[MonadThrow[IO]], EntityDecoder.text).map {
          jsonString =>
            val json = JsonParser(jsonString).filter(decodeFilter).get
            val t = writer.write(json)
            t.asRight[DecodeFailure]
        }
      }
    }

  implicit def encoder[T](implicit rw: RW[T]): EntityEncoder[IO, T] =
    EntityEncoder
      .Pure[Chunk[Byte]]
      .contramap[T] { t =>
        val string = t match {
          case s: String => s // Ignore String
          case _ =>
            val value = t.json.filter(encodeFilter).get
            JsonFormatter.Default(value)
        }
        val bytes = string.getBytes("UTF-8")
        Chunk.array(bytes)
      }
      .withContentType(`Content-Type`(MediaType.application.json))
}
