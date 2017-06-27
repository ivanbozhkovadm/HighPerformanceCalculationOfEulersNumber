import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Calendar;


class EulersNumber {
	
	public static int membersNumber = 350; 	//number of members of the line
	public static int threadsNumber = 10;	//number of threads
	static BigDecimal sum = BigDecimal.ZERO;	//global variable where sum all members
	static boolean quiet = false;
	static String file = null;

	
	public static void ManageCommandParameters(String[] args){
		//manage command parameters
		for (int i=0; i<args.length; i++) {
            if (args[i].equals("-p")) { membersNumber = Integer.parseInt(args[i+1]); }
           
            if (args[i].equals("-t")) { threadsNumber = Integer.parseInt(args[i+1]); }
           
            if (args[i].equals("-q")) { quiet = true; }
            
            if (args[i].equals("-o")) { file = args[i+1]; }
	        }
	}

	public static void CreateFile(){
		if(file == null)
			file = "output.txt";
		try {
			new File(file).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws FileNotFoundException{
		
		long start = Calendar.getInstance().getTimeInMillis();//timing
	
		ManageCommandParameters(args);
		CreateFile();	
		
		
		/*
		Threads creation and start them
			1)get the file
			2)create pull of threads
			3)every thread create new Task
				3.1)calculate  
			4)join all threads
		*/
		
		PrintWriter writer = new PrintWriter(new FileOutputStream(file), true);
		Thread[] threadBuffer = new Thread[threadsNumber];
		for (int i = 0; i < threadsNumber; ++i) {
			threadBuffer[i] = new Thread(new Calculation("Thread " + (i + 1), i, writer, quiet));
			threadBuffer[i].start();
		}
		
		for (int i = 0; i < threadsNumber; i++) {
				try {
					threadBuffer[i].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
		
		
			
		//timing
		long finish = Calendar.getInstance().getTimeInMillis();
		writer.println("Time for whole calculation (millis): " + (finish - start));
		writer.println("e = " + sum.toString());
	} 
}	

