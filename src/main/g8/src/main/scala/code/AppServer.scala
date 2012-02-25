package code

import akka.dispatch.{Future, Promise}
import akka.util.Timeout
import akka.util.duration._
import blueeyes._
import blueeyes.core.data._

case class AppConfig()

object AppServer extends BlueEyesServer
    with BlueEyesServiceBuilder
    with BijectionsChunkFutureJson
    with BijectionsChunkJson
{
  val defaultTimeout = Timeout(3 seconds)

  val appService =
    service("app", "1.0.0") {
      requestLogging(defaultTimeout) {
        healthMonitor(defaultTimeout) { monitor => context =>
          startup {
            Promise.successful(AppConfig())
          } ->
          request { config =>
            EchoService("/")
          } ->
          shutdown { _ =>
            Promise.successful(())
          }
        }
      }
    }
}
