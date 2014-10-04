/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apfe.maze.runtime;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class TerminalTest {

    static class TestRat extends Rat {

        @Override
        public Token peek() {
            return Token.create("123456", 123456);
        }

        @Override
        public Rat clone() {
            return new TestRat();
        }

        
    }

    static class TermSubClass extends Terminal {

        public static final int stTokenCode = 123456;

        public TermSubClass() {
        }

        @Override
        public boolean canPassThrough(Rat visitor) {
            return visitor.peek().getCode() == stTokenCode;
        }
    }

    /**
     * Test of createMazeElement method, of class Terminal.
     */
    @Test
    public void testCreateMazeElement() {
        System.out.println("createMazeElement");
        Vertex start = new Vertex();
        MazeElement expResult = null;
        MazeElement result = (new TermSubClass()).createMazeElement(start);
        assertNotNull(result);
        Edge edge = start.getOutGoingEdges().get(0);
        Rat rat = new TestRat();
        boolean ok = edge.canPassThrough(rat);
        assertTrue(ok);
    }

}
