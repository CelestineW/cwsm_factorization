import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.*;
import java.util.*;

public class Factor {
	
	static Random rand = new Random();
	static TreeMap<BigInteger, String> moduli_map = new TreeMap<BigInteger, String>();
	
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		File here = new File(".");
		System.out.println(here.getAbsolutePath());
		
		File moduli_file = new File("/Users/cwong2/Documents/3_Junior Year/cmsc441_proj2/Factorization/src/moduli_2_2.csv");
		
		Scanner input = new Scanner(moduli_file);

		input.nextLine();

		while(input.hasNextLine()){
			String[] moduli_set = input.nextLine().split(",");
			//System.out.println("Pair: " + moduli_set[0] + " " + moduli_set[1]);
			moduli_map.put(new BigInteger(moduli_set[1]), moduli_set[0]);
		}
		
		for (BigInteger key : moduli_map.keySet()) {
			
			Thread_Messenger isdone = new Thread_Messenger();
			
		    System.out.println(moduli_map.get(key));
	
			Thread t1 = new Thread(new PRThread(key, isdone));
			Thread t2 = new Thread(new PMOThread(key, isdone));

			t1.start();
			t2.start();

			t1.join();
			t2.join();


		}
		

		
	}
	
	public static class PRThread implements Runnable {

		private BigInteger num;
		private Thread_Messenger isDone;
		   
		PRThread(BigInteger n, Thread_Messenger isdone) {
			this.num = n;
			this.isDone = isdone;
			System.out.println("PR- Number to factor: " + n);
		}
		
		public void run() {
			PollardRho(this.num, isDone);
			isDone.isFactored = true;
			System.out.println("PR - Done with: " + num);
			
		}	
	}

	public static class PMOThread implements Runnable {

		private BigInteger num;
		private Thread_Messenger isDone;
		   
		PMOThread(BigInteger n, Thread_Messenger isdone) {
			this.num = n;
			this.isDone = isdone;
			System.out.println("P Minus - Number to factor: " + n);
		}
		
		public void run() {
			PollardPMinusOneWrap(this.num, isDone);
			isDone.isFactored = true;
			System.out.println("P Minus - Done with: " + num);
		}	
	}
	
	private static class Thread_Messenger {
		private boolean isFactored;
		public Thread_Messenger() {
			isFactored = false;
		}
	}
	
	
	// Pollard Rho implementation 
	public static BigInteger PollardRho(BigInteger n, Thread_Messenger alreadyFactored) {
		
		BigInteger x = new BigInteger("2");
		BigInteger y = new BigInteger("2");
		
		BigInteger d = BigInteger.ONE;
		
		while (true && !alreadyFactored.isFactored) {
			
			x = (x.pow(2)).add(BigInteger.ONE).mod(n);
			y = (y.pow(2)).add(BigInteger.ONE).mod(n);
			y = (y.pow(2)).add(BigInteger.ONE).mod(n);
			
			d = n.gcd(x.subtract(y));
			
			if (!d.equals(BigInteger.ONE) && !d.equals(n)) {
				System.out.println( moduli_map.get(n) + " is factorable by " + d + " and " + n.divide(d));
				return d;
			}
			
			// we will never find one this way 
			if (d.equals(n)) {
				System.out.println("Could not factor");
				return n;				
			}
		}
		
		return BigInteger.valueOf(-1);
	}
	
	public static BigInteger PollardPMinusOneWrap(BigInteger n, Thread_Messenger alreadyFactored){
				
		Set<BigInteger> bases = new HashSet<BigInteger>();
		BigInteger a = BigInteger.valueOf(2); // getting a base a,
		bases.add(a);
		
		BigInteger res = PollardPMinusOne(n,a);
		
		while((res.compareTo(BigInteger.valueOf(-1)) == 0 || res.compareTo(n) == 0) && !alreadyFactored.isFactored) {
			
			a = getRandBigInt(n);	
			// 1 < a < n
			while((BigInteger.ONE.compareTo(a) == 1  && a.compareTo(n) == 1) || bases.contains(a)){
				a = getRandBigInt(n);
			}
			bases.add(a);
			res = PollardPMinusOne(n,a);	
			
		}
		// Should never reach here.
		return BigInteger.valueOf(-1);
	}
	
	public static BigInteger PollardPMinusOne(BigInteger n, BigInteger a){
		
		
		BigInteger m = a; // gets accumulated for faster computations
		BigInteger bound = new BigInteger("1000000"); // B set according to elliptic curve method
		
		for (BigInteger i = BigInteger.valueOf(2); i.compareTo(bound) <  0 ; i = i.add(BigInteger.ONE)){	
			// (a ^ k! mod n)
			m = m.modPow(i, n);

			BigInteger gcdRes = n.gcd(m.subtract(BigInteger.ONE));

			// Non-Trivial
			if (gcdRes.compareTo(BigInteger.ONE) != 0){
				System.out.println(moduli_map.get(n) + " is factorable by " + gcdRes + " and " + n.divide(gcdRes));
				return gcdRes;
			}

		}
				
		// Factor not found. 
		return BigInteger.valueOf(-1);
	}
	
	public static BigInteger getRandBigInt(BigInteger size) {
		
		
	    BigInteger randBigInt = new BigInteger(size.bitLength(), rand);
	    
	    while( randBigInt.compareTo(size) >= 0 ) {
	    	
	    	// Regenerate BigInteger
	        randBigInt = new BigInteger(size.bitLength(), rand);
	    }
	    return randBigInt;
	}
	
	
	
}
