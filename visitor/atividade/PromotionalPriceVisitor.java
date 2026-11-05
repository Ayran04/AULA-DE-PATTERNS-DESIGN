public class PromotionalPriceVisitor implements ProductVisitor {

    private static final int BOOK_DISCOUNT_PERCENT = 10;
    private static final int ELECTRONICS_DISCOUNT_PERCENT = 5;

    @Override
    public void visit(Book book) {
        System.out.println(book.getTitle() + " - promotional cents: "
                + applyDiscount(book.getPriceInCents(), BOOK_DISCOUNT_PERCENT));
    }

    @Override
    public void visit(Electronics electronics) {
        System.out.println(electronics.getModel() + " - promotional cents: "
                + applyDiscount(electronics.getPriceInCents(), ELECTRONICS_DISCOUNT_PERCENT));
    }

    private static int applyDiscount(int priceInCents, int discountPercent) {
        long discounted = (long) priceInCents * (100 - discountPercent) / 100;
        return (int) discounted;
    }
}
