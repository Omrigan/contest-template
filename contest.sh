#!/bin/bash
function sedeasy {
  sed -i "s/$(echo $1 | sed -e 's/\([[\/.*]\|\]\)/\\&/g')/$(echo $2 | sed -e 's/[\/&]/\\&/g')/g" $3
}
function task (){
contestname=$1
taskname=$2
classname=$3
rm -rf "$contestname/$taskname"
cp -r ./Template "$contestname/$taskname"
cd "$contestname/$taskname"
mv "template.in" "${taskname,,}.in"
mv "template.out" "${taskname,,}.out"
mv "Template.iml" "$taskname.iml"
sed -i "s/?taskname?/$taskname/g" src/Template.java
sed -i "s/?classname?/$classname/g" src/Template.java
sed -i "s/?readtype?/$readtype/g" src/Template.java
mv "src/Template.java" "src/$taskname.java"
cd ../..
sedeasy "</modules>" \
"<module fileurl=\"file://\$PROJECT_DIR$/$contestname/$taskname/$taskname.iml\" filepath=\"\$PROJECT_DIR$/$contestname/$taskname/$taskname.iml\" /> \
</modules>" .idea/modules.xml
}
chr() {
  printf \\$(printf '%03o' $1)
}
contestname=$1
count=$2
type=$3
case "$type" in

"cf")
$readtype="2"
;;

"acmp")
$readtype="1"
;;
*)
;;

esac

rm -rf $contestname
mkdir $contestname
for i in $(seq 1 $count);
do echo $i;
read taskname
if [[ $taskname =~ [A-Za-z]+ ]];
then
  echo "OK"
else
  let "t=(64+$i)"
  taskname=$(chr $t)
  echo "use $taskname"
fi
task $contestname $taskname $taskname $readtype
done
