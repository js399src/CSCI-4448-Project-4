public enum Size {
    // I wanted these enums to have a value, so I could compare them later
    // Good discussion of this at https://www.baeldung.com/java-enum-values
    TINY (1), SMALL (2), MEDIUM (3), LARGE (4), HUGE (5);
    public final int level;
    Size(int level) {
        this.level = level;
    }
}
