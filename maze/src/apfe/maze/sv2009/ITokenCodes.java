/*
 * The MIT License
 *
 * Copyright 2014 gburdell.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package apfe.maze.sv2009;

/**
 *
 * @author gburdell
 */
public interface ITokenCodes {
	public static final int ACCEPT_ON_K = 1 ;
	public static final int ALIAS_K = 2 ;
	public static final int ALWAYS_COMB_K = 3 ;
	public static final int ALWAYS_FF_K = 4 ;
	public static final int ALWAYS_K = 5 ;
	public static final int ALWAYS_LATCH_K = 6 ;
	public static final int AND_K = 7 ;
	public static final int ASSERT_K = 8 ;
	public static final int ASSIGN_K = 9 ;
	public static final int ASSUME_K = 10 ;
	public static final int AUTOMATIC_K = 11 ;
	public static final int BEFORE_K = 12 ;
	public static final int BEGIN_K = 13 ;
	public static final int BIND_K = 14 ;
	public static final int BINSOF_K = 15 ;
	public static final int BINS_K = 16 ;
	public static final int BIT_K = 17 ;
	public static final int BREAK_K = 18 ;
	public static final int BUFIF0_K = 19 ;
	public static final int BUFIF1_K = 20 ;
	public static final int BUF_K = 21 ;
	public static final int BYTE_K = 22 ;
	public static final int CASEX_K = 23 ;
	public static final int CASEZ_K = 24 ;
	public static final int CASE_K = 25 ;
	public static final int CELL_K = 26 ;
	public static final int CHANDLE_K = 27 ;
	public static final int CHECKER_K = 28 ;
	public static final int CLASS_K = 29 ;
	public static final int CLOCKING_K = 30 ;
	public static final int CMOS_K = 31 ;
	public static final int CONFIG_K = 32 ;
	public static final int CONSTRAINT_K = 33 ;
	public static final int CONST_K = 34 ;
	public static final int CONTEXT_K = 35 ;
	public static final int CONTINUE_K = 36 ;
	public static final int COVERGROUP_K = 37 ;
	public static final int COVERPOINT_K = 38 ;
	public static final int COVER_K = 39 ;
	public static final int CROSS_K = 40 ;
	public static final int DEASSIGN_K = 41 ;
	public static final int DEFAULT_K = 42 ;
	public static final int DEFPARAM_K = 43 ;
	public static final int DESIGN_K = 44 ;
	public static final int DISABLE_K = 45 ;
	public static final int DIST_K = 46 ;
	public static final int DO_K = 47 ;
	public static final int EDGE_K = 48 ;
	public static final int ELSE_K = 49 ;
	public static final int ENDCASE_K = 50 ;
	public static final int ENDCHECKER_K = 51 ;
	public static final int ENDCLASS_K = 52 ;
	public static final int ENDCLOCKING_K = 53 ;
	public static final int ENDCONFIG_K = 54 ;
	public static final int ENDFUNCTION_K = 55 ;
	public static final int ENDGENERATE_K = 56 ;
	public static final int ENDGROUP_K = 57 ;
	public static final int ENDINTERFACE_K = 58 ;
	public static final int ENDMODULE_K = 59 ;
	public static final int ENDPACKAGE_K = 60 ;
	public static final int ENDPRIMITIVE_K = 61 ;
	public static final int ENDPROGRAM_K = 62 ;
	public static final int ENDPROPERTY_K = 63 ;
	public static final int ENDSEQUENCE_K = 64 ;
	public static final int ENDSPECIFY_K = 65 ;
	public static final int ENDTABLE_K = 66 ;
	public static final int ENDTASK_K = 67 ;
	public static final int END_K = 68 ;
	public static final int ENUM_K = 69 ;
	public static final int EVENTUALLY_K = 70 ;
	public static final int EVENT_K = 71 ;
	public static final int EXPECT_K = 72 ;
	public static final int EXPORT_K = 73 ;
	public static final int EXTENDS_K = 74 ;
	public static final int EXTERN_K = 75 ;
	public static final int FINAL_K = 76 ;
	public static final int FIRST_MATCH_K = 77 ;
	public static final int FORCE_K = 78 ;
	public static final int FOREACH_K = 79 ;
	public static final int FOREVER_K = 80 ;
	public static final int FORKJOIN_K = 81 ;
	public static final int FORK_K = 82 ;
	public static final int FOR_K = 83 ;
	public static final int FUNCTION_K = 84 ;
	public static final int GENERATE_K = 85 ;
	public static final int GENVAR_K = 86 ;
	public static final int GLOBAL_K = 87 ;
	public static final int HIGHZ0_K = 88 ;
	public static final int HIGHZ1_K = 89 ;
	public static final int IFF_K = 90 ;
	public static final int IFNONE_K = 91 ;
	public static final int IF_K = 92 ;
	public static final int IGNORE_BINS_K = 93 ;
	public static final int ILLEGAL_BINS_K = 94 ;
	public static final int IMPLIES_K = 95 ;
	public static final int IMPORT_K = 96 ;
	public static final int INCDIR_K = 97 ;
	public static final int INCLUDE_K = 98 ;
	public static final int INITIAL_K = 99 ;
	public static final int INOUT_K = 100 ;
	public static final int INPUT_K = 101 ;
	public static final int INSIDE_K = 102 ;
	public static final int INSTANCE_K = 103 ;
	public static final int INTEGER_K = 104 ;
	public static final int INTERFACE_K = 105 ;
	public static final int INTERSECT_K = 106 ;
	public static final int INT_K = 107 ;
	public static final int JOIN_ANY_K = 108 ;
	public static final int JOIN_K = 109 ;
	public static final int JOIN_NONE_K = 110 ;
	public static final int LARGE_K = 111 ;
	public static final int LET_K = 112 ;
	public static final int LIBLIST_K = 113 ;
	public static final int LIBRARY_K = 114 ;
	public static final int LOCALPARAM_K = 115 ;
	public static final int LOCAL_K = 116 ;
	public static final int LOGIC_K = 117 ;
	public static final int LONGINT_K = 118 ;
	public static final int MACROMODULE_K = 119 ;
	public static final int MATCHES_K = 120 ;
	public static final int MEDIUM_K = 121 ;
	public static final int MODPORT_K = 122 ;
	public static final int MODULE_K = 123 ;
	public static final int NAND_K = 124 ;
	public static final int NEGEDGE_K = 125 ;
	public static final int NEW_K = 126 ;
	public static final int NEXTTIME_K = 127 ;
	public static final int NMOS_K = 128 ;
	public static final int NOR_K = 129 ;
	public static final int NOSHOWCANCELLED_K = 130 ;
	public static final int NOTIF0_K = 131 ;
	public static final int NOTIF1_K = 132 ;
	public static final int NOT_K = 133 ;
	public static final int NULL_K = 134 ;
	public static final int OR_K = 135 ;
	public static final int OUTPUT_K = 136 ;
	public static final int PACKAGE_K = 137 ;
	public static final int PACKED_K = 138 ;
	public static final int PARAMETER_K = 139 ;
	public static final int PMOS_K = 140 ;
	public static final int POSEDGE_K = 141 ;
	public static final int PRIMITIVE_K = 142 ;
	public static final int PRIORITY_K = 143 ;
	public static final int PROGRAM_K = 144 ;
	public static final int PROPERTY_K = 145 ;
	public static final int PROTECTED_K = 146 ;
	public static final int PULL0_K = 147 ;
	public static final int PULL1_K = 148 ;
	public static final int PULLDOWN_K = 149 ;
	public static final int PULLUP_K = 150 ;
	public static final int PULSESTYLE_ONDETECT_K = 151 ;
	public static final int PULSESTYLE_ONEVENT_K = 152 ;
	public static final int PURE_K = 153 ;
	public static final int RANDCASE_K = 154 ;
	public static final int RANDC_K = 155 ;
	public static final int RANDSEQUENCE_K = 156 ;
	public static final int RAND_K = 157 ;
	public static final int RANDOMIZE_K = 158 ;
	public static final int RCMOS_K = 159 ;
	public static final int REALTIME_K = 160 ;
	public static final int REAL_K = 161 ;
	public static final int REF_K = 162 ;
	public static final int REG_K = 163 ;
	public static final int REJECT_ON_K = 164 ;
	public static final int RELEASE_K = 165 ;
	public static final int REPEAT_K = 166 ;
	public static final int RESTRICT_K = 167 ;
	public static final int RETURN_K = 168 ;
	public static final int RNMOS_K = 169 ;
	public static final int RPMOS_K = 170 ;
	public static final int RTRANIF0_K = 171 ;
	public static final int RTRANIF1_K = 172 ;
	public static final int RTRAN_K = 173 ;
	public static final int SCALARED_K = 174 ;
	public static final int SEQUENCE_K = 175 ;
	public static final int SHORTINT_K = 176 ;
	public static final int SHORTREAL_K = 177 ;
	public static final int SHOWCANCELLED_K = 178 ;
	public static final int SIGNED_K = 179 ;
	public static final int SMALL_K = 180 ;
	public static final int SOLVE_K = 181 ;
	public static final int SPECIFY_K = 182 ;
	public static final int SPECPARAM_K = 183 ;
	public static final int STATIC_K = 184 ;
	public static final int STRING_K = 185 ;
	public static final int STRONG0_K = 186 ;
	public static final int STRONG1_K = 187 ;
	public static final int STRONG_K = 188 ;
	public static final int STRUCT_K = 189 ;
	public static final int SUPER_K = 190 ;
	public static final int SUPPLY0_K = 191 ;
	public static final int SUPPLY1_K = 192 ;
	public static final int SYNC_ACCEPT_ON_K = 193 ;
	public static final int SYNC_REJECT_ON_K = 194 ;
	public static final int S_ALWAYS_K = 195 ;
	public static final int S_EVENTUALLY_K = 196 ;
	public static final int S_NEXTTIME_K = 197 ;
	public static final int S_UNTIL_K = 198 ;
	public static final int S_UNTIL_WITH_K = 199 ;
	public static final int TABLE_K = 200 ;
	public static final int TAGGED_K = 201 ;
	public static final int TASK_K = 202 ;
	public static final int THIS_K = 203 ;
	public static final int THROUGHOUT_K = 204 ;
	public static final int TIMEPRECISION_K = 205 ;
	public static final int TIMEUNIT_K = 206 ;
	public static final int TIME_K = 207 ;
	public static final int TRANIF0_K = 208 ;
	public static final int TRANIF1_K = 209 ;
	public static final int TRAN_K = 210 ;
	public static final int TRI0_K = 211 ;
	public static final int TRI1_K = 212 ;
	public static final int TRIAND_K = 213 ;
	public static final int TRIOR_K = 214 ;
	public static final int TRIREG_K = 215 ;
	public static final int TRI_K = 216 ;
	public static final int TYPEDEF_K = 217 ;
	public static final int TYPE_K = 218 ;
	public static final int UNION_K = 219 ;
	public static final int UNIQUE0_K = 220 ;
	public static final int UNIQUE_K = 221 ;
	public static final int UNSIGNED_K = 222 ;
	public static final int UNTIL_K = 223 ;
	public static final int UNTIL_WITH_K = 224 ;
	public static final int UNTYPED_K = 225 ;
	public static final int USE_K = 226 ;
	public static final int UWIRE_K = 227 ;
	public static final int VAR_K = 228 ;
	public static final int VECTORED_K = 229 ;
	public static final int VIRTUAL_K = 230 ;
	public static final int VOID_K = 231 ;
	public static final int WAIT_K = 232 ;
	public static final int WAIT_ORDER_K = 233 ;
	public static final int WAND_K = 234 ;
	public static final int WEAK0_K = 235 ;
	public static final int WEAK1_K = 236 ;
	public static final int WEAK_K = 237 ;
	public static final int WHILE_K = 238 ;
	public static final int WILDCARD_K = 239 ;
	public static final int WIRE_K = 240 ;
	public static final int WITHIN_K = 241 ;
	public static final int WITH_K = 242 ;
	public static final int WOR_K = 243 ;
	public static final int XNOR_K = 244 ;
	public static final int XOR_K = 245 ;
	public static final int W1STEP_K = 246 ;
	public static final int PATHPULSE_K = 247 ;
	public static final int DS_SETUP_K = 248 ;
	public static final int DS_REMOVAL_K = 249 ;
	public static final int DS_PERIOD_K = 250 ;
	public static final int DS_RECREM_K = 251 ;
	public static final int DS_SETUPHOLD_K = 252 ;
	public static final int DS_RECOVERY_K = 253 ;
	public static final int DS_HOLD_K = 254 ;
	public static final int DS_WIDTH_K = 255 ;
	public static final int DS_SKEW_K = 256 ;
	public static final int DS_FULLSKEW_K = 257 ;
	public static final int DS_TIMESKEW_K = 258 ;
	public static final int DS_NOCHANGE_K = 259 ;
	public static final int DS_FATAL_K = 260 ;
	public static final int DS_ERROR_K = 261 ;
	public static final int DS_WARNING_K = 262 ;
	public static final int DS_INFO_K = 263 ;
	public static final int DS_UNIT_K = 264 ;
	public static final int DS_ROOT_K = 265 ;
	public static final int AND = 266 ;
	public static final int AND2 = 267 ;
	public static final int AND3 = 268 ;
	public static final int AND_EQ = 269 ;
	public static final int AT = 270 ;
	public static final int AT2 = 271 ;
	public static final int AT_STAR = 272 ;
	public static final int BAR_EQ_GT = 273 ;
	public static final int BAR_MINUS_GT = 274 ;
	public static final int COLON2 = 275 ;
	public static final int COLON = 276 ;
	public static final int COLON_DIV = 277 ;
	public static final int COLON_EQ = 278 ;
	public static final int COMMA = 279 ;
	public static final int DIV = 280 ;
	public static final int DIV_EQ = 281 ;
	public static final int DOLLAR = 282 ;
	public static final int DOT = 283 ;
	public static final int DOT_STAR = 284 ;
	public static final int EQ2 = 285 ;
	public static final int EQ2_QMARK = 286 ;
	public static final int EQ3 = 287 ;
	public static final int EQ = 288 ;
	public static final int EQ_GT = 289 ;
	public static final int GT2 = 290 ;
	public static final int GT2_EQ = 291 ;
	public static final int GT3_EQ = 292 ;
	public static final int GT = 293 ;
	public static final int GT3 = 294 ;
	public static final int GT_EQ = 295 ;
	public static final int LBRACK = 296 ;
	public static final int LCURLY = 297 ;
	public static final int LPAREN = 298 ;
	public static final int LPAREN_STAR = 299 ;
	public static final int LT2 = 300 ;
	public static final int LT2_EQ = 301 ;
	public static final int LT3_EQ = 302 ;
	public static final int LT = 303 ;
	public static final int LT3 = 304 ;
	public static final int LT_EQ = 305 ;
	public static final int LT_MINUS_GT = 306 ;
	public static final int MINUS2 = 307 ;
	public static final int MINUS = 308 ;
	public static final int MINUS_COLON = 309 ;
	public static final int MINUS_EQ = 310 ;
	public static final int MINUS_GT2 = 311 ;
	public static final int MINUS_GT = 312 ;
	public static final int MOD = 313 ;
	public static final int MOD_EQ = 314 ;
	public static final int NOT = 315 ;
	public static final int NOT_EQ2 = 316 ;
	public static final int NOT_EQ = 317 ;
	public static final int NOT_EQ_QMARK = 318 ;
	public static final int OR2 = 319 ;
	public static final int OR = 320 ;
	public static final int OR_EQ = 321 ;
	public static final int PLUS2 = 322 ;
	public static final int PLUS = 323 ;
	public static final int PLUS_COLON = 324 ;
	public static final int PLUS_EQ = 325 ;
	public static final int POUND2 = 326 ;
	public static final int POUND = 327 ;
	public static final int POUND_EQ_POUND = 328 ;
	public static final int POUND_MINUS_POUND = 329 ;
	public static final int QMARK = 330 ;
	public static final int RBRACK = 331 ;
	public static final int RCURLY = 332 ;
	public static final int RPAREN = 333 ;
	public static final int SEMI = 334 ;
	public static final int STAR2 = 335 ;
	public static final int STAR = 336 ;
	public static final int STAR_COLON2_STAR = 337 ;
	public static final int STAR_EQ = 338 ;
	public static final int STAR_GT = 339 ;
	public static final int STAR_RPAREN = 340 ;
	public static final int TILDE = 341 ;
	public static final int TILDE_AND = 342 ;
	public static final int TILDE_OR = 343 ;
	public static final int TILDE_XOR = 344 ;
	public static final int XOR = 345 ;
	public static final int XOR_EQ = 346 ;
	public static final int XOR_TILDE = 347 ;
}
