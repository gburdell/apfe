/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tc2;

import tc2.apfe.Grammar;
import apfe.runtime.Acceptor;
import apfe.runtime.CharBufState;
import apfe.runtime.CharBuffer;

/**
 *
 * @author gburdell
 */
public class Main {
    
    private static void runTest(final String s1) {
        //
        CharBuffer cbuf = new CharBuffer("<none>", s1);
        CharBufState st = CharBufState.create(cbuf, true);
        Grammar gram = new Grammar();
        Acceptor acc = gram.accept();
        assert(null != acc);
        System.out.println("parse OK\n" + acc);
    }

    private static void test() {
        String dat[] = new String[]{
            "aaaaa",
            "bbbbbb",
            "ccccc",
            "ac",
            "b",
            "aaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbcccccccccccccccccccccc"
        };
        for (String s : dat) {
            System.out.println("Test: " + s);
            runTest(s);
        }
    }
    
    public static void main(String argv[]) {
        test();
    }
}
 
