
package apfe.maze.tc2.generated ;


import apfe.maze.runtime.*;
import apfe.maze.tc2.*;



public  class Grammar extends Acceptor implements NonTerminal, ITokenCodes {

    public Grammar() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new source_text() ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public Grammar create() {
        return new Grammar();
    }


}

