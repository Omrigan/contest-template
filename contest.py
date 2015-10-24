#!/usr/bin/python3
import argparse, os, shutil, task

presets = {
    #name readtype Main needed numprob
    "acmp":( 1, True, 4),
    "cf":(2, False, 6)
}
defaults = (0, False, 4)
parser = argparse.ArgumentParser()
parser.add_argument("-t", "--type", choices=[a for a in presets],
                    help="contest type")
parser.add_argument("-c", "--count", type=int, help="number of problems")
parser.add_argument("-r", "--readtype", choices=range(3),
                    type=int, help="read type")
parser.add_argument("-m", "--mainclass", action="store_true", help="read type")
parser.add_argument("name", help="name of contest")
args = parser.parse_args()

rt, main, count = defaults
name = args.name
if args.type:
    rt, main, count = presets[args.type]
if args.readtype:
    rt = args.readtype
if args.count:
    count = args.count
if args.mainclass:
    main = args.mainclass

name = name[0].upper()+name[1:]
shutil.rmtree(name, ignore_errors=True)
os.makedirs(name)
for i in range(count):
    task.createTask(name + "_" + chr(ord('A') + i),main,rt, name)
