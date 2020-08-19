generator.sh
##1.需求:
code-generator项目用于json生成model类,mysql语句,接口类,为了方便执行,使用脚本执行项目
##2.方案:
将项目打包成jar文件并执行jar文件,通过传入的参数来区分执行model/api/db等模板
##3.实现:
1.语法:

命令:
cd
多入参解析:getopts 'n:c:' var
命令替换:`pwd`=$(pwd)
echo
exit 0(正常),exit 1(异常)

变量:
PROJECT_PATH=`pwd`
PROJECT_TARGET_PATH=`pwd`"/target/"
${PROJECT_PATH}
MVN_COMMAND=${MVN_COMMAND:-"install"} :后是默认值

数组:
IFS=","
arr=($DEFINITION_NAME)
${#arr[@]}
${arr[$i]}

资源重定向
标准输出重定向到/dev/null :  1 > /dev/null

表达式:
文件表达式
if [ -f  file ]    如果文件存在
if [ -d ...   ]    如果目录存在
if [ -s file  ]    如果文件存在且非空 
if [ -r file  ]    如果文件存在且可读
if [ -w file  ]    如果文件存在且可写
if [ -x file  ]    如果文件存在且可执行
if  [! -d "${}"]

If  [ $a = $b ]                 如果string1等于string2
                                字符串允许使用赋值号做等号
if  [ $string1 !=  $string2 ]   如果string1不等于string2       
if  [ -n $string  ]             如果string 非空(非0），返回0(true)  
if  [ -z $string  ]             如果string 为空
if  [ $sting ]                  如果string 非空，返回0 (和-n类似)    



流程控制:
```
while 表达式 do
done
```
```
case 值 in
n)
c)
?)
esac
```
```
if 表达式
then
fi
```