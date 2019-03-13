package com.example.genealogy.service;

import com.example.genealogy.model.PedigreeModel;

import java.util.List;
import java.util.Optional;

public interface PedigreeService {
    List<PedigreeModel> findAll();

//    List<PedigreeModel> findAllByGenealogyId(Integer genealogyId);

    Optional<PedigreeModel> findById(Integer id);

    void add(PedigreeModel pedigreeModel, Integer idGenealogy);

    void update(PedigreeModel pedigreeModel);

    void delete(PedigreeModel pedigreeModel);

}
