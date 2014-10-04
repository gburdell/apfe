
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class data_type extends Acceptor implements NonTerminal, ITokenCodes {

    public data_type() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new integer_vector_type(),
new Optional(new signing()) ,
new Repetition(new packed_dimension()) ),
new Sequence(new integer_atom_type(),
new Optional(new signing()) ),
new non_integer_type(),
new Sequence(new struct_union(),
new Optional(new Sequence(new Terminal(PACKED_K),
new Optional(new signing()) )) ,
new Terminal(LCURLY),
new struct_union_member(),
new Repetition(new struct_union_member()) ,
new Terminal(RCURLY),
new Repetition(new packed_dimension()) ),
new Sequence(new Terminal(ENUM_K),
new Optional(new enum_base_type()) ,
new Terminal(LCURLY),
new enum_name_declaration(),
new Repetition(new Sequence(new Terminal(COMMA),
new enum_name_declaration())) ,
new Terminal(RCURLY),
new Repetition(new packed_dimension()) ),
new Terminal(STRING_K),
new Terminal(CHANDLE_K),
new Sequence(new Terminal(VIRTUAL_K),
new Optional(new Terminal(INTERFACE_K)) ,
new interface_identifier(),
new Optional(new Sequence(new Terminal(DOT),
new modport_identifier())) ),
new Terminal(EVENT_K),
new type_reference(),
new Sequence(new Optional(new Alternates(new package_scope(),
new class_scope())) ,
new type_identifier(),
new Repetition(new packed_dimension()) ),
new class_type(),
new ps_covergroup_identifier()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public data_type create() {
        return new data_type();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


