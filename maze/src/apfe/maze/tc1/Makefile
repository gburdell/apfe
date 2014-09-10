FLEX ?= jflex
TARGET ?= SvScanner.java

SvScanner.java: sv2009.flex
	${FLEX} --nobak ${<}

.PHONY: clean
clean:
	-rm -f ${TARGET}
