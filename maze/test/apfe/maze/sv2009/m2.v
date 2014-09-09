module m2(input clk, rst, output reg [3:0] q);
	always @(posedge clk)
		q <= (rst) ? 0 : q+1;
endmodule
