package com.vuongpq2.datn.service;


import com.vuongpq2.datn.model.DescriptionMemberModel;
import com.vuongpq2.datn.model.GenealogyModel;
import com.vuongpq2.datn.model.NodeMemberModel;
import com.vuongpq2.datn.model.PedigreeModel;
import com.vuongpq2.datn.repository.DescriptionMemberRepository;
import com.vuongpq2.datn.repository.NodeMemberRepository;
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
    public Optional<NodeMemberModel> findById(Integer id) {
        return nodeMemberRepository.findById(id);
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
        return nodeMemberRepository.findAllByPedigreeAndPatchKeyStartsWithOrderByPatchKeyAscMotherFatherIdAscRelationAsc(pedigreeModel,patchKey);
    }

    @Override
    public List<NodeMemberModel> findAllByPedigreeAndPatchKey(PedigreeModel pedigreeModel, String patchKey) {
        return nodeMemberRepository.findAllByPedigreeAndPatchKey(pedigreeModel, patchKey);
    }

    @Override
    public List<NodeMemberModel> findAllByPedigreeAndPatchKeyAndRelationEquals(PedigreeModel pedigreeModel, String patchKey, int relation) {
        return nodeMemberRepository.findAllByPedigreeAndPatchKeyAndRelationEquals(pedigreeModel, patchKey, relation);
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
        nodeMemberRepository.deleteById(id);
    }

    @Override
    public void deleteAllByPedigreeAndPatchKeyStartsWith(PedigreeModel pedigreeModel, String patchKey) {
        nodeMemberRepository.deleteAllByPedigreeAndPatchKeyStartsWith(pedigreeModel, patchKey);
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
