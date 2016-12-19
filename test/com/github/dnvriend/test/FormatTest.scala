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
import play.api.libs.json.{ Format, Json }

object Person {
  implicit val format: Format[Person] = Json.format[Person]
}

final case class Person(name: String, age: Int)

class FormatTest extends TestSpec {
  // a format contains two concepts:
  // - Reads[Person] to construct a Person type from JSON
  // - Writes[Person] to create JSON from a Person
  it should "serialize using a Format" in {
    Json.toJson(Person("foo", 25)).toString shouldBe """{"name":"foo","age":25}"""
  }

  it should "deserialize using a Format" in {
    Json.parse("""{"name":"foo","age":25}""").as[Person] shouldBe Person("foo", 25)
  }
}
