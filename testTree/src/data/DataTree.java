package data;

import java.util.ArrayList;
import java.util.List;

public class DataTree {
    private Node rootNode;

    public DataTree() {
    }

    public DataTree(Node rootNode) {
        this.rootNode = rootNode;
    }

    public void addNode (Node child) {
        if (child.getPathKey().equalsIgnoreCase("r") || rootNode == null) {
            rootNode = child;
            return;
        }
        List<Node> childs;
        Node parent = findNodeByPathKey(getRootNode(), child.getPathKey());
//        System.out.println("parent" + parent);
        if(parent != null) {
            Relation relation = child.getRelationParent();

            switch (relation) {
                case CHA:
                case ME:
                    String idMother = child.getIdMother();
                    String idParent = child.getIdParent();
                    if(child.getId().equals("338")) {
                        System.out.println("parent" + parent.toString());
                        System.out.println("child" + child.toString());
                        System.out.println("idMother" + idMother);
                    }
                    if(idMother.equals("") || idParent.equals("")) {

                        System.out.println("unknow" + parent.toString() + "| child " + child);
                        Node childUnknown = parent.getNodeUnknown();
                        childUnknown.setRelationParent(idMother.equals("")? Relation.VO: Relation.CHONG);
                        childUnknown.setLeftIndex(child.getLeftIndex());
//                        parent.getChilds().add(childUnknown);
                        childUnknown.getChilds().add(child);

                    }else {

                        Node node = null;
                        if (relation == Relation.ME) {
                            node = findNodeByIdMother(parent, child.getIdMother());
                        }else {
                            node = findNodeByIdParent(parent, child.getIdParent());
                        }
                        if (node != null) {
                            childs = node.getChilds();
                            if(childs == null) {
                                childs= new ArrayList<>();
                            }
                            childs.add(child);
                            node.setChilds(childs);
                        }
                    }

                    break;
                case VO:
                case CHONG:
                    childs = parent.getChilds();
                    if(childs == null) {
                        childs= new ArrayList<>();
                    }
                    childs.add(child);
                    parent.setChilds(childs);
                    break;
            }
        }

    }

    private Node findNodeByPathKey(Node child, String pathKey) {
        if(pathKey.equalsIgnoreCase(child.getPathKey() + "_"+ child.getId())) {
            return child;
        }
        List<Node> childs = child.getChilds();
        if(childs == null) return null;
        Node target = null;
        for (Node c: childs) {
            target =  findNodeByPathKey(c, pathKey);
            if(target != null) break;
        }
        return target;
    }

    private Node findNodeByIdMother(Node child, String idMother) {
        if(child.getId().equalsIgnoreCase(idMother) || idMother.equals("")) return child;
        List<Node> childs = child.getChilds();
        if(childs == null) return null;
        Node target = null;
        for (Node c: childs) {
            target =  findNodeByIdMother(c, idMother);
            if(target != null) break;
        }
        return target;
    }

    private Node findNodeByIdParent(Node child, String idParent) {
        if(child.getId().equalsIgnoreCase(idParent) || idParent.equals("")) return child;
        List<Node> childs = child.getChilds();
        if(childs == null) return null;
        Node target = null;
        for (Node c: childs) {
            target =  findNodeByIdParent(c, idParent);
            if(target != null) break;
        }
        return target;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }
    /*public Node findNodeByPathKey (Node node) {
        String pathKey = node.getPathKey();
        String[] arr = pathKey.split("_");
        String idParent = arr[arr.length - 1];
        return Controller.dataNodeByKey.get(idParent);
    }

    public Node findNodeByIdMother (Node node) {
        String idMother = node.getIdMother();
        return Controller.dataNodeByKey.get(idMother);
    }*/
}
