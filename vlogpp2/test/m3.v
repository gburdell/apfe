`define msg(x,y) `"x: `\`"y`\`"`"
`define PASTE /``/this is a comment
`define append(f) f``_master
`define poobah(_a)\
wildcard bins ``_a``_zero = {0};\
//wildcard bins ``_a``_one = {1};\
wildcard bins ``_a``_two = {2};

`PASTE
module m3;
`PASTE
$display(`msg(left side,right side));
`append(clock)
`poobah(dog)
endmodule
`undef DOOPY
`undef PASTE
