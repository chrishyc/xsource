package visitor;

public interface Visitor {
    String visitDot(Dot dot);
    
    String visitCompoundGraphic(CompoundShape cg);
}
