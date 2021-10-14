Compiled from "HashMapInnerClass.java"
public class inner.HashMapInnerClass {
  public inner.HashMapInnerClass();
    Code:
       0: aload_0
       1: invokespecial #2                  // Method java/lang/Object."<init>":()V
       4: aload_0
       5: ldc           #3                  // String leak_memory
       7: putfield      #1                  // Field outerRef:Ljava/lang/String;
      10: aload_0
      11: new           #4                  // class inner/HashMapInnerClass$1
      14: dup
      15: aload_0
      16: invokespecial #5                  // Method inner/HashMapInnerClass$1."<init>":(Linner/HashMapInnerClass;)V
      19: putfield      #6                  // Field map:Ljava/util/Map;
      22: return

  static java.lang.String access$000(inner.HashMapInnerClass);
    Code:
       0: aload_0
       1: getfield      #1                  // Field outerRef:Ljava/lang/String;
       4: areturn
}
