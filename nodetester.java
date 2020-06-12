import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class nodetester  {

	
	public static void main(String [] args) {
	
	
	
		int [] f = {2,3};
		int g [] = {9,4};
		int h [] = {-17,5};
		int [] j = {0,2};
		int [] l = {-9,3};
	//	int [] q = {2,4,3};
	//	int [] y = {2,4,3};
	
		Datum one = new Datum(f);
		Datum two = new Datum(g);
		Datum three = new Datum(h);
		Datum four = new Datum(j);
		Datum five = new Datum(l);
		//Datum six = new Datum(q);
		//Datum seven = new Datum(y);
		
		ArrayList<Datum> list = new ArrayList<Datum>();
		list.add(one);
		list.add(two);
		list.add(three);
		list.add(four);
		list.add(five);
		
	//	list.add(six);
	//list.add(seven);
		try {
			KDTree test = new KDTree(list);
System.out.println(test.rootNode.getLeaves());
testIterator(test , list);
			
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static void testIterator(KDTree tree, ArrayList<Datum> points){
        Iterator<Datum> it = tree.iterator();
        int [] raw_points = new int[points.size()];
        int [] raw_points_set =  new int[tree.size()];
        if (points.get(0).x.length > 2) {
            System.out.println("Warning! Does not check for the validity of the order of the points encountered by the iterator for more than 1D points.");
        }
        else {
            for (int i = 0; i < points.size(); i++)
                raw_points[i] = points.get(i).x[0];
            //sorting the array
            Arrays.sort(raw_points);
            //removing duplicates
            raw_points_set[0] = raw_points[0];
            int j = 0;
            for (int i = 1; i < raw_points.length ; i++) {
                if (raw_points_set[j] != raw_points[i]) {
                    j++;
                    raw_points_set[j] = raw_points[i];
                }

            }
        }
        int ct = 0;
        boolean ordering_flag=true;
        while (it.hasNext()) {
            int iterval = it.next().x[0];
            //System.out.println(iterval+" - "+raw_points_set[ct]);
            if(raw_points_set[ct] != iterval)
                ordering_flag=false;
            ct++;
        }
        System.out.println("Size is " + Integer.valueOf(tree.size()) + " & iterator count is " + Integer.valueOf(ct));

        if (points.get(0).x.length==2)
        {
            if(ordering_flag)
                System.out.println("The ordering of the datapoints encountered by the iterator is CORRECT. " +
                                   "\nFor a more robust check of the ordering keep the option RANDOM.");
            else
                System.out.println("The ordering of the datapoints encountered by the iterator is INCORRECT.");

        }

    }
}
	/*public static int splitValuee(int [] data,  int dim) {
		int val = 0;
		int m =0;
		int min = data[dim];
		int max = data[dim];
		for (int j = 0; j<data.length; j++) {
			if(data[j]<min) {
				min = data[j];
			}
			if(data[j]>max) {
				max = data[j];
			}
			
		}
	
		
		m = max+min;
		val = m/2;
		return val;
		
}*/
	
