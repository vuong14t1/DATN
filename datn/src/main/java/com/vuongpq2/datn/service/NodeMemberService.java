package com.vuongpq2.datn.service;

import com.vuongpq2.datn.model.DescriptionMemberModel;
import com.vuongpq2.datn.model.GenealogyModel;
import com.vuongpq2.datn.model.NodeMemberModel;
import com.vuongpq2.datn.model.PedigreeModel;

import java.util.List;
import java.util.Optional;

public interface NodeMemberService {
    NodeMemberModel add(NodeMemberModel nodeMemberModel, DescriptionMemberModel descriptionMemberModel);

    Optional<NodeMemberModel> findById(Integer id);

    NodeMemberModel findByIdAndFetch(Integer id);

    List<NodeMemberModel> findAll();

    List<NodeMemberModel> findAllByPedigreeAndPatchKeyStartsWith(PedigreeModel pedigreeModel, String patchKey);

    List<NodeMemberModel> findAllByPedigreeAndPatchKey(PedigreeModel pedigreeModel, String patchKey);
    List<NodeMemberModel> findAllByPedigreeAndPatchKeyAndMotherFatherId(PedigreeModel pedigreeModel, String patchKey, Integer motherFatherId);

    List<NodeMemberModel> findAllByPedigreeAndPatchKeyAndRelationEquals(PedigreeModel pedigreeModel, String patchKey, int relation);

    void removeAllByPedigree(PedigreeModel pedigreeModel);

    void removeAllByGenealogy(GenealogyModel genealogyModel);

    void deleteByIdAndPatchKey(Integer id, String patchKey);

    void deleteAllByPedigreeAndPatchKeyStartsWith (PedigreeModel pedigreeModel, String patchKey);

    void deleteById(Integer id);

    void deleteAll();

    void updatePatchKey(String oldPatch, String newPatch);

    boolean mergerPedigree(int genealogy, int idPedigreeFrom, int idPedigreeTo, int idParent, int idMother, int childIndex);
    List<NodeMemberModel> findAllByPedigreeAndNameLike(PedigreeModel pedigreeModel, String search);
}
