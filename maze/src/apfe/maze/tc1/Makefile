FLEX ?= jflex
TARGET ?= Tc1Scanner.java

${TARGET}: tc1.flex
	${FLEX} --nobak ${<}

.PHONY: clean
clean:
	-rm -f ${TARGET}
