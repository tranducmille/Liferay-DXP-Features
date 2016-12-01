package com.xtivia.book.portlet.util;

import java.util.List;

import com.liferay.portal.kernel.util.Validator;
import com.xtivia.book.portlet.entity.Book;
/**
 * @author created by dtran
 * A validator class
 */
public class BookValidator {
	/**
	 * validate a book
	 * @param book
	 * @param errors
	 * @return true/false
	 */
	public static boolean validateBook(Book book, List<String> errors) {

		boolean valid = true;
		if (Validator.isNull(book.getTitle())) {
			errors.add("title-required");
			valid = false;
		}

		if (Validator.isNull(book.getAuthor())) {
			errors.add("author-required");
			valid = false;
		}

		if (Validator.isNull(book.getIsbn())) {
			errors.add("isbn-required");
			valid = false;
		}
		if (Validator.isNull(book.getSummary())) {
			errors.add("summary-required");
			valid = false;
		}
		return valid;
	}
	
	/**
	 * extract words by counter
	 * @param word
	 * @param count
	 * @return string
	 */
	public static String extractWords(String word, int count){
		
		if(word == null || count < 0)
			return "";
		
		String splitter[] = word.split(" ");
		StringBuilder builder = new StringBuilder();
		int counter = 1;
		for(String s: splitter){
			if(counter <= count){
				builder.append(s);
				if(counter < count){
					builder.append(" ");
				}
				counter++;
			}else{
				break;
			}		
		}
		return builder.toString() + " ...";	 
	}
	
	public static void main(String[] a){
		System.out.println(BookValidator.extractWords("Most of us ha", 5));
	}

}
