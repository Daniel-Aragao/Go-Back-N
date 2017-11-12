#!/bin/bash

javac trabalho.java

cont=0;
for i in 0 
do
   java trabalho "entrada0$i"

   if diff -q saida saida0$i; then
	echo "Diff saida0"$i;	   
	cont=$((cont+2));
   fi		   
done
echo "Nota: $cont"
