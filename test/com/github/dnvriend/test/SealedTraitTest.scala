/*
 * Copyright 2016 Dennis Vriend
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.dnvriend.test

import com.github.dnvriend.TestSpec
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

object Car {
  val reads: Reads[Car] =
    ((JsPath \ "class").read[String] and (JsPath \ "object").read[JsValue])((str, js) => str match {
      case "Ford" =>
        println("deser ford"); js.as[Ford](Ford.format)
      case "Vw" =>
        println("deser vw"); js.as[Vw](Vw.format)
      case "Bmw" =>
        println("deser bmw"); js.as[Bmw](Bmw.format)
    })

  val writes: Writes[Car] = new Writes[Car] {
    override def writes(car: Car): JsValue = {
      val json: JsValue = car match {
        case x: Ford =>
          println("ser " + car); Json.toJson[Ford](x)(Ford.format)
        case x: Vw =>
          println("ser " + car); Json.toJson[Vw](x)(Vw.format)
        case x: Bmw =>
          println("ser " + car); Json.toJson[Bmw](x)(Bmw.format)
      }
      Json.obj("class" -> car.getClass.getSimpleName, "object" -> json)
    }
  }

  implicit val format: Format[Car] = Format(reads, writes)
}

sealed trait Car
object Ford {
  implicit val format: Format[Ford] = Json.format[Ford]
}
final case class Ford(hp: Int) extends Car
object Vw {
  implicit val format: Format[Vw] = Json.format[Vw]
}
final case class Vw(hp: Int) extends Car
object Bmw {
  implicit val format: Format[Bmw] = Json.format[Bmw]
}
final case class Bmw(hp: Int) extends Car

class SealedTraitTest extends TestSpec {
  def serialize(car: Car): String =
    Json.toJson(car)(Car.format).toString()

  it should "serialize using a Format" in {
    serialize(Ford(100)) shouldBe """{"class":"Ford","object":{"hp":100}}"""
    serialize(Vw(200)) shouldBe """{"class":"Vw","object":{"hp":200}}"""
    serialize(Bmw(350)) shouldBe """{"class":"Bmw","object":{"hp":350}}"""
  }

  it should "deserialize using a Format" in {
    Json.parse("""{"class":"Ford","object":{"hp":100}}""").as[Car] shouldBe Ford(100)
    Json.parse("""{"class":"Vw","object":{"hp":200}}""").as[Car] shouldBe Vw(200)
    Json.parse("""{"class":"Bmw","object":{"hp":300}}""").as[Car] shouldBe Bmw(300)
  }
}
