package com.example.genealogy.data.tree;

import com.example.genealogy.data.Enum.Relation;
import com.example.genealogy.model.NodeMemberModel;

import java.util.ArrayList;
import java.util.List;

public class TreeUtils {
    public static Node convertModelToNode (NodeMemberModel nodeMemberModel) {
        Node node = new Node();
        node.setId(nodeMemberModel.getId().toString());
        node.setIdMother(nodeMemberModel.getMotherId().toString());
        node.setIdParent(nodeMemberModel.getParent().getId().toString());
        node.setLifeIndex(nodeMemberModel.getLifeIndex());
        node.setPathKey(nodeMemberModel.getPatchKey());
        node.setRelationParent(Relation.getByCode(nodeMemberModel.getRelation()));
        node.setName(nodeMemberModel.getName());
        node.setImage(nodeMemberModel.getImage());
        node.setChilds(new ArrayList<>());
        return node;
    }

    public static Node getChartTree (List<NodeMemberModel> nodeMemberModelList) {
        TreeBuilder treeBuilder = new TreeBuilder();
        for(NodeMemberModel nodeMemberModel: nodeMemberModelList) {
            treeBuilder.addNode(TreeUtils.convertModelToNode(nodeMemberModel));
        }
        return treeBuilder.getRootNode();
    }
}
