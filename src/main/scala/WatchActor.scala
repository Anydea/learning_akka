import akka.actor.{ Actor, Props, Terminated }
import akka.event.Logging

class WatchActor extends Actor {
	val log = Logging(context.system, this)
	val child = context.actorOf(Props.empty, "child")
	context.watch(child)

	var lastSender = context.system.deadLetters

	def receive = {
		case "kill" => context.stop(child); log.info("let child die")
		case Terminated(`child`) => log.info("child finished")
	}
}