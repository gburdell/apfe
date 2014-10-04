
package apfe.maze.tc2.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.tc2.*;



public  class e extends Acceptor implements NonTerminal, ITokenCodes {

    public e() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Repetition(new Terminal(A_K), true) ,
new Repetition(new Terminal(B_K)) ,
new Optional(new Terminal(C_K)) ,
new Repetition(new Terminal(D_K), true) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public e create() {
        return new e();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


