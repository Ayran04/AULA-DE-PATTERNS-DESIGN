public class Book implements Product {

    private final String title;
    private final int priceInCents;

    public Book(String title, int priceInCents) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (priceInCents <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.title = title;
        this.priceInCents = priceInCents;
    }

    public String getTitle() {
        return title;
    }

    public int getPriceInCents() {
        return priceInCents;
    }

    @Override
    public void accept(ProductVisitor visitor) {
        visitor.visit(this);
    }
}
