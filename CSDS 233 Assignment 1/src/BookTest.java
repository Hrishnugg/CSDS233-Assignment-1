import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void testValidBookConstructor() {
        String title = "Macbeth";
        String isbn = "1234567890123";
        String author = "William Shakespeare";
        Book book = new Book(title, isbn, author);
        assertEquals(title, book.getTitle());
        assertEquals(isbn, book.getISBN());
        assertEquals(author, book.getAuthor());
        assertEquals(1234567890123L, book.getNumericISBN());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBookConstructorInvalidIsbn() {
        String title = "Macbeth";
        String isbn = "123456789012";
        String author = "William Shakespeare";
        Book book = new Book(title, isbn, author);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBookConstructorNonNumericIsbn() {
        String title = "Macbeth";
        String isbn = "12345678901a3";
        String author = "William Shakespeare";
        Book book = new Book(title, isbn, author);
    }
}
