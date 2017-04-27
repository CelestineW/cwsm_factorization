import java.math.*;
import java.util.*;

public class Factor {
	
	static Random rand = new Random();
	
	// Pollard Rho implementation 
	public static BigInteger PollardRho(BigInteger n) {
		
		BigInteger x = new BigInteger("2");
		BigInteger y = new BigInteger("2");
		
		BigInteger d = BigInteger.ONE;
		
		while (true) {
			
			x = (x.pow(2)).add(BigInteger.ONE).mod(n);
			y = (y.pow(2)).add(BigInteger.ONE).mod(n);
			y = (y.pow(2)).add(BigInteger.ONE).mod(n);
			
			d = n.gcd(x.subtract(y));
			
			if (!d.equals(BigInteger.ONE) && !d.equals(n)) {
				System.out.println("d: "+ d);
				return d;
			}
			
			// we will never find one this way 
			if (d.equals(n)) {
				return n;				
			}
			
		}
		
	}
	
	public static BigInteger PollardPMinusOne(BigInteger n){
		
		BigInteger a = getRandBigInt(n); // getting a base a,
		a = BigInteger.valueOf(2); // For Testing

		
		//System.out.println("Your num: " + n);
		
		//System.out.println("Your base, a : " + a);

		
		BigInteger m = a; // gets accumulated for faster computations
		BigInteger bound = new BigInteger("10000000"); // B set according to elliptic curve method
		
		for (BigInteger i = BigInteger.valueOf(2); i.compareTo(n) <  0 ; i = i.add(BigInteger.ONE)){	
			
			// (a ^ k! mod n)
			m = m.modPow(i, n);

			BigInteger gcdRes = n.gcd(m.subtract(BigInteger.ONE));

			// Non-Trivial
			if (gcdRes.compareTo(BigInteger.ONE) != 0){
				
				System.out.println("Returning, a factor of n: " + gcdRes);	
				return gcdRes;
			}

			//System.out.println("Iterations: " + i.subtract(BigInteger.ONE));

		}
				
		// Factor not found. 
		return BigInteger.valueOf(-1);
	}
	
	public static BigInteger getRandBigInt(BigInteger size) {
		
		//System.out.println(size.bitLength());
		
	    BigInteger randBigInt = new BigInteger(size.bitLength(), rand);
	    
	    while( randBigInt.compareTo(size) >= 0 ) {
	    	
	    	// Regenerate BigInteger
	        randBigInt = new BigInteger(size.bitLength(), rand);
	    }
	    return randBigInt;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Factorizing Moduli");
		
		// Sample n 
		BigInteger a = getRandBigInt(BigInteger.valueOf(1024));
		
		BigInteger j = new BigInteger("5874944399468278831");
		
		BigInteger k = new BigInteger("1633339503459119690580928718149821598898458545541617999969136559642034201952904677527967822044015401967438017");

		BigInteger i = new BigInteger("15581958524003781659");
	
		BigInteger q = new BigInteger("1024");
		
		//PollardRho(j);
		//PollardPMinusOne(j);

		PollardRho(i);
		PollardPMinusOne(i);
	}
	
}
