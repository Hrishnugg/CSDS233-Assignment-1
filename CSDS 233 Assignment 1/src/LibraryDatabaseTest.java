import org.junit.Test;
import static org.junit.Assert.*;

public class LibraryDatabaseTest {

    @Test
    public void testConstructor() {
        LibraryDatabase library = new LibraryDatabase(10);
        assertNotNull(library);
        try {
            library = new LibraryDatabase(-1);
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException ex) {
            //received exception
        }
    }

    @Test
    public void testAdd() {
        LibraryDatabase library = new LibraryDatabase(3);
        Book book1 = new Book("Title1", "1234567891234", "Author1");
        library.add(book1);
        assertEquals(1, library.size());
        Book book2 = new Book("Title2", "1234567890123", "Author2");
        library.add(book2);
        assertEquals(2, library.size());
        // Test adding more elements than initial capacity.
        Book book3 = new Book("Title3", "1234567890124", "Author3");
        library.add(book3);
        assertEquals(3, library.size());
        Book book4 = new Book("Title4", "1234567890125", "Author4");
        library.add(book4);
        assertEquals(4, library.size());
        Book book5 = new Book("Title5", "1234567890126", "Author5");
        library.add(book5);
        assertEquals(5, library.size());
    }

    @Test
    public void testRemove() {
        LibraryDatabase library = new LibraryDatabase(10);
        // Test removing from empty database
        assertNull(library.remove("1234567890123"));
        // Test removing existing books from database.
        Book book1 = new Book("Title1", "1234567891234", "Author1");
        library.add(book1);
        Book book2 = new Book("Title2", "1234567890123", "Author2");
        library.add(book2);
        assertEquals(2, library.size());
        Book removed = library.remove("1234567891234");
        assertNotNull(removed);
        assertEquals(1, library.size());
        // Try a non-existent book.
        removed = library.remove("1234567891245");
        assertNull(removed);
    }

    @Test
    public void testSize() {
        LibraryDatabase library = new LibraryDatabase(10);
        assertEquals(0, library.size());
        Book book1 = new Book("Title1", "1234567891234", "Author1");
        Book book2 = new Book("Title2", "1234567890123", "Author2");
        library.add(book1);
        assertEquals(1, library.size());
        library.add(book2);
        assertEquals(2, library.size());
    }

    @Test
    public void testRandomSelection() {
        LibraryDatabase library = new LibraryDatabase(10);
        Book book1 = new Book("Title1", "1234567891234", "Author1");
        library.add(book1);
        Book book2 = new Book("Title2", "1234567890123", "Author2");
        library.add(book2);
        Book randomBook = library.randomSelection();
        assertNotNull(randomBook);
        assertTrue(randomBook.equals(book1) || randomBook.equals(book2));
    }

    @Test
    public void testFindByTitle() {
        LibraryDatabase library = new LibraryDatabase(10);
        Book book1 = new Book("Title1", "1234567891234", "Author1");
        library.add(book1);
        Book book2 = new Book("Title2", "1234567890123", "Author2");
        library.add(book2);
        Book found = library.findByTitle("Title1");
        assertNotNull(found);
        assertEquals("Title1", found.getTitle());
        found = library.findByTitle("Title3");
        assertNull(found);
    }

    @Test
    public void testFindByISBN() {
        LibraryDatabase library = new LibraryDatabase(10);
        Book book1 = new Book("Title1", "1234567891234", "Author1");
        library.add(book1);
        Book book2 = new Book("Title2", "1234567890123", "Author2");
        library.add(book2);
        Book found = library.findByISBN("1234567891234");
        assertNotNull(found);
        assertEquals("1234567891234", found.getISBN());
        found = library.findByISBN("1234567890123");
        assertNotNull(found);
        assertEquals("1234567890123", found.getISBN());
        found = library.findByISBN("12345678901245");
        assertNull(found);
    }

    @Test
    public void testGetAllByAuthor(){
        LibraryDatabase library = new LibraryDatabase(10);
        Book book1 = new Book("Title 1", "1234567894721", "Author 1");
        Book book2 = new Book("Title 2", "1234567899560", "Author 2");
        Book book3 = new Book("Title 3", "1234567897129", "Author 3");
        Book book4 = new Book("Title 4", "1234567890192", "Author 4");
        Book book5 = new Book("Title 5", "1234567899238", "Author 2");
        Book book6 = new Book("Title 6", "1234567891278", "Author 2");
        library.add(book1);
        library.add(book2);
        library.add(book3);
        library.add(book4);
        library.add(book5);
        library.add(book6);
        Book[] allByAuthor2 = {book6, book5, book2};
        assertEquals(library.getAllByAuthor("Author 2"), allByAuthor2);
        Book[] allByAuthor3 = {book3};
        assertEquals(library.getAllByAuthor("Author 3"), allByAuthor3);
        assertNull(library.getAllByAuthor("Author 5"));
    }

    @Test
    public void testRemoveDuplicates(){
        LibraryDatabase library = new LibraryDatabase(10);
        Book book1 = new Book("Title 1", "1234567894721", "Author 1");
        Book book2 = new Book("Title 2", "1234567899560", "Author 2");
        Book book3 = new Book("Title 3", "1234567897129", "Author 3");
        Book book4 = new Book("Title 4", "1234567890192", "Author 4");
        Book book5 = new Book("Title 5", "1234567899238", "Author 2");
        Book book6 = new Book("Title 6", "1234567891278", "Author 2");
        Book book7 = new Book("Title 7", "1234567894721", "Author 5");
        Book book8 = new Book("Title 8", "1234567894721", "Author 6");
        Book book9 = new Book("Title 9", "1234567897129", "Author 7");
        Book book10 = new Book("Title 10", "1234567893927", "Author 10");
        library.add(book1);
        library.add(book2);
        library.add(book3);
        library.add(book4);
        library.add(book5);
        library.add(book6);
        library.add(book7);
        library.add(book8);
        library.add(book9);
        library.add(book10);
        // The expected result should have the book which was added first among a set of duplicates.
        Book[] expectedResult = {book4, book6, book10, book1, book3, book5, book2};
        library.removeDuplicates();
        assertEquals(library.size(), 7);
        Book[] libArray = library.toArray();
        for (int i = 0; i < library.size(); i++) {
            assertEquals(libArray[i], expectedResult[i]);
        }
    }

    @Test
    public void testToArray(){
        LibraryDatabase library = new LibraryDatabase(10);
        Book book1 = new Book("Title 1", "1234567894721", "Author 1");
        Book book2 = new Book("Title 2", "1234567899560", "Author 2");
        Book book3 = new Book("Title 3", "1234567897129", "Author 3");
        Book book4 = new Book("Title 4", "1234567890192", "Author 4");
        Book book5 = new Book("Title 5", "1234567899238", "Author 2");
        Book book6 = new Book("Title 6", "1234567891278", "Author 2");
        Book book7 = new Book("Title 7", "1234567894721", "Author 5");
        Book book8 = new Book("Title 8", "1234567894721", "Author 6");
        Book book9 = new Book("Title 9", "1234567897129", "Author 7");
        Book book10 = new Book("Title 10", "1234567893927", "Author 10");
        library.add(book1);
        library.add(book2);
        library.add(book3);
        library.add(book4);
        library.add(book5);
        library.add(book6);
        library.add(book7);
        library.add(book8);
        library.add(book9);
        library.add(book10);
        assertEquals(library.size(), 10);
        Book[] libArray = library.toArray();
        assertEquals(libArray.length, 10);
        for (int i = 1; i < libArray.length; i++) {
            // Check that the array is sorted in ascending order.
            assertTrue(libArray[i].getNumericISBN() >= libArray[i-1].getNumericISBN());
        }
    }
}

