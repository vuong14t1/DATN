package com.example.genealogy.repository;

import com.example.genealogy.model.GenealogyModel;
import com.example.genealogy.model.NodeMemberModel;
import com.example.genealogy.model.PedigreeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NodeMemberRepository extends JpaRepository<NodeMemberModel, Long> {
    List<NodeMemberModel> findAllByPedigreeAndGenealogyAndPatchKeyStartsWith(PedigreeModel pedigreeModel, GenealogyModel genealogyModel, String patchKeyStartsKey);

    List<NodeMemberModel> findAllByPedigreeAndGenealogyAndPatchKeyStartsWithOrderByPatchKeyAscMotherIdAscRelationAsc (PedigreeModel pedigreeModel, GenealogyModel genealogyModel, String patchKeyStartsKey);

    List<NodeMemberModel> findAllByPedigreeAndGenealogyAndPatchKey (PedigreeModel pedigreeModel, GenealogyModel genealogyModel, String patchKeyStartsKey);

    List<NodeMemberModel> findAllByPedigreeAndGenealogyAndPatchKeyAndRelationEquals(PedigreeModel pedigreeModel, GenealogyModel genealogyModel, String patchKeyStartsKey, Integer relation);

    void removeAllByPedigreeAndGenealogy(PedigreeModel pedigreeModel, GenealogyModel genealogyModel);

    void removeAllByGenealogy(GenealogyModel genealogyModel);

    @Query("SELECT p FROM NodeMemberModel p JOIN FETCH  p.parent WHERE p.id = :id")
    NodeMemberModel findByIdAndFetch(@Param("id") long id);

    void deleteAllByIdOrPatchKeyStartsWith(Integer id, String patchKey);

    @Modifying(clearAutomatically = true)
    @Query( nativeQuery = true, value = "update node_member set node_member.patch_key = REPLACE( node_member.patch_key ,:oldKey ,:newKey) where node_member.patch_key like concat(:oldKey,'%')")
    void updatePatchKey(@Param("oldKey") String oldKey, @Param("newKey") String  newKey );
}
