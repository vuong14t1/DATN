package data;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String id;
    private String pathKey;
    private String idParent;
    private String idMother;
    private int leftIndex;
    private String name;
    private Relation relationParent;
    private List<Node> childs;
    public Node() {
        id = "-1";
        pathKey = "";
        childs = new ArrayList<>();
    }

    public Node(String id, String pathKey, String idParent, String idMother, int liftIndex, String name, int relateParent) {
        this.id = id;
        this.pathKey = pathKey;
        this.idParent = idParent;
        this.idMother = idMother;
        this.leftIndex = liftIndex;
        this.name = name;
        this.relationParent = Relation.getByCode(relateParent);
        this.childs = new ArrayList<>(1);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPathKey() {
        return pathKey;
    }

    public void setPathKey(String pathKey) {
        this.pathKey = pathKey;
    }

    public String getIdParent() {
        return idParent;
    }

    public void setIdParent(String idParent) {
        this.idParent = idParent;
    }

    public String getIdMother() {
        return idMother;
    }

    public void setIdMother(String idMother) {
        this.idMother = idMother;
    }

    public int getLeftIndex() {
        return leftIndex;
    }

    public void setLeftIndex(int leftIndex) {
        this.leftIndex = leftIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getChilds() {
        return childs;
    }

    public void setChilds(List<Node> childs) {
        this.childs = childs;
    }

    public Relation getRelationParent() {
        return relationParent;
    }

    public void setRelationParent(Relation relationParent) {
        this.relationParent = relationParent;
    }

    @Override
    public String toString() {
        return this.id + "_"
                + this.pathKey + "_"
                + this.idParent + "_"
                + this.idMother + "_"
                + this.leftIndex + "_"
                + this.name + "_"
                + this.relationParent;
    }

    public Node getNodeUnknown () {
        for(Node c: childs) {
            if(c.getId().equals("-1")){
//                System.out.println("get node retain " + c);
                return c;
            }
        }
        Node node = new Node();
        node.setChilds(new ArrayList<>());
        node.setId("-1");
        node.setIdMother("-1");
        node.setIdParent("-1");
        node.setLeftIndex(-1);
        node.setPathKey("");
        node.setName("unknown");
        childs.add(node);
        return node;
    }
}
