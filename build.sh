#!/bin/bash

currentDir=`/bin/pwd`
className=SecondPriceAuction
fileName=$className.class
classFile=./target/classes/wifeofxardas/com/github/$fileName
echo $currentDir
cp $classFile ../neo-compiler/neoj
cd ../neo-compiler/neoj
dotnet run $fileName

echo $className.avm
cp ./$className.avm $currentDir