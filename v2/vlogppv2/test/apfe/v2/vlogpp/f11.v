
//comment

`__FILE__
  `__FILE__ //comment

/**another
comment
*/

    `__FILE__ `__LINE__
    `__LINE__

/*comment*/ 

`ifdef NOT_DEFINED
`undef NOT_DEFINED
`else
`define M2 true
`endif

`ifdef M1
FAIL1
`elsif M2
PASS1
`elsif M3
`ifdef M1
FAIL2
`endif
`endif

`define M4
`ifdef M5
`undef M4
`elsif M4
PASS2
`else
FAIL3
`endif

`define M4 bar
