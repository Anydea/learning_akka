import akka.actor.ActorSystem
import akka.actor.Props




object firstSystem extends App {
	

val system = ActorSystem("mySystem")

//val myActor = system.actorOf(Props[HotSwapActor], "testActor")

//myActor ! "foo"
//myActor ! "foo"
//myActor ! "bar"
//myActor ! "bar"

//	val SwapActor = system.actorOf(Props[Swapper], "Swapper1")

//	SwapActor ! Swap
//	SwapActor ! Swap

//	val ActorWithStash = system.actorOf(Props[ActorWithProtocol], "ActorWithStash")
//
//	ActorWithStash ! "close"
//	ActorWithStash ! "something"
//	ActorWithStash ! "open"


  val ChainActor1 = system.actorOf(Props[ProducerConsumer],"ChainActor1")
  val ChainActor2 = system.actorOf(Props[ProducerConsumer],"ChainActor2")
  ChainActor1 ! Give("message")
  ChainActor1 ! ChainActor2
}

