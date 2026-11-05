public class ProductReportVisitor implements ProductVisitor {

    @Override
    public void visit(Book book) {
        System.out.println("Book: " + book.getTitle()
                + " - cents: " + book.getPriceInCents());
    }

    @Override
    public void visit(Electronics electronics) {
        System.out.println("Electronics: " + electronics.getModel()
                + " - cents: " + electronics.getPriceInCents());
    }
}
