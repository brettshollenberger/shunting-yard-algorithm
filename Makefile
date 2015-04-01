JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        Stack.java \
        Queue.java \
        Operator.java \
				Polish.java \
        Main.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
