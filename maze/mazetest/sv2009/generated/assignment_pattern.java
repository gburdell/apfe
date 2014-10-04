
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class assignment_pattern extends Acceptor implements NonTerminal, ITokenCodes {

    public assignment_pattern() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(SQUOTE),
new Terminal(LCURLY),
new structure_pattern_key(),
new Terminal(COLON),
new expression(),
new Repetition(new Sequence(new Terminal(COMMA),
new structure_pattern_key(),
new Terminal(COLON),
new expression())) ,
new Terminal(RCURLY)),
new Sequence(new Terminal(SQUOTE),
new Terminal(LCURLY),
new array_pattern_key(),
new Terminal(COLON),
new expression(),
new Repetition(new Sequence(new Terminal(COMMA),
new array_pattern_key(),
new Terminal(COLON),
new expression())) ,
new Terminal(RCURLY)),
new Sequence(new Terminal(SQUOTE),
new Terminal(LCURLY),
new constant_expression(),
new Terminal(LCURLY),
new expression(),
new Repetition(new Sequence(new Terminal(COMMA),
new expression())) ,
new Terminal(RCURLY),
new Terminal(RCURLY)),
new Sequence(new Terminal(SQUOTE),
new Terminal(LCURLY),
new expression(),
new Repetition(new Sequence(new Terminal(COMMA),
new expression())) ,
new Terminal(RCURLY))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public assignment_pattern create() {
        return new assignment_pattern();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


