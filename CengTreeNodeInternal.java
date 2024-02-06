import java.util.ArrayList;

public class CengTreeNodeInternal extends CengTreeNode
{
    private ArrayList<Integer> keys;
    private ArrayList<CengTreeNode> children;

    public CengTreeNodeInternal(CengTreeNode parent)
    {
        super(parent);

        // TODO: Extra initializations, if necessary.
        keys = new ArrayList<Integer>();
        children = new ArrayList<CengTreeNode>();
        this.type = CengNodeType.Internal;
    }

    // GUI Methods - Do not modify
    public ArrayList<CengTreeNode> getAllChildren()
    {
        return this.children;
    }
    public Integer keyCount()
    {
        return this.keys.size();
    }
    public Integer keyAtIndex(Integer index)
    {
        if(index >= this.keyCount() || index < 0)
        {
            return -1;
        }
        else
        {
            return this.keys.get(index);
        }
    }

    // Extra Functions
    public CengTreeNode getChildrenAtIndex(int index) {return this.children.get(index);}

    public void addKey(int bookId) {
            int size = keys.size();
            for(int i=0; i < size; i++) {
                if (keys.get(i) > bookId) {
                    keys.add(i, bookId);
                    return;
                }
            }
            keys.add(bookId);
    }

    public void addChildren(CengTreeNode child){
        this.children.add(child);
    }
    public void addChildrenNextToOld(CengTreeNode oldChild , CengTreeNode newChild){
        //islarger -> true if newChild is greater key (should be inserted right side)
        //else to left side
        for(int i = 0; i < children.size(); i++){
            if(children.get(i) == oldChild) {
                children.add(i + 1, newChild);
                return;
            }
        }
        children.add(children.size()-1, newChild);
    }
    public void deleteChild(int index){
        this.children.remove(index);
    }
    public void deleteKey(int index){
        this.keys.remove(index);
    }
    public ArrayList<Integer> giveKeys(){return this.keys;}
}
