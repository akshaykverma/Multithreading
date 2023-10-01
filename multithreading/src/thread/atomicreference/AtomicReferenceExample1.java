package thread.atomicreference;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceExample1 {

	public static void main(String args[]) {
		
		String oldName = "old name";
		String newName = "new name";
		
		AtomicReference<String> atomicRef = new AtomicReference<String>(oldName);
		
		// if below statement is used it will fall into else block.
		//atomicRef.set("Unexpected Value");
		
		// checks if the oldName == current value inside atomicRef, if yes changes to newName and return true 
		if (atomicRef.compareAndSet(oldName, newName)) {
			System.out.println("New value is " + atomicRef.get());
		} 
		else {
			System.out.println("Nothing changed");
		}
	}
}
