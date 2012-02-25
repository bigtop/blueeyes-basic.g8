package code

import akka.dispatch.{Future, Promise}
import blueeyes.bkka.AkkaDefaults
import blueeyes.concurrent._
import blueeyes.core.data._
import blueeyes.core.http._
import blueeyes.core.service._

object EchoService extends HttpRequestHandlerCombinators with AkkaDefaults {
  val log = net.lag.logging.Logger.apply

  def apply(pathPrefix: String) =
    describe("echo service") {
      path(pathPrefix) { request: HttpRequest[ByteChunk] =>
        log.info("Echoing request " + request)
        Future(HttpResponse(content = request.content))
      }
    }
}
