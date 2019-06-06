package com.vuongpq2.datn.config.tree;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vuongpq2.datn.data.ClassColor;
import com.vuongpq2.datn.data.Enum.Relation;
import com.vuongpq2.datn.data.GioiTinh;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChartConfig {
    private Config chart;
    private Child nodeStructure;

    public ChartConfig() {
        chart = new Config();
//        nodeStructure = new Child();
    }

    public Config getChart() {
        return chart;
    }

    public void setChart(Config chart) {
        this.chart = chart;
    }

    public Child getNodeStructure() {
        return nodeStructure;
    }

    public void setNodeStructure(Child nodeStructure) {
        this.nodeStructure = nodeStructure;
    }

//    public void addChild(Child child) {
//        if (nodeStructure.getPatchKey().equalsIgnoreCase("r") || nodeStructure == null) {
//            nodeStructure = child;
//            return;
//        }
//        String keyParent = child.getPatchKey();
//        Child parent = getParentFromNode(getNodeStructure(), keyParent);
//        if (parent != null) {
//            List<Child> children = parent.getChildren();
//            if (children == null) {
//                children = new ArrayList<>();
//            }
//            if (parent != nodeStructure) { parent.setCollapsed(true); }
//            children.add(child);
//            parent.setChildren(children);
//        }
//        else {
//            System.out.println("null parrent");
//            List<Child> children = nodeStructure.getChildren();
//            if(children == null) children = new ArrayList<>();
//            children.add(child);
//            nodeStructure.setChildren(children);
//        }
//    }

    /*public void addChildHaveMother(Child child) {
        //if null ==> create
        if (nodeStructure == null) {
            child.addHTMLclass(ClassColor.people_node_root);
            nodeStructure = child;
            return;
        }

        String keyParent = child.getParentKey();
        Child parent = getParentFromNode(getNodeStructure(), keyParent);
        if (parent != null) {
            List<Child> children ;
            //co quan he vo/chong ==> con gan nhat
            QuanHe quanHe = QuanHe.values()[child.getRelation()];
            switch (quanHe){
                case CHA:
                case ME:
                    child.addHTMLclass(GioiTinh.values()[child.getGender()]== GioiTinh.NAM ? ClassColor.people_node_son:ClassColor.people_node_daughter);
                    Long idMother = child.getIdMother();
                    if(idMother != null) {
                        parent = getMotherFromNode(parent,idMother);
                        if(parent!= null){
                            children = parent.getChildren();
                            if (children == null) {
                                children = new ArrayList<>();
                            }
                            if (parent != nodeStructure) { parent.setCollapsed(true); }
                            children.add(child);
                            parent.setChildren(children);
                        }
                    }else{
                        Child childUnknown = parent.getChildrenUnknown();
                        childUnknown.setHTMLid(parent.getHTMLid());
                        childUnknown.getChildren().add(child);
                    }
                    break;
                case CHONG:
                case VO:
                    child.addHTMLclass(GioiTinh.values()[child.getGender()]== GioiTinh.NAM ? ClassColor.people_node_husband:ClassColor.people_node_wife);
                    children = parent.getChildren();
                    if (children == null) {
                        children = new ArrayList<>();
                    }
//                    if (parent != nodeStructure) { parent.setCollapsed(true); }
                    children.add(child);
                    parent.setChildren(children);
                    break;
            }
//            parent.setChildren(children);
        }
        else {
            System.out.println("null parrent");
        }
    }*/

    public void addChild(Child child) {
        if (child.getPatchKey().equalsIgnoreCase("r") || nodeStructure == null) {
            child.addHTMLclass(ClassColor.people_node_root);
            nodeStructure = child;
            return;
        }
        List<Child> childs;
        Child parent = findNodeByPathKey(nodeStructure, child.getPatchKey());
        if(parent != null) {
            Relation relation = Relation.getByCode(child.getRelation());
            switch (relation) {
                case CHA:
                case ME:
                    child.addHTMLclass(GioiTinh.values()[child.getGender()]== GioiTinh.NAM ? ClassColor.people_node_son:ClassColor.people_node_daughter);
                    int idMother = child.getIdMother();

                    if(idMother == -1) {
                        Child childUnknown = parent.getChildrenUnknown();
                        childUnknown.setHTMLid(parent.getHTMLid());
                        if (relation == Relation.ME) {
                            childUnknown.setRelation(Relation.CHONG.getCode());
                        }else {
                            childUnknown.setRelation(Relation.VO.getCode());
                        }
                        childUnknown.setChildrenDropLevel(child.getChildrenDropLevel());
                        childUnknown.getChildren().add(child);

                    }else {

                        Child node = null;
//                        if (relation == Relation.ME) {
//                            //neu moi quan he la me thi tim node gan nhat la cha
//                            node = findNodeByIdParent(parent, child.getIdFather());
//                        }else {
//                            //neu moi quan he la cha thi tim node gan nhat la me
//                        }
                        node = findNodeByIdMother(parent, child.getIdMother());
                        //TH khong tim thay id me
                        if(node == null) {
                            node = parent;
                        }

                        if (parent != nodeStructure) { parent.setCollapsed(true); }
                        childs = node.getChildren();
                        if(childs == null) {
                            childs= new ArrayList<>();
                        }
                        childs.add(child);
                        node.setChildren(childs);
//                        if (node != null) {
//                            childs = node.getChildren();
//                            if(childs == null) {
//                                childs= new ArrayList<>();
//                            }
//                            childs.add(child);
//                            node.setChildren(childs);
//                        }else {
//                            //TH khong tim thay cha hoac me thi tu dong tao node unknown
////                            System.out.println("unknow" + parent.toString() + "| child " + child);
//                            Child childUnknown = parent.getChildrenUnknown();
//                            if (relation == Relation.ME) {
//                                childUnknown.setRelation(Relation.CHONG.getCode());
//                            }else {
//                                childUnknown.setRelation(Relation.VO.getCode());
//                            }
//                            childUnknown.setChildrenDropLevel(child.getChildrenDropLevel());
//                            childUnknown.getChildren().add(child);
//
//                        }
                    }

                    break;
                case VO:
                case CHONG:
                    child.addHTMLclass(GioiTinh.values()[child.getGender()]== GioiTinh.NAM ? ClassColor.people_node_husband:ClassColor.people_node_wife);
                    childs = parent.getChildren();
                    if(childs == null) {
                        childs= new ArrayList<>();
                    }
                    if (parent != nodeStructure) { parent.setCollapsed(true); }
                    childs.add(child);
                    parent.setChildren(childs);
                    break;
            }
        }
    }
    private Child findNodeByPathKey(Child child, String pathKey) {
        if(pathKey.equalsIgnoreCase(child.getPatchKey() + "_"+ child.getId())) {
            return child;
        }
        List<Child> childs = child.getChildren();
        if(childs == null) return null;
        Child target = null;
        for (Child c: childs) {
            target =  findNodeByPathKey(c, pathKey);
            if(target != null) break;
        }
        return target;
    }

    private Child findNodeByIdMother(Child child, int idMother) {
        if(child.getId() == idMother ||idMother == -1) return child;
        List<Child> childs = child.getChildren();
        if(childs == null) return null;
        Child target = null;
        for (Child c: childs) {
            target =  findNodeByIdMother(c, idMother);
            if(target != null) break;
        }
        return target;
    }

    private Child findNodeByIdParent(Child child, int idParent) {
        if(child.getId() == idParent || idParent == -1) return child;
        List<Child> childs = child.getChildren();
        if(childs == null) return null;
        Child target = null;
        for (Child c: childs) {
            target =  findNodeByIdParent(c, idParent);
            if(target != null) break;
        }
        return target;
    }

//    private Child getParentFromNode(Child child, String keyParent) {
//        if (keyParent.equalsIgnoreCase(child.getParentKey() + "_" + child.getId())) {
//            return child;
//        }
//        else {
//            List<Child> children = child.getChildren();
//            if (children == null) { return null; }
//            Child result = null;
//            for (Child c : children) {
//                result = getParentFromNode(c, keyParent);
//                if (result != null) { break; }
//            }
//            return result;
//        }
//    }
//
//
//    private Child getMotherFromNode(Child child,Long idMother) {
//        if(idMother == null || child.getId() == idMother) return child;
//        else{
//            List<Child> children = child.getChildren();
//            if (children == null) { return null; }
//            Child result = null;
//            for (Child c : children) {
//                result = getMotherFromNode(c, idMother);
//                if (result != null) { break; }
//            }
//            return result;
//        }
//    }

}
