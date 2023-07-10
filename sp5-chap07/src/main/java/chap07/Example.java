package chap07;

public class Example {
	public void test() {
		ImpeCalculator impeCal = new ImpeCalculator();
		long start1 = System.currentTimeMillis();
		long fourFactorial1 = impeCal.factorial(4);
		long end1 = System.currentTimeMillis();
		System.out.printf("ImpeCalculator.factorial(4) 실행 시간 = %d\n", (end1 - start1));
		
		RecCalculator recCal = new RecCalculator();
		long start2 = System.currentTimeMillis();
		long fourFactorial2 = recCal.factorial(4);
		long end2 = System.currentTimeMillis();
		System.out.printf("RecCalculator.factorial(4) 실행 시간 = %d\n", (end2 - start2));
		
		ExeTimeCalculator calculator = new ExeTimeCalculator(impeCal);
		long result = calculator.factorial(4);
	}
}
