/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author AyokaGooding
 */
public class DFA {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        Scanner reader = new Scanner(System.in);
        
         String a = args[0];
         String b = args[1];
         String c = args[2];

        BufferedReader file = new BufferedReader(new FileReader(a)); //args[0]
        BufferedReader file2 = new BufferedReader(new FileReader(b));
        FileWriter writer = new FileWriter(c);
        BufferedWriter buffer = new BufferedWriter(writer);

        ArrayList<String> input2 = new ArrayList<String>();
        ArrayList<String> ARRLDFA = new ArrayList<String>();
        String line = "";
        String line2 = "";

        while ((line = file.readLine()) != null) {

            if (!line.isEmpty()) {
                ARRLDFA.add(line);

            }
        }

        while ((line2 = file2.readLine()) != null) {

            if (!line2.isEmpty()) {
                input2.add(line2);
            }

        }

        String input[] = new String[ARRLDFA.size()];

        for (int i = 0; i < ARRLDFA.size(); i++) {
            input[i] = ARRLDFA.get(i);
        }

        //get all the states into the States array
        int frontnum = 8;
        int backnum = 10;
        input[2] = input[2].replaceAll(" ", "");
        input[2] = input[2].replaceAll(",", "");

        int statesLength = (input[2].length() - 9) / 2;
        String States[] = new String[statesLength];
        for (int i = 0; i < statesLength; i++) {
            States[i] = input[2].substring(frontnum, backnum);
            frontnum += 2;
            backnum += 2;
        }

        // get the alphabet into the alphabet array
        frontnum = 10;
        backnum = 11;

        input[3] = input[3].replaceAll(" ", "");
        input[3] = input[3].replaceAll(",", "");

        statesLength = input[3].length() - 11;
        String Alpha[] = new String[statesLength];

        for (int i = 0; i < statesLength; i++) {
            Alpha[i] = input[3].substring(frontnum, backnum);
            frontnum++;
            backnum++;
        }

        // get the starting state
        input[4] = input[4].replaceAll(" ", "");
        input[4] = input[4].replaceAll(",", "");
        String SS = input[4].substring(14, 16);

        // get final states
        frontnum = 13;
        backnum = 15;
        input[5] = input[5].replaceAll(" ", "");
        input[5] = input[5].replaceAll(",", "");

        statesLength = (input[5].length() - 14) / 2;
        String FS[] = new String[statesLength];

        for (int i = 0; i < statesLength; i++) {
            FS[i] = input[5].substring(frontnum, backnum);
            frontnum += 2;
            backnum += 2;
        }

        //Transition Function in 2d array
        String tf = "";
        for (int i = 7; i < input.length - 1; i++) {
            tf = tf + input[i];
        }
        tf = tf.replaceAll(",", "");
        tf = tf.replaceAll(" ", "");
        tf = tf.replaceAll("\\(", "");
        tf = tf.replaceAll("\\)", "");

        int mid = 3;
        frontnum = 1;
        backnum = 3;
        int fn2 = 4;
        int bn2 = 6;
        int numT = (ARRLDFA.size() - 8);

        String TF[][] = new String[numT][3];
        for (int row = 0; row < numT; row++) {
            for (int col = 0; col < 3; col++) {

                if (col == 0) {
                    TF[row][col] = tf.substring(frontnum, backnum);
                    frontnum += 6;
                    backnum += 6;
                }
                if (col == 1) {
                    TF[row][col] = tf.substring(mid, mid + 1);
                    mid += 6;
                }
                if (col == 2) {
                    TF[row][col] = tf.substring(fn2, bn2);
                    fn2 += 6;
                    bn2 += 6;
                }
            }
        }

        //looping through the states then the alphabet
        String[][] table = new String[States.length + 1][Alpha.length + 1];

        for (int row = 1; row < States.length + 1; row++) {
            table[row][0] = States[row - 1];
        }
        for (int row = 0; row < States.length; row++) {
            for (int col = 1; col < Alpha.length + 1; col++) {
                table[0][col] = Alpha[col - 1];
            }
        }

        //Adding all the transitions to the table
        int Stemp1 = 0;
        int Atemp1 = 0;
        String begin = "";
        String midd = "";
        String end = "";
        boolean t = true;
        for (int rowTF = 0; rowTF < numT; rowTF++) {
            begin = TF[rowTF][0];
            midd = TF[rowTF][1];
            end = TF[rowTF][2];

            for (int rowTab = 1; rowTab < States.length + 1; rowTab++) {
                if (begin.equals(table[rowTab][0])) {
                    Stemp1 = rowTab;
                    break;
                }
            }
            for (int colTab = 1; colTab < Alpha.length + 1; colTab++) {
                if (midd.equals(table[0][colTab])) {
                    Atemp1 = colTab;
                    break;
                }
            }
            table[Stemp1][Atemp1] = end;
        }

        String[] alpString = new String[input2.size()];
        String currentState = SS;
        int alp = 0;
        int stu = 0;
        boolean isLastNum = false;
        boolean isFinalState = false;
        boolean isNull = false;
        
        for (int i = 0; i < input2.size(); i++) {

            alpString[i] = input2.get(i);

        }

        //looping through every letter in str
        for (int j = 0; j < alpString.length; j++) {

         //   try {

                for (int k = 0; k < alpString[j].length(); k++) {

                    Character CurrLetter = alpString[j].charAt(k);

                    for (int row = 1; row < States.length + 1; row++) {

                        if (currentState.equals(table[row][0])) {

                            stu = row;
                            break;
                        }

                    }

                    //going through alphabet
                    for (int col = 1; col < Alpha.length + 1; col++) {
                        if (table[0][col].equals(CurrLetter.toString())) {
                            alp = col;
                            break;
                        }

                    }

                    currentState = table[stu][alp];

                    if (k == alpString[j].length() - 1) {
                        isLastNum = true;
                    }

                    for (int l = 0; l < FS.length; l++) {

                        if (isLastNum == true && currentState.equals(FS[l])) {
                            isFinalState = true;
                            break;
                        }

                    }

                    if (isNull == true) {
                        System.out.println(alpString[j] + " rejects");
                        buffer.write(alpString[j] + " is rejected" + "\r\n\r\n");
                        break;
                    }
                    if (isLastNum == true && isFinalState == false) {
                        System.out.println(alpString[j] + " rejects");
                        buffer.write(alpString[j] + " is rejected" + "\r\n\r\n");
                        break;
                    }
                    if (isLastNum == true && isFinalState == true) {
                        System.out.println(alpString[j] + " accepts");
                        buffer.write(alpString[j] + " is accepted" + "\r\n\r\n");
                        break;
                    }

                }

           // } //catch (NullPointerException e) {

            //    System.out.println(alpString[j] + " rejects");
             //   buffer.write(alpString[j] + " is rejected" + "\r\n\r\n");

           // }

            currentState = SS;
            isLastNum = false;
            isFinalState = false;
            isNull = false;
        }

        
       // System.out.println(Arrays.deepToString(table).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

        file.close();

        buffer.close();
        

    }

}
