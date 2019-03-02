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
        Node parent = getParentFromNode(rootNode, child.getPathKey());
//        System.out.println("parent" + parent);
        if(parent != null) {
            Relation relation = child.getRelationParent();

            switch (relation) {
                case CHA:
                case ME:
                    String idMother = child.getIdMother();
                    String idParent = child.getIdParent();
                    /*if(child.getId().equals("338")) {
                        System.out.println("unknow" + parent.toString());
                        System.out.println("child" + child.toString());
                        System.out.println("idMother" + idMother);
                    }*/
                    if(!idMother.equals("") || !idParent.equals("")) {
                        Node mother = getMotherFromNode(parent, child.getIdMother());
                        if (mother != null) {
                            childs = mother.getChilds();
                            if(childs == null) {
                                childs= new ArrayList<>();
                            }
                            childs.add(child);
                            mother.setChilds(childs);
                        }
                    }else {
                        System.out.println("unknow" + parent.toString() + "| child " + child);
                        Node childUnknown = parent.getNodeUnknown();
//                        parent.getChilds().add(childUnknown);
                        childUnknown.getChilds().add(child);
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

    private Node getParentFromNode (Node child, String pathKey) {
        if(pathKey.equalsIgnoreCase(child.getPathKey() + "_"+ child.getId())) {
            return child;
        }
        List<Node> childs = child.getChilds();
        if(childs == null) return null;
        Node target = null;
        for (Node c: childs) {
            target =  getParentFromNode(c, pathKey);
            if(target != null) break;
        }
        return target;
    }

    private Node getMotherFromNode (Node child, String idMother) {
        if(child.getId().equalsIgnoreCase(idMother) || idMother.equals("")) return child;
        List<Node> childs = child.getChilds();
        if(childs == null) return null;
        Node target = null;
        for (Node c: childs) {
            target =  getMotherFromNode(c, idMother);
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
    /*public Node getParentFromNode (Node node) {
        String pathKey = node.getPathKey();
        String[] arr = pathKey.split("_");
        String idParent = arr[arr.length - 1];
        return Controller.dataNodeByKey.get(idParent);
    }

    public Node getMotherFromNode (Node node) {
        String idMother = node.getIdMother();
        return Controller.dataNodeByKey.get(idMother);
    }*/
}
