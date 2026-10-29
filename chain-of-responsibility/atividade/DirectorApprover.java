public class DirectorApprover extends ExpenseApprover {

    private static final int LIMIT_IN_CENTS = 1000000;

    @Override
    protected int getLimitInCents() {
        return LIMIT_IN_CENTS;
    }

    @Override
    protected String getName() {
        return "Director";
    }
}
