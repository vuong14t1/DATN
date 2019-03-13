package com.example.genealogy.service;

import com.example.genealogy.model.DescriptionMemberModel;
import com.example.genealogy.model.GenealogyModel;
import com.example.genealogy.model.NodeMemberModel;
import com.example.genealogy.model.PedigreeModel;

import java.util.List;
import java.util.Optional;

public interface NodeMemberService {
    void add(NodeMemberModel nodeMemberModel, DescriptionMemberModel descriptionMemberModel);

    Optional<NodeMemberModel> findById(Integer id);

    NodeMemberModel findByIdAndFetch(Integer id);

    List<NodeMemberModel> findAll();

    List<NodeMemberModel> findAllByPedigreeAndPatchKeyStartsWith(PedigreeModel pedigreeModel, String patchKey);

    List<NodeMemberModel> findAllByPedigreeAndPatchKey(PedigreeModel pedigreeModel, String patchKey);

    void removeAllByPedigree(PedigreeModel pedigreeModel);

    void removeAllByGenealogy(GenealogyModel genealogyModel);

    void deleteByIdAndPatchKey(Integer id, String patchKey);

    void deleteById(Integer id);

    void deleteAll();

    void updatePatchKey(String oldPatch, String newPatch);

    boolean mergerPedigree(int genealogy, int idPedigreeFrom, int idPedigreeTo, int idParent, int idMother, int childIndex);
}
