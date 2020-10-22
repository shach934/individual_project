package individual_project;

enum Status {
    /**
     * enum for task status.
     * It contains four different task status, DOING, PENDING, ASAP, DONE. to follow the enum convention.
     * It accept the case insensitive text, convert it to status.
     * It could return a normal text(Doing, Pending, ASAP, Done) with toString().
     */
    DOING("Doing"),
    PENDING("Pending"),
    ASAP("ASAP"),
    DONE("Done");
    private String text;

    Status(String text) {
        this.text = text;
    }
    public String getText() {
        return this.text;
    }

    public static Status fromString(String text) {
        for (Status b : Status.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        // The \u0001B[31m makes the text in command window RED, \u0001B[31m makes GREEN.
        System.out.println("\u001B[31m" + "No Status with text " + "\u001B[32m" + text + "\u001B[31m" + " found" + "\u001B[0m");
        return null;
    }
}