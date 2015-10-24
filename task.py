#!/usr/bin/python3

import argparse, os, shutil, fileinput

def createTask(taskname, main, readtype, contestname):
    if readtype==None:
        readtype = 0
    if contestname!="":
        contestname+="/"
    else:
        contestname = "./"
    shutil.rmtree(contestname+taskname, ignore_errors=True)
    shutil.copytree("./Template", contestname+taskname)
    os.chdir(contestname)
    os.chdir(taskname)
    shutil.move("template.in", taskname + ".in")
    shutil.move("template.out", taskname + ".out")
    shutil.move("Template.iml", taskname + ".iml")
    if main:
        classname = "Main"
    else:
        classname = taskname
    for lines in fileinput.FileInput("src/Template.java", inplace=1): ## edit file in place
        lines = lines.replace("%taskname%",taskname)
        lines = lines.replace("%classname%",classname)
        lines = lines.replace("%readtype%",str(readtype))
        print(lines, end='')
    shutil.move("src/Template.java", "src/" + classname+ ".java")
    os.chdir("..")
    if contestname!="":
        os.chdir("..")

    modulestring = \
'<module fileurl="file://$PROJECT_DIR$/?contest{0}/{0}.iml" filepath="\$PROJECT_DIR$/?contest{0}/{0}.iml" />'.format(taskname)
    if contestname!="":
        modulestring = modulestring.replace('?contest', contestname+"/")
    else:
        modulestring = modulestring.replace('?contest', "")

    for lines in fileinput.FileInput(".idea/modules.xml", inplace=1): ## edit file in place
        lines = lines.replace(modulestring,"").replace("</modules>", modulestring + "\n</modules>")
        print(lines, end='')

if __name__=="__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("-r", "--readtype", choices=range(3),
                        type=int, help="read type")
    parser.add_argument("-m", "--mainclass", action="store_true", help="read type")
    parser.add_argument("name", help="name of task")
    args = parser.parse_args()
    args.name = args.name[0].upper()+args.name[1:]

    createTask(args.name, args.mainclass, args.readtype, "tasks")



