package edu.gatech.seclass;

public class WhiteboxClass {

    public static void whiteboxMethod1() {
        //Task 1: Add to the class a method called whiteboxMethod1 that contains a division by zero fault such that
        // (1) it is possible to create a test suite that achieves 100% branch coverage and does not reveal the fault, and
        // (2) every test suite that achieves 100% statement coverage reveals the fault.

        // Meeting the criteria above is not possible. Branch coverage subsumes statement coverage, so finding a fault during 100% statement coverage will also be revealed during 100% branch coverage.

    }

    public static int whiteboxMethod2(int x){
        //Task 2: Add to the class a method called whiteboxMethod2 that contains a division by zero fault such that
        // (1) it is possible to create a test suite that achieves less than 100% statement coverage and reveals the fault, and
        // (2)  it is possible to create a test suite that achieves 100% path coverage and does not reveal the fault.

        int y = 17;
        int z = 1;
        if (x > 10)
            z = x-y;
        int ans =  1/z;
        return ans;

    }

    public static void whiteboxMethod3(boolean a, boolean b){
        //Task 3: Add to the class a method called whiteboxMethod3 that contains a division by zero fault such that
        // (1) every test suite that achieves 100% statement coverage but less than 100% branch coverage does not reveal the fault, and
        // (2)  it is possible to create a test suite that achieves 100% branch coverage and reveals the fault.

        // The task above is not possible since a test suite will always be able to exploit the fault if every statement is being covered for all possible inputs.


    }

    public static void whiteboxMethod4(){
        //Task 4: Add to the class a method called whiteboxMethod4 that contains a division by zero fault such that
        // (1) it is possible to create a test suite with less than 100% statement coverage that does find the fault , and
        // (2) every test suite that achieves 100% statement coverage does not reveal the fault.

        //Meeting the criteria of Task 4 above is not possible. If the failure is found in less than 100% statement coverage it will also be found in every test suite with 100% statement coverage, since this could also include the failed test case.


    }

    public boolean whiteboxMethod5 (boolean a, boolean b) {
        int x = 2;
        int y = 4;
        if(a)
            x = x*2;
        else
            b = !b;
        if(b)
            y -= x;
        else
            x -= y;
        return ((x/y)>= 1);
    }


// ================
//
    // Fill in column “output” with T, F, or E:
    //
// | a | b |output|
// ================
// | T | T |   E   |
// | T | F |   F   |
// | F | T |   F   |
// | F | F |   T   |
// ================
//
// Fill in the blanks in the following sentences with
// “NEVER”, “SOMETIMES” or “ALWAYS”:
//
// Test suites with 100% statement coverage __SOMETIMES__ reveal the fault in this method.
// Test suites with 100% branch coverage __SOMETIMES__ reveal the fault in this method.
// Test suites with 100% path coverage ___ALWAYS___ reveal the fault in this method.
// ================

}

