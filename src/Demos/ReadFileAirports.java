package Demos;

import java.io.*;
import java.util.*;

public abstract   class ReadFileAirports implements Comparable <Airport>  {
    private static int[] arraySort = new int[10];
   private static int[][] sortTwoArray = new int[5][5];
    //defult Constructor.
//    public ReadFileAirports(int id, String airportName, String city, String country, String code3, String code4, double latitude, double longitude, int altitude, float timeZone, char dst, String dbTimeZone) {
//        super(id, airportName, city, country, code3, code4, latitude, longitude, altitude, timeZone, dst, dbTimeZone);
//    }

    //This Method print contains one deminsion Array.
    public static List<Airport> printArray(List<Airport> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + " | " + list.get(i));
        }
        return list;
    }
    /*this method print one deminstion integer array.
    * */
    public static void printArrayOD(int[]array){
        for (int i = 0; i <array.length ; i++) {
            System.out.print(" " + array[i]);
        }
    }
    /*this method print integer two deminsion array*/
    public static void printArrayTD(int[][] array){
        for (int row = 0; row < array.length ; row++) {
            for (int column = 0; column <array[row].length ; column++) {
                System.out.print("|"+ array[row][column]);
            }
            System.out.println();
        }
    }

    private static List<Airport> getDatFile(String fileName) {
        //  final String path ="data\\"+fileName;
        final String path = "D:\\Coders Programming\\Java\\Coursera\\Object Oriented Java Programming Data Structures and Beyond\\Course One\\My UCSDUnfoldingMaps_Master\\data\\" + fileName;
        List<Airport> list = new ArrayList<>();
        String line;
        try {
            //    FileInputStream fis = new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while ((line = reader.readLine()) != null) {
                String[] token = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                for (int i = 0; i < token.length; i++) {
                    token[i] = token[i].replace("\"", "");
                    if (token.length >= 0) {
                        Airport airport = createAirport(token);
                        list.add(airport);
                    }
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error with file ");
            e.printStackTrace();
        }
        return list;
    }

    private static Airport createAirport(String[] mateData) {
        int id_name = Integer.parseInt(mateData[0]);
        String airport = mateData[1];
        String city_Name = mateData[2];
        String country_Name = mateData[3];
        String code_Three = mateData[4];
        String code_Four = mateData[5];
        double lat = Double.parseDouble(mateData[6]);
        double longt = Double.parseDouble(mateData[7]);
        int altitude = Integer.parseInt(mateData[8]);
        float zone = Float.parseFloat(mateData[9]);
        char dst = mateData[10].charAt(0);
        String dbZone = mateData[11];
        return new Airport(id_name, airport, city_Name, country_Name, code_Three, code_Four
                , lat, longt, altitude, zone, dst, dbZone);
    }

    public static String findAirportCode(String toFind, List<Airport> airports) {
//            int index = 0;
//            while (index < airports.size()) {
//                if (toFind.equals(airports.get(index).getCode3())) {
//                    return airports.get(index).getCountry();
//                }
//                index++;
//            }
//        return null;
        for (int i = 0; i < airports.size(); i++) {
            if (toFind.equals(airports.get(i).getCode3())) {
                return airports.get(i).getCountry();
            }
        }
        return null;
    }

    public static String findAirportCodeBS( List<Airport> airports,int low,int high,String toFind){
//        int low = 0;
//        int high = airports.size()-1;
//        int mid;
//        while (low <= high) {
//            mid = low + ((high-low)/2);
//            int compare = toFind.compareTo(airports.get(mid).getCity());
//            if (compare < 0) {
//                high = mid - 1;
//            }
//            else if (compare > 0) {
//                low = mid+1;
//            }
//            else return (airports.get(mid)).getCode3();
//        }
//        return null;
//      int low = 0;
//      int high = airports.size()-1;
      int mid = low+((high-low))/2;
        int numberTry = 0;
      boolean isFound = false;
      while (!isFound){
          if (low<high) {
              System.out.println("Element is not found....");
              break;
        }
          numberTry++;
          int comapre = toFind.compareTo(airports.get(mid).getCity());
        Integer[] convert = airports.toArray(new Integer[airports.size()]);

          if (airports.get(mid).equals(toFind)){
              System.out.println("Element is  found.....");
              System.out.println("Found element after Try: " + numberTry);
              break;
          }
          if (comapre< low  ){
              low = mid+1;
          }else if (comapre>high){
              high = mid -1;
          }
        else return (airports.get(mid).getCode3());
      }
      return null;
    }
/*This method sort integer Array ascending or descending...
* */

    public static void selectionSort(int[]value){
       int minIdx;
        for (int i = 0; i < value.length-1 ; i++) {
            minIdx=i ;
            for (int j = i; j <value.length ; j++) {
                if (value[j]>value[minIdx]){
                    minIdx = j;
                }
            }
           // swap(value,i,minIdx);
            int temp = value[i];
            value[i] = value[minIdx];
            value[minIdx] = temp;
        }
        printArrayOD(arraySort);
//=========================================================
        /* Another sort Ascending Array by manipualtion*/
//        Arrays.sort(value);
//        for (int element: value) {
//            System.out.printf(" %s " ,element);
//        }
    }
    /*This method selection soer two demination array...*/
    public static void selectSortTwoArray(int[][]value){
      int rMax,cMax,vMax;
        for (int row = 0; row <value.length ; row++) {
            for (int column = 0; column <value[row].length ; column++) {
                rMax = row;
                cMax = column;
                vMax = value[row][column];
// finish the row current row
                for (int cInner = column+1; cInner <value[row].length ; cInner++) {
                    if (value[row][cInner]<vMax){
                        rMax = row;
                        cMax = cInner;
                        vMax = value[row][cInner];
                    }
                }
                // evaluate remaining rows
                for (int rInner = row+1; rInner <value.length ; rInner++) {
                    for (int cInner = row+1; cInner <value[row].length ; cInner++) {
                        if (value[rInner][cInner]<vMax){
                            rMax = rInner;
                            cMax= cInner;
                            vMax = value[rInner][cInner];
                        }
                    }
                }
                // swap. No aux needed, as vMax contains value of max
                value[rMax][cMax] = value[row][column];
                value[row][column] = vMax;
            }
        }
        printArrayTD(value);
    }

    public static void swap(int[]array,int i,int y){
        int temp = array[i];
        array[i] =array[y];
        array[y]= temp;
    }
    /*This method sort array using mySterySort*/
    public static void mySterySort(int[] vals){
        int currInd;
        for (int pos = 1; pos <vals.length ; pos++) {
            currInd = pos;
            while (currInd>0 && vals[currInd]<vals[currInd-1]){
                swap(vals,currInd,currInd-1);
                currInd = currInd -1;
            }
        }
        printArrayOD(arraySort);
    }

   private static List<Integer> builtsort = new ArrayList<>();
    public static void myBuiltInSortingTest(List<Integer> list){
        Random random = new Random();

        for (int i = 0; i <list.size() ; i++) {
            list.add(random.nextInt(100));
        }
        Collections.sort(list);
        for (int i = 0; i <list.size() ; i++) {
            System.out.println(" " +list.get(i));
        }
        System.out.println("New array after builting sort: " + list);
    }

    public static int findLocation(int[]array,int num){
        int k = 0;
        for (int i = 0; i <array.length ; i++) {
            if (array[i] == num){
                 k =i;
                 break;
            }
        }
        return k;
    }
    
    public static void display(){
        List<Airport> airports = getDatFile("airports.dat");
        System.out.println(airports);
//              System.out.println("=========================================");
//        System.out.println("Search airport file by Linear Search......");
//            System.out.println(findAirportCode("KJP",airports));
//            System.out.println("============================================");
//        System.out.println("Search airport file by BinarySerch  Search......");
//             System.out.println( findAirportCodeBS(airports,0,
//                   airports.size()-1,"Ganges"));
//        System.out.println("============================================================");
//
//        System.out.println("Selection sort one Deminsion Array......");
        /*Sort Array by random number */
//        Random random = new Random();
//        for (int i = 0; i <arraySort.length ; i++) {
//            arraySort[i] = random.nextInt(100);
//        }
//       selectionSort(arraySort);
//        System.out.println("=====================================================");
//        System.out.println("Selection sort two deminsion Array.....");
//        /*Selection sort Two demintion array*/
//        for (int i = 0; i <sortTwoArray.length ; i++) {
//            for (int j = 0; j <sortTwoArray[i].length ; j++) {
//                sortTwoArray[i][j] = random.nextInt(15);
//            }
//        }
//        selectSortTwoArray(sortTwoArray);
//        System.out.println("================================");
//        mySterySort(arraySort);
//        System.out.println("==============================");
//        System.out.println(findLocation(arraySort,10));
//        System.out.println("=============================");
//        myBuiltInSortingTest(builtsort);
    }
    // method run before  all funcation.
    public static void main(String[] a){
        display();
    }

}
