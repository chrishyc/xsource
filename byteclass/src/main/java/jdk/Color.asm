Classfile /Users/chris/workspace/xsource/byteclass/src/main/java/jdk/Color.class
  Last modified 2021-4-8; size 723 bytes
  MD5 checksum 85414be2623ebbd7d017689ed40f135a
  Compiled from "Color.java"
public final class jdk.Color extends java.lang.Enum<jdk.Color>
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_FINAL, ACC_SUPER, ACC_ENUM
Constant pool:
   #1 = Fieldref           #4.#29         // jdk/Color.$VALUES:[Ljdk/Color;
   #2 = Methodref          #30.#31        // "[Ljdk/Color;".clone:()Ljava/lang/Object;
   #3 = Class              #14            // "[Ljdk/Color;"
   #4 = Class              #32            // jdk/Color
   #5 = Methodref          #10.#33        // java/lang/Enum.valueOf:(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;
   #6 = Methodref          #10.#34        // java/lang/Enum."<init>":(Ljava/lang/String;I)V
   #7 = String             #11            // GREEN
   #8 = Methodref          #4.#34         // jdk/Color."<init>":(Ljava/lang/String;I)V
   #9 = Fieldref           #4.#35         // jdk/Color.GREEN:Ljdk/Color;
  #10 = Class              #36            // java/lang/Enum
  #11 = Utf8               GREEN
  #12 = Utf8               Ljdk/Color;
  #13 = Utf8               $VALUES
  #14 = Utf8               [Ljdk/Color;
  #15 = Utf8               values
  #16 = Utf8               ()[Ljdk/Color;
  #17 = Utf8               Code
  #18 = Utf8               LineNumberTable
  #19 = Utf8               valueOf
  #20 = Utf8               (Ljava/lang/String;)Ljdk/Color;
  #21 = Utf8               <init>
  #22 = Utf8               (Ljava/lang/String;I)V
  #23 = Utf8               Signature
  #24 = Utf8               ()V
  #25 = Utf8               <clinit>
  #26 = Utf8               Ljava/lang/Enum<Ljdk/Color;>;
  #27 = Utf8               SourceFile
  #28 = Utf8               Color.java
  #29 = NameAndType        #13:#14        // $VALUES:[Ljdk/Color;
  #30 = Class              #14            // "[Ljdk/Color;"
  #31 = NameAndType        #37:#38        // clone:()Ljava/lang/Object;
  #32 = Utf8               jdk/Color
  #33 = NameAndType        #19:#39        // valueOf:(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;
  #34 = NameAndType        #21:#22        // "<init>":(Ljava/lang/String;I)V
  #35 = NameAndType        #11:#12        // GREEN:Ljdk/Color;
  #36 = Utf8               java/lang/Enum
  #37 = Utf8               clone
  #38 = Utf8               ()Ljava/lang/Object;
  #39 = Utf8               (Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;
{
  public static final jdk.Color GREEN;
    descriptor: Ljdk/Color;
    flags: ACC_PUBLIC, ACC_STATIC, ACC_FINAL, ACC_ENUM

  public static jdk.Color[] values();
    descriptor: ()[Ljdk/Color;
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=1, locals=0, args_size=0
         0: getstatic     #1                  // Field $VALUES:[Ljdk/Color;
         3: invokevirtual #2                  // Method "[Ljdk/Color;".clone:()Ljava/lang/Object;
         6: checkcast     #3                  // class "[Ljdk/Color;"
         9: areturn
      LineNumberTable:
        line 3: 0

  public static jdk.Color valueOf(java.lang.String);
    descriptor: (Ljava/lang/String;)Ljdk/Color;
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=1, args_size=1
         0: ldc           #4                  // class jdk/Color
         2: aload_0
         3: invokestatic  #5                  // Method java/lang/Enum.valueOf:(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;
         6: checkcast     #4                  // class jdk/Color
         9: areturn
      LineNumberTable:
        line 3: 0

  static {};
    descriptor: ()V
    flags: ACC_STATIC
    Code:
      stack=4, locals=0, args_size=0
         0: new           #4                  // class jdk/Color
         3: dup
         4: ldc           #7                  // String GREEN
         6: iconst_0
         7: invokespecial #8                  // Method "<init>":(Ljava/lang/String;I)V
        10: putstatic     #9                  // Field GREEN:Ljdk/Color;
        13: iconst_1
        14: anewarray     #4                  // class jdk/Color
        17: dup
        18: iconst_0
        19: getstatic     #9                  // Field GREEN:Ljdk/Color;
        22: aastore
        23: putstatic     #1                  // Field $VALUES:[Ljdk/Color;
        26: return
      LineNumberTable:
        line 4: 0
        line 3: 13
}
Signature: #26                          // Ljava/lang/Enum<Ljdk/Color;>;
SourceFile: "Color.java"
