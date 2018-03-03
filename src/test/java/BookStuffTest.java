import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BookStuffTest {

    BookStuff bs;

    @Before
    public void setUp() throws Exception {
        bs = new BookStuff();

    }

    @After
    public void tearDown() throws Exception {
        bs = null;
    }

    @Test
    public void shouldAddANonExistingBook() {
        Optional<Book> nonExistingBook = bs.createBook("MyBook", "Holger", "1", "4567", 2018);
        assertEquals(bs.getAllBooks().size(), 3);
    }


    @Test
    public void shouldAddASecondCopyOfAnExistingBook() {
        Optional<Book> existingBook = bs.createBook("Greenbox", "Tim Mälzer", "2", "3442392438", 2012);
        assertEquals(bs.getAllBooks().size(), 3);
    }

    @Test
    public void shouldNotAddFirstCopyOfAnExistingBook() {
        Optional<Book> existingBook = bs.createBook("Greenbox", "Tim Mälzer", "1", "3442392438", 2012);
        assertEquals(bs.getAllBooks().size(), 2);

    }
}