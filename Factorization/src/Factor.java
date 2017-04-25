import java.math.*;
import java.util.*;

public class Factor {
	
	static Random rand = new Random();
	
	public static void PollardRho(BigInteger n) {
		
		int i = 1;
		Random rand = new Random();
		BigInteger x1 = new BigInteger(n.bitLength(), rand);
		BigInteger y = x1;
		int k = 2;
		
		while (true) {
		
			i = i + 1; 			
		}
	}
	
	public static void PollardRhoMinusOneBootstrap(BigInteger n){
		
		BigInteger a = getRandBigInt(n); // getting a base a,
		
		
		
		
	}
	
	public static boolean PollardRhoMinusOne(BigInteger n, BigInteger a){
		
		a = getRandBigInt(n);
		
		System.out.println(a);
		
		return true;
		
	}
	
	public static BigInteger getRandBigInt(BigInteger size) {
		
	  
	    BigInteger randBigInt = new BigInteger(size.bitLength(), rand);
	    
	    while( randBigInt.compareTo(size) >= 0 ) {
	    	
	    	// Regenerate BigInteger
	        randBigInt = new BigInteger(size.bitLength(), rand);
	    }
	    return randBigInt;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("Factorizing Moduli");
		System.out.println("trying again to commit/print");
		System.out.println("Sara trying to commit");
		System.out.println("Sara trying to commit again");
		
		BigInteger a = getRandBigInt(BigInteger.valueOf(1024));
		
		System.out.println(a);
		
	}
	
}
