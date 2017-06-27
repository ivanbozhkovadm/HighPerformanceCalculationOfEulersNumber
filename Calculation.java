
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Calendar;

class Calculation implements Runnable {
	
	private static final int PRECISION = 30;
	
	private int taskNumber;
	private String threadInfo;
	PrintWriter writer;	
	boolean quiet;

	Calculation(String threadInfo, int taskNumber, PrintWriter writer, boolean quiet) {
		this.threadInfo = threadInfo; 
		this.taskNumber = taskNumber; 
		this.writer = writer;
		this.quiet = quiet;
	}
	
	//Factorial function
	static BigDecimal factorial(int n) {
		BigDecimal result = BigDecimal.ONE;
		for(int i = 1; i <= n; ++i) {
			result = result.multiply(new BigDecimal(i));
		}
		return result;
	}
	
	@Override
	public void run() {
		
		long start = Calendar.getInstance().getTimeInMillis();
		//check quiet functionality
		if(!quiet) {
			writer.println(threadInfo + " started calculation.");
		}
		
		//granulation is equal to threadsNumber
		for(int i = this.taskNumber; i <= EulersNumber.membersNumber ; i += EulersNumber.threadsNumber) {
			
			//formula
			BigDecimal equationMember = new BigDecimal(3*i*3*i+1);//numerator
			equationMember = equationMember.divide(factorial(3*i), PRECISION, BigDecimal.ROUND_HALF_UP);//denominator
			
			
			//critical section 
			synchronized (EulersNumber.sum) {
				EulersNumber.sum = EulersNumber.sum.add(equationMember); //sum
			}
		}
		
		
		//time calculation
		long finish = Calendar.getInstance().getTimeInMillis();
		if(!quiet) {
			writer.println(threadInfo + " is ready.");
			writer.println(threadInfo + " has time (millis): " + (finish - start));
		}
	}
	
}


 
