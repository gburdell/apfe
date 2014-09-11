FLEX ?= jflex
TARGET ?= SvScanner.java

${TARGET}: sv2009.flex
	${FLEX} --nobak ${<}

.PHONY: clean
clean:
	-rm -f ${TARGET}
