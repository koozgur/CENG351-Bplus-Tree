import java.util.ArrayList;

public class CengTreeNodeLeaf extends CengTreeNode
{
    private ArrayList<CengBook> books;
    // TODO: Any extra attributes

    public CengTreeNodeLeaf(CengTreeNode parent)
    {
        super(parent);

        // TODO: Extra initializations
        books = new ArrayList<CengBook>();
        this.type = CengNodeType.Leaf;
    }

    // GUI Methods - Do not modify
    public int bookCount()
    {
        return books.size();
    }
    public Integer bookKeyAtIndex(Integer index)
    {
        if(index >= this.bookCount()) {
            return -1;
        } else {
            CengBook book = this.books.get(index);

            return book.getBookID();
        }
    }

    // Extra Functions
    public void insertBook(CengBook newBook){
        int size = books.size();
        for(int i=0; i < size; i++){
            if(books.get(i).getBookID() > newBook.getBookID()){
                books.add(i, newBook);
                return;
            }
        }
        books.add(newBook);
    }

    public CengBook getBook(int index){
        return this.books.get(index);
    }
    public void deleteBook(int index){
        this.books.remove(index);
    }

    public boolean bookExists(Integer bookId){
        for(int i = 0; i < this.books.size(); i++){
            if(bookId.equals( this.books.get(i).getBookID()))
                return true;
        }
        return false;
    }
    public CengBook getBook(Integer bookId){
        for(int i = 0; i < this.books.size(); i++){
            if(bookId.equals( this.books.get(i).getBookID()))
                return this.books.get(i);
        }
        return null;
    }
}
