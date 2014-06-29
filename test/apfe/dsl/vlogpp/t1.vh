// A comment

`ifndef  __t1_vh__
`define  __t1_vh__

`ifndef N
`define N 23
`endif

           `define add

`define max(a,b) ((a < b) ? b : a)

//make sure the positional parm replacement does right thing
`define min(aa,aab) ((aa > aab) ? aab : aa

            `define add(a,b,c=0,d=0,e=0,f=0,g=0,h=0,i=0) (a+b+c+d+e+f+g+h+i)

`m1(a,b, c)
/*comment*/
`m2/*foo*/(b,//bar
d)

module t1 (input [`N-1:0] a);
integer i = `add(4,5);
endmodule

`endif	//__t1_vh__
