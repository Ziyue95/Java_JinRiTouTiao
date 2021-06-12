package com.company;
import java.util.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Main {

    public  static void print(int index, Object obj){
        System.out.println(String.format("%d. %s", index, obj.toString()));
    }
    /** Basic Operation */
    public static void demoOperation(){
        print(1, 5 / 2);//int division -> int 2
        print(2, 5 / 2.0);//int/float -> float 2.5
        print(3, 5 + 2);
        print(3, 5 * 2);
        print(4, 5 - 2);
        print(5, 5 << 2);// 0x101 -> 0x10100
        print(6, 5 >> 2);// 0x101 -> 0x1
        print(7, 5 & 2);//and operation on binary 0x101 0x10 -> 0x000 = 0
        print(8, 5 | 2);//or operation on binary 0x101 0x10 -> 0x111 = 7
        print(9, 5 == 2);
        print(10, 5 != 2);
        print(11, 5 % 2);// return the residue of 2 when dividing 5;

        int a = 2;
        a += 2;
        System.out.println("int a = 2; a += 2, a: " + a);
        double b = 2.0;
        float c = (float)3.14;//will cause error when doing float c = 3.14;
        char d = 65;
        char e = 'a';
        String str="javatpoint";
        char[] ch=str.toCharArray();
        print(11, d);
        print(12, e);
        print(13, Arrays.toString(ch));

        char data[] = {'a','b','c'};
        str = new String(data);
        System.out.println(str);//get abc as output;


    }

    /** Control Flow */
    public static void demoControlFlow(){
        /** if/else if/else control */
        int score = 65;
        if(score > 80){ print(1,"A");}
        else if(score > 60){ print(2,"B");}
        else{print(3,"C");}
        /** define boolean variables in java */
        Boolean a = true;
        Boolean b = false;
        if(a){print(4, "a is true");}
        if(!b){print(5, "b is false");}

        /** case control */
        String grade = "B";
        switch(grade){
            case "A":
                print(6, "score > 80");
                break;
            case "B":
                print(7,"score in (60,80)");
                break;
            default://in other cases
                print(8,"score < 60");
        }
        /** for loop , continue and break */
        for(int i = 0; i < 6; i = 1 + i){
            if(i == 1) {
                continue;
            }
            if(i == 5){
                break;
            }
            if(i % 2 == 0) {
                print(9, i);
            }
        }
        /** for loop over Array */
        String str = "hello";
        for(char c: str.toCharArray()){
            print(10, c);
        }

        int target = 20;
        int current = 0;
        while(current < target) {
            current += 5;
            print(11, current);
        }
    }
    /** string operation */
    public static void demoString(){
        String str = "hello world";
        print(1, str.indexOf("e"));
        print(2, str.charAt(6));
        print(3, str.codePointAt(1));//transfer 1st char into its ASCII code
        print(4, str.compareTo("hello word")); // compare the diff of ASCII code between first diff char
        print(5, str.compareToIgnoreCase("HEllo Word"));
        print(6, str.concat("!!"));
        print(7, str.contains("hello"));
        print(9, str.endsWith("orld"));
        print(10, str.startsWith("hel"));
        print(11, str.lastIndexOf('o'));
        print(12, str.toUpperCase());
        print(13, str.replace('o', 'a'));
        print(14, str.replaceAll("o|l", "a"));
        print(15, str.replaceAll("hello", "hi"));

        StringBuilder sb = new StringBuilder();
        sb.append(true);
        sb.append(1);
        sb.append(2.2);
        print(16, sb.toString());
        print(17, "a" + "b" + String.valueOf(12));
    }
    /** list operation */
    public static void demoList() {
        /** create list of string */
        List<String> strList = new ArrayList<String>();
        for (int i = 0; i < 4; ++i) {
            strList.add(String.valueOf(i));
        }
        print(1, strList);
        List<String> strListB = new ArrayList<String>();
        for (int i = 0; i < 4; ++i) {
            strListB.add(String.valueOf(i * i));
        }
        strList.addAll(strListB);
        print(2, strList);
        strList.remove(0);
        print(3, strList);
        strList.remove(String.valueOf(9));
        print(4, strList);
        print(5, strList.get(1));

        Collections.sort(strList);
        print(6, strList);
        Collections.sort(strList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        print(7, strList);

        Collections.reverse(strList);
        print(8, strList);

        /** create array of integer */
        int[] array = new int[]{1, 2, 3,4,5,6};
        print(9, array[1]);

        /** create list of integer */
        List<Integer> intList = new ArrayList<Integer>();
        for (int i = 0; i < 4; ++i) {
            intList.add(i*i*i);
        }
        print(10, intList);

    }
    /** set operation */
    public static void demoSet() {
        Set<String> strSet = new HashSet<String>();
        for (int i = 0; i < 3; ++i) {
            strSet.add(String.valueOf(i));
            strSet.add(String.valueOf(i));
            strSet.add(String.valueOf(i));
        }
        print(1, strSet);
        strSet.remove("1");
        print(2, strSet);
        print(3, strSet.contains(4));
        strSet.addAll(Arrays.asList(new String[]{"A", "B", "C"}));
        print(4, strSet);

        for (String value : strSet) {
            print(5, value);
        }

        print(6, strSet.isEmpty());
        print(7, strSet.size());
    }
    /** create HashMap */
    public static void demoKeyValue() {
        /** key of this HashMap is String, value of this HashMap is String */
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 4; ++i) {
            map.put(String.valueOf(i), String.valueOf(i * i));
            //map.put(String.valueOf(i), String.valueOf(i * i * i));
        }
        print(1, map);
        /** interate over the entries of HashMap */
        for (Map.Entry<String, String> entry : map.entrySet()) {
            print(2, entry.getKey() + ":" + entry.getValue());
        }
        print(3, map.keySet());
        print(4, map.values());
        print(5, map.containsKey("2"));
        print(5, map.containsValue("4"));
        print(6, map.get("2"));//returns the value corresponding to key "2"
        map.replace("1", "A");//replace values of HashMap
        print(7, map);
    }
    /** try/catch/finally (try/Except in python) expression */
    public static void demoException() {

        try {
            print(1, "hello");
            String b = null;
            b.indexOf("a");

        } catch (NullPointerException npe) {
            print(3, "NPE");
        } catch (Exception e) {
            print(4, "Exception");
        } finally {
            /** code will be executed anyway regardless of exception */
            print(2, "finally");
            // 做清理工作
        }

        try {
            print(1, "hello");
            int a = 2;
            a = a / 0;
        /** add a log file when an Exception occurs */
        } catch (NullPointerException npe) {
            print(3, "NPE");
        } catch (Exception e) {
            print(4, "Exception");
        } finally {
            /** code will be executed anyway regardless of exception */
            print(2, "finally");
            // 做清理工作
        }
    }

    public static void demoCommon() {
        /** Random class */
        // salt uuid
        Random random = new Random();
        random.setSeed(1);
        for (int i = 0; i < 4; ++i) {
            print(1, random.nextInt(100));
            print(2, random.nextDouble());
        }
        List<Integer> array = Arrays.asList(new Integer[]{1, 2, 3, 4, 5});
        print(3, array);
        Collections.shuffle(array);
        print(4, array);

        Date date = new Date();
        print(5, date);
        print(6, date.getTime());
        DateFormat df = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd");
        print(7, df.format(date));
        print(8, DateFormat.getDateInstance(DateFormat.FULL).format(date));
        // 习题，已知今天日期，计算未来的时间，星期几

        print(9, UUID.randomUUID());
        print(10, Math.max(1, 2));
        print(11, Math.ceil(2.2));
        print(12, Math.floor(2.5));
        print(13, Math.log(2.71)); //e

    }

    public static void main(String[] args) {
	// write your code here
        //System.out.println("Hello nowcoder!");
        //print(1, "hello world!");
        //demoOperation();
        //demoControlFlow();
        //demoString();
        //demoList();
        //demoSet();
        //demoKeyValue();
        //demoException();
        demoCommon();
    }
}
