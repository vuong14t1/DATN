package com.example.genealogy.service;

import com.example.genealogy.model.DescriptionMemberModel;
import com.example.genealogy.model.GenealogyModel;
import com.example.genealogy.model.NodeMemberModel;
import com.example.genealogy.model.PedigreeModel;

import java.util.List;

public class NodeMemberServiceImpl implements NodeMemberService {

    @Override
    public void add(NodeMemberModel nodeMemberModel, DescriptionMemberModel descriptionMemberModel) {

    }

    @Override
    public NodeMemberModel findById(Integer id) {
        return null;
    }

    @Override
    public NodeMemberModel findByIdAndFetch(Integer id) {
        return null;
    }

    @Override
    public List<NodeMemberModel> findAll() {
        return null;
    }

    @Override
    public List<NodeMemberModel> findAllByPedigreeAndPatchKeyStartsWith(PedigreeModel pedigreeModel, String patchKey) {
        return null;
    }

    @Override
    public List<NodeMemberModel> findAllByPedigreeAndPatchKey(PedigreeModel pedigreeModel, String patchKey) {
        return null;
    }

    @Override
    public void removeAllByPedigree(PedigreeModel pedigreeModel) {

    }

    @Override
    public void removeAllByGenealogy(GenealogyModel genealogyModel) {

    }

    @Override
    public void deleteByIdAndPatchKey(Integer id, String patchKey) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public void updatePatchKey(String oldPatch, String newPatch) {

    }

    @Override
    public boolean mergerPedigree(int genealogy, int idPedigreeFrom, int idPedigreeTo, int idParent, int idMother, int childIndex) {
        return false;
    }
}
