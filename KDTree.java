
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
public class KDTree implements Iterable<Datum>{ 

	KDNode 		rootNode;
	int    		k; 
	int			numLeaves;
	
	// constructor

	public KDTree(ArrayList<Datum> datalist) throws Exception {

		Datum[]  dataListArray  = new Datum[ datalist.size() ]; 

		if (datalist.size() == 0) {
			throw new Exception("Trying to create a KD tree with no data");
		}
		else {
			this.k = datalist.get(0).x.length;
			
		}
		int ct=0;
		for (Datum d :  datalist) {
			dataListArray[ct] = datalist.get(ct);
			ct++;
		}
		
	//   Construct a KDNode that is the root node of the KDTree.

		rootNode = new KDNode(dataListArray);
	}
	
	//   KDTree methods
	
	public Datum nearestPoint(Datum queryPoint) {
		return rootNode.nearestPointInNode(queryPoint);
	}
	

	public int height() {
		return this.rootNode.height();	
	}

	public int countNodes() {
		return this.rootNode.countNodes();	
	}
	
	public int size() {
		return this.numLeaves;	
	}

	//-------------------  helper methods for KDTree   ------------------------------

	public static long distSquared(Datum d1, Datum d2) {

		long result = 0;
		for (int dim = 0; dim < d1.x.length; dim++) {
			result +=  (d1.x[dim] - d2.x[dim])*((long) (d1.x[dim] - d2.x[dim]));
		}
		// if the Datum coordinate values are large then we can easily exceed the limit of 'int'.
		return result;
	}

	public double meanDepth(){
		int[] sumdepths_numLeaves =  this.rootNode.sumDepths_numLeaves();
		return 1.0 * sumdepths_numLeaves[0] / sumdepths_numLeaves[1];
	}
	
	class KDNode { 

		boolean leaf;
		Datum leafDatum;           //  only stores Datum if this is a leaf
		
		//  the next two variables are only defined if node is not a leaf

		int splitDim;      // the dimension we will split on
		int splitValue;    // datum is in low if value in splitDim <= splitValue, and high if value in splitDim > splitValue  
		
		KDNode lowChild, highChild;   //  the low and high child of a particular node (null if leaf)
		  //  You may think of them as "left" and "right" instead of "low" and "high", respectively

		KDNode(Datum[] datalist) throws Exception{

			/*
			 *  This method takes in an array of Datum and returns 
			 *  the calling KDNode object as the root of a sub-tree containing  
			 *  the above fields.
			 */

			//   ADD YOUR CODE BELOW HERE
			Datum [] data;
			if(datalist.length>1 && datalist[0].equals(datalist[1])) {
				data = new Datum[1];
				data[0] = datalist[1];
			}
			else {
				data = datalist.clone();
			}
		
			
			if(data.length == 1) {
				leaf = true;
				lowChild = null;
				highChild= null;
			leafDatum = datalist[0];
			
			numLeaves++;
		
				return ;
				
			}
			else {
				
		splitDim = findSplitDim(datalist);
		
		splitValue = findSplitValue(datalist, splitDim);
		//System.out.println("Split val: " + splitValue );
		ArrayList <Datum> splitlow = new ArrayList<Datum>();
		ArrayList <Datum> splithigh = new ArrayList<Datum>();
		for(int i =0; i< datalist.length; i++) {
   		 if(datalist[i].x[splitDim]<=splitValue) {
   			 splitlow.add(datalist[i]);
   		 }
   		 if(datalist[i].x[splitDim]>splitValue) {
   			 splithigh.add(datalist[i]);
   		 }
   	 }
		
		int ct=0;
		Datum [] listlow = new Datum[splitlow.size()];
		for (Datum d : splitlow) {
			listlow[ct] = splitlow.get(ct);
			ct++;
		}
		//System.out.println("Lowlist: "+ Arrays.deepToString(listlow));
		int ind =0;
		Datum[] listhigh = new Datum[splithigh.size()];
		for(Datum e : splithigh) {
			listhigh[ind] = splithigh.get(ind);
			ind++;
		}
	//	System.out.println("HighList: " + Arrays.deepToString(listhigh));

			this.lowChild = new KDNode(listlow);
	
	
				this.highChild= new KDNode(listhigh);

		}
		
		
			
		
		
			
			//   ADD YOUR CODE ABOVE HERE

		
		}
		public int findSplitDim(Datum[] data) {
			 
			  int range = 0;
			  int dim = 0;
				for (int i = 0; i<k;i++) {
					int min = data[0].x[i];
					int max = data[0].x[i];
				for (int j = 0; j<data.length; j++) {
					if(data[j].x[i]<min) {
						min = data[j].x[i];
					}
					if(data[j].x[i]>max) {
						max = data[j].x[i];
					}
					if(max - min > range) {
						range = max-min;
						dim = i;
					}
		}}
				return dim;
		}
		public int findSplitValue(Datum [] data, int dim) {
			
			int val = 0;
			int m =0;
			int min = data[0].x[dim];
			
			int max = data[0].x[dim];
			if(k==1) {
				
				int mon = data[0].x[0];
				
				int mox = data[0].x[0];
				for (int j = 0; j<data.length; j++) {
					if(data[j].x[0]< mon) {
						mon = data[j].x[0];
					}
					if(data[j].x[0]>mox) {
						mox = data[j].x[0];
					}
					
				}
			
				m= mox+mon;
				val = m/2;
				return val;
			}
			else {
			for (int j = 0; j<data.length; j++) {
				if(data[j].x[dim]<min) {
					min = data[j].x[dim];
				}
				if(data[j].x[dim]>max) {
					max = data[j].x[dim];
				}
				
			}
			}
			m = max+min;
			val = m/2;
			return val;
			
		}
		public double Distance(Datum one, Datum two) {
			double t =0;
			double dis=0;
			for(int i = 0; i <k; i++) {
				t += Math.pow((double)one.x[i] - ((double)two.x[i]),2);
				dis = Math.sqrt(t);
			}
			return dis;
		}


		public Datum nearestPointInNode(Datum queryPoint) {
			Datum nearestPoint , nearestPoint_otherSide;
		
			//   ADD YOUR CODE BELOW HERE
			nearestPoint = null;
			double best = 0;
			KDNode node = rootNode;
			
			if(node.leaf) {
				nearestPoint = node.leafDatum;
				best = Distance(node.leafDatum, queryPoint);
				}
			
			else {
				int s = node.splitValue;
				int dim = node.splitDim;
				if(queryPoint.x[dim]<s) {
					node.lowChild.nearestPointInNode(queryPoint);
					node.highChild.nearestPointInNode(queryPoint);
				}
				else {
					node.highChild.nearestPointInNode(queryPoint);
					node.lowChild.nearestPointInNode(queryPoint);
				}
				}
			return nearestPoint;
			
			
			//   ADD YOUR CODE ABOVE HERE

		}
		
		// -----------------  KDNode helper methods (might be useful for debugging) -------------------

		public int height() {
			if (this.leaf) 	
				return 0;
			else {
				return 1 + Math.max( this.lowChild.height(), this.highChild.height());
			}
		}

		public int countNodes() {
			if (this.leaf)
				return 1;
			else
				return 1 + this.lowChild.countNodes() + this.highChild.countNodes();
		}
		
		/*  
		 * Returns a 2D array of ints.  The first element is the sum of the depths of leaves
		 * of the subtree rooted at this KDNode.   The second element is the number of leaves
		 * this subtree.    Hence,  I call the variables  sumDepth_size_*  where sumDepth refers
		 * to element 0 and size refers to element 1.
		 */
				
		public int[] sumDepths_numLeaves(){
			int[] sumDepths_numLeaves_low, sumDepths_numLeaves_high;
			int[] return_sumDepths_numLeaves = new int[2];
			
			/*     
			 *  The sum of the depths of the leaves is the sum of the depth of the leaves of the subtrees, 
			 *  plus the number of leaves (size) since each leaf defines a path and the depth of each leaf 
			 *  is one greater than the depth of each leaf in the subtree.
			 */
			
			if (this.leaf) {  // base case
				return_sumDepths_numLeaves[0] = 0;
				return_sumDepths_numLeaves[1] = 1;
			}
			else {
				sumDepths_numLeaves_low  = this.lowChild.sumDepths_numLeaves();
				sumDepths_numLeaves_high = this.highChild.sumDepths_numLeaves();
				return_sumDepths_numLeaves[0] = sumDepths_numLeaves_low[0] + sumDepths_numLeaves_high[0] + sumDepths_numLeaves_low[1] + sumDepths_numLeaves_high[1];
				return_sumDepths_numLeaves[1] = sumDepths_numLeaves_low[1] + sumDepths_numLeaves_high[1];
			}	
			return return_sumDepths_numLeaves;
		}
		public ArrayList<Datum> getLeaves(){
			ArrayList<Datum>list = new ArrayList<Datum>();
			this.leafHelper(list);
			return list;
		}
		private void leafHelper(ArrayList<Datum> L) {
			if(this.lowChild == null && this.highChild == null ) {
				L.add(leafDatum);
			}
			else {
				if(lowChild!=null) {
					lowChild.leafHelper(L);
				}
				if(highChild != null) {
					highChild.leafHelper(L);
				}
			}
		}
		
	}
	


	public Iterator<Datum> iterator() {
		return new KDTreeIterator();
	}
	
	private class KDTreeIterator implements Iterator<Datum> {
		
		//   ADD YOUR CODE BELOW HERE
	 ArrayList<Datum> list = rootNode.getLeaves();
	 
	 private int index = 0;
	
	
		public boolean hasNext() {
			
			return (index < list.size());
		}
		public Datum next() {
			//while hasNext == true
			// print ur element
			// update index of list by 1

			if(!hasNext()) { throw new IllegalArgumentException(" Does not have next ");
			}
			Datum temp = list.get(index);
			index = index+1;
			return temp;
			
		}
	}
		
		
	
			
		
	
		//   ADD YOUR CODE ABOVE HERE

	}





