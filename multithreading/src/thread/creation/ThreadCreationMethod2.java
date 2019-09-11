package thread.creation;

public class ThreadCreationMethod2 {

	public static void main(String args[]) throws InterruptedException {
		
		
		NewThread thread = new NewThread();
		thread.setName("New Worker Thread");
		thread.start();
		System.out.println("Inside Thread : " + Thread.currentThread().getName());
	}
	
}

class NewThread extends Thread {
	
	@Override
	public void run() {
		System.out.println("Inside Thread : " + this.getName());
	}
}