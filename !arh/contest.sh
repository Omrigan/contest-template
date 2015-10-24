#!/bin/bash
chr() {
  printf \\$(printf '%03o' $1)
}

count="5"
readtype="0"


while [[ $# > 1 ]]
do
key="$1"
case $key in
    -c|--count)
    count="$2"
    shift # past argument
    ;;
    -t|--type)
    type="$2"
    shift # past argument
    ;;
    -rt|--readtype)
    readtype="$2"
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
  contestname=$1
fi

case "$type" in
"cf")
readtype="2"
;;

"acmp")
readtype="1"
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
  taskname="$contestname$(chr $t)"
  echo "use $taskname"
fi
!arh/task.sh -c $contestname -rt $readtype  $taskname
done
