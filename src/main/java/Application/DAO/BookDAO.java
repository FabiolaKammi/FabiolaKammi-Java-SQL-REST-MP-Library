package Application.DAO;

import Application.Util.ConnectionUtil;
import Application.Model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A DAO is a class that mediates the transformation of data between the format of objects in Java to rows in a
 * database. The methods here are mostly filled out, you will just need to add a SQL statement.
 *
 * We may assume that the database has already created a table named 'book'.
 * It contains similar values as the Author class:
 * isbn, which is of type int and is a primary key,
 * author_id, which is of type int, and is a foreign key associated with the column 'id' of 'author',
 * title, which is of type varchar(255),
 * copies_available, which is of type int.
 */
public class BookDAO {
    /**
     * TODO: retrieve all books from the Book table.
     * You only need to change the sql String.
     * @return all Books.
     */
    public List<Book> getAllBooks(){
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Book";

        try (Connection connection = ConnectionUtil.getConnection();
            //Write SQL logic here
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()){
            while(rs.next()){
                Book book = new Book(rs.getInt("isbn"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getInt("copies_available"));
                books.add(book);
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return books;
    }

    /**
     * TODO: retrieve a book from the Book table, identified by its isbn.
     * You only need to change the sql String and leverage PreparedStatement's setString and setInt methods.
     * @return a book identified by isbn.
     */
    public Book getBookByIsbn(int isbn){
        String sql = "SELECT * FROM Book WHERE isbn = ?";
        try (Connection connection = ConnectionUtil.getConnection();
            //Write SQL logic here
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            //write preparedStatement's setInt method here.
            preparedStatement.setInt(1, isbn);

             try (ResultSet rs = preparedStatement.executeQuery()){
                if(rs.next()) {
                    return new Book(rs.getInt("isbn"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getInt("copies_available"));
                
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * TODO: insert a book into the Book table.
     * Unlike some of the other insert problems, the primary key here will be provided by the client as part of the
     * Book object. Given the specific nature of an ISBN as both a numerical organization of books outside of this
     * database, and as a primary key, it would make sense for the client to submit an ISBN when submitting a book.
     * You only need to change the sql String and leverage PreparedStatement's setString and setInt methods.
     */
    public Book insertBook(Book book){
        String sql = "INSERT INTO Book(isbn, author_id, title, copies_available)";
        try (Connection connection = ConnectionUtil.getConnection();
            //Write SQL logic here
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            //write preparedStatement's setString and setInt methods here.
             preparedStatement.setInt(1, book.getIsbn());
             preparedStatement.setInt(2, book.getAuthor_id());
             preparedStatement.setString(3, book.getTitle());
             preparedStatement.setInt(4, book.getCopies_available());

             preparedStatement.executeUpdate();
            return book;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    /**
     * TODO: retrieve all books from the Book table with copies_available over zero.
     * You only need to change the sql String with a query that utilizes a WHERE clause.
     * @returnall books with book count > 0.
     */
     
    public List<Book> getBooksWithBookCountOverZero() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Book WHERE copies_available > 0";

        try(Connection connection = ConnectionUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                    Book book = new Book(rs.getInt("isbn"),
                        rs.getInt("author_id"),
                        rs.getString("title"),
                        rs.getInt("copies_available"));
                    books.add(book);
                }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return books;
    } 

    
}
