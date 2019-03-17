package com.example.genealogy.service;

import com.example.genealogy.model.DescriptionMemberModel;
import com.example.genealogy.model.GenealogyModel;
import com.example.genealogy.model.NodeMemberModel;
import com.example.genealogy.model.PedigreeModel;
import com.example.genealogy.repository.DescriptionMemberRepository;
import com.example.genealogy.repository.NodeMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NodeMemberServiceImpl implements NodeMemberService {
    @Autowired
    private NodeMemberRepository nodeMemberRepository;
    @Autowired
    private DescriptionMemberRepository descriptionMemberRepository;

    @Override
    public void add(NodeMemberModel nodeMemberModel, DescriptionMemberModel descriptionMemberModel) {
        nodeMemberRepository.save(nodeMemberModel);
        descriptionMemberModel.setNodeMemberModel(nodeMemberModel);
        descriptionMemberRepository.save(descriptionMemberModel);
        nodeMemberModel.setDescriptionMemberModel(descriptionMemberModel);
        nodeMemberRepository.save(nodeMemberModel);
    }

    @Override
    public NodeMemberModel findById(Integer id) {
        return nodeMemberRepository.findOne(id);
    }

    @Override
    public NodeMemberModel findByIdAndFetch(Integer id) {
        return nodeMemberRepository.findByIdAndFetch(id);
    }

    @Override
    public List<NodeMemberModel> findAll() {
        return nodeMemberRepository.findAll();
    }

    @Override
    public List<NodeMemberModel> findAllByPedigreeAndPatchKeyStartsWith(PedigreeModel pedigreeModel, String patchKey) {
        return nodeMemberRepository.findAllByPedigreeAndPatchKeyStartsWithOrderByPatchKeyAscMotherIdAscRelationAsc(pedigreeModel,patchKey);
    }

    @Override
    public List<NodeMemberModel> findAllByPedigreeAndPatchKey(PedigreeModel pedigreeModel, String patchKey) {
        return nodeMemberRepository.findAllByPedigreeAndPatchKey(pedigreeModel, patchKey);
    }

    @Override
    public void removeAllByPedigree(PedigreeModel pedigreeModel) {
        nodeMemberRepository.removeAllByPedigree(pedigreeModel);
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
    public void deleteAll() {
        nodeMemberRepository.deleteAll();
    }

    @Override
    public void updatePatchKey(String oldPatch, String newPatch) {

    }

    @Override
    public boolean mergerPedigree(int genealogy, int idPedigreeFrom, int idPedigreeTo, int idParent, int idMother, int childIndex) {
        return false;
    }
}
