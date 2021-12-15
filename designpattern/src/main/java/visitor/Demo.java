package visitor;


/**
 * https://refactoringguru.cn/design-patterns/visitor
 */
public class Demo {
    public static void main(String[] args) {
        Dot dot = new Dot(1, 10, 55);
        CompoundShape compoundShape = new CompoundShape(4);
        compoundShape.add(dot);
        
        CompoundShape c = new CompoundShape(5);
        c.add(dot);
        compoundShape.add(c);
        
        export(compoundShape);
    }
    
    private static void export(Shape... shapes) {
        XMLExportVisitor exportVisitor = new XMLExportVisitor();
        System.out.println(exportVisitor.export(shapes));
    }
}
