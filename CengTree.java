import java.util.ArrayList;
public class CengTree
{
    public CengTreeNode root;
    public CengTree(Integer order)
    {
        CengTreeNode.order = order;
        // TODO: Initialize the class
        root = new CengTreeNodeLeaf(null);
        //root.type = CengNodeType.Leaf;
    }

    public void addBook(CengBook book)
    {

        addRecursive(book, root);
        // Insert Book to Tree
    }

    private void addRecursive(CengBook book, CengTreeNode node){
        if(node.getType().equals(CengNodeType.Internal)){
            int size = ((CengTreeNodeInternal)node).keyCount();
            int i = 0;
            int overflowAtLowerLevel = 0;
            for(;i < size; i++){
                if(book.getBookID() < ((CengTreeNodeInternal)node).keyAtIndex(i)){
                    addRecursive(book, ((CengTreeNodeInternal)node).getChildrenAtIndex(i));
                    break;
                }
            }

            if(i == size){
                addRecursive( book, ( ( CengTreeNodeInternal ) node ).getChildrenAtIndex(i));

            }

            if(((CengTreeNodeInternal) node).keyCount() > 2 * CengTreeNode.order){
                //Interior node overflowed
                Integer midKey = ((CengTreeNodeInternal)node).keyAtIndex(CengTreeNode.order);

                CengTreeNode newInternal = new CengTreeNodeInternal(node.getParent());
                ArrayList<CengTreeNode>allChilds = ((CengTreeNodeInternal) node).getAllChildren();
                for(int j = 1; j <= CengTreeNode.order; j++){
                    ((CengTreeNodeInternal) newInternal).addChildren(allChilds.get(CengTreeNode.order+j));
                    ( allChilds.get(CengTreeNode.order+j) ).setParent(newInternal);
                    ((CengTreeNodeInternal)newInternal).addKey( ((CengTreeNodeInternal) node).keyAtIndex(CengTreeNode.order + j));

                }
                ((CengTreeNodeInternal)newInternal).addChildren(allChilds.get(allChilds.size()-1));
                allChilds.get(allChilds.size()-1).setParent(newInternal);

                for(int k = CengTreeNode.order; k <= 2 * CengTreeNode.order ; k++){
                    ((CengTreeNodeInternal)node).deleteKey(CengTreeNode.order);
                    ((CengTreeNodeInternal)node).deleteChild(CengTreeNode.order+1);
                }

                if(node.getParent() == null){ //root is overflowed but it is not leaf now
                    CengTreeNode newRoot = new CengTreeNodeInternal(null);
                    ((CengTreeNodeInternal)newRoot).addKey( midKey );

                    //create a new leafNode with last d records , delete these records from previous leaf and set parent's child array to these two leafs

                    node.setParent(newRoot);
                    newInternal.setParent(newRoot);

                    ((CengTreeNodeInternal)newRoot).addChildren(node);
                    ((CengTreeNodeInternal)newRoot).addChildren(newInternal);
                    root = newRoot;
                }
                else {

                    CengTreeNodeInternal parent = (CengTreeNodeInternal) node.getParent();
                    parent.addKey(midKey);
                    parent.addChildrenNextToOld(node, newInternal);
                }
            }
        }




        else{ //leaf level

            ((CengTreeNodeLeaf)node).insertBook(book);
            Integer midKey = 0;
            if( ((CengTreeNodeLeaf)node).bookCount() > 2 * CengTreeNode.order){
                //leaf overflow
                midKey = ((CengTreeNodeLeaf)node).bookKeyAtIndex(CengTreeNode.order);

                CengTreeNode newLeaf = new CengTreeNodeLeaf(node.getParent());

                for(int i = 0; i <= CengTreeNode.order; i++){
                    ((CengTreeNodeLeaf)newLeaf).insertBook(((CengTreeNodeLeaf) node).getBook(CengTreeNode.order + i));
                }

                for(int i = CengTreeNode.order; i <= 2 * CengTreeNode.order ; i++){
                    ((CengTreeNodeLeaf)node).deleteBook(CengTreeNode.order);
                    //increasing the index since we move element right to the left//
                if(node.getParent() == null){
                    //root is leaf now and it is overflowed
                    CengTreeNode newRoot = new CengTreeNodeInternal(null);
                    ((CengTreeNodeInternal)newRoot).addKey( midKey );

                    //create a new leafNode with last d records , delete these records from previous leaf and set parent's child array to these two leafs

                    ((CengTreeNodeLeaf)node).setParent(newRoot);
                    newLeaf.setParent(newRoot);

                    ((CengTreeNodeInternal)newRoot).addChildren(node);
                    ((CengTreeNodeInternal)newRoot).addChildren(newLeaf);
                    root = newRoot;

                }
                else {

                    CengTreeNode parent = (CengTreeNodeInternal) node.getParent();
                    ArrayList<Integer> keysStored = ((CengTreeNodeInternal)parent).giveKeys();


                    ((CengTreeNodeInternal)parent).addKey(midKey);
                    ((CengTreeNodeInternal)parent).addChildrenNextToOld(node, newLeaf);

                }

            }

        }
    }




    public ArrayList<CengTreeNode> searchBook(Integer bookID)
    {
        // Search within whole Tree, return visited nodes.
        // Return null if not found

        ArrayList<CengTreeNode> list = new ArrayList<>() ;
        CengTreeNode currentNode = root;
        while(! currentNode.getType().equals(CengNodeType.Leaf)){
            boolean flag = false;
            list.add(currentNode);
            for(int i = 0; i < ((CengTreeNodeInternal) currentNode).keyCount(); i++){
                if(bookID < ((CengTreeNodeInternal) currentNode).keyAtIndex(i) ){
                    currentNode = ((CengTreeNodeInternal) currentNode).getChildrenAtIndex(i);
                    flag = true;
                    break;
                }
            }
            if(!flag)
                currentNode = ((CengTreeNodeInternal) currentNode).getChildrenAtIndex(((CengTreeNodeInternal) currentNode).keyCount());


        }
        if( ((CengTreeNodeLeaf)currentNode).bookExists(bookID)){
            list.add(currentNode);

            String pref = "";
            for(CengTreeNode x: list){
                if(x.type.equals(CengNodeType.Internal)){
                    System.out.println(pref+"<index>");
                    for(int i = 0; i < ((CengTreeNodeInternal)x).keyCount(); i++){
                        System.out.println(pref + ((CengTreeNodeInternal)x).keyAtIndex(i));
                    }
                    System.out.println(pref+"</index>");
                }
                else{
                    CengBook l = ((CengTreeNodeLeaf)x).getBook(bookID);
                    if( !(l == null))
                        System.out.println(pref +"<record>"+l.getBookID()+"|"+l.getBookTitle()+"|"+l.getAuthor() + "|" + l.getGenre()+"</record>" );
                }
                pref+= "\t";
            }
        }

        else{
            list = null;
            System.out.println("Could not find "+bookID+".");
        }
        return list;
    }

    public void printTree()
    {
        printSubTree(root,0);

        //Printing whole tree to the console
    }
    private void printSubTree(CengTreeNode subRoot, int indentationAmount){
        String indentation = "";
        for(int i = 0; i < indentationAmount; i++){
            indentation += "\t";
        }
        if(subRoot.getType().equals(CengNodeType.Internal)){
            System.out.println(indentation + "<index>");
            for(int i = 0; i < ((CengTreeNodeInternal)subRoot).keyCount(); i++){
                System.out.println(indentation + ((CengTreeNodeInternal)subRoot).keyAtIndex(i));
            }
            System.out.println(indentation + "</index>");
            ArrayList<CengTreeNode>childrens = ((CengTreeNodeInternal)subRoot).getAllChildren();
            for(CengTreeNode x: childrens){
                printSubTree(x,indentationAmount+1);
            }
        }
        else{
            System.out.println(indentation + "<data>");
            for(int i = 0; i < ((CengTreeNodeLeaf)subRoot).bookCount(); i++){
                CengBook curr = ((CengTreeNodeLeaf)subRoot).getBook(i);
                System.out.println(indentation+"<record>"+curr.getBookID()+"|"+curr.getBookTitle()+"|"+curr.getAuthor() + "|" + curr.getGenre()+"</record>");
            }
            System.out.println(indentation + "</data>");
        }
    }
}
