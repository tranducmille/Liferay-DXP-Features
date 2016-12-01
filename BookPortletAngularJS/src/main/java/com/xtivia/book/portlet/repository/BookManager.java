package com.xtivia.book.portlet.repository;

import java.util.ArrayList;
import java.util.List;

import com.xtivia.book.portlet.entity.Book;
import com.xtivia.book.portlet.util.SequenceGenerator;
/**
 * @author created by dtran
 * A book manager implementation
 */
public class BookManager {
	
	private static BookManager _INSTANCE = null;
	
	/**
	 * BOokManager singleton instance
	 * @return singleton object
	 */
	public static BookManager getInstance(){
		if(_INSTANCE == null){
			_INSTANCE = new BookManager();
		}		
		return _INSTANCE;
	}
	
	private List<Book> books = new ArrayList<Book>();
	
	/**
	 * initialize data
	 */
	public BookManager(){
		books.add(new Book(SequenceGenerator.getInstance().getSequence(), "The New Yorker Stories","Anne Beattie","1234556","The new yorker stories comments"));
		books.add(new Book(SequenceGenerator.getInstance().getSequence(), "Cleopatra: A life","Stacy Schiff","2345678","Cleopatra: A life comments"));
		books.add(new Book(SequenceGenerator.getInstance().getSequence(), "Not Dead Yet: The Memoir","Phil Collins","1101907479","Born in the United Kingdom, Phil Collins rose to fame as the drummer and frontman of Genesis, before establishing himself as a solo artist, and selling hundreds of millions of records throughout his career."));
		books.add(new Book(SequenceGenerator.getInstance().getSequence(), "A Life in Parts","Bryan Cranston","1476793859","Bryan Cranston won four Emmy Awards for Outstanding Lead Actor in a Drama Series for his portrayal of Walter White in AMC’s Breaking Bad"));
		books.add(new Book(SequenceGenerator.getInstance().getSequence(), "Scrappy Little Nobody","Anna Kendrick","1501117203","A collection of humorous autobiographical essays by the Academy Award-nominated actress and star of Up in the Air and Pitch Perfect."));
		books.add(new Book(SequenceGenerator.getInstance().getSequence(), "Born to Run","Bruce Springsteen","1501141511","Bruce Springsteen has been inducted into the Rock and Roll Hall of Fame, the Songwriters Hall of Fame, and the New Jersey Hall of Fame. He is the recipient of 20 Grammy Awards, the Academy Award, and the Kennedy Center Honors.  He lives in New Jersey with his family."));
		books.add(new Book(SequenceGenerator.getInstance().getSequence(), "The Magnolia Story","Chip Gaines","0718079183","These famous words are now synonymous with the dynamic husband-and-wife team Chip and Joanna Gaines, stars of HGTV’s Fixer Upper"));
		books.add(new Book(SequenceGenerator.getInstance().getSequence(), "Shaken: Discovering Your True Identity in the Midst of Life's Storms","Tim Tebow","0735289867","Most of us have been on the receiving end of rejection, a broken dream, or heartbreak. And while this is not an easy space to go through, when we are grounded in the truth, we can endure the tough times."));
	}
	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
	
	/**
	 * return book by id
	 * @param id
	 * @return Book
	 */
	public Book getBookById(int id){
		Book rs = books.stream().filter(book -> book.getId() == id)
								.findFirst().orElse(null);
		return rs;
	}
	
	/**
	 * delete book by id
	 * @param id
	 */
	public void deleteBookById(int id){
		Book book = getBookById(id);
		if(book != null){
			books.remove(book);
		}else{
			System.out.println("Can not find the deleted book id");
		}
	}
	
	/**
	 * add a new book
	 * @param book
	 */
	public void addBook(Book book){
		books.add(book);
	}
	
	/**
	 * @param isbn
	 * @return list of Book
	 */
	public List<Book> findBooksByISBN(String isbn){
		List<Book> rs = new ArrayList<Book>();
		books.stream().filter(book -> book.getIsbn().contains(isbn)).forEach(b -> rs.add(b));
		return rs;
	}
	
}
