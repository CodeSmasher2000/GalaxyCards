package testClasses;

public interface TestCase {
	public void setup();
	public void runTest() throws TestFailedException;
	public void reset();
}
