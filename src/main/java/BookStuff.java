
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class BookStuff {

    BookRepository bookRepository = new BookRepository();

    public Optional<Book> createBook(String title,
                                     String author,
                                     String edition,
                                     String isbn,
                                     int yearOfPublication) {
        Book book = new Book(title, author, edition, isbn, yearOfPublication);

        Optional<Book> bookFromRepo = bookRepository.findTopByIsbn(isbn);

        Book newBook = bookFromRepo.orElseGet(() -> bookRepository.save(book));

        Optional<Book> exisitingBookDifferentEdition = bookFromRepo
                .filter(b -> !b.isSameCopy(book))
                .map(b -> bookRepository.save(b));

        return Optional.of(exisitingBookDifferentEdition).orElse(Optional.of(newBook));
    }

    public List<Book> getAllBooks() {
        return bookRepository.books;
    }
}

class Book {

    public final String title;
    public final String author;
    public final String edition;
    public final String isbn;
    public final int yearOfPublication;

    public Book(String title, String author, String edition, String isbn, int yearOfPublication) {

        this.title = title;
        this.author = author;
        this.edition = edition;
        this.isbn = isbn;
        this.yearOfPublication = yearOfPublication;
    }

    public boolean isSameCopy(Book book) {
        return this.equals(book);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return yearOfPublication == book.yearOfPublication &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(edition, book.edition) &&
                Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, edition, isbn, yearOfPublication);
    }
}

class BookRepository {

    static Book book1 = new Book("Essen ist fertig!", "Jamie Oliver", "1", "3831007292", 2005);
    static Book book2 = new Book("Greenbox", "Tim Mälzer", "1", "3442392438", 2012);
    public List<Book> books = new ArrayList<Book>(Arrays.asList(book1, book2));

    public Optional<Book> findTopByIsbn(String isbn) {
        return books.stream().filter(b -> b.isbn.equals(isbn)).findFirst();
    }

    public Book save(Book book) {
        books.add(book);
        return book;
    }
}
