import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = Library.loadLibraryData("data.ser");;
        Scanner scanner = new Scanner(System.in);



        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Display all books");
            System.out.println("2. Find book by title");
            System.out.println("3. Find book by author");
            System.out.println("4. Borrow a book");
            System.out.println("5. Return a book");
            System.out.println("6. Save Library Data in a data.ser file");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    // Display all books
                    System.out.println("All Books:");
                    library.printBooks(library.getAllBooks());
                    break;
                case 2:
                    // Find book by title
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    Book foundBook=library.getBookByTite(title);
                    library.printBook(foundBook);
                    break;
                case 3:
                    // Find book by author
                    System.out.print("Enter author name: ");
                    String author = scanner.nextLine();
                    List<Book> authorBook=library.getBooksByAuthor(author);
                    library.printBooks(authorBook);
                    break;
                case 4:
                    // Borrow a book
                    System.out.print("Enter your email: ");
                    String email = scanner.nextLine();
                    if(!library.isBorrower(email)){
                        System.out.print("You are not one of our borrowers!!!");
                        break;
                    }
                    System.out.print("Enter book title to borrow: ");
                    title = scanner.nextLine();
                    library.borrowBook(email, title);
                    System.out.println(email+" Borrowed book: " + title);
                    break;
                case 5:
                    // Return a book
                    System.out.print("Enter your email: ");
                    email = scanner.nextLine();
                    if(!library.isBorrower(email)){
                        System.out.print("You are not one of our borrowers!!!");
                        break;
                    }
                    System.out.print("Enter book title to return: ");
                    title = scanner.nextLine();
                    library.returnBook(email, title);
                    System.out.println(email+" Returned book: " + title);
                    break;
                case 6:
                    // Save library data
                    library.saveLibraryData("data.ser");
                    System.out.println("Saved library data in dat.ser file");
                    break;
                case 7:
                    // Exit
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
class Library implements Serializable{
    private List<Book> books = new ArrayList<Book>();
    private List<Borrower> borrowers = new ArrayList<Borrower>();

    public void saveLibraryData(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
            System.out.println("Library data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Library loadLibraryData(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Library) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Library library=new Library();
        library.addBook(new Book("Book 1", "Author 1", "ISBN1"));
        library.addBook(new Book("Book 2", "Author 2", "ISBN2"));
        library.addBook(new Book("Book 3", "Author 1", "ISBN3"));

        library.registerBorrower(new Borrower("Borrower 1", "borrower1@example.com"));
        library.registerBorrower(new Borrower("Borrower 2", "borrower2@example.com"));
        library.registerBorrower(new Borrower("Borrower 3", "b"));
            return library;
        }
    }

    public List<Book> getAllBooks(){
        return this.books;
    }
    public void returnBook(String email,String title){
        Book bBook=null;
        Borrower bBorrower=null;
        for (Borrower b : this.borrowers) {
            if (b.email.equals(email)) {
                bBorrower=b;
            }
        }
        if(bBorrower==null){
            System.out.println("Invalid email");
            return;
        }
        for (Book book : bBorrower.books) {
            if (book.title.equalsIgnoreCase(title)) {
                bBook= book;
            }
        }
        if(bBook==null){
            System.out.println("Invalid book title");
        }
        this.books.add(bBook);
        bBorrower.books.remove(bBook);
    }
    public void borrowBook(String email,String title){
        Book bBook=null;
        Borrower bBorrower=null;
        for (Borrower b : this.borrowers) {
            if (b.email.equals(email)) {
                bBorrower=b;
                break;
            }
        }
        for (Book book : this.books) {
            if (book.title.equalsIgnoreCase(title)) {
                bBook= book;
                break;
            }
        }
        if(bBook==null){
            System.out.println("Invalid book title");
            return;
        }else if(bBorrower==null){
            System.out.println("Invalid email");
            return;
        }
        this.books.remove(bBook);
        bBorrower.books.add(bBook);
    }
    public boolean isBorrower(String email){
        for (Borrower b : this.borrowers) {
            if (b.email.equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public List<Book> getBooksByAuthor(String author){
        List<Book> bks=new ArrayList<Book>();
        for (Book book : this.books) {
            if (book.author.equalsIgnoreCase(author)) {
                bks.add(book);
            }
        }
        return bks;
    }
    public Book getBookByTite(String title){
        for (Book book : this.books) {
            if (book.title.equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;    
    }
    public void printBook(Book book){
        System.out.println(String.format("title: %1$s, author: %2$s, ISBN: %3$s",book.title,book.author,book.ISBN));
    }
    public void printBooks(List<Book> books){
        if(books.size()==0){
            System.out.println("0 books!");
        }
        for(Book book: books){
            System.out.println(String.format("title: %1$s, author: %2$s, ISBN: %3$s",book.title,book.author,book.ISBN));
        }
    }
    public void registerBorrower(Borrower borrower){
        borrowers.add(borrower);
    }
    public void addBook(Book book){
        books.add(book);
    }
}
class Book implements Serializable{
   public String title;
   public String author;
   public String ISBN;
public Book(String title,String author,String isbn){
    this.title=title;
    this.author=author;
    this.ISBN=isbn;
}
}

class Borrower implements Serializable{
    public String name;
    public String email;
    public List<Book> books=new ArrayList<Book>();
    public Borrower(String name,String email){
        this.name=name;
        this.email=email;
    }
}
