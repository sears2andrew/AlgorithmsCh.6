//Andrew Sears

package chap6;

import java.util.*;

public class Chap6 {
    
//    
//    start 6.25
//    
// this function is used as the initial call to use 6.25
//it takes in an array of integers and puts the data into
//an arraylist. then it calls and returns the segment function
//
//tested:   1234458      expected output:   yes
//          33333                           no
//          123456789                       yes    
//    

    
    public static boolean get3Sequence(int[] list) {
        ArrayList<Integer> temp = new ArrayList<>();
        for(int a = 0; a < list.length; a++) {
            temp.add(list[a]);
        }
        return getSegments(temp, 3);
    }
    
//this function starts off by calculating what each segment should add up to
//and loops for each segment. then a new temp arraylist is created for each
//segment and tested to see if it is empty. 
    
    
    private static boolean getSegments(ArrayList<Integer> list, int numSegments){
        ArrayList<Integer> tempList = new ArrayList<>();
        int sum = 0;
        for(int l = 0; l < list.size(); l++) {
            sum += list.get(l);
            tempList.add(list.get(l));
        }
        sum /= numSegments;
        for(int s = numSegments; s > 0; s--) {
            ArrayList<Integer> tempSeq = getSequence(tempList, sum);
            if (tempSeq.isEmpty()) {
                return false;
            }
            for(int t = 0; t < tempSeq.size(); t++) {
                int idx = tempList.indexOf(tempSeq.get(t));
                if (idx >= 0) {
                    tempList.remove(idx);
                }
            }
        }
        return true;
    }
    
//this function takes in an arraylist and a ceiling
//if a new highest is fount while traversing an array
//and it is less than the ceiling it is set to max.
//max is returned after the larraylist is explored
    
    private static int getMax(ArrayList<Integer> list, int noHigher) {
        int max = Integer.MIN_VALUE;
        for(int l = 0; l < list.size(); l++) {
            if (list.get(l) > max && list.get(l) <= noHigher) {
                max = list.get(l);
            }
        }
        return max;
    }
    
//this function loops through the given arraylist that 
//makes a copy of the list and returns it to avoid dearrying
//the original arraylist

    private static ArrayList<Integer> cloneList(ArrayList<Integer> list) {
        ArrayList<Integer> temp = new ArrayList<>();
        for(int a = 0; a < list.size(); a++) {
            temp.add(list.get(a));
        }
        return temp;
    }
    
//thsi function takes in an arraylist and an int that represents
//the value needed to be added to a segment to complete that segment
//then return if the given list is empty or if the new distance calculation
//results in 0. if it is 0, add the max to the result arrylist
//and remove it from a copy of the arraylist and recursivly call getsequence
//with the remaining list

    private static ArrayList<Integer> getSequence(ArrayList<Integer> list, int total) {
        ArrayList<Integer> result = new ArrayList<>();
        if (list.isEmpty()) {
            return result;
        }
        int max = getMax(list, total);
        int difference = total - max;
        if (difference == 0) {
            result.add(max);
            return result;
        }
        ArrayList<Integer> subList = cloneList(list);
        subList.remove(subList.indexOf(max));
        ArrayList<Integer> tempSeq = getSequence(subList, difference);
        if (tempSeq.isEmpty()) {
            return result;
        }
        result.add(max);
        result.addAll(tempSeq);
        return result;
    }
    
//    
//    end 6.25
//    
//    start 6.2
//
//this function takes in an array of possible stops
//that can be made on a trip. if it has reached that last stop, it adds 
//it to an arraylist that is to be returned. if has not reached the destination
//than it calculated the distance from the current location to the next choices
//it minamalizes the penalty and add the choice to the arraylist
//then sets the new current location at that distance.
//
//tested:   200, 300, 400, 600      expected output:    1, 3, 4
//          200, 400, 580, 610                          1, 2, 4
//          200, 400, 401                               1, 2, 3
//    
    
    public static ArrayList<Integer> getOptimalStops(int[] list) {
        int startMile = 0;
        ArrayList<Integer> stops = new ArrayList<>();
        for(int l = 0; l < list.length; l++) {
            if (l == list.length - 1) {
                stops.add(l + 1);
            }
            else{
                int distance1 = list[l] - startMile;
                int distance2 = list[l + 1] - startMile;
                if (Math.abs(200 - distance1) < Math.abs(200 - distance2)) {
                    stops.add(l + 1);
                    startMile = list[l];
                }
            }
        }
        return stops;
    }
    
    
//
//    end 6.2
//
//    start 6.6
//
//this function takes in a multiplication matrix and
//an array of inputs. it then creates and empty set and
//populates it with data from the array. then the function
//itterates through the data and adds calculated pairs to the 
//set. if any of those result in a 0, then the result of the multiplication
//will be 0. so the function returns in a 0 is within the data set.
//    
//tested:   bbbbac      expected output:    yes
//          bbbbbb                          no
//          baccbc                          yes    
//    
    

    static boolean parenthesize(int[][] matrix, int[] arr){
        
        int holder = arr.length;
        Set<Integer>[][] set = new Set[holder][holder];
        for(int i=0; i<holder; i++){
            
            set[i][i] = new HashSet<>();
            set[i][i].add(arr[i]);
        }
        for(int i = 1; i<holder; i++){
            
            int counter = -1;
            for(int j = i; j<holder; j++){  
                
                counter++;
                set[counter][j] =  new HashSet<>();
                for(int k=counter; k < counter+i; k++){
                    
                    Iterator<Integer> outer = set[counter][k].iterator();
                    while(outer.hasNext()){
                        
                        int nextouter = outer.next();
                        Iterator<Integer> inner = set[k+1][j].iterator();
                        while(inner.hasNext()){
                            
                            set[counter][j].add(matrix[nextouter][inner.next()]);
                        }
                    }
                }
            }
        }
        return set[0][holder-1].contains(0);
    }    

    
//
//    end 6.6
//
//    start main
//
//allow fiarly easy testing of each of the chapter problems
//
   

    public static void main(String args[] ) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("enter the number with your coresponding choice:\n\n1)run 6.2\n2)run 6.6\n3)run 6.25\n4) Exit");
        int choice = input.nextInt();
        switch (choice) {
            case 1:
                int[] test =  {200, 400, 401};
                ArrayList<Integer> stops = getOptimalStops(test);
                System.out.print("stop at: ");
                for(int i = 0; i < stops.size(); i++){
                    System.out.print(stops.get(i) + " ");
                }
                break;
            case 2:
                int     matrix[][] = {{1,1,0},{2,1,0},{0,2,2}};
                System.out.println("how large of a arring array do you want?");
                int arrsize = input.nextInt();
                int [] data = new int [arrsize];
                for(int i = 0; i<arrsize;i++){
                    System.out.println("enter array value: " +i);
                    switch (input.next()) {
                        case "a":
                            data[i] = 0;
                            break;
                        case "b":
                            data[i] = 1;
                            break;
                        default:
                            data[i] = 2;
                            break;
                    }
                }
                System.out.print("result output is: ");
                System.out.println(parenthesize(matrix, data));
                break;
            case 3:
                System.out.println("Input set: 1, 2, 3, 4, 4, 5, 8 ");
                int[] arr = { 1, 2, 3, 4, 4, 5, 8 }; 
                boolean ok = get3Sequence(arr); 
                String holder = "no";
                if(ok){
                    holder = "yes";
                }
                System.out.println("result: " + holder);
                break;
            default:
                System.out.print("exiting");
                break;
        }
    }
    
    
//
//    end main
//

    
}
