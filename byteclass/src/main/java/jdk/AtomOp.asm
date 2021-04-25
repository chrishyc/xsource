Classfile /Users/chris/workspace/xsource/byteclass/src/main/java/jdk/AtomOp.class
  Last modified 2021-4-8; size 285 bytes
  MD5 checksum 4f8febeb646f26f36c68bb3bcd34b1b6
  Compiled from "AtomOp.java"
public class jdk.AtomOp
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #4.#14         // java/lang/Object."<init>":()V
   #2 = Fieldref           #3.#15         // jdk/AtomOp.i:I
   #3 = Class              #16            // jdk/AtomOp
   #4 = Class              #17            // java/lang/Object
   #5 = Utf8               i
   #6 = Utf8               I
   #7 = Utf8               <init>
   #8 = Utf8               ()V
   #9 = Utf8               Code
  #10 = Utf8               LineNumberTable
  #11 = Utf8               test
  #12 = Utf8               SourceFile
  #13 = Utf8               AtomOp.java
  #14 = NameAndType        #7:#8          // "<init>":()V
  #15 = NameAndType        #5:#6          // i:I
  #16 = Utf8               jdk/AtomOp
  #17 = Utf8               java/lang/Object
{
  int i;
    descriptor: I
    flags:

  public jdk.AtomOp();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: aload_0
         5: iconst_0
         6: putfield      #2                  // Field i:I
         9: return
      LineNumberTable:
        line 3: 0
        line 4: 4

  public void test();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=3, locals=1, args_size=1
         0: aload_0
         1: dup
         2: getfield      #2                  // Field i:I
         5: iconst_1
         6: iadd
         7: putfield      #2                  // Field i:I
        10: return
      LineNumberTable:
        line 7: 0
        line 8: 10
}
SourceFile: "AtomOp.java"
