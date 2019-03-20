package com.vuongpq2.datn.data.tree;


import com.vuongpq2.datn.data.Enum.Relation;

import java.util.ArrayList;
import java.util.List;

public class TreeBuilder {
    private Node rootNode;

    public TreeBuilder() {
    }

    public TreeBuilder(Node rootNode) {
        this.rootNode = rootNode;
    }

    public void addNode (Node child) {
        if (child.getPathKey().equalsIgnoreCase("r") || rootNode == null) {
            rootNode = child;
            return;
        }
        List<Node> childs;
        Node parent = findNodeByPathKey(getRootNode(), child.getPathKey());
//        if(child.getId().equals("332")) {
//            System.out.println("parent" + parent.toString());
//            System.out.println("child" + child.toString());
//        }
//        System.out.println("parent" + parent);
        if(parent != null) {
            Relation relation = child.getRelationParent();

            switch (relation) {
                case CHA:
                case ME:
                    String idMother = child.getIdMother();
                    String idParent = child.getIdParent();

                    if(idMother.equals("") || idParent.equals("")) {

//                        System.out.println("unknow" + parent.toString() + "| child " + child);
                        Node childUnknown = parent.getNodeUnknown();
                        childUnknown.setRelationParent(relation == Relation.ME ? Relation.CHONG: Relation.VO);
                        childUnknown.setLifeIndex(child.getLifeIndex());
                        childUnknown.getChilds().add(child);

                    }else {

                        Node node = null;
                        if (relation == Relation.ME) {
                            //neu moi quan he la me thi tim node gan nhat la cha
                            node = findNodeByIdParent(parent, child.getIdParent());
                        }else {
                            //neu moi quan he la cha thi tim node gan nhat la me
                            node = findNodeByIdMother(parent, child.getIdMother());
                        }
                        if (node != null) {
                            childs = node.getChilds();
                            if(childs == null) {
                                childs= new ArrayList<>();
                            }
                            childs.add(child);
                            node.setChilds(childs);
                        }else {
                            //TH khong tim thay cha hoac me thi tu dong tao node unknown
//                            System.out.println("unknow" + parent.toString() + "| child " + child);
                            Node childUnknown = parent.getNodeUnknown();
                            childUnknown.setRelationParent(relation == Relation.ME ? Relation.CHONG: Relation.VO);
                            childUnknown.setLifeIndex(child.getLifeIndex());
                            childUnknown.getChilds().add(child);

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
