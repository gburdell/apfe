source_text : timeunits_declaration? description*
;
description :
module_declaration
| udp_declaration
| interface_declaration
| program_declaration
| package_declaration
| attribute_instance* package_item
| attribute_instance* bind_directive
| config_declaration
;
module_nonansi_header :
attribute_instance* module_keyword lifetime? module_identifier
package_import_declaration* parameter_port_list? list_of_ports SEMI
;
module_ansi_header :
attribute_instance* module_keyword lifetime? module_identifier
package_import_declaration* parameter_port_list? list_of_port_declarations? SEMI
;
module_declaration :
module_nonansi_header timeunits_declaration? module_item*
ENDMODULE_K (COLON module_identifier)?
| module_ansi_header timeunits_declaration? non_port_module_item*
ENDMODULE_K (COLON module_identifier)?
| attribute_instance* module_keyword lifetime? module_identifier ( DOT_STAR ) SEMI
timeunits_declaration? module_item* ENDMODULE_K (COLON module_identifier)?
| EXTERN_K module_nonansi_header
| EXTERN_K module_ansi_header
;
module_keyword : MODULE_K | MACROMODULE_K
;
interface_declaration :
interface_nonansi_header timeunits_declaration? interface_item*
ENDINTERFACE_K (COLON interface_identifier)?
| interface_ansi_header timeunits_declaration? non_port_interface_item*
ENDINTERFACE_K (COLON interface_identifier)?
| attribute_instance* INTERFACE_K interface_identifier ( DOT_STAR ) SEMI
timeunits_declaration? interface_item*
ENDINTERFACE_K (COLON interface_identifier)?
| EXTERN_K interface_nonansi_header
| EXTERN_K interface_ansi_header
;
interface_nonansi_header :
attribute_instance* INTERFACE_K lifetime? interface_identifier
package_import_declaration* parameter_port_list? list_of_ports SEMI
;
interface_ansi_header :
attribute_instance* INTERFACE_K lifetime? interface_identifier
package_import_declaration* parameter_port_list? list_of_port_declarations? SEMI
;
program_declaration :
program_nonansi_header timeunits_declaration? program_item*
ENDPROGRAM_K (COLON program_identifier)?
| program_ansi_header timeunits_declaration? non_port_program_item*
ENDPROGRAM_K (COLON program_identifier)?
| attribute_instance* PROGRAM_K program_identifier ( DOT_STAR ) SEMI
timeunits_declaration? program_item*
ENDPROGRAM_K (COLON program_identifier)?
| EXTERN_K program_nonansi_header
| EXTERN_K program_ansi_header
;
program_nonansi_header :
attribute_instance* PROGRAM_K lifetime? program_identifier
package_import_declaration* parameter_port_list? list_of_ports SEMI
;
program_ansi_header :
attribute_instance* PROGRAM_K lifetime? program_identifier
package_import_declaration* parameter_port_list? list_of_port_declarations? SEMI
;
checker_declaration :
CHECKER_K checker_identifier (LPAREN checker_port_list? RPAREN)? SEMI
checker_or_generate_item*
ENDCHECKER_K (COLON checker_identifier)?
;
class_declaration :
VIRTUAL_K? CLASS_K lifetime? class_identifier parameter_port_list?
(EXTENDS_K class_type (LPAREN list_of_arguments RPAREN)? )? SEMI
class_item*
ENDCLASS_K (COLON class_identifier)?
;
package_declaration :
attribute_instance* PACKAGE_K lifetime? package_identifier SEMI
timeunits_declaration? (attribute_instance* package_item)*
ENDPACKAGE_K (COLON package_identifier)?
;
timeunits_declaration :
TIMEUNIT_K time_literal (DIV time_literal)? SEMI
| TIMEPRECISION_K time_literal SEMI
| TIMEUNIT_K time_literal SEMI TIMEPRECISION_K time_literal SEMI
| TIMEPRECISION_K time_literal SEMI TIMEUNIT_K time_literal SEMI
;
parameter_port_list :
POUND LPAREN list_of_param_assignments (COMMA parameter_port_declaration)* RPAREN
| POUND LPAREN parameter_port_declaration (COMMA parameter_port_declaration)* RPAREN
| POUND LPAREN RPAREN
;
parameter_port_declaration :
parameter_declaration
| local_parameter_declaration
| data_type list_of_param_assignments
| TYPE_K list_of_type_assignments
;
list_of_ports : LPAREN port (COMMA port)* RPAREN
;
list_of_port_declarations :
LPAREN ( attribute_instance* ansi_port_declaration (COMMA attribute_instance* ansi_port_declaration)* )? RPAREN
;
port_declaration :
attribute_instance* inout_declaration
| attribute_instance* input_declaration
| attribute_instance* output_declaration
| attribute_instance* ref_declaration
| attribute_instance* interface_port_declaration
;
port :
port_expression?
| DOT port_identifier LPAREN port_expression? RPAREN
;
port_expression :
port_reference
| (port_reference (COMMA port_reference)* )*
;
port_reference :
port_identifier constant_select
;
port_direction : INPUT_K | OUTPUT_K | INOUT_K | REF_K
;
net_port_header : port_direction? net_port_type
;
variable_port_header : port_direction? variable_port_type
;
interface_port_header :
interface_identifier (DOT modport_identifier)?
| INTERFACE_K (DOT modport_identifier)?
;
ansi_port_declaration :
(net_port_header | interface_port_header)? port_identifier unpacked_dimension*
( EQ constant_expression )?
| variable_port_header? port_identifier variable_dimension* (EQ constant_expression )?
| port_direction? DOT port_identifier LPAREN expression? RPAREN
;
elaboration_system_task :
// $fatal $error $warning $info
DS_FATAL_K (LPAREN finish_number (COMMA list_of_arguments)? RPAREN)? SEMI
| DS_ERROR_K (LPAREN list_of_arguments RPAREN)? SEMI
| DS_WARNING_K (LPAREN list_of_arguments RPAREN)? SEMI
| DS_INFO_K (LPAREN list_of_arguments RPAREN)? SEMI
;
finish_number : NUMBER	//012
;
module_common_item :
module_or_generate_item_declaration
| interface_instantiation
| program_instantiation
| assertion_item
| bind_directive
| continuous_assign
| net_alias
| initial_construct
| final_construct
| always_construct
| loop_generate_construct
| conditional_generate_construct
| elaboration_system_task
;
module_item :
port_declaration SEMI
| non_port_module_item
;
module_or_generate_item :
attribute_instance* parameter_override
| attribute_instance* gate_instantiation
| attribute_instance* udp_instantiation
| attribute_instance* module_instantiation
| attribute_instance* module_common_item
;
module_or_generate_item_declaration :
package_or_generate_item_declaration
| genvar_declaration
| clocking_declaration
| DEFAULT_K CLOCKING_K clocking_identifier SEMI
| DEFAULT_K DISABLE_K IFF_K expression_or_dist SEMI
;
non_port_module_item :
generate_region
| module_or_generate_item
| specify_block
| attribute_instance* specparam_declaration
| program_declaration
| module_declaration
| interface_declaration
| timeunits_declaration
;
parameter_override : DEFPARAM_K list_of_defparam_assignments SEMI
;
bind_directive :
BIND_K bind_target_scope (COLON bind_target_instance_list)? bind_instantiation SEMI
| BIND_K bind_target_instance bind_instantiation SEMI
;
bind_target_scope :
module_identifier
| interface_identifier
;
bind_target_instance :
hierarchical_identifier constant_bit_select
;
bind_target_instance_list :
bind_target_instance (COMMA bind_target_instance)*
;
bind_instantiation :
program_instantiation
| module_instantiation
| interface_instantiation
| checker_instantiation
;

//A.1.5 Configuration source text
config_declaration :
CONFIG_K config_identifier SEMI
(local_parameter_declaration SEMI)*
design_statement
config_rule_statement*
ENDCONFIG_K (COLON config_identifier)?
;
design_statement : DESIGN_K((library_identifier DOT)? cell_identifier)* SEMI
;
config_rule_statement :
default_clause liblist_clause SEMI
| inst_clause liblist_clause SEMI
| inst_clause use_clause SEMI
| cell_clause liblist_clause SEMI
| cell_clause use_clause SEMI
;
default_clause : DEFAULT_K
;
inst_clause : INSTANCE_K inst_name
;
inst_name : topmodule_identifier (DOT instance_identifier)*
;
cell_clause : CELL_K (library_identifier DOT)? cell_identifier
;
liblist_clause : LIBLIST_K library_identifier*
;
use_clause : USE_K (library_identifier DOT)? cell_identifier (COLON CONFIG_K)?
| USE_K named_parameter_assignment (COMMA named_parameter_assignment)* (COLON CONFIG_K)?
| USE_K (library_identifier DOT)? cell_identifier named_parameter_assignment
(COMMA named_parameter_assignment)* (COLON CONFIG_K)?
;

//A.1.6 Interface items
interface_or_generate_item :
attribute_instance* module_common_item
| attribute_instance* modport_declaration
| attribute_instance* extern_tf_declaration
;
extern :
EXTERN_K method_prototype SEMI
| EXTERN_K FORKJOIN_K task_prototype SEMI
;
interface_item :
port_declaration SEMI
| non_port_interface_item
;
non_port_interface_item :
generate_region
| interface_or_generate_item
| program_declaration
| interface_declaration
| timeunits_declaration
;
//A.1.7 Program items
program_item :
port_declaration SEMI
| non_port_program_item
;
non_port_program_item :
attribute_instance* continuous_assign
| attribute_instance* module_or_generate_item_declaration
| attribute_instance* initial_construct
| attribute_instance* final_construct
| attribute_instance* concurrent_assertion_item
| attribute_instance* timeunits_declaration
| program_generate_item
;
program_generate_item :
loop_generate_construct
| conditional_generate_construct
| generate_region
| elaboration_system_task
;
//A.1.8 Checker items
checker_port_list :
checker_port_item (COMMA checker_port_item)*
;
checker_port_item :
attribute_instance* property_formal_type port_identifier variable_dimension*
(EQ property_actual_arg)?
;
checker_or_generate_item :
checker_or_generate_item_declaration
| initial_construct
| checker_always_construct
| final_construct
| assertion_item
| checker_generate_item
;
checker_or_generate_item_declaration :
RAND_K? data_declaration
| function_declaration
| assertion_item_declaration
| covergroup_declaration
| overload_declaration
| genvar_declaration
| clocking_declaration
| DEFAULT_K CLOCKING_K clocking_identifier SEMI
| DEFAULT_K DISABLE_K IFF_K expression_or_dist SEMI
| SEMI
;
checker_generate_item :
loop_generate_construct
| conditional_generate_construct
| generate_region
| elaboration_system_task
;
checker_always_construct : ALWAYS_K statement
;
//A.1.9 Class items
class_item :
attribute_instance* class_property
| attribute_instance* class_method
| attribute_instance* class_constraint
| attribute_instance* class_declaration
| attribute_instance* covergroup_declaration
| local_parameter_declaration SEMI
| parameter_declaration SEMI
| SEMI
;
class_property :
property_qualifier* data_declaration
| CONST_K class_item_qualifier* data_type const_identifier (EQ constant_expression)? SEMI
;
class_method :
method_qualifier* task_declaration
| method_qualifier* function_declaration
| EXTERN_K method_qualifier* method_prototype SEMI
| method_qualifier* class_constructor_declaration
| EXTERN_K method_qualifier* class_constructor_prototype
;
class_constructor_prototype :
FUNCTION_K NEW_K LPAREN tf_port_list? RPAREN SEMI
;
class_constraint :
constraint_prototype
| constraint_declaration
;
class_item_qualifier :
STATIC_K
| PROTECTED_K
| LOCAL_K
;
property_qualifier :
random_qualifier
| class_item_qualifier
;
random_qualifier :
RAND_K
| RANDC_K
;
method_qualifier :
PURE_K? VIRTUAL_K
| class_item_qualifier
;
method_prototype :
task_prototype
| function_prototype
;
class_constructor_declaration :
FUNCTION_K class_scope? NEW_K (LPAREN tf_port_list? RPAREN)? SEMI
block_item_declaration*
(SUPER_K DOT NEW_K (LPAREN list_of_arguments RPAREN)? SEMI )?
function_statement_or_null*
ENDFUNCTION_K (COLON NEW_K)?
;
//A.1.10 Constraints
constraint_declaration : STATIC_K? CONSTRAINT_K constraint_identifier constraint_block
;
constraint_block : LCURLY constraint_block_item* RCURLY
;
constraint_block_item :
SOLVE_K solve_before_list BEFORE_K solve_before_list SEMI
| constraint_expression
;
solve_before_list : solve_before_primary (COMMA solve_before_primary)*
;
solve_before_primary : (implicit_class_handle DOT | class_scope)? hierarchical_identifier select
;
constraint_expression :
expression_or_dist SEMI
| expression MINUS_GT constraint_set
| IF_K LPAREN expression RPAREN constraint_set (ELSE_K constraint_set)?
| FOREACH_K LPAREN ps_or_hierarchical_array_identifier LBRACK loop_variables RBRACK RPAREN constraint_set
;
constraint_set :
constraint_expression
| LCURLY constraint_expression* RCURLY
;
dist_list : dist_item (COMMA dist_item)*
;
dist_item : value_range dist_weight?
;
dist_weight :
COLON_EQ expression
| COLON_DIV expression
;
constraint_prototype : constraint_prototype_qualifier? STATIC_K? CONSTRAINT_K constraint_identifier SEMI
;
constraint_prototype_qualifier : EXTERN_K | PURE_K
;
extern_constraint_declaration :
STATIC_K? CONSTRAINT_K class_scope constraint_identifier constraint_block
;
identifier_list : identifier (COMMA identifier)*
;
//A.1.11 Package items
package_item :
package_or_generate_item_declaration
| anonymous_program
| package_export_declaration
| timeunits_declaration
;
package_or_generate_item_declaration :
net_declaration
| data_declaration
| task_declaration
| function_declaration
| checker_declaration
| dpi_import_export
| extern_constraint_declaration
| class_declaration
| class_constructor_declaration
| local_parameter_declaration SEMI
| parameter_declaration SEMI
| covergroup_declaration
| overload_declaration
| assertion_item_declaration
| SEMI
;
anonymous_program : PROGRAM_K SEMI anonymous_program_item* ENDPROGRAM_K
;
anonymous_program_item :
task_declaration
| function_declaration
| class_declaration
| covergroup_declaration
| class_constructor_declaration
| SEMI
;
//A.2 Declarations
//A.2.1 Declaration types
//A.2.1.1 Module parameter declarations
local_parameter_declaration :
LOCALPARAM_K data_type_or_implicit list_of_param_assignments
| LOCALPARAM_K TYPE_K list_of_type_assignments
;
parameter_declaration :
PARAMETER_K data_type_or_implicit list_of_param_assignments
| PARAMETER_K TYPE_K list_of_type_assignments
;
specparam_declaration :
SPECPARAM_K packed_dimension? list_of_specparam_assignments SEMI
;
//A.2.1.2 Port declarations
inout_declaration :
INOUT_K net_port_type list_of_port_identifiers
;
input_declaration :
INPUT_K net_port_type list_of_port_identifiers
| INPUT_K variable_port_type list_of_variable_identifiers
;
output_declaration :
OUTPUT_K net_port_type list_of_port_identifiers
| OUTPUT_K variable_port_type list_of_variable_port_identifiers
;
interface_port_declaration :
interface_identifier list_of_interface_identifiers
| interface_identifier DOT modport_identifier list_of_interface_identifiers
;
ref_declaration : REF_K variable_port_type list_of_port_identifiers
;
//A.2.1.3 Type declarations
data_declaration :
CONST_K? VAR_K? lifetime? data_type_or_implicit list_of_variable_decl_assignments SEMI
| type_declaration
| package_import_declaration
| virtual_interface_declaration
;
package_import_declaration :
IMPORT_K package_import_item (COMMA package_import_item)* SEMI
;
package_import_item :
package_identifier COLON2 identifier
| package_identifier COLON2 STAR
;
package_export_declaration :
EXPORT_K STAR_COLON2_STAR SEMI
| EXPORT_K package_import_item (COMMA package_import_item)* SEMI
;
genvar_declaration : GENVAR_K list_of_genvar_identifiers SEMI
;
net_declaration :
net_type (drive_strength | charge_strength)? (VECTORED_K | SCALARED_K)?
data_type_or_implicit delay3? list_of_net_decl_assignments SEMI
;
type_declaration :
TYPEDEF_K data_type type_identifier variable_dimension* SEMI
| TYPEDEF_K interface_instance_identifier constant_bit_select DOT type_identifier type_identifier SEMI
| TYPEDEF_K (ENUM_K | STRUCT_K | UNION_K | CLASS_K)? type_identifier SEMI
;
lifetime : STATIC_K | AUTOMATIC_K
;
//A.2.2 Declaration data types
//A.2.2.1 Net and variable types
casting_type : simple_type | constant_primary | signing | STRING_K | CONST_K
;
data_type :
integer_vector_type signing? packed_dimension*
| integer_atom_type signing?
| non_integer_type
| struct_union (PACKED_K signing?)? LCURLY struct_union_member struct_union_member* RCURLY
packed_dimension*
| ENUM_K enum_base_type? LCURLY enum_name_declaration (COMMA enum_name_declaration)* RCURLY
packed_dimension*
| STRING_K
| CHANDLE_K
| VIRTUAL_K INTERFACE_K? interface_identifier
| (class_scope | package_scope)? type_identifier packed_dimension*
| class_type
| EVENT_K
| ps_covergroup_identifier
| type_reference
;
data_type_or_implicit :
data_type
| implicit_data_type
;
implicit_data_type : signing? packed_dimension*
;
enum_base_type :
integer_atom_type signing?
| integer_vector_type signing? packed_dimension?
| type_identifier packed_dimension?
;
enum_name_declaration :
enum_identifier (LBRACK integral_number (COLON integral_number)? RBRACK)? (EQ constant_expression)?
;
class_scope : class_type COLON2
;
class_type :
ps_class_identifier parameter_value_assignment?
(COLON2 class_identifier parameter_value_assignment?)*
;
integer_type : integer_vector_type | integer_atom_type
;
integer_atom_type : BYTE_K | SHORTINT_K | INT_K | LONGINT_K | INTEGER_K | TIME_K
;
integer_vector_type : BIT_K | LOGIC_K | REG_K
;
non_integer_type : SHORTREAL_K | REAL_K | REALTIME_K
;
net_type : SUPPLY0_K | SUPPLY1_K | TRI_K | TRIAND_K | TRIOR_K | TRIREG_K| TRI0_K | TRI1_K | UWIRE_K| WIRE_K | WAND_K | WOR_K
;
net_port_type :
net_type? data_type_or_implicit
;
variable_port_type : var_data_type
;
var_data_type : data_type | VAR_K data_type_or_implicit
;
signing : SIGNED_K | UNSIGNED_K
;
simple_type : integer_type | non_integer_type | ps_type_identifier | ps_parameter_identifier
;
struct_union_member :
attribute_instance* random_qualifier? data_type_or_void list_of_variable_decl_assignments SEMI
;
data_type_or_void : data_type | VOID_K
;
struct_union : STRUCT_K | UNION_K TAGGED_K?
;
type_reference :
TYPE_K LPAREN expression RPAREN
| TYPE_K LPAREN data_type RPAREN
;
//A.2.2.2 Strengths
drive_strength :
LPAREN strength0 COMMA strength1 RPAREN
| LPAREN strength1 COMMA strength0 RPAREN
| LPAREN strength0 COMMA HIGHZ1_K RPAREN
| LPAREN strength1 COMMA HIGHZ0_K RPAREN
| LPAREN HIGHZ0_K COMMA strength1 RPAREN
| LPAREN HIGHZ1_K COMMA strength0 RPAREN
;
strength0 : SUPPLY0_K | STRONG0_K | PULL0_K | WEAK0_K
;
strength1 : SUPPLY1_K | STRONG1_K | PULL1_K | WEAK1_K
;
charge_strength : LPAREN SMALL_K RPAREN | LPAREN MEDIUM_K RPAREN | LPAREN LARGE_K RPAREN
;
//A.2.2.3 Delays
delay3 : POUND delay_value | POUND LPAREN mintypmax_expression (COMMA mintypmax_expression (COMMA mintypmax_expression)? )? RPAREN
;
delay2 : POUND delay_value | POUND LPAREN mintypmax_expression (COMMA mintypmax_expression)? RPAREN
;
delay_value :
unsigned_number
| real_number
| ps_identifier
| time_literal
| W1STEP_K //1step
;
//A.2.3 Declaration lists
list_of_defparam_assignments : defparam_assignment (COMMA defparam_assignment)*
;
list_of_genvar_identifiers : genvar_identifier (COMMA genvar_identifier)*
;
list_of_interface_identifiers : interface_identifier param_assignment*
(COMMA interface_identifier param_assignment*)*
;
list_of_net_decl_assignments : net_decl_assignment (COMMA net_decl_assignment)*
;
list_of_param_assignments : param_assignment (COMMA param_assignment)*
;
list_of_port_identifiers : port_identifier param_assignment*
(COMMA port_identifier param_assignment*)*
;
list_of_udp_port_identifiers : port_identifier (COMMA port_identifier)*
;
list_of_specparam_assignments : specparam_assignment (COMMA specparam_assignment)*
;
list_of_tf_variable_identifiers : port_identifier variable_dimension* (EQ expression)?
(COMMA port_identifier variable_dimension* (EQ expression)?)*
;
list_of_type_assignments : type_assignment (COMMA type_assignment)*
;
list_of_variable_decl_assignments : variable_decl_assignment (COMMA variable_decl_assignment)*
;
list_of_variable_identifiers : variable_identifier variable_dimension*
(COMMA variable_identifier variable_dimension*)*
;
list_of_variable_port_identifiers : port_identifier variable_dimension* (EQ constant_expression)?
(COMMA port_identifier variable_dimension* (EQ constant_expression)?)*
;
list_of_virtual_interface_decl :
variable_identifier (EQ interface_instance_identifier)?
(COMMA variable_identifier (EQ interface_instance_identifier)?)*
;
//A.2.4 Declaration assignments
defparam_assignment : hierarchical_parameter_identifier EQ constant_mintypmax_expression
;
net_decl_assignment : net_identifier unpacked_dimension* (EQ expression)?
;
param_assignment :
parameter_identifier unpacked_dimension* (EQ constant_param_expression)?
;
specparam_assignment :
specparam_identifier EQ constant_mintypmax_expression
| pulse_control_specparam
;
type_assignment :
type_identifier (EQ data_type)?
;
pulse_control_specparam :
//PATHPULSE$
PATHPULSE_K EQ LPAREN reject_limit_value (COMMA error_limit_value)? RPAREN
//TODO: what the heck is this? | PATHPULSE$specify_input_terminal_descriptor$specify_output_terminal_descriptor
//= ( reject_limit_value (COMMA error_limit_value)? )
;
error_limit_value : limit_value
;
reject_limit_value : limit_value
;
limit_value : constant_mintypmax_expression
;
variable_decl_assignment :
variable_identifier variable_dimension? (EQ expression)?
| dynamic_array_variable_identifier unsized_dimension variable_dimension?
(EQ dynamic_array_new)?
| class_variable_identifier (EQ class_new)?
;
class_new : NEW_K (LPAREN list_of_arguments RPAREN | expression)?
;
dynamic_array_new : NEW_K expression? ( LPAREN expression RPAREN )?
;

//NOTE: from here on, the yapfe grammar was extended to support BNF
//constructs: ::= {} and []
//A.2.5 Declaration ranges
unpacked_dimension ::=
LBRACK constant_range RBRACK
| LBRACK constant_expression RBRACK
;
packed_dimension ::=
LBRACK constant_range RBRACK
| unsized_dimension
;
associative_dimension ::=
LBRACK data_type RBRACK
| LBRACK STAR RBRACK
;
variable_dimension ::=
unsized_dimension
| unpacked_dimension
| associative_dimension
| queue_dimension
;
queue_dimension ::= LBRACK DOLLAR [ COLON constant_expression ] RBRACK
;
unsized_dimension ::= LBRACK RBRACK
;
//A.2.6 Function declarations
function_data_type_or_implicit ::=
data_type_or_void
| implicit_data_type
;
function_declaration ::= FUNCTION_K [ lifetime ] function_body_declaration
;
function_body_declaration ::=
function_data_type_or_implicit
[ interface_identifier DOT | class_scope ] function_identifier SEMI
{ tf_item_declaration }
{ function_statement_or_null }
ENDFUNCTION_K [ COLON function_identifier ]
| function_data_type_or_implicit
[ interface_identifier DOT | class_scope ] function_identifier LPAREN [ tf_port_list ] RPAREN SEMI
{ block_item_declaration }
{ function_statement_or_null }
ENDFUNCTION_K [ COLON function_identifier ]
;
function_prototype ::= FUNCTION_K data_type_or_void function_identifier LPAREN [ tf_port_list ] RPAREN
;
dpi_import_export ::=
IMPORT_K dpi_spec_string [ dpi_function_import_property ] [ c_identifier EQ ] dpi_function_proto SEMI
| IMPORT_K dpi_spec_string [ dpi_task_import_property ] [ c_identifier EQ ] dpi_task_proto SEMI
| EXPORT_K dpi_spec_string [ c_identifier EQ ] FUNCTION_K function_identifier SEMI
| EXPORT_K dpi_spec_string [ c_identifier EQ ] TASK_K task_identifier SEMI
;
dpi_spec_string ::= STRING //"DPI-C" | "DPI"
;
dpi_function_import_property ::= CONTEXT_K | PURE_K
;
dpi_task_import_property ::= CONTEXT_K
;
dpi_function_proto ::= function_prototype
;
dpi_task_proto ::= task_prototype
;
//A.2.7 Task declarations
task_declaration ::= TASK_K [ lifetime ] task_body_declaration
;
task_body_declaration ::=
[ interface_identifier DOT | class_scope ] task_identifier SEMI
{ tf_item_declaration }
{ statement_or_null }
ENDTASK_K [ COLON task_identifier ]
| [ interface_identifier DOT | class_scope ] task_identifier LPAREN [ tf_port_list ] RPAREN SEMI
{ block_item_declaration }
{ statement_or_null }
ENDTASK_K [ COLON task_identifier ]
;
tf_item_declaration ::=
block_item_declaration
| tf_port_declaration
;
tf_port_list ::=
tf_port_item { COMMA tf_port_item }
;
tf_port_item ::=
{ attribute_instance }
[ tf_port_direction ] [ VAR_K ] data_type_or_implicit
[ port_identifier { variable_dimension } [ EQ expression ] ]
;
tf_port_direction ::= port_direction | CONST_K REF_K
;
tf_port_declaration ::=
{ attribute_instance } tf_port_direction [ VAR_K ] data_type_or_implicit list_of_tf_variable_identifiers SEMI
;
task_prototype ::= TASK_K task_identifier LPAREN [ tf_port_list ] RPAREN
;
//A.2.8 Block item declarations
block_item_declaration ::=
{ attribute_instance } data_declaration
| { attribute_instance } local_parameter_declaration SEMI
| { attribute_instance } parameter_declaration SEMI
| { attribute_instance } overload_declaration
| { attribute_instance } let_declaration
;
overload_declaration ::=
BIND_K overload_operator FUNCTION_K data_type function_identifier LPAREN overload_proto_formals RPAREN SEMI
;
overload_operator ::= PLUS | PLUS2 | MINUS | MINUS2 | STAR | STAR2 | DIV | MOD | EQ2 | NOT_EQ | LT | LT_EQ | GT | GT_EQ | EQ
;
overload_proto_formals ::= data_type {COMMA data_type}
;
//A.2.9 Interface declarations
virtual_interface_declaration ::=
VIRTUAL_K [ INTERFACE_K ] interface_identifier [ parameter_value_assignment] [DOT modport_identifier]
list_of_virtual_interface_decl SEMI
;
modport_declaration ::= MODPORT_K modport_item { COMMA modport_item } SEMI
;
modport_item ::= modport_identifier LPAREN modport_ports_declaration { COMMA modport_ports_declaration } RPAREN
;
modport_ports_declaration ::=
{ attribute_instance } modport_simple_ports_declaration
| { attribute_instance } modport_tf_ports_declaration
| { attribute_instance } modport_clocking_declaration
;
modport_clocking_declaration ::= CLOCKING_K clocking_identifier
;
modport_simple_ports_declaration ::=
port_direction modport_simple_port { COMMA modport_simple_port }
;
modport_simple_port ::=
port_identifier
| DOT port_identifier LPAREN [ expression ] RPAREN
;
modport_tf_ports_declaration ::=
import_export modport_tf_port { COMMA modport_tf_port }
;
modport_tf_port ::=
method_prototype
| tf_identifier
;
import_export ::= IMPORT_K| EXPORT_K
;
//A.2.10 Assertion declarations
concurrent_assertion_item ::=
[ block_identifier COLON ] concurrent_assertion_statement
| checker_instantiation
;
concurrent_assertion_statement ::=
assert_property_statement
| assume_property_statement
| cover_property_statement
| cover_sequence_statement
| restrict_property_statement
;
assert_property_statement::=
ASSERT_K PROPERTY_K LPAREN property_spec RPAREN action_block
;
assume_property_statement::=
ASSUME_K PROPERTY_K LPAREN property_spec RPAREN action_block
;
cover_property_statement::=
COVER_K PROPERTY_K LPAREN property_spec RPAREN statement_or_null
;
expect_property_statement ::=
EXPECT_K LPAREN property_spec RPAREN action_block
;
cover_sequence_statement::=
COVER_K SEQUENCE_K LPAREN [clocking_event ] [ DISABLE_K IFF_K LPAREN expression_or_dist RPAREN ]
sequence_expr RPAREN statement_or_null
;
restrict_property_statement::=
RESTRICT_K PROPERTY_K LPAREN property_spec RPAREN SEMI
;
property_instance ::=
ps_or_hierarchical_property_identifier [ LPAREN [ property_list_of_arguments ] RPAREN ]
;
property_list_of_arguments ::=
[property_actual_arg] { COMMA [property_actual_arg] } { COMMA DOT identifier LPAREN [property_actual_arg] RPAREN }
| DOT identifier LPAREN [property_actual_arg] RPAREN { COMMA DOT identifier LPAREN [property_actual_arg] RPAREN }
;
property_actual_arg ::=
property_expr
| sequence_actual_arg
;
assertion_item_declaration ::=
property_declaration
| sequence_declaration
| let_declaration
;
property_declaration ::=
PROPERTY_K property_identifier [ LPAREN [ property_port_list ] RPAREN ] SEMI
{ assertion_variable_declaration }
property_statement_spec
ENDPROPERTY_K [ COLON property_identifier ]
;
property_port_list ::=
property_port_item {COMMA property_port_item}
;
property_port_item ::=
{ attribute_instance } [ LOCAL_K [ property_lvar_port_direction ] ] property_formal_type
port_identifier {variable_dimension} [ EQ property_actual_arg ]
;
property_lvar_port_direction ::= INPUT_K
;
property_formal_type ::=
sequence_formal_type
| PROPERTY_K
;
property_spec ::=
[clocking_event ] [ DISABLE_K IFF_K LPAREN expression_or_dist RPAREN ] property_expr
;
property_statement_spec ::=
[ clocking_event ] [ DISABLE_K IFF_K LPAREN expression_or_dist RPAREN ] property_statement
;
property_statement ::=
property_expr SEMI
| CASE_K LPAREN expression_or_dist RPAREN property_case_item { property_case_item } ENDCASE_K
| IF_K LPAREN expression_or_dist RPAREN property_expr [ ELSE_K property_expr ]
;
property_case_item::=
expression_or_dist { COMMA expression_or_dist } COLON property_statement
| DEFAULT_K [ COLON ] property_statement
;
property_expr ::=
sequence_expr
| STRONG_K LPAREN sequence_expr RPAREN
| WEAK_K LPAREN sequence_expr RPAREN
| LPAREN property_expr RPAREN
| NOT_K property_expr
| property_expr OR_K property_expr
| property_expr AND_K property_expr
| sequence_expr BAR_MINUS_GT property_expr
| sequence_expr BAR_EQ_GT property_expr
| property_statement
| sequence_expr POUND_MINUS_POUND property_expr
| sequence_expr POUND_EQ_POUND property_expr
| NEXTTIME_K property_expr
| NEXTTIME_K [ constant_expression ] property_expr
| S_NEXTTIME_K property_expr
| S_NEXTTIME_K [ constant_expression ] property_expr
| ALWAYS_K property_expr
| ALWAYS_K [ cycle_delay_const_range_expression ] property_expr
| S_ALWAYS_K [ constant_range] property_expr
| S_EVENTUALLY_K property_expr
| EVENTUALLY_K [ constant_range ] property_expr
| S_EVENTUALLY_K [ cycle_delay_const_range_expression ] property_expr
| property_expr UNTIL_K property_expr
| property_expr S_UNTIL_K property_expr
| property_expr UNTIL_WITH_K property_expr
| property_expr S_UNTIL_WITH_K property_expr
| property_expr IMPLIES_K property_expr
| property_expr IFF_K property_expr
| ACCEPT_ON_K LPAREN expression_or_dist RPAREN property_expr
| REJECT_ON_K LPAREN expression_or_dist RPAREN property_expr
| SYNC_ACCEPT_ON_K LPAREN expression_or_dist RPAREN property_expr
| SYNC_REJECT_ON_K LPAREN expression_or_dist RPAREN property_expr
| property_instance
| clocking_event property_expr
;
sequence_declaration ::=
SEQUENCE_K sequence_identifier [ LPAREN [ sequence_port_list ] RPAREN ] SEMI
{ assertion_variable_declaration }
sequence_expr SEMI
ENDSEQUENCE_K [ COLON sequence_identifier ]
;
sequence_port_list ::=
sequence_port_item {COMMA sequence_port_item}
;
sequence_port_item ::=
{ attribute_instance } [ LOCAL_K [ sequence_lvar_port_direction ] ] sequence_formal_type
port_identifier {variable_dimension} [ EQ sequence_actual_arg ]
;
sequence_lvar_port_direction ::= INPUT_K | INOUT_K | OUTPUT_K
;
sequence_formal_type ::=
data_type_or_implicit
| SEQUENCE_K
| EVENT_K
| UNTYPED_K
;
sequence_expr ::=
cycle_delay_range sequence_expr { cycle_delay_range sequence_expr }
| sequence_expr cycle_delay_range sequence_expr { cycle_delay_range sequence_expr }
| expression_or_dist [ boolean_abbrev ]
| sequence_instance [ sequence_abbrev ]
| LPAREN sequence_expr {COMMA sequence_match_item } RPAREN [ sequence_abbrev ]
| sequence_expr AND_K sequence_expr
| sequence_expr INTERSECT_K sequence_expr
| sequence_expr OR_K sequence_expr
| FIRST_MATCH_K LPAREN sequence_expr {COMMA sequence_match_item} RPAREN
| expression_or_dist THROUGHOUT_K sequence_expr
| sequence_expr WITHIN_K sequence_expr
| clocking_event sequence_expr
;
cycle_delay_range ::=
POUND2 constant_primary
| POUND2 LBRACK cycle_delay_const_range_expression RBRACK
| POUND2 LBRACK STAR RBRACK
| POUND2 LBRACK PLUS RBRACK
;
sequence_method_call ::=
sequence_instance DOT method_identifier
;
sequence_match_item ::=
operator_assignment
| inc_or_dec_expression
| subroutine_call
;
sequence_instance ::=
ps_or_hierarchical_sequence_identifier [ LPAREN [ sequence_list_of_arguments ] RPAREN ]
;
sequence_list_of_arguments ::=
[sequence_actual_arg] { COMMA [sequence_actual_arg] } { COMMA DOT identifier LPAREN [sequence_actual_arg] RPAREN }
| DOT identifier LPAREN [sequence_actual_arg] RPAREN { COMMA DOT identifier LPAREN [sequence_actual_arg] RPAREN }
;
sequence_actual_arg ::=
event_expression
| sequence_expr
;
boolean_abbrev ::=
consecutive_repetition
| non_consecutive_repetition
| goto_repetition
;
sequence_abbrev ::= consecutive_repetition
;
consecutive_repetition ::=
LBRACK STAR const_or_range_expression RBRACK
| LBRACK STAR RBRACK
| LBRACK PLUS RBRACK
;
non_consecutive_repetition ::= LBRACK EQ const_or_range_expression RBRACK
;
goto_repetition ::= LBRACK MINUS_GT const_or_range_expression RBRACK
;
const_or_range_expression ::=
constant_expression
| cycle_delay_const_range_expression
;
cycle_delay_const_range_expression ::=
constant_expression COLON constant_expression
| constant_expression COLON DOLLAR
;
expression_or_dist ::= expression [ DIST_K LCURLY dist_list RCURLY ]
;
assertion_variable_declaration ::=
var_data_type list_of_variable_decl_assignments SEMI
;
let_declaration ::=
LET_K let_identifier [ LPAREN [ let_port_list ] RPAREN ] EQ expression SEMI
;
let_identifier ::=
identifier
;
let_port_list ::=
let_port_item {COMMA let_port_item}
;
let_port_item ::=
{ attribute_instance } let_formal_type port_identifier { variable_dimension } [ EQ expression ]
;
let_formal_type ::=
data_type_or_implicit
;
let_expression ::=
[ package_scope ] let_identifier [ LPAREN [ let_list_of_arguments ] RPAREN ]
;
let_list_of_arguments ::=
[ let_actual_arg ] {COMMA [ let_actual_arg ] } {COMMA DOT identifier LPAREN [ let_actual_arg ] RPAREN }
| DOT identifier LPAREN [ let_actual_arg ] RPAREN { COMMA DOT identifier LPAREN [ let_actual_arg ] RPAREN }
;
let_actual_arg ::=
expression
;
//A.2.11 Covergroup declarations
covergroup_declaration ::=
COVERGROUP_K covergroup_identifier [ LPAREN [ tf_port_list ] RPAREN ] [ coverage_event ] SEMI
{ coverage_spec_or_option }
ENDGROUP_K [ COLON covergroup_identifier ]
;
coverage_spec_or_option ::=
{attribute_instance} coverage_spec
| {attribute_instance} coverage_option SEMI
;
coverage_option ::=
//option
IDENT DOT member_identifier EQ expression
//option_type
| IDENT DOT member_identifier EQ constant_expression
;
coverage_spec ::=
cover_point
| cover_cross
;
coverage_event ::=
clocking_event	//IDENT ==> sample (but not a keyword)
| WITH_K FUNCTION_K IDENT LPAREN [ tf_port_list ] RPAREN
| AT2 LPAREN block_event_expression RPAREN
;
block_event_expression ::=
block_event_expression OR_K block_event_expression
| BEGIN_K hierarchical_btf_identifier
| END_K hierarchical_btf_identifier
;
hierarchical_btf_identifier ::=
hierarchical_tf_identifier
| hierarchical_block_identifier
| hierarchical_identifier [ class_scope ] method_identifier
;
cover_point ::= [ cover_point_identifier COLON ] COVERPOINT_K expression [ IFF_K LPAREN expression RPAREN ] bins_or_empty
;
bins_or_empty ::=
LCURLY {attribute_instance} { bins_or_options SEMI } RCURLY
| SEMI
;
bins_or_options ::=
coverage_option
| [ WILDCARD_K ] bins_keyword bin_identifier [ LBRACK [ expression ] RBRACK ] EQ LCURLY open_range_list RCURLY [ IFF_K LPAREN expression
RPAREN ]
| [ WILDCARD_K ] bins_keyword bin_identifier [ RBRACK RBRACK ] EQ trans_list [ IFF_K LPAREN expression RPAREN ]
| bins_keyword bin_identifier [ LBRACK [ expression ] RBRACK ] EQ DEFAULT_K [ IFF_K LPAREN expression RPAREN ]
| bins_keyword bin_identifier EQ DEFAULT_K SEQUENCE_K [ IFF_K LPAREN expression RPAREN ]
;
bins_keyword::= BINS_K | ILLEGAL_BINS_K | IGNORE_BINS_K
;
range_list ::= value_range { COMMA value_range }
;
trans_list ::= LPAREN trans_set RPAREN { COMMA LPAREN trans_set RPAREN }
;
trans_set ::= trans_range_list { EQ_GT trans_range_list }
;
trans_range_list ::=
trans_item
| trans_item LBRACK STAR repeat_range RBRACK
| trans_item LBRACK MINUS_GT repeat_range RBRACK
| trans_item LBRACK EQ repeat_range RBRACK
;
trans_item ::= range_list
;
repeat_range ::=
expression
| expression COLON expression
;
cover_cross ::=
[ cross_identifier COLON ] CROSS_K list_of_coverpoints [ IFF_K LPAREN expression RPAREN ] select_bins_or_empty
;
list_of_coverpoints ::= cross_item COMMA cross_item { COMMA cross_item }
;
cross_item ::=
cover_point_identifier
| variable_identifier
;
select_bins_or_empty ::=
LCURLY { bins_selection_or_option SEMI } RCURLY
| SEMI
;
bins_selection_or_option ::=
{ attribute_instance } coverage_option
| { attribute_instance } bins_selection
;
bins_selection ::= bins_keyword bin_identifier EQ select_expression [ IFF_K LPAREN expression RPAREN ]
;
select_expression ::=
select_condition
| NOT select_condition
| select_expression AND2 select_expression
| select_expression OR2 select_expression
| LPAREN select_expression RPAREN
;
select_condition ::= BINSOF_K LPAREN bins_expression RPAREN [ INTERSECT_K LCURLY open_range_list RCURLY ]
;
bins_expression ::=
variable_identifier
| cover_point_identifier [ DOT bin_identifier ]
;
open_range_list ::= open_value_range { COMMA open_value_range }
;
open_value_range ::= value_range
;
//A.3 Primitive instances
//A.3.1 Primitive instantiation and instances
gate_instantiation ::=
cmos_switchtype [delay3] cmos_switch_instance { COMMA cmos_switch_instance } SEMI
| enable_gatetype [drive_strength] [delay3] enable_gate_instance { COMMA enable_gate_instance } SEMI
| mos_switchtype [delay3] mos_switch_instance { COMMA mos_switch_instance } SEMI
| n_input_gatetype [drive_strength] [delay2] n_input_gate_instance { COMMA n_input_gate_instance } SEMI
| n_output_gatetype [drive_strength] [delay2] n_output_gate_instance
{ COMMA n_output_gate_instance } SEMI
| pass_en_switchtype [delay2] pass_enable_switch_instance { COMMA pass_enable_switch_instance } SEMI
| pass_switchtype pass_switch_instance { COMMA pass_switch_instance } SEMI
| PULLDOWN_K [pulldown_strength] pull_gate_instance { COMMA pull_gate_instance } SEMI
| PULLUP_K [pullup_strength] pull_gate_instance { COMMA pull_gate_instance } SEMI
;
cmos_switch_instance ::= [ name_of_instance ] LPAREN output_terminal COMMA input_terminal COMMA
ncontrol_terminal COMMA pcontrol_terminal RPAREN
;
enable_gate_instance ::= [ name_of_instance ] LPAREN output_terminal COMMA input_terminal COMMA enable_terminal RPAREN
;
mos_switch_instance ::= [ name_of_instance ] LPAREN output_terminal COMMA input_terminal COMMA enable_terminal RPAREN
;
n_input_gate_instance ::= [ name_of_instance ] LPAREN output_terminal COMMA input_terminal { COMMA input_terminal } RPAREN
;
n_output_gate_instance ::= [ name_of_instance ] LPAREN output_terminal { COMMA output_terminal } COMMA
input_terminal RPAREN
;
pass_switch_instance ::= [ name_of_instance ] LPAREN inout_terminal COMMA inout_terminal RPAREN
;
pass_enable_switch_instance ::= [ name_of_instance ] LPAREN inout_terminal COMMA inout_terminal COMMA
enable_terminal RPAREN
;
pull_gate_instance ::= [ name_of_instance ] LPAREN output_terminal RPAREN
;
//A.3.2 Primitive strengths
pulldown_strength ::=
LPAREN strength0 COMMA strength1 RPAREN
| LPAREN strength1 COMMA strength0 RPAREN
| LPAREN strength0 RPAREN
;
pullup_strength ::=
LPAREN strength0 COMMA strength1 RPAREN
| LPAREN strength1 COMMA strength0 RPAREN
| LPAREN strength1 RPAREN
;
//A.3.3 Primitive terminals
enable_terminal ::= expression
;
inout_terminal ::= net_lvalue
;
input_terminal ::= expression
;
ncontrol_terminal ::= expression
;
output_terminal ::= net_lvalue
;
pcontrol_terminal ::= expression
;
//A.3.4 Primitive gate and switch types
cmos_switchtype ::= CMOS_K | RCMOS_K
;
enable_gatetype ::= BUFIF0_K | BUFIF1_K | NOTIF0_K | NOTIF1_K
;
mos_switchtype ::= NMOS_K | PMOS_K | RNMOS_K | RPMOS_K
;
n_input_gatetype ::= AND_K | NAND_K | OR_K | NOR_K | XOR_K | XNOR_K
;
n_output_gatetype ::= BUF_K | NOT_K
;
pass_en_switchtype ::= TRANIF0_K | TRANIF1_K | RTRANIF1_K | RTRANIF0_K
;
pass_switchtype ::= TRAN_K | RTRAN_K
;
//A.4 Instantiations
//A.4.1 Instantiation
//A.4.1.1 Module instantiation
module_instantiation ::=
module_identifier [ parameter_value_assignment ] hierarchical_instance { COMMA hierarchical_instance } SEMI
;
parameter_value_assignment ::= POUND LPAREN [ list_of_parameter_assignments ] RPAREN
;
list_of_parameter_assignments ::=
ordered_parameter_assignment { COMMA ordered_parameter_assignment }
| named_parameter_assignment { COMMA named_parameter_assignment }
;
ordered_parameter_assignment ::= param_expression
;
named_parameter_assignment ::= DOT parameter_identifier LPAREN [ param_expression ] RPAREN
;
hierarchical_instance ::= name_of_instance LPAREN [ list_of_port_connections ] RPAREN
;
name_of_instance ::= instance_identifier { unpacked_dimension }
;
list_of_port_connections ::=
ordered_port_connection { COMMA ordered_port_connection }
| named_port_connection { COMMA named_port_connection }
;
ordered_port_connection ::= { attribute_instance } [ expression ]
;
named_port_connection ::=
{ attribute_instance } DOT port_identifier [ LPAREN [ expression ] RPAREN ]
| { attribute_instance } DOT_STAR
;
//A.4.1.2 Interface instantiation
interface_instantiation ::=
interface_identifier [ parameter_value_assignment ] hierarchical_instance { COMMA hierarchical_instance } SEMI
;
//A.4.1.3 Program instantiation
program_instantiation ::=
program_identifier [ parameter_value_assignment ] hierarchical_instance { COMMA hierarchical_instance } SEMI
;
//A.4.1.4 Checker instantiation
checker_instantiation ::=
checker_identifier name_of_instance LPAREN [list_of_checker_port_connections] RPAREN SEMI
;
list_of_checker_port_connections ::=
ordered_checker_port_connection { COMMA ordered_checker_port_connection }
| named_checker_port_connection { COMMA named_checker_port_connection }
;
ordered_checker_port_connection ::= { attribute_instance } [ property_actual_arg ]
;
named_checker_port_connection ::=
{ attribute_instance } DOT port_identifier [ LPAREN [ property_actual_arg ] RPAREN ]
| { attribute_instance } DOT_STAR
;
//A.4.2 Generated instantiation
generate_region ::=
GENERATE_K { generate_item } ENDGENERATE_K
;
loop_generate_construct ::=
FOR_K LPAREN genvar_initialization SEMI genvar_expression SEMI genvar_iteration RPAREN
generate_block
;
genvar_initialization ::=
[ GENVAR_K ] genvar_identifier EQ constant_expression
;
genvar_iteration ::=
genvar_identifier assignment_operator genvar_expression
| inc_or_dec_operator genvar_identifier
| genvar_identifier inc_or_dec_operator
;
conditional_generate_construct ::=
if_generate_construct
| case_generate_construct
;
if_generate_construct ::=
IF_K LPAREN constant_expression RPAREN generate_block [ ELSE_K generate_block ]
;
case_generate_construct ::=
CASE_K LPAREN constant_expression RPAREN case_generate_item { case_generate_item } ENDCASE_K
;
case_generate_item ::=
constant_expression { COMMA constant_expression } COLON generate_block
| DEFAULT_K [ COLON ] generate_block
;
generate_block ::=
generate_item
| [ generate_block_identifier COLON ] BEGIN_K [ COLON generate_block_identifier ]
{ generate_item }
END_K [ COLON generate_block_identifier ]
;
generate_item ::=
module_or_generate_item
| interface_or_generate_item
| checker_or_generate_item
;
//A.5 UDP declaration and instantiation
//A.5.1 UDP declaration
udp_nonansi_declaration ::=
{ attribute_instance } PRIMITIVE_K udp_identifier LPAREN udp_port_list RPAREN SEMI
;
udp_ansi_declaration ::=
{ attribute_instance } PRIMITIVE_K udp_identifier LPAREN udp_declaration_port_list RPAREN SEMI
;
udp_declaration ::=
udp_nonansi_declaration udp_port_declaration { udp_port_declaration }
udp_body
ENDPRIMITIVE_K [ COLON udp_identifier ]
| udp_ansi_declaration
udp_body
ENDPRIMITIVE_K [ COLON udp_identifier ]
| EXTERN_K udp_nonansi_declaration
| EXTERN_K udp_ansi_declaration
| { attribute_instance } PRIMITIVE_K udp_identifier LPAREN DOT_STAR RPAREN SEMI
{ udp_port_declaration }
udp_body
ENDPRIMITIVE_K [ COLON udp_identifier ]
;
//A.5.2 UDP ports
udp_port_list ::= output_port_identifier COMMA input_port_identifier { COMMA input_port_identifier }
;
udp_declaration_port_list ::= udp_output_declaration COMMA udp_input_declaration { COMMA udp_input_declaration }
;
udp_port_declaration ::=
udp_output_declaration SEMI
| udp_input_declaration SEMI
| udp_reg_declaration SEMI
;
udp_output_declaration ::=
{ attribute_instance } OUTPUT_K port_identifier
| { attribute_instance } OUTPUT_K REG_K port_identifier [ EQ constant_expression ]
;
udp_input_declaration ::= { attribute_instance } INPUT_K list_of_udp_port_identifiers
;
udp_reg_declaration ::= { attribute_instance } REG_K variable_identifier
;
//A.5.3 UDP body
udp_body ::= combinational_body | sequential_body
;
combinational_body ::= TABLE_K combinational_entry { combinational_entry } ENDTABLE_K
;
combinational_entry ::= level_input_list COLON output_symbol SEMI
;
sequential_body ::= [ udp_initial_statement ] TABLE_K sequential_entry { sequential_entry } ENDTABLE_K
;
udp_initial_statement ::= INITIAL_K output_port_identifier EQ init_val SEMI
;
init_val ::= NUMBER //1’b0 | 1’b1 | 1’bx | 1’bX | 1’B0 | 1’B1 | 1’Bx | 1’BX | 1 | 0
;
sequential_entry ::= seq_input_list COLON current_state COLON next_state SEMI
;
seq_input_list ::= level_input_list | edge_input_list
;
level_input_list ::= level_symbol { level_symbol }
;
edge_input_list ::= { level_symbol } edge_indicator { level_symbol }
;
edge_indicator ::= LPAREN level_symbol level_symbol RPAREN | edge_symbol
;
current_state ::= level_symbol
;
next_state ::= output_symbol | MINUS
;
output_symbol ::= NUMBER | IDENT //DIGIT | ALPHA //0 | 1 | x | X
;
level_symbol ::= QMARK | NUMBER | IDENT //0 | 1 | x | X | ? | b | B
;
edge_symbol ::= STAR | IDENT //r | R | f | F | p | P | n | N | *
;
//A.5.4 UDP instantiation
udp_instantiation ::= udp_identifier [ drive_strength ] [ delay2 ] udp_instance { COMMA udp_instance } SEMI
;
udp_instance ::= [ name_of_instance ] LPAREN output_terminal COMMA input_terminal { COMMA input_terminal } RPAREN
;
//A.6 Behavioral statements
//A.6.1 Continuous assignment and net alias statements
continuous_assign ::=
ASSIGN_K [ drive_strength ] [ delay3 ] list_of_net_assignments SEMI
| ASSIGN_K [ delay_control ] list_of_variable_assignments SEMI
;
list_of_net_assignments ::= net_assignment { COMMA net_assignment }
;
list_of_variable_assignments ::= variable_assignment { COMMA variable_assignment }
;
net_alias ::= ALIAS_K net_lvalue EQ net_lvalue { EQ net_lvalue } SEMI
;
net_assignment ::= net_lvalue EQ expression
;
//A.6.2 Procedural blocks and assignments
initial_construct ::= INITIAL_K statement_or_null
;
always_construct ::= always_keyword statement
;
always_keyword ::= ALWAYS_K | ALWAYS_COMB_K | ALWAYS_LATCH_K | ALWAYS_FF_K
;
final_construct ::= FINAL_K function_statement
;
blocking_assignment ::=
variable_lvalue EQ delay_or_event_control expression
| nonrange_variable_lvalue EQ dynamic_array_new
| [ implicit_class_handle DOT | class_scope | package_scope ] hierarchical_variable_identifier
select EQ class_new
| operator_assignment
;
operator_assignment ::= variable_lvalue assignment_operator expression
;
assignment_operator ::=
EQ | PLUS_EQ | MINUS_EQ | STAR_EQ | DIV_EQ | MOD_EQ | AND_EQ | OR_EQ | XOR_EQ | LT2_EQ | GT2_EQ | LT3_EQ | GT3_EQ
;
nonblocking_assignment ::=
variable_lvalue LT_EQ [ delay_or_event_control ] expression
;
procedural_continuous_assignment ::=
ASSIGN_K variable_assignment
| DEASSIGN_K variable_lvalue
| FORCE_K variable_assignment
| FORCE_K net_assignment
| RELEASE_K variable_lvalue
| RELEASE_K net_lvalue
;
variable_assignment ::= variable_lvalue EQ expression
;
//A.6.3 Parallel and sequential blocks
action_block ::=
statement_or_null
| [ statement ] ELSE_K statement_or_null
;
seq_block ::=
BEGIN_K [ COLON block_identifier ] { block_item_declaration } { statement_or_null }
END_K [ COLON block_identifier ]
;
par_block ::=
FORK_K [ COLON block_identifier ] { block_item_declaration } { statement_or_null }
join_keyword [ COLON block_identifier ]
;
join_keyword ::= JOIN_K | JOIN_ANY_K | JOIN_NONE_K
;
//A.6.4 Statements
statement_or_null ::=
statement
| { attribute_instance } SEMI
;
statement ::= [ block_identifier COLON ] { attribute_instance } statement_item
;
statement_item ::=
blocking_assignment SEMI
| nonblocking_assignment SEMI
| procedural_continuous_assignment SEMI
| case_statement
| conditional_statement
| inc_or_dec_expression SEMI
| subroutine_call_statement
| disable_statement
| event_trigger
| loop_statement
| jump_statement
| par_block
| procedural_timing_control_statement
| seq_block
| wait_statement
| procedural_assertion_statement
| clocking_drive SEMI
| randsequence_statement
| randcase_statement
| expect_property_statement
;
function_statement ::= statement
;
function_statement_or_null ::=
function_statement
| { attribute_instance } SEMI
;
variable_identifier_list ::= variable_identifier { COMMA variable_identifier }
;
//A.6.5 Timing control statements
procedural_timing_control_statement ::=
procedural_timing_control statement_or_null
;
delay_or_event_control ::=
delay_control
| event_control
| REPEAT_K LPAREN expression RPAREN event_control
;
delay_control ::=
POUND delay_value
| POUND LPAREN mintypmax_expression RPAREN
;
event_control ::=
AT hierarchical_event_identifier
| AT LPAREN event_expression RPAREN
| AT_STAR
| AT LPAREN STAR RPAREN
| AT ps_or_hierarchical_sequence_identifier
;
event_expression ::=
[ edge_identifier ] expression [ IFF_K expression ]
| sequence_instance [ IFF_K expression ]
| event_expression OR_K event_expression
| event_expression COMMA event_expression
| LPAREN event_expression RPAREN
;
procedural_timing_control ::=
delay_control
| event_control
| cycle_delay
;
jump_statement ::=
RETURN_K [ expression ] SEMI
| BREAK_K SEMI
| CONTINUE_K SEMI
;
wait_statement ::=
WAIT_K LPAREN expression RPAREN statement_or_null
| WAIT_K FORK_K SEMI
| WAIT_ORDER_K LPAREN hierarchical_identifier { COMMA hierarchical_identifier } RPAREN action_block
;
event_trigger ::=
MINUS_GT  hierarchical_event_identifier SEMI
| MINUS_GT2 [ delay_or_event_control ] hierarchical_event_identifier SEMI
;
disable_statement ::=
DISABLE_K hierarchical_task_identifier SEMI
| DISABLE_K hierarchical_block_identifier SEMI
| DISABLE_K FORK_K SEMI
;
//A.6.6 Conditional statements
conditional_statement ::=
[ unique_priority ] IF_K LPAREN cond_predicate RPAREN statement_or_null
{ ELSE_K IF_K LPAREN cond_predicate RPAREN statement_or_null }
[ ELSE_K statement_or_null ]
;
unique_priority ::= UNIQUE_K | UNIQUE0_K| PRIORITY_K
;
cond_predicate ::=
expression_or_cond_pattern { AND3 expression_or_cond_pattern }
;
expression_or_cond_pattern ::=
expression | cond_pattern
;
cond_pattern ::= expression MATCHES_K pattern
;
//A.6.7 Case statements
case_statement ::=
[ unique_priority ] case_keyword LPAREN case_expression RPAREN
case_item { case_item } ENDCASE_K
| [ unique_priority ] case_keyword LPAREN case_expression RPAREN MATCHES_K
case_pattern_item { case_pattern_item } ENDCASE_K
| [ unique_priority ] CASE_K LPAREN case_expression RPAREN INSIDE_K
case_inside_item { case_inside_item } ENDCASE_K
;
case_keyword ::= CASE_K | CASEZ_K | CASEX_K
;
case_expression ::= expression
;
case_item ::=
case_item_expression { COMMA case_item_expression } COLON statement_or_null
| DEFAULT_K [ COLON ] statement_or_null
;
case_pattern_item ::=
pattern [ AND3 expression ] COLON statement_or_null
| DEFAULT_K [ COLON ] statement_or_null
;
case_inside_item ::=
open_range_list COLON statement_or_null
| DEFAULT_K [ COLON ] statement_or_null
;
case_item_expression ::= expression
;
randcase_statement ::=
RANDCASE_K randcase_item { randcase_item } ENDCASE_K
;
randcase_item ::= expression COLON statement_or_null
;
//A.6.7.1 Patterns
pattern ::=
DOT variable_identifier
| DOT_STAR
| constant_expression
| TAGGED_K member_identifier [ pattern ]
| SQUOTE LCURLY pattern { COMMA pattern } RCURLY
| SQUOTE LCURLY member_identifier COLON pattern { COMMA member_identifier COLON pattern } RCURLY
;
assignment_pattern ::=
SQUOTE LCURLY expression { COMMA expression } RCURLY
| SQUOTE LCURLY structure_pattern_key COLON expression { COMMA structure_pattern_key COLON expression } RCURLY
| SQUOTE LCURLY array_pattern_key COLON expression { COMMA array_pattern_key COLON expression } RCURLY
| SQUOTE LCURLY constant_expression LCURLY expression { COMMA expression } RCURLY RCURLY
;
structure_pattern_key ::= member_identifier | assignment_pattern_key
;
array_pattern_key ::= constant_expression | assignment_pattern_key
;
assignment_pattern_key ::= simple_type | DEFAULT_K
;
assignment_pattern_expression ::=
[ assignment_pattern_expression_type ] assignment_pattern
;
assignment_pattern_expression_type ::=
ps_type_identifier
| ps_parameter_identifier
| integer_atom_type
| type_reference
;
constant_assignment_pattern_expression ::= assignment_pattern_expression
;
assignment_pattern_net_lvalue ::=
SQUOTE LCURLY net_lvalue {COMMA net_lvalue } RCURLY
;
assignment_pattern_variable_lvalue ::=
SQUOTE LCURLY variable_lvalue {COMMA variable_lvalue } RCURLY
;
//A.6.8 Looping statements
loop_statement ::=
FOREVER_K statement_or_null
| REPEAT_K LPAREN expression RPAREN statement_or_null
| WHILE_K LPAREN expression RPAREN statement_or_null
| FOR_K LPAREN for_initialization SEMI expression SEMI for_step RPAREN
statement_or_null
| DO_K statement_or_null WHILE_K LPAREN expression RPAREN SEMI
| FOREACH_K LPAREN ps_or_hierarchical_array_identifier LBRACK loop_variables RBRACK RPAREN statement
;
for_initialization ::=
list_of_variable_assignments
| for_variable_declaration { COMMA for_variable_declaration }
;
for_variable_declaration ::=
data_type variable_identifier EQ expression { COMMA variable_identifier EQ expression }
;
for_step ::= for_step_assignment { COMMA for_step_assignment }
;
for_step_assignment ::=
operator_assignment
| inc_or_dec_expression
| function_subroutine_call
;
loop_variables ::= [ index_variable_identifier ] { COMMA [ index_variable_identifier ] }
;
//A.6.9 Subroutine call statements
subroutine_call_statement ::=
subroutine_call SEMI
| VOID_K SQUOTE LPAREN function_subroutine_call RPAREN SEMI
;
//A.6.10 Assertion statements
assertion_item ::=
concurrent_assertion_item
| deferred_immediate_assertion_item
;
deferred_immediate_assertion_item ::= [ block_identifier COLON ] deferred_immediate_assertion_statement
;
procedural_assertion_statement ::=
concurrent_assertion_statement
| immediate_assertion_statement
| checker_instantiation
;
immediate_assertion_statement ::=
simple_immediate_assertion_statement
| deferred_immediate_assertion_statement
;
simple_immediate_assertion_statement ::=
simple_immediate_assert_statement
| simple_immediate_assume_statement
| simple_immediate_cover_statement
;
simple_immediate_assert_statement ::=
ASSERT_K LPAREN expression RPAREN action_block
;
simple_immediate_assume_statement ::=
ASSUME_K LPAREN expression RPAREN action_block
;
simple_immediate_cover_statement ::=
COVER_K LPAREN expression RPAREN statement_or_null
;
deferred_immediate_assertion_statement ::=
deferred_immediate_assert_statement
| deferred_immediate_assume_statement
| deferred_immediate_cover_statement
;
deferred_immediate_assert_statement ::=
ASSERT_K POUND DIGIT  LPAREN expression RPAREN action_block
;
deferred_immediate_assume_statement ::=
ASSUME_K POUND DIGIT  LPAREN expression RPAREN action_block
;
deferred_immediate_cover_statement ::=
COVER_K POUND DIGIT  LPAREN expression RPAREN statement_or_null
;
//A.6.11 Clocking block
clocking_declaration ::= [ DEFAULT_K ] CLOCKING_K [ clocking_identifier ] clocking_event SEMI
{ clocking_item }
ENDCLOCKING_K [ COLON clocking_identifier ]
| GLOBAL_K CLOCKING_K [ clocking_identifier ] clocking_event SEMI ENDCLOCKING_K [ COLON clocking_identifier ]
;
clocking_event ::=
AT identifier
| AT LPAREN event_expression RPAREN
;
clocking_item ::=
DEFAULT_K default_skew SEMI
| clocking_direction list_of_clocking_decl_assign SEMI
| { attribute_instance } assertion_item_declaration
;
default_skew ::=
INPUT_K clocking_skew
| OUTPUT_K clocking_skew
| INPUT_K clocking_skew OUTPUT_K clocking_skew
;
clocking_direction ::=
INPUT_K [ clocking_skew ]
| OUTPUT_K [ clocking_skew ]
| INPUT_K [ clocking_skew ] OUTPUT_K [ clocking_skew ]
| INOUT_K
;
list_of_clocking_decl_assign ::= clocking_decl_assign { COMMA clocking_decl_assign }
;
clocking_decl_assign ::= signal_identifier [ EQ expression ]
;
clocking_skew ::=
edge_identifier [ delay_control ]
| delay_control
;
clocking_drive ::=
clockvar_expression LT_EQ [ cycle_delay ] expression
;
cycle_delay ::=
POUND2 integral_number
| POUND2 identifier
| POUND2 LPAREN expression RPAREN
;
clockvar ::= hierarchical_identifier
;
clockvar_expression ::= clockvar select
;
//A.6.12 Randsequence
randsequence_statement ::= RANDSEQUENCE_K LPAREN [ production_identifier ] RPAREN
production { production }
ENDSEQUENCE_K
;
production ::= [ data_type_or_void ] production_identifier [ LPAREN tf_port_list RPAREN ] COLON rs_rule { OR rs_rule } SEMI
;
rs_rule ::= rs_production_list [ COLON_EQ weight_specification [ rs_code_block ] ]
;
rs_production_list ::=
rs_prod { rs_prod }
| RAND_K JOIN_K [ LPAREN expression RPAREN ] production_item production_item { production_item }
;
weight_specification ::=
integral_number
| ps_identifier
| LPAREN expression RPAREN
;
rs_code_block ::= LCURLY { data_declaration } { statement_or_null } RCURLY
;
rs_prod ::=
production_item
| rs_code_block
| rs_if_else
| rs_repeat
| rs_case
;
production_item ::= production_identifier [ LPAREN list_of_arguments RPAREN ]
;
rs_if_else ::= IF_K LPAREN expression RPAREN production_item [ ELSE_K production_item ]
;
rs_repeat ::= REPEAT_K LPAREN expression RPAREN production_item
;
rs_case ::= CASE_K LPAREN case_expression RPAREN rs_case_item { rs_case_item } ENDCASE_K
;
rs_case_item ::=
case_item_expression { COMMA case_item_expression } COLON production_item SEMI
| DEFAULT_K [ COLON ] production_item SEMI
;
//A.7 Specify section
//A.7.1 Specify block declaration
specify_block ::= SPECIFY_K { specify_item } ENDSPECIFY_K
;
specify_item ::=
specparam_declaration
| pulsestyle_declaration
| showcancelled_declaration
| path_declaration
| system_timing_check
;
pulsestyle_declaration ::=
PULSESTYLE_ONEVENT_K list_of_path_outputs SEMI
| PULSESTYLE_ONDETECT_K list_of_path_outputs SEMI
;
showcancelled_declaration ::=
SHOWCANCELLED_K list_of_path_outputs SEMI
| NOSHOWCANCELLED_K list_of_path_outputs SEMI
;
//A.7.2 Specify path declarations
path_declaration ::=
simple_path_declaration SEMI
| edge_sensitive_path_declaration SEMI
| state_dependent_path_declaration SEMI
;
simple_path_declaration ::=
parallel_path_description EQ path_delay_value
| full_path_description EQ path_delay_value
;
parallel_path_description ::=
LPAREN specify_input_terminal_descriptor [ polarity_operator ] EQ_GT specify_output_terminal_descriptor RPAREN
;
full_path_description ::=
LPAREN list_of_path_inputs [ polarity_operator ] STAR_GT list_of_path_outputs RPAREN
;
list_of_path_inputs ::=
specify_input_terminal_descriptor { COMMA specify_input_terminal_descriptor }
;
list_of_path_outputs ::=
specify_output_terminal_descriptor { COMMA specify_output_terminal_descriptor }
;
//A.7.3 Specify block terminals
specify_input_terminal_descriptor ::=
input_identifier [ LBRACK constant_range_expression RBRACK ]
;
specify_output_terminal_descriptor ::=
output_identifier [ LBRACK constant_range_expression RBRACK ]
;
input_identifier ::= input_port_identifier | inout_port_identifier | interface_identifier DOT port_identifier
;
output_identifier ::= output_port_identifier | inout_port_identifier | interface_identifier DOT port_identifier
;
//A.7.4 Specify path delays
path_delay_value ::=
list_of_path_delay_expressions
| LPAREN list_of_path_delay_expressions RPAREN
;
list_of_path_delay_expressions ::=
t_path_delay_expression
| trise_path_delay_expression COMMA tfall_path_delay_expression
| trise_path_delay_expression COMMA tfall_path_delay_expression COMMA tz_path_delay_expression
| t01_path_delay_expression COMMA t10_path_delay_expression COMMA t0z_path_delay_expression COMMA
tz1_path_delay_expression COMMA t1z_path_delay_expression COMMA tz0_path_delay_expression
| t01_path_delay_expression COMMA t10_path_delay_expression COMMA t0z_path_delay_expression COMMA
tz1_path_delay_expression COMMA t1z_path_delay_expression COMMA tz0_path_delay_expression COMMA
t0x_path_delay_expression COMMA tx1_path_delay_expression COMMA t1x_path_delay_expression COMMA
tx0_path_delay_expression COMMA txz_path_delay_expression COMMA tzx_path_delay_expression
;
t_path_delay_expression ::= path_delay_expression
;
trise_path_delay_expression ::= path_delay_expression
;
tfall_path_delay_expression ::= path_delay_expression
;
tz_path_delay_expression ::= path_delay_expression
;
t01_path_delay_expression ::= path_delay_expression
;
t10_path_delay_expression ::= path_delay_expression
;
t0z_path_delay_expression ::= path_delay_expression
;
tz1_path_delay_expression ::= path_delay_expression
;
t1z_path_delay_expression ::= path_delay_expression
;
tz0_path_delay_expression ::= path_delay_expression
;
t0x_path_delay_expression ::= path_delay_expression
;
tx1_path_delay_expression ::= path_delay_expression
;
t1x_path_delay_expression ::= path_delay_expression
;
tx0_path_delay_expression ::= path_delay_expression
;
txz_path_delay_expression ::= path_delay_expression
;
tzx_path_delay_expression ::= path_delay_expression
;
path_delay_expression ::= constant_mintypmax_expression
;
edge_sensitive_path_declaration ::=
parallel_edge_sensitive_path_description EQ path_delay_value
| full_edge_sensitive_path_description EQ path_delay_value
;
parallel_edge_sensitive_path_description ::=
LPAREN [ edge_identifier ] specify_input_terminal_descriptor [ polarity_operator ] EQ_GT
LPAREN specify_output_terminal_descriptor [ polarity_operator ] COLON data_source_expression RPAREN RPAREN
;
full_edge_sensitive_path_description ::=
LPAREN [ edge_identifier ] list_of_path_inputs [ polarity_operator ] STAR_GT
LPAREN list_of_path_outputs [ polarity_operator ] COLON data_source_expression RPAREN RPAREN
;
data_source_expression ::= expression
;
edge_identifier ::= POSEDGE_K | NEGEDGE_K | EDGE_K
;
state_dependent_path_declaration ::=
IF_K LPAREN module_path_expression RPAREN simple_path_declaration
| IF_K LPAREN module_path_expression RPAREN edge_sensitive_path_declaration
| IFNONE_K simple_path_declaration
;
polarity_operator ::= PLUS | MINUS
;
//A.7.5 System timing checks
//A.7.5.1 System timing check commands
//NOTE: rules starting w/ $ were changed to ds_  (dollar sign)
system_timing_check ::=
ds_setup_timing_check
| ds_hold_timing_check
| ds_setuphold_timing_check
| ds_recovery_timing_check
| ds_removal_timing_check
| ds_recrem_timing_check
| ds_skew_timing_check
| ds_timeskew_timing_check
| ds_fullskew_timing_check
| ds_period_timing_check
| ds_width_timing_check
| ds_nochange_timing_check
;
ds_setup_timing_check ::=
DS_SETUP_K LPAREN data_event COMMA reference_event COMMA timing_check_limit [ COMMA [ notifier ] ] RPAREN SEMI
;
ds_hold_timing_check ::=
DS_HOLD_K LPAREN reference_event COMMA data_event COMMA timing_check_limit [ COMMA [ notifier ] ] RPAREN SEMI
;
ds_setuphold_timing_check ::=
DS_SETUPHOLD_K LPAREN reference_event COMMA data_event COMMA timing_check_limit COMMA timing_check_limit
[ COMMA [ notifier ] [ COMMA [ timestamp_condition ] [ COMMA [ timecheck_condition ]
[ COMMA [ delayed_reference ] [ COMMA [ delayed_data ] ] ] ] ] ] RPAREN SEMI
;
ds_recovery_timing_check ::=
DS_RECOVERY_K LPAREN reference_event COMMA data_event COMMA timing_check_limit [ COMMA [ notifier ] ] RPAREN SEMI
;
ds_removal_timing_check ::=
DS_REMOVAL_K LPAREN reference_event COMMA data_event COMMA timing_check_limit [ COMMA [ notifier ] ] RPAREN SEMI
;
ds_recrem_timing_check ::=
DS_RECREM_K LPAREN reference_event COMMA data_event COMMA timing_check_limit COMMA timing_check_limit
[ COMMA [ notifier ] [ COMMA [ timestamp_condition ] [ COMMA [ timecheck_condition ]
[ COMMA [ delayed_reference ] [ COMMA [ delayed_data ] ] ] ] ] ] RPAREN SEMI
;
ds_skew_timing_check ::=
DS_SKEW_K LPAREN reference_event COMMA data_event COMMA timing_check_limit [ COMMA [ notifier ] ] RPAREN SEMI
;
ds_timeskew_timing_check ::=
DS_TIMESKEW_K LPAREN reference_event COMMA data_event COMMA timing_check_limit
[ COMMA [ notifier ] [ COMMA [ event_based_flag ] [ COMMA [ remain_active_flag ] ] ] ] RPAREN SEMI
;
ds_fullskew_timing_check ::=
DS_FULLSKEW_K LPAREN reference_event COMMA data_event COMMA timing_check_limit COMMA timing_check_limit
[ COMMA [ notifier ] [ COMMA [ event_based_flag ] [ COMMA [ remain_active_flag ] ] ] ] RPAREN SEMI
;
ds_period_timing_check ::=
DS_PERIOD_K LPAREN controlled_reference_event COMMA timing_check_limit [ COMMA [ notifier ] ] RPAREN SEMI
;
ds_width_timing_check ::=
DS_WIDTH_K LPAREN controlled_reference_event COMMA timing_check_limit COMMA threshold [ COMMA [ notifier ] ] RPAREN SEMI
;
ds_nochange_timing_check ::=
DS_NOCHANGE_K LPAREN reference_event COMMA data_event COMMA start_edge_offset COMMA end_edge_offset [ COMMA [ notifier ] ] RPAREN SEMI
;
//A.7.5.2 System timing check command arguments
timecheck_condition ::= mintypmax_expression
;
controlled_reference_event ::= controlled_timing_check_event
;
data_event ::= timing_check_event
;
delayed_data ::=
terminal_identifier
| terminal_identifier LBRACK constant_mintypmax_expression RBRACK
;
delayed_reference ::=
terminal_identifier
| terminal_identifier LBRACK constant_mintypmax_expression RBRACK
;
end_edge_offset ::= mintypmax_expression
;
event_based_flag ::= constant_expression
;
notifier ::= variable_identifier
;
reference_event ::= timing_check_event
;
remain_active_flag ::= constant_mintypmax_expression
;
timestamp_condition ::= mintypmax_expression
;
start_edge_offset ::= mintypmax_expression
;
threshold ::= constant_expression
;
timing_check_limit ::= expression
;
//A.7.5.3 System timing check event definitions
timing_check_event ::=
[timing_check_event_control] specify_terminal_descriptor [ AND3 timing_check_condition ]
;
controlled_timing_check_event ::=
timing_check_event_control specify_terminal_descriptor [ AND3 timing_check_condition ]
;
timing_check_event_control ::=
POSEDGE_K
| NEGEDGE_K
| EDGE_K
| edge_control_specifier
;
specify_terminal_descriptor ::=
specify_input_terminal_descriptor
| specify_output_terminal_descriptor
;
edge_control_specifier ::= EDGE_K LBRACK edge_descriptor { COMMA edge_descriptor } RBRACK
;
edge_descriptor ::= NUMBER | z_or_x zero_or_one | zero_or_one z_or_x //01 | 10 | z_or_x zero_or_one | zero_or_one z_or_x
;
zero_or_one ::= DIGIT //0 | 1
;
z_or_x ::= IDENT //x | X | z | Z
;
timing_check_condition ::=
scalar_timing_check_condition
| LPAREN scalar_timing_check_condition RPAREN
;
scalar_timing_check_condition ::=
expression
| TILDE expression
| expression EQ2 scalar_constant
| expression EQ3 scalar_constant
| expression NOT_EQ scalar_constant
| expression NOT_EQ2 scalar_constant
;
scalar_constant ::= NUMBER //1’b0 | 1’b1 | 1’B0 | 1’B1 | ’b0 | ’b1 | ’B0 | ’B1 | 1 | 0
;
//A.8 Expressions
//A.8.1 Concatenations
concatenation ::=
LCURLY expression { COMMA expression } RCURLY
;
constant_concatenation ::=
LCURLY constant_expression { COMMA constant_expression } RCURLY
;
constant_multiple_concatenation ::= LCURLY constant_expression constant_concatenation RCURLY
;
module_path_concatenation ::= LCURLY module_path_expression { COMMA module_path_expression } RCURLY
;
module_path_multiple_concatenation ::= LCURLY constant_expression module_path_concatenation RCURLY
;
multiple_concatenation ::= LCURLY expression concatenation RCURLY
;
streaming_concatenation ::= LCURLY stream_operator [ slice_size ] stream_concatenation RCURLY
;
stream_operator ::= GT2 | LT2
;
slice_size ::= simple_type | constant_expression
;
stream_concatenation ::= LCURLY stream_expression { COMMA stream_expression } RCURLY
;
stream_expression ::= expression [ WITH_K LBRACK array_range_expression RBRACK ]
;
array_range_expression ::=
expression
| expression COLON expression
| expression PLUS_COLON expression
| expression MINUS_COLON expression
;
empty_queue ::= LCURLY RCURLY
;
//A.8.2 Subroutine calls
constant_function_call ::= function_subroutine_call
;
tf_call ::= ps_or_hierarchical_tf_identifier { attribute_instance } [ LPAREN list_of_arguments RPAREN ]
;
system_tf_call ::=
system_tf_identifier [ LPAREN list_of_arguments RPAREN ]
| system_tf_identifier LPAREN data_type [ COMMA expression ] RPAREN
;
subroutine_call ::=
tf_call
| system_tf_call
| method_call
//NOTE: "std::"
| [std_colon2] randomize_call
;
std_colon2: IDENT COLON2;

function_subroutine_call ::= subroutine_call
;
list_of_arguments ::=
[ expression ] { COMMA [ expression ] } { COMMA DOT identifier LPAREN [ expression ] RPAREN }
| DOT identifier LPAREN [ expression ] RPAREN { COMMA DOT identifier LPAREN [ expression ] RPAREN }
;
method_call ::= method_call_root DOT method_call_body
;
method_call_body ::=
method_identifier { attribute_instance } [ LPAREN list_of_arguments RPAREN ]
| built_in_method_call
;
built_in_method_call ::=
array_manipulation_call
| randomize_call
;
array_manipulation_call ::=
array_method_name { attribute_instance }
[ LPAREN list_of_arguments RPAREN ]
[ WITH_K LPAREN expression RPAREN ]
;
randomize_call ::=
//NOTE: "randomize" is not a keyword, so no RANDOMIZE_K
IDENT { attribute_instance }
[ LPAREN [ variable_identifier_list | NULL_K ] RPAREN ]
[ WITH_K [ LPAREN [ identifier_list ] RPAREN ] constraint_block ]
;
method_call_root ::= primary | implicit_class_handle
;
array_method_name ::=
method_identifier | UNIQUE_K | AND_K | OR_K | XOR_K
;
//A.8.3 Expressions
inc_or_dec_expression ::=
inc_or_dec_operator { attribute_instance } variable_lvalue
| variable_lvalue { attribute_instance } inc_or_dec_operator
;
conditional_expression ::= cond_predicate QMARK { attribute_instance } expression COLON expression
;
constant_expression ::=
constant_primary
| unary_operator { attribute_instance } constant_primary
| constant_expression binary_operator { attribute_instance } constant_expression
| constant_expression QMARK { attribute_instance } constant_expression COLON constant_expression
;
constant_mintypmax_expression ::=
constant_expression
| constant_expression COLON constant_expression COLON constant_expression
;
constant_param_expression ::=
constant_mintypmax_expression | data_type | DOLLAR
;
param_expression ::= mintypmax_expression | data_type
;
constant_range_expression ::=
constant_expression
| constant_part_select_range
;
constant_part_select_range ::=
constant_range
| constant_indexed_range
;
constant_range ::= constant_expression COLON constant_expression
;
constant_indexed_range ::=
constant_expression PLUS_COLON constant_expression
| constant_expression MINUS_COLON constant_expression
;
expression ::=
primary
| unary_operator { attribute_instance } primary
| inc_or_dec_expression
| LPAREN operator_assignment RPAREN
| expression binary_operator { attribute_instance } expression
| conditional_expression
| inside_expression
| tagged_union_expression
;
tagged_union_expression ::=
TAGGED_K member_identifier [ expression ]
;
inside_expression ::= expression INSIDE_K LCURLY open_range_list RCURLY
;
value_range ::=
expression
| LBRACK expression COLON expression RBRACK
;
mintypmax_expression ::=
expression
| expression COLON expression COLON expression
;
module_path_conditional_expression ::= module_path_expression QMARK { attribute_instance }
module_path_expression COLON module_path_expression
;
module_path_expression ::=
module_path_primary
| unary_module_path_operator { attribute_instance } module_path_primary
| module_path_expression binary_module_path_operator { attribute_instance }
module_path_expression
| module_path_conditional_expression
;
module_path_mintypmax_expression ::=
module_path_expression
| module_path_expression COLON module_path_expression COLON module_path_expression
;
part_select_range ::= constant_range | indexed_range
;
indexed_range ::=
expression PLUS_COLON constant_expression
| expression MINUS_COLON constant_expression
;
genvar_expression ::= constant_expression
;
//A.8.4 Primaries
constant_primary ::=
primary_literal
| ps_parameter_identifier constant_select
| specparam_identifier [ LBRACK constant_range_expression RBRACK ]
| genvar_identifier
| [ package_scope | class_scope ] enum_identifier
| constant_concatenation [ LBRACK constant_range_expression RBRACK ]
| constant_multiple_concatenation [ LBRACK constant_range_expression RBRACK ]
| constant_function_call
| constant_let_expression
| LPAREN constant_mintypmax_expression RPAREN
| constant_cast
| constant_assignment_pattern_expression
| type_reference
;
module_path_primary ::=
number
| identifier
| module_path_concatenation
| module_path_multiple_concatenation
| function_subroutine_call
| LPAREN module_path_mintypmax_expression RPAREN
;
primary ::=
primary_literal
| [ class_qualifier | package_scope ] hierarchical_identifier select
| empty_queue
| concatenation [ LBRACK range_expression RBRACK ]
| multiple_concatenation [ LBRACK range_expression RBRACK ]
| function_subroutine_call
| let_expression
| LPAREN mintypmax_expression RPAREN
| cast
| assignment_pattern_expression
| streaming_concatenation
| sequence_method_call
| THIS_K
| DOLLAR
| NULL_K
;
//LOCAL_COLON2: local::
class_qualifier : [ IDENT COLON2 ] [ implicit_class_handle DOT | class_scope ]
;
range_expression ::=
expression
| part_select_range
;
primary_literal ::= number | time_literal | unbased_unsized_literal | string_literal
;
time_literal ::=
unsigned_number time_unit
| fixed_point_number time_unit
;
time_unit ::= IDENT //s | ms | us | ns | ps | fs
;
implicit_class_handle ::= THIS_K | SUPER_K | THIS_K DOT SUPER_K
;
bit_select ::= { LBRACK expression RBRACK }
;
select ::=
[ { DOT member_identifier bit_select } DOT member_identifier ] bit_select [ LBRACK part_select_range RBRACK ]
;
nonrange_select ::=
[ { DOT member_identifier bit_select } DOT member_identifier ] bit_select
;
constant_bit_select ::= { LBRACK constant_expression RBRACK }
;
constant_select ::=
[ { DOT member_identifier constant_bit_select } DOT member_identifier ] constant_bit_select
[ LBRACK constant_part_select_range RBRACK ]
;
constant_cast ::=
casting_type SQUOTE LPAREN constant_expression RPAREN
;
constant_let_expression ::= let_expression
;
cast ::=
casting_type SQUOTE LPAREN expression RPAREN
;
//A.8.5 Expression left-side values
net_lvalue ::=
ps_or_hierarchical_net_identifier constant_select
| LCURLY net_lvalue { COMMA net_lvalue } RCURLY
| [ assignment_pattern_expression_type ] assignment_pattern_net_lvalue
;
variable_lvalue ::=
[ implicit_class_handle DOT | package_scope ] hierarchical_variable_identifier select
| LCURLY variable_lvalue { COMMA variable_lvalue } RCURLY
| [ assignment_pattern_expression_type ] assignment_pattern_variable_lvalue
| streaming_concatenation
;
nonrange_variable_lvalue ::=
[ implicit_class_handle DOT | package_scope ] hierarchical_variable_identifier nonrange_select
;
//A.8.6 Operators
unary_operator ::=
PLUS | MINUS | NOT | TILDE | AND | TILDE_AND | OR | TILDE_OR | XOR | TILDE_XOR | XOR_TILDE
;
binary_operator ::=
PLUS | MINUS | STAR | DIV | MOD | EQ2 | NOT_EQ | EQ3 | NOT_EQ2 | EQ2_QMARK | NOT_EQ_QMARK | AND2 | OR2 | STAR2
| LT | LT_EQ | GT | GT_EQ | AND | OR | XOR | XOR_TILDE | TILDE_XOR | GT2 | LT2 | GT3 | LT3
| MINUS_GT | LT_MINUS_GT
;
inc_or_dec_operator ::= PLUS2 | MINUS2
;
unary_module_path_operator ::=
NOT | TILDE | AND | TILDE_AND | OR | TILDE_OR | XOR | TILDE_XOR | XOR_TILDE
;
binary_module_path_operator ::=
EQ2 | NOT_EQ | AND2 | OR2 | AND | OR | XOR | XOR_TILDE | TILDE_XOR
;

//A.8.7 Numbers
//TODO

//A.8.8 Strings
string_literal ::= STRING //" { Any_ASCII_Characters } "
;
//A.9 General
//A.9.1 Attributes
attribute_instance ::= LPAREN_STAR attr_spec { COMMA attr_spec } STAR_RPAREN
;
attr_spec ::= attr_name [ EQ constant_expression ]
;
attr_name ::= identifier
;

//A.9.3 Identifiers
array_identifier ::= identifier
;
block_identifier ::= identifier
;
bin_identifier ::= identifier
;
c_identifier ::= identifier //TODO: [ a-zA-Z_ ] { [ a-zA-Z0-9_ ] }
;
cell_identifier ::= identifier
;
checker_identifier ::= identifier
;
class_identifier ::= identifier
;
class_variable_identifier ::= variable_identifier
;
clocking_identifier ::= identifier
;
config_identifier ::= identifier
;
const_identifier ::= identifier
;
constraint_identifier ::= identifier
;
covergroup_identifier ::= identifier
;
covergroup_variable_identifier ::= variable_identifier
;
cover_point_identifier ::= identifier
;
cross_identifier ::= identifier
;
dynamic_array_variable_identifier ::= variable_identifier
;
enum_identifier ::= identifier
;
escaped_identifier ::= ESC_IDENT //TODO: \ {any_ASCII_character_except_white_space} white_space
;
formal_identifier ::= identifier
;
function_identifier ::= identifier
;
igenerate_block_identifier ::= identifier
;
genvar_identifier ::= identifier
;
hierarchical_array_identifier ::= hierarchical_identifier
;
hierarchical_block_identifier ::= hierarchical_identifier
;
hierarchical_event_identifier ::= hierarchical_identifier
;
//TODO: DS_ROOT_K: $root
hierarchical_identifier ::= [ DS_ROOT_K DOT ] { identifier constant_bit_select DOT } identifier
;
hierarchical_net_identifier ::= hierarchical_identifier
;
hierarchical_parameter_identifier ::= hierarchical_identifier
;
hierarchical_property_identifier ::= hierarchical_identifier
;
hierarchical_sequence_identifier ::= hierarchical_identifier
;
hierarchical_task_identifier ::= hierarchical_identifier
;
hierarchical_tf_identifier ::= hierarchical_identifier
;
hierarchical_variable_identifier ::= hierarchical_identifier
;
identifier ::=
simple_identifier
| escaped_identifier
;
index_variable_identifier ::= identifier
;
interface_identifier ::= identifier
;
interface_instance_identifier ::= identifier
;
inout_port_identifier ::= identifier
;
input_port_identifier ::= identifier
;
instance_identifier ::= identifier
;
library_identifier ::= identifier
;
member_identifier ::= identifier
;
method_identifier ::= identifier
;
modport_identifier ::= identifier
;
module_identifier ::= identifier
;
net_identifier ::= identifier
;
output_port_identifier ::= identifier
;
package_identifier ::= identifier
;
package_scope ::=
package_identifier COLON2
//TODO: UNIT_K: $unit
| DS_UNIT_K COLON2
;
parameter_identifier ::= identifier
;
port_identifier ::= identifier
;
production_identifier ::= identifier
;
program_identifier ::= identifier
;
property_identifier ::= identifier
;
ps_class_identifier ::= [ package_scope ] class_identifier
;
ps_covergroup_identifier ::= [ package_scope ] covergroup_identifier
;
ps_identifier ::= [ package_scope ] identifier
;
ps_or_hierarchical_array_identifier ::=
[ implicit_class_handle DOT | class_scope | package_scope ] hierarchical_array_identifier
;
ps_or_hierarchical_net_identifier ::= [ package_scope ] net_identifier | hierarchical_net_identifier
;
ps_or_hierarchical_property_identifier ::=
[ package_scope ] property_identifier
| hierarchical_property_identifier
;
ps_or_hierarchical_sequence_identifier ::=
[ package_scope ] sequence_identifier
| hierarchical_sequence_identifier
;
ps_or_hierarchical_tf_identifier ::= [ package_scope ] tf_identifier | hierarchical_tf_identifier
;
ps_parameter_identifier ::=
[ package_scope | class_scope ] parameter_identifier
| { generate_block_identifier [ LBRACK constant_expression RBRACK ] DOT } parameter_identifier
;
//LOCAL_K: local::
ps_type_identifier ::= [ LOCAL_K | package_scope ] type_identifier
;
sequence_identifier ::= identifier
;
signal_identifier ::= identifier
;
simple_identifier ::= IDENT //TODO: [ a-zA-Z_ ] { [ a-zA-Z0-9_$ ] }
;
specparam_identifier ::= identifier
;
system_tf_identifier ::= SYSTEM_IDENT //TODO: $[ a-zA-Z0-9_$ ]{ [ a-zA-Z0-9_$ ] }
;
task_identifier ::= identifier
;
tf_identifier ::= identifier
;
terminal_identifier ::= identifier
;
topmodule_identifier ::= identifier
;
type_identifier ::= identifier
;
udp_identifier ::= identifier
;
variable_identifier ::= identifier
;

unsigned_number : NUMBER;
fixed_point_number : NUMBER;
number : NUMBER;
unbased_unsized_literal : NUMBER;
integral_number : NUMBER;
generate_block_identifier : IDENT;
real_number : NUMBER;

extern_tf_declaration : 
EXTERN_K method_prototype SEMI
| EXTERN_K FORKJOIN_K task_prototype SEMI
;
