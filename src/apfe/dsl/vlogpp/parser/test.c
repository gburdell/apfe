/*block comment
next line
*/  //foobar  
/**line***/
/*missing
//line
*/

#define A /*
cross line
*/ 4
#define B(_p1,_p2) _p1 + _p2

int foo() {return A + 
	B(5,
	6);}
