public class TotalPriceVisitor implements ProductVisitor {

    private long totalInCents;

    @Override
    public void visit(Book book) {
        totalInCents += book.getPriceInCents();
    }

    @Override
    public void visit(Electronics electronics) {
        totalInCents += electronics.getPriceInCents();
    }

    public long getTotalInCents() {
        return totalInCents;
    }
}
