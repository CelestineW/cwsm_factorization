import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.*;
import java.util.*;

public class Factor {
	
	static Random rand = new Random();
	static HashMap<BigInteger, String> moduli_map = new HashMap<BigInteger, String>();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		File here = new File(".");
		System.out.println(here.getAbsolutePath());
		
		File moduli_file = new File("/Users/cwong2/Documents/3_Junior Year/cmsc441_proj2/Factorization/src/temp_moduli.txt");
		
		Scanner input = new Scanner(moduli_file);

		
		while(input.hasNextLine()){
			String[] moduli_set = input.nextLine().split(",");
			System.out.println("Pair: " + moduli_set[0] + " " + moduli_set[1]);
			//moduli_map.put(new BigInteger(moduli_set[1]), moduli_set[0]);

		}
		
		
		// Sample n 
		BigInteger a = getRandBigInt(BigInteger.valueOf(1024));
		
		BigInteger j = new BigInteger("5874944399468278831");
		
		BigInteger k = new BigInteger("1633339503459119690580928718149821598898458545541617999969136559642034201952904677527967822044015401967438017");

		BigInteger i = new BigInteger("15581958524003781659");
	
		BigInteger q = new BigInteger("1024");
		
		//System.out.println(PollardPMinusOne(k));
		
	}
	
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
		
		BigInteger m = a; // gets accumulated for faster computations
		BigInteger bound = new BigInteger("10000000"); // B set according to elliptic curve method
		
		for (BigInteger i = BigInteger.valueOf(2); i.compareTo(bound) <  0 ; i = i.add(BigInteger.ONE)){	
			// (a ^ k! mod n)
			m = m.modPow(i, n);

			BigInteger gcdRes = n.gcd(m.subtract(BigInteger.ONE));

			// Non-Trivial
			if (gcdRes.compareTo(BigInteger.ONE) != 0){
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
