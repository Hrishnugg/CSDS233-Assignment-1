/**
 * This class implements a database of Books.
 * CSDS 233, Assignment 1
 * Author: Hrishikesh Hari
 */
public class LibraryDatabase {
    private int capacity = 0;
    private Book[] database;
    private int size = 0;

    /**
     * Constructs the Library database.
     * @param initialCapacity Expected number of books for which space is reserved at start.
     * @throws IllegalArgumentException if initialCapacity is negative.
     */
    public LibraryDatabase(int initialCapacity) throws IllegalArgumentException {
        if (initialCapacity < 0){
            throw new IllegalArgumentException("Capacity cannot be negative!");
        }
        capacity = initialCapacity;
        database = new Book[capacity];
    }

    /**
     * Adds a book to the database. Resizes the database if necessary.
     * @param book The book to be added.
     */
    public void add(Book book){
        // Resize the database if we are at max capacity.
        if (size == capacity){
            //double the database size everytime, avoid 0 capacity edge case
            int newCapacity = (capacity+1)*2;
            Book[] newDB = new Book[newCapacity];
            for (int i = 0; i < size; i++) {
                newDB[i] = database[i];
            }
            capacity = newCapacity;
            database = newDB;
        }
        // Find position to insert. We start with size so that if the book
        // has a greater ISBN than all existing books, we will insert at the end.
        int position = size;
        for (int i = 0; i < size; i++) {
            // If we find a book with greater ISBN, we have the position to insert.
            if (database[i].getNumericISBN() > book.getNumericISBN()){
                position = i;
                break;
            }
        }
        // Move later elements after position to make space for the new book.
        for (int i = size-1; i >= position; i--) {
            database[i+1] = database[i];
        }
        database[position] = book;
        size++;
    }

    /**
     * Removes the book with matching ISBN from the database.
     * @param isbn ISBN number of the book to be removed.
     * @return The book which was removed from the database. Returns null if none was removed.
     */
    public Book remove(String isbn){
        Book removed = null;
        for (int i = 0; i < size; i++) {
            // Find matching book by ISBN comparison.
            if (database[i].getISBN().equals(isbn)) {
                int position = i;
                removed = database[i];
                // Remove this element and break out of the loop.
                for (int j = position; j < size-1; j++) {
                    database[j] = database[j+1];
                }
                size--;
                break;
            }
        }
        return removed;
    }

    /**
     *
     * @return Number of books in the database.
     */
    public int size(){
        return size;
    }

    /**
     *
     * @return A randomly selected book from the database.
     */
    public Book randomSelection(){
        // Check for empty database.
        if (size == 0){
            return null;
        }
        double rand = Math.random()*size;
        int index = (int) Math.floor(rand);
        return database[index];
    }

    /**
     * Finds the book from the database which matches the given title.
     * @param title Title of the book we are searching for.
     * @return The book with matching title, if found in the database, else null.
     */
    public Book findByTitle(String title){
        // Linear search for matching title.
        for (int i = 0; i < size; i++) {
            // Check if the title matches.
            if (database[i].getTitle().equals(title)){
                return database[i];
            }
        }
        return null;
    }

    /**
     * Finds a book with matching ISBN number from teh database.
     * @param isbn ISBN number for the book we are looking for.
     * @return A Book with matching ISBN number, if found, else null.
     */
    public Book findByISBN(String isbn){
        // Perform binary search O(logN).
        // Using numeric ISBN makes comparisons more efficient.
        long numericIsbn = Long.parseLong(isbn);
        return binSearch(numericIsbn, 0, size-1);
    }

    /**
     * Performs Binary Search in the database with given ISBN.
     * @param numericISBN  ISBN number of the book we are looking for as a long.
     * @param left Left bound of the sub-array we are searching in.
     * @param right Right bound of the sub-array we are searching in.
     * @return The Book with matching ISBN, if found, else null.
     */
    private Book binSearch(long numericISBN, int left, int right){
        // Check to see if we have a null sub-array.
        if (left > right){
            return null;
        }
        int mid = (left + right)/2;
        // Check to see if middle element matches.
        if (database[mid].getNumericISBN() == numericISBN){
            return database[mid];
        }
        // If middle element's ISBN is greater than given ISBN, we recursively search in the left sub-array.
        // Else, we recursively search in the right sub-array.
        if (database[mid].getNumericISBN() > numericISBN){
            return binSearch(numericISBN, left, mid-1);
        }
        else return binSearch(numericISBN, mid+1, right);
    }

    /**
     * Finds all books for the given author.
     * @param author Author we are searching for in the database.
     * @return Array of all the books corresponding to the given author.
     */
    public Book[] getAllByAuthor(String author){
        int[] authorIndices = new int[size];
        int count = 0;
        // We collect all the positions in database which have books with the matching author.
        for (int i = 0; i < size; i++) {
            // Check to see if the current book's author matches the given author.
            if (database[i].getAuthor().equals(author)){
                authorIndices[count] = i;
                count++;
            }
        }
        // Check if we have at least one match.
        if (count == 0){
            return null;
        }
        Book[] allByAuthor = new Book[count];
        // Now we iterate through the positions we stored earlier, and fill in the return array.
        for (int i = 0; i < count; i++) {
            allByAuthor[i] = database[authorIndices[i]];
        }
        return allByAuthor;
    }

    /**
     * Removes all books with duplicate ISBN numbers from the database.
     * When we encounter duplicates, among them, only the first one added to the
     * database is preserved.
     */
    public void removeDuplicates(){
        // Check for empty database.
        if (size == 0){
            return;
        }
        Book[] newDB = new Book[size];
        int newDBIndex = 0;
        newDB[0] = database[0];
        // Here we create a new array and add only non-duplicate books to the new array.
        // Since the database is sorted by ascending ISBN, all duplicates will appear together.
        for (int i = 1; i < size; i++) {
            // Pick only the first book from a set of duplicates in the database.
            if (database[i].getNumericISBN() != newDB[newDBIndex].getNumericISBN()){
                newDBIndex++;
                newDB[newDBIndex] = database[i];
            }
        }
        // Now set the new array to be the database.
        database = newDB;
        size = newDBIndex+1;
        capacity = size;
    }

    /**
     *
     * @return The entire database contents as an array, sorted in ascending order by ISBN.
     */
    public Book[] toArray(){
        return database;
    }
}