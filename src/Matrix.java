public class Matrix {
    private final int row;             // number of rows
    private final int col;             // number of columns
    private final double[][] data;   // M-by-N array
    
    // create M-by-N matrix of 0's
    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        data = new double[row][col];
    }

    //Defined matrix for testing, 1,2,3,4,5... No random
    public static Matrix test(int row, int col){
		Matrix A = new Matrix(row,col);
		int count =0;
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++){
                A.data[i][j] = count;
        		count++;
            }
        return A;	
    }

    //Sets the kernel for convolution
    public static Matrix kernel(int row, int col) {
        Matrix A = new Matrix(row, col);
        int cont = 1;
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++){
                A.data[i][j] = cont;
        		cont++;
            }
        return A;
    }
    
	// create and return a random M-by-N matrix with values between 0 and 100
    public static Matrix random(int row, int col) {
        Matrix A = new Matrix(row, col);
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                A.data[i][j] = Math.round((Math.random()*100));
        return A;
    }
    
    //Creates empty matrix
    public static Matrix empty(int row, int col) {
    	Matrix A = new Matrix(row, col);
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                A.data[i][j] = 0;
        return A;    	
    }

    // print matrix to standard output
    public void show() {
    	System.out.println();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) 
                System.out.print(data[i][j]+"\t");
            System.out.println();
        }
    }
    
    public static Matrix convolution(double [][] input, int height, int width, double [][] kernel, int kernelHeight,int kernelWidth){
    	int smallWidth = width - kernelWidth + 1;
		int smallHeight = height - kernelHeight + 1; 
		Matrix output = Matrix.empty(smallHeight, smallWidth);
		for(int i=0;i<smallHeight;++i){
			for(int j=0;j<smallWidth;++j){
				double sum = 0;
				for(int ki=0;ki<kernelHeight;++ki){
					for(int kj=0;kj<kernelWidth;++kj){
						sum = sum + (int)Math.round(input[i+ki][j+kj] * kernel[ki][kj]);
					}
				}
				output.data[i][j]=sum;
			}
		}
		return output;
    }

    public static Matrix diffX(Matrix M) {
    	final long startTime = System.nanoTime();
    	final long endTime;
    	Matrix output = Matrix.empty(M.row-1, M.col);
    	try {
	    	for (int i=1; i<M.row; i++) {
	    		for (int j=0; j<M.col; j++) {
	    			output.data[i-1][j] =  M.data[i][j]-M.data[i-1][j];
	    		}
	    	}
    	} finally {
    		endTime = System.nanoTime();
    	}
    	System.out.println("Time elapsed for dx: "+ (endTime-startTime)/100000+" ms");
    	return output;
    }
    
    public static Matrix diffY(Matrix M) {
    	final long startTime = System.nanoTime();
    	final long endTime;
    	Matrix output = Matrix.empty(M.row, M.col-1);
    	try {
	    	for (int i=0; i<M.row; i++) {
	    		for (int j=1; j<M.col; j++) {
	    			output.data[i][j-1] =  M.data[i][j]-M.data[i][j-1];
	    		}
	    	}
    	} finally {
    		endTime = System.nanoTime();
    	}
    	System.out.println("Time elapsed for dy: "+ (endTime-startTime)/1000000+" ms");
    	return output;
    }
    
    //Find max min using loop
    public static int getMaxValue(Matrix M){
    	final long startTime = System.nanoTime();
    	final long endTime;   	
        int maxValue = (int) M.data[0][0];
        try{
	        for(int i=0;i<M.row;i++){  
	            for(int j=1;j<M.col;j++){
	            	if(M.data[i][j] > maxValue){  
	                    maxValue = (int) M.data[i][j];  
	                }	
	            }
	        }
		} finally {
			endTime = System.nanoTime();
		}
		System.out.println("Time elapsed for max: "+ (endTime-startTime)/1000000+" ms");
        return maxValue;  
    }  
      
    public static int getMinValue(Matrix M){
    	final long startTime = System.nanoTime();
    	final long endTime;
        int minValue = (int)M.data[0][0]; 
        try {
	        for(int i=0;i<M.row;i++){  
	            for(int j=1;j<M.col;j++){
	            	if(M.data[i][j] < minValue){  
	                    minValue = (int)M.data[i][j];  
	                }
	            }
	        }
        } finally {
			endTime = System.nanoTime();
		}
		System.out.println("Time elapsed for min: "+ (endTime-startTime)/1000000+" ms");
        return minValue;  
    } 
    
    /**
	 * @param args
	 * Convolution and derivation of matrices for image processing or similar
	 */
	public static void main(String args[]) {
		// TODO Auto-generated method stub
		if (args.length != 2 || Integer.parseInt(args[0])<3 || Integer.parseInt(args[1]) < 4 ) {
			System.out.println("Be a good boy and add correct inputs ;)");
			System.exit(1);
		}
		
		//Random Matrix [row][col]
        Matrix M = Matrix.random(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        //M.show(); 
        Matrix K = Matrix.kernel(1, 3);
        //K.show();

        //Calculates valid convolution, pixels only affected by the operation, no padding
        Matrix resultConv = convolution(M.data, M.row, M.col, K.data, K.row, K.col);
        //resultConv.show();
        M = null;
        K = null;
        
        Matrix resultDiffX = diffX(resultConv);
        //resultDiffX.show();
        //int min = Min(resultDiffX, resultDiffX.col-1, resultDiffX.row,(int)resultDiffX.data[resultDiffX.row][resultDiffX.col-1]);
        System.out.println("\n--------------------");
        System.out.println("Computing max-min dx");
        int max = getMaxValue(resultDiffX);
        int min = getMinValue(resultDiffX);
        System.out.println("MAX value dx: "+max);
        System.out.println("MIN value dx: "+min);
        resultDiffX=null;
        
        Matrix resultDiffY = diffY(resultConv);
        //resultDiffY.show();
        System.out.println("\n--------------------");
        System.out.println("Computing max-min dy");
        max = getMaxValue(resultDiffY);
        min = getMinValue(resultDiffY);
        resultDiffY=null;
        System.out.println("MAX value dy: "+max);
        System.out.println("MIN value dy: "+min);
        System.out.println("\n\nThat's all folks!");
	}

}