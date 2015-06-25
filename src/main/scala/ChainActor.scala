import akka.actor.{ Actor, ActorLogging, ActorRef }

case object GiveMeThings
case class Give(thing: Any)

trait ProducerBehavior {
	this: Actor with ActorLogging =>
		val producerBehavior: Receive = {
			case GiveMeThings =>
        log.info("Hear a request from {}", sender)
				sender  ! Give("thing")
		}
}

trait ConsumerBehavior {
	this: Actor with ActorLogging => 
		val consumerBehavior: Receive = {
			case ref: ActorRef =>
        log.info("Request a thing from {}", ref)
				ref ! GiveMeThings

			case Give(thing) => 
				log.info("Got a thing ! It's {}", thing)
		}
}

class Producer extends Actor with ActorLogging with ProducerBehavior{
	def receive  = producerBehavior
}

class Consumer extends Actor with ActorLogging with ConsumerBehavior{
	def receive = consumerBehavior
}

class ProducerConsumer extends Actor with ActorLogging with ProducerBehavior with  ConsumerBehavior {
	def receive = producerBehavior orElse consumerBehavior
}
