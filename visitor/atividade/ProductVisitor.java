public interface ProductVisitor {

    void visit(Book book);

    void visit(Electronics electronics);
}
