#!/usr/bin/env bash
function sedeasy {
  sed -i "s/$(echo $1 | sed -e 's/\([[\/.*]\|\]\)/\\&/g')/$(echo $2 | sed -e 's/[\/&]/\\&/g')/g" $3
}
contestname="tasks"
readtype="0"
taskname="newtask"
while [[ $# > 1 ]]
do
key="$1"
case $key in
    -c|--contest)
    contestname="$2"
    shift # past argument
    ;;

    -rt|--readtype)
    readtype="$2"
    shift;
    ;;
    -t|--type)
    type="$2"
    shift;
    ;;
    -h|--help)
    echo "Usage: contest.sh [-c count] [-t contesttype] [-rt readtype] name"
    exit 0;
    ;;
    *)
            # unknown option
    ;;
esac
shift # past argument or value
done

if [[ -n $1 ]]; then
  taskname=$1
fi

classname=$taskname

rm -rf "$contestname/$taskname"
cp -r ./Template "$contestname/$taskname"
cd "$contestname/$taskname"
mv "template.in" "${taskname,,}.in"
mv "template.out" "${taskname,,}.out"
mv "Template.iml" "$taskname.iml"
sed -i "s/?taskname?/$taskname/g" src/Template.java
sed -i "s/?classname?/$classname/g" src/Template.java
sed -i "s/?readtype?/$readtype/g" src/Template.java
mv "src/Template.java" "src/$classname.java"
cd ../..
module="<module fileurl=\"file://\$PROJECT_DIR$/$contestname/$taskname/$taskname.iml\" filepath=\"\$PROJECT_DIR$/$contestname/$taskname/$taskname.iml\" /> \
</modules>"
sedeasy "$module" "" ./.idea/modules.xml
sedeasy "</modules>"  "$module" .idea/modules.xml