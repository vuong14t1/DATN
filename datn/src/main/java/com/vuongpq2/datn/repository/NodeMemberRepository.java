package com.vuongpq2.datn.repository;

import com.vuongpq2.datn.model.NodeMemberModel;
import com.vuongpq2.datn.model.PedigreeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NodeMemberRepository extends JpaRepository<NodeMemberModel, Integer> {
    List<NodeMemberModel> findAllByPedigreeAndPatchKeyStartsWith(PedigreeModel pedigreeModel, String patchKeyStartsKey);

    List<NodeMemberModel> findAllByPedigreeAndPatchKeyStartsWithOrderByPatchKeyAscMotherFatherIdAscRelationAsc(PedigreeModel pedigreeModel, String patchKeyStartsKey);

    List<NodeMemberModel> findAllByPedigreeAndPatchKey(PedigreeModel pedigreeModel, String patchKeyStartsKey);
    List<NodeMemberModel> findAllByPedigreeAndPatchKeyAndMotherFatherId(PedigreeModel pedigreeModel, String patchKeyStartsKey, Integer idMotherOrFather);

    List<NodeMemberModel> findAllByPedigreeAndPatchKeyAndRelationEquals(PedigreeModel pedigreeModel, String patchKeyStartsKey, Integer relation);

    void removeAllByPedigree(PedigreeModel pedigreeModel);


    @Query("SELECT p FROM NodeMemberModel p JOIN FETCH  p.parent WHERE p.id = :id")
    NodeMemberModel findByIdAndFetch(@Param("id") int id);

    void deleteAllByIdOrPatchKeyStartsWith(Integer id, String patchKey);

    void deleteAllByPedigreeAndPatchKeyStartsWith(PedigreeModel pedigreeModel, String patchKey);

    @Modifying(clearAutomatically = true)
    @Query( nativeQuery = true, value = "update node_member set node_member.patch_key = REPLACE( node_member.patch_key ,:oldKey ,:newKey) where node_member.patch_key like concat(:oldKey,'%')")
    void updatePatchKey(@Param("oldKey") String oldKey, @Param("newKey") String newKey);

    List<NodeMemberModel> findAllByPedigreeAndNameLike(PedigreeModel pedigreeModel, String search);
}
