package $package$

import akka.dispatch.{Future, Promise}
import akka.util.duration._
import akka.util.Timeout
import blueeyes.bkka.AkkaDefaults
import blueeyes._
import blueeyes.concurrent._
import blueeyes.core.data._
import blueeyes.core.http._
import blueeyes.core.service._

trait EchoService extends BlueEyesServiceBuilder
    with HttpRequestHandlerCombinators
    with BijectionsChunkFutureJson
    with BijectionsChunkJson
    with AkkaDefaults
{
  val echoServiceHandlerUri = "/echo"

  def echoServiceHandler =
    describe("ECHO ... EcHo ... echo ...") {
      path(echoServiceHandlerUri) { request: HttpRequest[ByteChunk] =>
        Future(HttpResponse(content = request.content))
      }
    }

  def echoService =
    service("echo", "1.0.0") {
      requestLogging(Timeout(3 seconds)) {
        healthMonitor(Timeout(3 seconds)) { monitor => context =>
          startup {
            Promise.successful(())
          } ->
          request { config: Unit =>
            echoServiceHandler
          } ->
          shutdown { _ =>
            Promise.successful(())
          }
        }
      }
    }
}
