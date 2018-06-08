#!/bin/bash

currentDir=`/bin/pwd`
className=SecondPriceAuction
fileName=$className.class
classFile=./target/classes/wifeofxardas/com/github/$fileName

cp $classFile ../neo-compiler/neoj
cd ../neo-compiler/neoj
dotnet run $fileName

cp ./$className.avm $currentDir