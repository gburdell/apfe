module foo();
output wire ww, ww2;
integer fives[4] = '{ 5, 10, 15, 20 };
integer threes[4] = ' { 3, 6, 9, 12 };
endmodule

module bar(
	input wire clk,
	output wire z,
	output reg x
);
endmodule
