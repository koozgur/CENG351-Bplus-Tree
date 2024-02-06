import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class CengTreeParser
{
    public static ArrayList<CengBook> parseBooksFromFile(String filename)
    {
        ArrayList<CengBook> bookList = new ArrayList<CengBook>();

        // You need to parse the input file in order to use GUI tables.
        // TODO: Parse the input file, and convert them into CengBooks
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] subs = line.split("\\|");
                bookList.add(new CengBook(Integer.parseInt(subs[0]),subs[1],subs[2],subs[3]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public static void startParsingCommandLine() throws IOException
    {
        // TODO: Start listening and parsing command line -System.in-.
        // There are 4 commands:
        String readCengBook = null;
        Scanner scanner = new Scanner(System.in);
        try {
            readCengBook = scanner.nextLine();
        }catch (Exception e){
            readCengBook = "";
        }
        while(!readCengBook.equals("quit")){
            if(readCengBook.isEmpty()){
                try {
                    readCengBook = scanner.nextLine();
                }catch (Exception e){
                    readCengBook = "";
                }
                continue;
            }
            if(readCengBook.equals("print")) {
                CengBookRunner.printTree();
            }
            else {
                String[] strings = readCengBook.split("\\|");
                if (strings[0].equals("search")) {
                    CengBookRunner.searchBook(Integer.parseInt(strings[1]));
                }
                else if(strings[0].equals("add")){ // add
                    CengBook newlyCreatedBook = new CengBook(Integer.parseInt(strings[1]), strings[2], strings[3], strings[4]);
                    CengBookRunner.addBook(newlyCreatedBook);
                }
                else{
                    CengBook newlyCreatedBook = new CengBook(Integer.parseInt(strings[0]), strings[1], strings[2], strings[3]);
                    CengBookRunner.addBook(newlyCreatedBook);
                }
            }
            //read again
            try {
                readCengBook = scanner.nextLine();
            }catch (Exception e){
                readCengBook = "";
            }

        }
        scanner.close();
        // 1) quit : End the app, gracefully. Print nothing, call nothing, just break off your command line loop.
        // 2) add : Parse and create the book, and call CengBookRunner.addBook(newlyCreatedBook).
        // 3) search : Parse the bookID, and call CengBookRunner.searchBook(bookID).
        // 4) print : Print the whole tree, call CengBookRunner.printTree().

        // Commands (quit, add, search, print) are case-insensitive.
    }
}
