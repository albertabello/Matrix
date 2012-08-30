public class Matrix {
    private final int M;             // number of rows
    private final int N;             // number of columns
    private final double[][] data;   // M-by-N array
    private static double[] conv = {-1,0,1};
    
    // create M-by-N matrix of 0's
    public Matrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new double[M][N];
    }

	// create and return a random M-by-N matrix with values between 0 and 1
    public static Matrix random(int M, int N) {
        Matrix A = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[i][j] = Math.round((Math.random()*10));
        return A;
    }
    
    //Creates empty matrix
    public static Matrix empty(int M, int N) {
    	Matrix A = new Matrix(M, N);
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                A.data[i][j] = 0;
        return A;    	
    }

    // print matrix to standard output
    public void show() {
    	System.out.println();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) 
                System.out.print(data[i][j]+"\t");
            System.out.println();
        }
    }
    
    public static double [][] convolution2D(double [][] input, int width, int height, double [] kernel, int kernelWidth,int kernelHeight){
    	int smallWidth = width - kernelWidth + 1;
		int smallHeight = height - kernelHeight + 1; 
		double [][] output = new double [smallWidth][smallHeight];
		for(int i=0;i<smallWidth;++i){
			for(int j=0;j<smallHeight;++j){
				output[i][j]=0;
			}
		}
		for(int i=0;i<smallWidth;++i){
			for(int j=0;j<smallHeight;++j){
				output[i][j] = doConv(input,i,j,kernel,
						kernelWidth,kernelHeight);
			}
		}
		return output;
    }
    
    public static double doConv(double [][] input, int x, int y, double [] k, int kernelWidth, int kernelHeight){
		double output = 0;
		for(int i=0;i<kernelWidth;++i){
			for(int j=0;j<kernelHeight;++j){
				output = output + (int)Math.round(input[x+i][y+j] * k[i]);
			}
		}
		return output;
	}
    
    public static Matrix convolution2DPadded(double [][] input, int width, int height, double [] kernel, int kernelWidth, int kernelHeight){
		int smallWidth = width - kernelWidth + 1;
		int smallHeight = height - kernelHeight + 1; 
		double small [][] = new double [smallWidth][smallHeight];
		small = convolution2D(input,width,height,kernel,kernelWidth,kernelHeight);
		Matrix output = Matrix.empty(smallWidth,smallHeight);
		for(int j=0;j<smallHeight;++j){
			for(int i=0;i<smallWidth;++i){
				output.data[i][j]=small[i][j];
			}
		}
		//Returns small convoluted matrix, not counting the zero padding just important data
		return output;
	}

    public static Matrix diffX(Matrix M) {
    	Matrix output = Matrix.empty(M.M, M.N-1);
    	for (int i=0; i<M.M; i++) {
    		int x=1;
    		for (int j=0; j<M.N-1; j++) {
    			output.data[i][j] =  M.data[i][j]*(M.N-x);
    			x++;
    		}
    	}
    	return output;
    }
    
    public static Matrix diffY(Matrix M) {
    	Matrix output = Matrix.empty(M.M-1, M.N);
    	for (int i=0; i<M.N; i++) {
    		int y=1;
    		for (int j=0; j<M.M-1; j++) {
    			output.data[j][i] =  M.data[j][i]*(M.M-y);
    			y++;
    		}
    	}
    	return output;
    }
    
    public static Matrix slowConvolution(double [][] input, int width, int height, double [] kernel, int kernelWidth, int kernelHeight){
		int sum, mm,nn;
		int centerX=kernelWidth/2;
		int centerY=kernelHeight/2;
		int rowIndex, colIndex;

		Matrix output = Matrix.empty(width,height);
		for (int i=0; i < height; i++){
			for (int j=0; j < width; j++){
				sum=0;
				for (int m=0; m<kernelHeight; m++){
					mm = kernelHeight - 1 -m;
					for (int n=0; n<kernelWidth; n++){
						nn = kernelWidth -1 -n;
						System.out.print(n);
						rowIndex = j + m - centerY;
						colIndex = j + n - centerX;
						
						if (rowIndex >= 0 && rowIndex < height && colIndex >= 0 && colIndex < width){
							sum = (int) (sum + input[width * rowIndex][colIndex] * kernel[kernelWidth * mm]);
						}
					}
					System.out.println(m);
				}
				output.data[width * i][j] = sum;
			}
		}
		
		//http://www.songho.ca/dsp/convolution/convolution.html
		return output;
	}
    
    
   
	/**
	 * @param args
	 * Convolution and derivation of matrices or Sobel Operator
	 */
	public static void main(String args[]) {
		// TODO Auto-generated method stub
		if (args.length != 2 || Integer.parseInt(args[0])<3 || Integer.parseInt(args[1]) == 0 ) {
			System.out.println("Be a good boy");
			System.exit(1);
		}
		
		//Random Matrix
        Matrix M = Matrix.random(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        M.show(); 
		//System.out.println("\n\n");
        //Empty result Matrix
        //Matrix resultConv = Matrix.empty(Integer.parseInt(args[0])+1-1, Integer.parseInt(args[1])+3-1);
        //resultConv.show();
        
        Matrix resultConv = convolution2DPadded(M.data, M.M, M.N, conv, 3, 1);
        resultConv.show();
        
        Matrix convRes = slowConvolution(M.data, M.M, M.N, conv, 3, 1);
        convRes.show();
        /*Matrix resultDiffX = diffX(resultConv);
        resultDiffX.show();
        
        Matrix resultDiffY = diffY(resultConv);
        resultDiffY.show();*/
        
	}

}