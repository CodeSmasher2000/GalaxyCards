package guiPacket;

public class Test {

	public static void main(String[] args) {
		int[] arr = {0,1,2,3,4,5,6,7,8,9,10};
		
		int endIndex = arr.length-1;
		int startIndex = 0;
		
		int steps = (endIndex+1-startIndex);
		int i = (startIndex+endIndex)>>1;
		int stepdir = 1;
		for(int q=0; q<steps; q++, i+=stepdir*q, stepdir=-stepdir)
		{
		   // test point i here and return early if error exceeds threshold
			System.out.println(arr[i]);
		}
		
	}
}
