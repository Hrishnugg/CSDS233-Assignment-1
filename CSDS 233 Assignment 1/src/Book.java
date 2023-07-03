/**
 * This class represents a book in the library database.
 */
public class Book {
    private String title = "";
    private String isbn = "";
    private String author = "";
    // Numeric ISBN is used to make more efficient comparison between books.
    private long numericIsbn = 0;

    /**
     * Constructor of the Book.
     * @param newTitle Title of the book.
     * @param newIsbn ISBN of the book. Expected to be 13 digit numeric.
     * @param newAuthor Author of the book.
     * @throws IllegalArgumentException if the given ISBN is not 13 digit numberic.
     */
    public Book(String newTitle, String newIsbn, String newAuthor) throws IllegalArgumentException {
        title = newTitle;
        // Check to see if ISBN is valid. (13 digit numeric code)
        if (!validateISBN(newIsbn)){
            throw new IllegalArgumentException("The ISBN is not a 13 digit numeric value!");
        }
        isbn = newIsbn;
        numericIsbn = Long.parseLong(newIsbn);
        author = newAuthor;
    }

    /**
     *
     * @return Title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return ISBN of the book.
     */
    public String getISBN() {
        return isbn;
    }

    /**
     *
     * @return Numeric value of the ISBN of the book. Used for more efficient comparisons.
     */
    public long getNumericISBN(){
        return numericIsbn;
    }

    /**
     *
     * @return Author of the book.
     */
    public String getAuthor() {
        return author;
    }

    /**
     *
     * @param isbn ISBN to be validated. It should be 13 digit numeric.
     * @return true for valid ISBN, else false.
     */
    private boolean validateISBN(String isbn){
        // Check to see if ISBN string is 13 characters long.
        if (isbn.length() != 13){
            return false;
        }
        for (int i = 0; i < isbn.length(); i++) {
            // Check to see if all characters in ISBN are digits.
            if (isbn.charAt(i) < '0' || isbn.charAt(i) > '9'){
                return false;
            }
        }
        return true;
    }
}
