package apfe.maze.tc1;
import apfe.maze.runtime.Scanner;
import apfe.maze.runtime.Token;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

%%

%public
%class Tc1Scanner
%extends    Scanner
%implements ITokenCodes
%unicode
%line
%column
%function xnextToken
%type Token

%{
  private final StringBuilder string = new StringBuilder();

  private static String stFileName = "?";

  public static void setFileName(String fn) {
  	stFileName = fn;
  }

  public static String getFileName() {
  	return stFileName;
  }

  private Token create(int id, String text) {
  	  	return new Token(stFileName, yyline+1, yycolumn+1, text, id);
  }

  private Token create(int id) {
  	  	return create(id, yytext());
  }

  private void ticLine() {
  	String ss = yytext();
  }

  public Tc1Scanner(String fn) throws FileNotFoundException {
	this(new FileReader(fn));
    setFileName(fn);
  }

  //TODO: derive from RuntimeException to pass back to parser
  private void error(String msg) {
      StringBuilder sb = new StringBuilder("Error: ");
      sb.append(getFileName()).append(':').append(yyline+1).append(':')
              .append(yycolumn+1).append(": ").append(msg)
              .append(": ").append(yytext());
      throw new RuntimeException(sb.toString());
  }

    @Override
    public Token nextToken() {
        try {
            return xnextToken();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
  
  public boolean isEOF() {
  	return zzAtEOF;
	}
%}

/* main character classes */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]

Space = [ \t\f]
WhiteSpace = {LineTerminator} | {Space}

/* comments */
Comment = ("/*" ~"*/") | {EndOfLineComment}
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?

/* identifiers */
IDENT = [a-zA-Z_][a-zA-Z_0-9$]*
ESC_IDENT = \\ ~{WhiteSpace}
SYSTEM_IDENT = "$" {IDENT}

/*No whitespace between number and unit*/
TIME_LITERAL = ({UnsignedNumber} | {FixedPointNumber}) {TimeUnit}
TimeUnit = [munpf]? "s"

TicLine = "`line" {Space}+ [0-9]+ {Space}+ \" [^\"]+ \" {Space}+ [0-2]
TimeScale = "`timescale" {Space}+ {TIME_LITERAL} {Space}* "/" {Space}* {TIME_LITERAL}

OctDigit = [0-7]
HexDigit = [0-9a-fA-F]
NonZeroUnsignedNumber = [1-9][_0-9]*
UnsignedNumber        = [0-9][_0-9]*
RealNumber = {UnsignedNumber} '.' {UnsignedNumber} ([eE] [+-]? {UnsignedNumber})?
FixedPointNumber = {UnsignedNumber} '.' {UnsignedNumber}
BinaryValue = [01xXzZ?][01xXzZ?_]* 
OctalValue = [0-7xXzZ?][0-7xXzZ?_]*
HexValue = [0-9a-fA-FxXzZ?][0-9a-fA-FxXzZ?_]*
DecimalBase = \' [sS]? [dD] {Space}*
BinaryBase = \' [sS]? [bB] {Space}*
OctalBase = \' [sS]? [oO] {Space}*
HexBase = \' [sS]? [hH] {Space}*
UNBASED_UNSIZED_LITERAL = \' {Space}* [01xXzZ?]
Size = {NonZeroUnsignedNumber} {Space}*

//A.8.7 Numbers
UNSIGNED_NUMBER = {UnsignedNumber}
NUMBER = {IntegralNumber} | {RealNumber}

IntegralNumber = {Size}? ({OctalNumber} | {BinaryNumber} | {HexNumber} | {DecimalNumber})

DecimalNumber = {DecimalBase}? ({UnsignedNumber} | ([xXzZ?] '_'*))

BinaryNumber = {BinaryBase} {BinaryValue}

OctalNumber = {OctalBase} {OctalValue}

HexNumber = {HexBase} {HexValue}

/* string and character literals */
StringCharacter = [^\r\n\"\\]

%state STRING

%%

<YYINITIAL> {
    {WhiteSpace}+ |
	{Comment}     |
    {TimeScale}   { /* ignore */ }

	//{insert 'create'
	"accept_on" {return create(ACCEPT_ON_K);}
	"alias" {return create(ALIAS_K);}
	"always_comb" {return create(ALWAYS_COMB_K);}
	"always_ff" {return create(ALWAYS_FF_K);}
	"always" {return create(ALWAYS_K);}
	"always_latch" {return create(ALWAYS_LATCH_K);}
	"and" {return create(AND_K);}
	"assert" {return create(ASSERT_K);}
	"assign" {return create(ASSIGN_K);}
	"assume" {return create(ASSUME_K);}
	"automatic" {return create(AUTOMATIC_K);}
	"before" {return create(BEFORE_K);}
	"begin" {return create(BEGIN_K);}
	"bind" {return create(BIND_K);}
	"binsof" {return create(BINSOF_K);}
	"bins" {return create(BINS_K);}
	"bit" {return create(BIT_K);}
	"break" {return create(BREAK_K);}
	"bufif0" {return create(BUFIF0_K);}
	"bufif1" {return create(BUFIF1_K);}
	"buf" {return create(BUF_K);}
	"byte" {return create(BYTE_K);}
	"casex" {return create(CASEX_K);}
	"casez" {return create(CASEZ_K);}
	"case" {return create(CASE_K);}
	"cell" {return create(CELL_K);}
	"chandle" {return create(CHANDLE_K);}
	"checker" {return create(CHECKER_K);}
	"class" {return create(CLASS_K);}
	"clocking" {return create(CLOCKING_K);}
	"cmos" {return create(CMOS_K);}
	"config" {return create(CONFIG_K);}
	"constraint" {return create(CONSTRAINT_K);}
	"const" {return create(CONST_K);}
	"context" {return create(CONTEXT_K);}
	"continue" {return create(CONTINUE_K);}
	"covergroup" {return create(COVERGROUP_K);}
	"coverpoint" {return create(COVERPOINT_K);}
	"cover" {return create(COVER_K);}
	"cross" {return create(CROSS_K);}
	"deassign" {return create(DEASSIGN_K);}
	"default" {return create(DEFAULT_K);}
	"defparam" {return create(DEFPARAM_K);}
	"design" {return create(DESIGN_K);}
	"disable" {return create(DISABLE_K);}
	"dist" {return create(DIST_K);}
	"do" {return create(DO_K);}
	"edge" {return create(EDGE_K);}
	"else" {return create(ELSE_K);}
	"endcase" {return create(ENDCASE_K);}
	"endchecker" {return create(ENDCHECKER_K);}
	"endclass" {return create(ENDCLASS_K);}
	"endclocking" {return create(ENDCLOCKING_K);}
	"endconfig" {return create(ENDCONFIG_K);}
	"endfunction" {return create(ENDFUNCTION_K);}
	"endgenerate" {return create(ENDGENERATE_K);}
	"endgroup" {return create(ENDGROUP_K);}
	"endinterface" {return create(ENDINTERFACE_K);}
	"endmodule" {return create(ENDMODULE_K);}
	"endpackage" {return create(ENDPACKAGE_K);}
	"endprimitive" {return create(ENDPRIMITIVE_K);}
	"endprogram" {return create(ENDPROGRAM_K);}
	"endproperty" {return create(ENDPROPERTY_K);}
	"endsequence" {return create(ENDSEQUENCE_K);}
	"endspecify" {return create(ENDSPECIFY_K);}
	"endtable" {return create(ENDTABLE_K);}
	"endtask" {return create(ENDTASK_K);}
	"end" {return create(END_K);}
	"enum" {return create(ENUM_K);}
	"eventually" {return create(EVENTUALLY_K);}
	"event" {return create(EVENT_K);}
	"expect" {return create(EXPECT_K);}
	"export" {return create(EXPORT_K);}
	"extends" {return create(EXTENDS_K);}
	"extern" {return create(EXTERN_K);}
	"final" {return create(FINAL_K);}
	"first_match" {return create(FIRST_MATCH_K);}
	"force" {return create(FORCE_K);}
	"foreach" {return create(FOREACH_K);}
	"forever" {return create(FOREVER_K);}
	"forkjoin" {return create(FORKJOIN_K);}
	"fork" {return create(FORK_K);}
	"for" {return create(FOR_K);}
	"function" {return create(FUNCTION_K);}
	"generate" {return create(GENERATE_K);}
	"genvar" {return create(GENVAR_K);}
	"global" {return create(GLOBAL_K);}
	"highz0" {return create(HIGHZ0_K);}
	"highz1" {return create(HIGHZ1_K);}
	"iff" {return create(IFF_K);}
	"ifnone" {return create(IFNONE_K);}
	"if" {return create(IF_K);}
	"ignore_bins" {return create(IGNORE_BINS_K);}
	"illegal_bins" {return create(ILLEGAL_BINS_K);}
	"implies" {return create(IMPLIES_K);}
	"import" {return create(IMPORT_K);}
	"incdir" {return create(INCDIR_K);}
	"include" {return create(INCLUDE_K);}
	"initial" {return create(INITIAL_K);}
	"inout" {return create(INOUT_K);}
	"input" {return create(INPUT_K);}
	"inside" {return create(INSIDE_K);}
	"instance" {return create(INSTANCE_K);}
	"integer" {return create(INTEGER_K);}
	"interface" {return create(INTERFACE_K);}
	"intersect" {return create(INTERSECT_K);}
	"int" {return create(INT_K);}
	"join_any" {return create(JOIN_ANY_K);}
	"join" {return create(JOIN_K);}
	"join_none" {return create(JOIN_NONE_K);}
	"large" {return create(LARGE_K);}
	"let" {return create(LET_K);}
	"liblist" {return create(LIBLIST_K);}
	"library" {return create(LIBRARY_K);}
	"localparam" {return create(LOCALPARAM_K);}
	"local" {return create(LOCAL_K);}
	"logic" {return create(LOGIC_K);}
	"longint" {return create(LONGINT_K);}
	"macromodule" {return create(MACROMODULE_K);}
	"matches" {return create(MATCHES_K);}
	"medium" {return create(MEDIUM_K);}
	"modport" {return create(MODPORT_K);}
	"module" {return create(MODULE_K);}
	"nand" {return create(NAND_K);}
	"negedge" {return create(NEGEDGE_K);}
	"new" {return create(NEW_K);}
	"nexttime" {return create(NEXTTIME_K);}
	"nmos" {return create(NMOS_K);}
	"nor" {return create(NOR_K);}
	"noshowcancelled" {return create(NOSHOWCANCELLED_K);}
	"notif0" {return create(NOTIF0_K);}
	"notif1" {return create(NOTIF1_K);}
	"not" {return create(NOT_K);}
	"null" {return create(NULL_K);}
	"or" {return create(OR_K);}
	"output" {return create(OUTPUT_K);}
	"package" {return create(PACKAGE_K);}
	"packed" {return create(PACKED_K);}
	"parameter" {return create(PARAMETER_K);}
	"pmos" {return create(PMOS_K);}
	"posedge" {return create(POSEDGE_K);}
	"primitive" {return create(PRIMITIVE_K);}
	"priority" {return create(PRIORITY_K);}
	"program" {return create(PROGRAM_K);}
	"property" {return create(PROPERTY_K);}
	"protected" {return create(PROTECTED_K);}
	"pull0" {return create(PULL0_K);}
	"pull1" {return create(PULL1_K);}
	"pulldown" {return create(PULLDOWN_K);}
	"pullup" {return create(PULLUP_K);}
	"pulsestyle_ondetect" {return create(PULSESTYLE_ONDETECT_K);}
	"pulsestyle_onevent" {return create(PULSESTYLE_ONEVENT_K);}
	"pure" {return create(PURE_K);}
	"randcase" {return create(RANDCASE_K);}
	"randc" {return create(RANDC_K);}
	"randsequence" {return create(RANDSEQUENCE_K);}
	"rand" {return create(RAND_K);}
	"randomize" {return create(RANDOMIZE_K);}
	"rcmos" {return create(RCMOS_K);}
	"realtime" {return create(REALTIME_K);}
	"real" {return create(REAL_K);}
	"ref" {return create(REF_K);}
	"reg" {return create(REG_K);}
	"reject_on" {return create(REJECT_ON_K);}
	"release" {return create(RELEASE_K);}
	"repeat" {return create(REPEAT_K);}
	"restrict" {return create(RESTRICT_K);}
	"return" {return create(RETURN_K);}
	"rnmos" {return create(RNMOS_K);}
	"rpmos" {return create(RPMOS_K);}
	"rtranif0" {return create(RTRANIF0_K);}
	"rtranif1" {return create(RTRANIF1_K);}
	"rtran" {return create(RTRAN_K);}
	"scalared" {return create(SCALARED_K);}
	"sequence" {return create(SEQUENCE_K);}
	"shortint" {return create(SHORTINT_K);}
	"shortreal" {return create(SHORTREAL_K);}
	"showcancelled" {return create(SHOWCANCELLED_K);}
	"signed" {return create(SIGNED_K);}
	"small" {return create(SMALL_K);}
	"solve" {return create(SOLVE_K);}
	"specify" {return create(SPECIFY_K);}
	"specparam" {return create(SPECPARAM_K);}
	"static" {return create(STATIC_K);}
	"string" {return create(STRING_K);}
	"strong0" {return create(STRONG0_K);}
	"strong1" {return create(STRONG1_K);}
	"strong" {return create(STRONG_K);}
	"struct" {return create(STRUCT_K);}
	"super" {return create(SUPER_K);}
	"supply0" {return create(SUPPLY0_K);}
	"supply1" {return create(SUPPLY1_K);}
	"sync_accept_on" {return create(SYNC_ACCEPT_ON_K);}
	"sync_reject_on" {return create(SYNC_REJECT_ON_K);}
	"s_always" {return create(S_ALWAYS_K);}
	"s_eventually" {return create(S_EVENTUALLY_K);}
	"s_nexttime" {return create(S_NEXTTIME_K);}
	"s_until" {return create(S_UNTIL_K);}
	"s_until_with" {return create(S_UNTIL_WITH_K);}
	"table" {return create(TABLE_K);}
	"tagged" {return create(TAGGED_K);}
	"task" {return create(TASK_K);}
	"this" {return create(THIS_K);}
	"throughout" {return create(THROUGHOUT_K);}
	"timeprecision" {return create(TIMEPRECISION_K);}
	"timeunit" {return create(TIMEUNIT_K);}
	"time" {return create(TIME_K);}
	"tranif0" {return create(TRANIF0_K);}
	"tranif1" {return create(TRANIF1_K);}
	"tran" {return create(TRAN_K);}
	"tri0" {return create(TRI0_K);}
	"tri1" {return create(TRI1_K);}
	"triand" {return create(TRIAND_K);}
	"trior" {return create(TRIOR_K);}
	"trireg" {return create(TRIREG_K);}
	"tri" {return create(TRI_K);}
	"typedef" {return create(TYPEDEF_K);}
	"type" {return create(TYPE_K);}
	"union" {return create(UNION_K);}
	"unique0" {return create(UNIQUE0_K);}
	"unique" {return create(UNIQUE_K);}
	"unsigned" {return create(UNSIGNED_K);}
	"until" {return create(UNTIL_K);}
	"until_with" {return create(UNTIL_WITH_K);}
	"untyped" {return create(UNTYPED_K);}
	"use" {return create(USE_K);}
	"uwire" {return create(UWIRE_K);}
	"var" {return create(VAR_K);}
	"vectored" {return create(VECTORED_K);}
	"virtual" {return create(VIRTUAL_K);}
	"void" {return create(VOID_K);}
	"wait" {return create(WAIT_K);}
	"wait_order" {return create(WAIT_ORDER_K);}
	"wand" {return create(WAND_K);}
	"weak0" {return create(WEAK0_K);}
	"weak1" {return create(WEAK1_K);}
	"weak" {return create(WEAK_K);}
	"while" {return create(WHILE_K);}
	"wildcard" {return create(WILDCARD_K);}
	"wire" {return create(WIRE_K);}
	"within" {return create(WITHIN_K);}
	"with" {return create(WITH_K);}
	"wor" {return create(WOR_K);}
	"xnor" {return create(XNOR_K);}
	"xor" {return create(XOR_K);}
	"1step" {return create(W1STEP_K);}
	"PATHPULSE$" {return create(PATHPULSE_K);}
	"$setup" {return create(DS_SETUP_K);}
	"$removal" {return create(DS_REMOVAL_K);}
	"$period" {return create(DS_PERIOD_K);}
	"$recrem" {return create(DS_RECREM_K);}
	"$setuphold" {return create(DS_SETUPHOLD_K);}
	"$recovery" {return create(DS_RECOVERY_K);}
	"$hold" {return create(DS_HOLD_K);}
	"$width" {return create(DS_WIDTH_K);}
	"$skew" {return create(DS_SKEW_K);}
	"$fullskew" {return create(DS_FULLSKEW_K);}
	"$timeskew" {return create(DS_TIMESKEW_K);}
	"$nochange" {return create(DS_NOCHANGE_K);}
	"$fatal" {return create(DS_FATAL_K);}
	"$error" {return create(DS_ERROR_K);}
	"$warning" {return create(DS_WARNING_K);}
	"$info" {return create(DS_INFO_K);}
	"$unit" {return create(DS_UNIT_K);}
	"$root" {return create(DS_ROOT_K);}
	"&" {return create(AND);}
	"&&" {return create(AND2);}
	"&&&" {return create(AND3);}
	"&=" {return create(AND_EQ);}
	"@" {return create(AT);}
	"@@" {return create(AT2);}
	"@*" {return create(AT_STAR);}
	"|=>" {return create(BAR_EQ_GT);}
	"|->" {return create(BAR_MINUS_GT);}
	"::" {return create(COLON2);}
	":" {return create(COLON);}
	":/" {return create(COLON_DIV);}
	":=" {return create(COLON_EQ);}
	"," {return create(COMMA);}
	"/" {return create(DIV);}
	"/=" {return create(DIV_EQ);}
	"$" {return create(DOLLAR);}
	"." {return create(DOT);}
	".*" {return create(DOT_STAR);}
	"==" {return create(EQ2);}
	"==?" {return create(EQ2_QMARK);}
	"===" {return create(EQ3);}
	"=" {return create(EQ);}
	"=>" {return create(EQ_GT);}
	">>" {return create(GT2);}
	">>=" {return create(GT2_EQ);}
	">>>=" {return create(GT3_EQ);}
	">" {return create(GT);}
	">>>" {return create(GT3);}
	">=" {return create(GT_EQ);}
	"[" {return create(LBRACK);}
	"{" {return create(LCURLY);}
	"(" {return create(LPAREN);}
	"(*" {return create(LPAREN_STAR);}
	"<<" {return create(LT2);}
	"<<=" {return create(LT2_EQ);}
	"<<<=" {return create(LT3_EQ);}
	"<" {return create(LT);}
	"<<<" {return create(LT3);}
	"<=" {return create(LT_EQ);}
	"<->" {return create(LT_MINUS_GT);}
	"--" {return create(MINUS2);}
	"-" {return create(MINUS);}
	"-:" {return create(MINUS_COLON);}
	"-=" {return create(MINUS_EQ);}
	"->>" {return create(MINUS_GT2);}
	"->" {return create(MINUS_GT);}
	"%" {return create(MOD);}
	"%=" {return create(MOD_EQ);}
	"!" {return create(NOT);}
	"!==" {return create(NOT_EQ2);}
	"!=" {return create(NOT_EQ);}
	"!=?" {return create(NOT_EQ_QMARK);}
	"||" {return create(OR2);}
	"|" {return create(OR);}
	"|=" {return create(OR_EQ);}
	"++" {return create(PLUS2);}
	"+" {return create(PLUS);}
	"+:" {return create(PLUS_COLON);}
	"+=" {return create(PLUS_EQ);}
	"##" {return create(POUND2);}
	"#" {return create(POUND);}
	"#=#" {return create(POUND_EQ_POUND);}
	"#-#" {return create(POUND_MINUS_POUND);}
	"?" {return create(QMARK);}
	"]" {return create(RBRACK);}
	"}" {return create(RCURLY);}
	")" {return create(RPAREN);}
	";" {return create(SEMI);}
	"**" {return create(STAR2);}
	"*" {return create(STAR);}
	"*::*" {return create(STAR_COLON2_STAR);}
	"*=" {return create(STAR_EQ);}
	"*>" {return create(STAR_GT);}
	"*)" {return create(STAR_RPAREN);}
	"~" {return create(TILDE);}
	"~&" {return create(TILDE_AND);}
	"~|" {return create(TILDE_OR);}
	"~^" {return create(TILDE_XOR);}
	"^" {return create(XOR);}
	"^=" {return create(XOR_EQ);}
	"^~" {return create(XOR_TILDE);}
	{IDENT} {return create(IDENT);}
	{ESC_IDENT} {return create(ESC_IDENT);}
	{UNSIGNED_NUMBER} {return create(UNSIGNED_NUMBER);}
	{UNBASED_UNSIZED_LITERAL} {return create(UNBASED_UNSIZED_LITERAL);}
	{NUMBER} {return create(NUMBER);}
	{TIME_LITERAL} {return create(TIME_LITERAL);}
	{SYSTEM_IDENT} {return create(SYSTEM_IDENT);}
	"'" {return create(SQUOTE);}
	//create}

  	{TicLine}	{ ticLine(); }

  /* string literal */
  \"                             { yybegin(STRING); string.setLength(0); }
}

<STRING> {
  \"                             { yybegin(YYINITIAL); return create(STRING_LITERAL, string.toString()); }
  
  {StringCharacter}+             { string.append( yytext() ); }
  
  /* escape sequences */
  "\\b"                          { string.append( '\b' ); }
  "\\t"                          { string.append( '\t' ); }
  "\\n"                          { string.append( '\n' ); }
  "\\f"                          { string.append( '\f' ); }
  "\\r"                          { string.append( '\r' ); }
  "\\\""                         { string.append( '\"' ); }
  "\\'"                          { string.append( '\'' ); }
  "\\\\"                         { string.append( '\\' ); }
  \\[0-3]?{OctDigit}?{OctDigit}  { char val = (char) Integer.parseInt(yytext().substring(1),8); string.append( val ); }
  \\h{HexDigit}?{HexDigit}  { char val = (char) Integer.parseInt(yytext().substring(1),16); string.append( val ); }
  
  /* error cases */
  \\.                            { error("Illegal escape sequence"); }
  {LineTerminator}               { error("Unterminated string at end of line"); }
}

/* error fallback */
[^]                              { error("Illegal character"); }
<<EOF>>                          { return create(Token.EOF);  }
