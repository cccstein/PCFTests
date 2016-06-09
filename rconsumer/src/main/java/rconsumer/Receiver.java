package rconsumer;

public class Receiver {
	int noOfReceives = 0;

	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
		noOfReceives++;
	}
}
