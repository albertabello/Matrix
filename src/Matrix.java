public class Matrix {
    private final int M;             // number of rows
    private final int N;             // number of columns
    private final int[][] data;   // M-by-N array
    
    // create M-by-N matrix of 0's
    public Matrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new int[M][N];
    }

	// create and return a random M-by-N matrix with values between 0 and 1
    public static Matrix random(int M, int N) {
        Matrix A = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[i][j] = (int) (Math.random()*10);
        return A;
    }
    
    // print matrix to standard output
    public void show() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) 
                System.out.print(data[i][j]);
            System.out.println();
        }
    }
    
	/**
	 * @param args
	 */
	public static void main(String args[]) {
		// TODO Auto-generated method stub
		if (args.length != 2) {
			System.out.println("Be a good boy");
			System.exit(1);
		}
		
        Matrix M = Matrix.random(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        M.show(); 
		
		//Correct input arguments
		System.out.println(args[0]);
		System.out.println(args[1]);
		
	}

}