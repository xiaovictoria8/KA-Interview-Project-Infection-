import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
	public static void main(String[] args) {
		
		//Running tests for total_infection
		System.out.println("Running tests for total_infection");
		Result tiResult = JUnitCore.runClasses(TotalInfectionTests.class);
		int tiTotalTests = tiResult.getFailureCount() + tiResult.getIgnoreCount() + 
				tiResult.getRunCount();
		System.out.println("Number of tests ran: " + tiTotalTests);
		System.out.println("Number of tests passed: " + tiResult.getRunCount());
		System.out.println("Number of tests failed: " + tiResult.getFailureCount());
		for (Failure failure : tiResult.getFailures()) {
			System.out.println(failure.toString());
		}
		
		//Running tests for limited_infection
		System.out.println("\nRunning tests for limited_infection");
		Result liResult = JUnitCore.runClasses(LimitedInfectionTests.class);
		int liTotalTests = liResult.getFailureCount() + liResult.getIgnoreCount() + 
				liResult.getRunCount();
		System.out.println("Number of tests ran: " + liTotalTests);
		System.out.println("Number of tests passed: " + liResult.getRunCount());
		System.out.println("Number of tests failed: " + liResult.getFailureCount());
		for (Failure failure : liResult.getFailures()) {
			System.out.println(failure.toString());
		}
		
		//Running tests for limited_infection
		System.out.println("\nRunning tests for perfect_limited_infection");
		Result pliResult = JUnitCore.runClasses(PerfectLimitedInfectionTests.class);
		int pliTotalTests = pliResult.getFailureCount() + pliResult.getIgnoreCount() + 
				pliResult.getRunCount();
		System.out.println("Number of tests ran: " + pliTotalTests);
		System.out.println("Number of tests passed: " + pliResult.getRunCount());
		System.out.println("Number of tests failed: " + pliResult.getFailureCount());
		for (Failure failure : pliResult.getFailures()) {
			System.out.println(failure.toString());
		}
	}

}
