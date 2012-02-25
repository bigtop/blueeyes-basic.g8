package code

import akka.dispatch.{Await, Future}
import akka.util.Duration
import akka.util.duration._
import akka.util.Timeout
import blueeyes._
import blueeyes.concurrent.test._
import blueeyes.core.data._
import blueeyes.core.http._
import blueeyes.core.http.HttpStatusCodes._
import blueeyes.core.http.MimeTypes._
import blueeyes.core.service.test.BlueEyesServiceSpecification
import scalaz._

class EchoServiceSpec extends BlueEyesServiceSpecification
    with EchoService
    with BlueEyesServiceBuilder
    with BijectionsChunkString
{
  def await[A](f: Future[A]): A =
    Await.result(f, Duration("3s"))

  def doGet(uri: String): HttpResponse[String] =
    await(service.contentType[String](text/plain).get[String](uri))

  def doPost(uri: String, data: String): HttpResponse[String] =
    await(service.contentType[String](text/plain).post(uri)(data))

  "GET /" should {
    "return a 404 response" in {
      val res = doGet("/")
      (res.status.code mustEqual NotFound) and (res.content mustEqual None)
    }
  }

  "POST /" should {
    "return an 404 reponse" in {
      val res = doGet("/")
      (res.status.code mustEqual NotFound) and (res.content mustEqual None)
    }
  }

  "GET /echo" should {
    "return an empty 200 reponse" in {
      val res = doGet("/echo")
      (res.status.code mustEqual OK) and (res.content mustEqual None)
    }
  }

  "POST /echo" should {
    "echo the request content" in {
      val res = doPost("/echo", "ping")
      (res.status.code mustEqual OK) and (res.content mustEqual Some("ping"))
    }
  }
}
