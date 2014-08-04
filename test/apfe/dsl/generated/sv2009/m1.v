module zmuxi31d_prim (z, d0, d1, d2, s0, s1, s2);
output z;
input  d0, d1, d2, s0, s1, s2;
// for Blacktie
              
                                              
      
wire [2:0] sel = {s0,s1,s2}; // 0in one_hot
reg z;
    always @ (s2 or d2 or s1 or d1 or s0 or d0)
        casez ({s2,d2,s1,d1,s0,d0})
            6'b0?0?10: z = 1'b1;
            6'b0?0?11: z = 1'b0;
            6'b0?100?: z = 1'b1;
            6'b0?110?: z = 1'b0;
            6'b0?1010: z = 1'b1;
            6'b0?1111: z = 1'b0;
            6'b100?0?: z = 1'b1;
            6'b110?0?: z = 1'b0;
            6'b100?10: z = 1'b1;
            6'b110?11: z = 1'b0;
            6'b10100?: z = 1'b1;
            6'b11110?: z = 1'b0;
            6'b101010: z = 1'b1;
            6'b111111: z = 1'b0;
            default: z = 1'bx;
        endcase
endmodule


/* -------------------------------------------------------------------------- */
//


module m1(
	idx, base, fd, sd
);

input	[6:0]	idx;
output	[26:0]	base;
output	[17:0]	fd;
output	[10:0]	sd;


reg	[26:0]	base;
reg	[17:0]	fd;
reg	[10:0]	sd;

always @(idx)
  case (idx)
    7'h00   : begin  base = 27'h3fffffe; fd = 18'h3fff7; sd = 11'h7e8;  end
    7'h01   : begin  base = 27'h3f01fbf; fd = 18'h3f027; sd = 11'h7ba;  end
    7'h02   : begin  base = 27'h3e07e06; fd = 18'h3e0b4; sd = 11'h78d;  end
    7'h7b   : begin  base = 27'h01465fe; fd = 18'h10a4c; sd = 11'h10e;  end
    7'h7c   : begin  base = 27'h0104104; fd = 18'h10830; sd = 11'h10a;  end
    7'h7d   : begin  base = 27'h00c246d; fd = 18'h1061b; sd = 11'h108;  end
    7'h7e   : begin  base = 27'h0081020; fd = 18'h1040b; sd = 11'h104;  end
    7'h7f   : begin  base = 27'h0040404; fd = 18'h10202; sd = 11'h101;  end
  endcase

endmodule

