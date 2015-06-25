import akka.actor.Actor
import akka.actor.Props
import akka.event.Logging

class MyActor extends Actor{
	val log = Logging(context.system, this)
	def receive = {
		case "test"  => log.info("received test")
		case _ 		 => log.info("received unknown message")
	}
}

class HotSwapActor extends Actor {
	val log = Logging(context.system, this)
	import context._
	def angry: Receive  = {
		case "foo"  => log.info("I am already angry")//sender() ! "I am already angry"
		case "bar"  => become(happy)
	}

	def happy: Receive = {
		case "bar"  => log.info("I am already happy :-)") //sender() ! "I am already happy :-)"
    	case "foo" => become(angry)
	}

	def receive = {
    	case "foo" => become(angry)
    	case "bar" => become(happy)
	}
}

case object Swap
class Swapper extends Actor {
	import context._
	val log = Logging(system, this)

	def receive = {
		case Swap => 
			log.info("Hi")
			become({
				case Swap =>
					log.info("Ho")
					unbecome()
				}, discardOld = false)
	}
}


import akka.actor.Stash

class ActorWithProtocol extends Actor with Stash {
	val log = Logging(context.system, this)
	def receive = {
		case "open" => 
			log.info(" opening ... ")
			unstashAll()
			context.become({
				case "write" => log.info(" writing ...")
				case "close" => 
					log.info("closing .. ")
					context.unbecome()
				case msg => 
					log.info(msg.toString())
					stash()
				}, discardOld = false)
		case msg => 
			log.info(msg.toString())
			stash()
	}
}

