package jvm;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner6;

import static javax.lang.model.element.ElementKind.ENUM_CONSTANT;
import static javax.lang.model.element.ElementKind.INTERFACE;
import static javax.lang.model.element.ElementKind.METHOD;
import static javax.tools.Diagnostic.Kind.WARNING;


public class T02_compile_04_annotations_nameChecker {
  private final Messager messager;
  NameCheckScanner nameCheckScanner = new NameCheckScanner();

  T02_compile_04_annotations_nameChecker(ProcessingEnvironment processsingEnv) {
    this.messager = processsingEnv.getMessager();
  }

  /**
   * 对Java程序命名进行检查，根据《Java语言规范》第三版第6.8节的要求，Java程序命名应当符合下列格式: *
   * <ul>
   * <li>类或接口:符合驼式命名法，首字母大写。
   * <li>方法:符合驼式命名法，首字母小写。
   * <li>字段:
   * <ul>
   * <li>类、实例变量: 符合驼式命名法，首字母小写。
   * <li>常量: 要求全部大写。
   * </ul>
   * </ul>
   */
  public void checkNames(Element element) {
    nameCheckScanner.scan(element);
  }

  /**
   * 名称检查器实现类，继承了JDK 6中新提供的ElementScanner6<br> * 将会以Visitor模式访问抽象语法树中的元素
   */
  private class NameCheckScanner extends ElementScanner6<Void, Void> {
    /**
     * 此方法用于检查Java类
     */
    @Override
    public Void visitType(TypeElement e, Void p) {
      scan(e.getTypeParameters(), p);
      checkCamelCase(e, true);
      super.visitType(e, p);

      return null;
    }

    /**
     * 检查方法命名是否合法
     */
    @Override
    public Void visitExecutable(ExecutableElement e, Void p) {
      if (e.getKind() == METHOD) {
        Name name = e.getSimpleName();
        if (name.contentEquals(e.getEnclosingElement().getSimpleName()))
          messager.printMessage(WARNING, "一个普通方法 “" + name + "”不应当与类名重复，避免与构造函数产生混淆");
      }
      super.visitExecutable(e, p);
      return null;
    }

    /**
     * 检查变量命名是否合法
     */
    @Override
    public Void visitVariable(VariableElement e, Void p) {
      // 如果这个Variable是枚举或常量，则按大写命名检查，否则按照驼式命名法规则检查
      if (e.getKind() == ENUM_CONSTANT || e.getConstantValue() != null || heuristicallyConstant(e))
        checkAllCaps(e);
      else
        checkCamelCase(e, false);
      return null;
    }

    /**
     * 判断一个变量是否是常量
     */
    private boolean heuristicallyConstant(VariableElement e) {
      if (e.getEnclosingElement().getKind() == INTERFACE)
        return true;
      return false;
    }

    /**
     * 检查传入的Element是否符合驼式命名法，如果不符合，则输出警告信息
     */
    private void checkCamelCase(Element e, boolean initialCaps) {

    }

    /**
     * 大写命名检查，要求第一个字母必须是大写的英文字母，其余部分可以是下划线或大写字母
     */
    private void checkAllCaps(Element e) {
      String name = e.getSimpleName().toString();
      boolean conventional = true;
      int firstCodePoint = name.codePointAt(0);
      if (!Character.isUpperCase(firstCodePoint)) conventional = false;
      else {
        boolean previousUnderscore = false;
        int cp = firstCodePoint;
        for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
          cp = name.codePointAt(i);
          if (cp == (int) '_') {
            if (previousUnderscore) {
              conventional = false;
              break;
            }
            previousUnderscore = true;
          } else {
          }
        }
        previousUnderscore = false;
        if (!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
          conventional = false;
        }
      }
      if (!conventional)
        messager.printMessage(WARNING, "常量“" + name + "”应当全部以大写字母或下划线命名，并且以字母开头", e);
    }
  }
}
