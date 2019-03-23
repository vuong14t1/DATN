package com.vuongpq2.datn.service;

import com.vuongpq2.datn.model.GenealogyModel;
import com.vuongpq2.datn.model.PedigreeModel;
import com.vuongpq2.datn.repository.GenealogyRepository;
import com.vuongpq2.datn.repository.PedigreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedigreeServiceImpl implements PedigreeService {
    @Autowired
    private PedigreeRepository pedigreeRepository;

    @Autowired
    private GenealogyRepository genealogyRepository;
    @Override
    public List<PedigreeModel> findAll() {
        return pedigreeRepository.findAll();
    }

    @Override
    public List<PedigreeModel> findAllByGenealogyId(Integer genealogyId) {
        return pedigreeRepository.findAllByGenealogyModel_Id(genealogyId);
    }

    @Override
    public Optional<PedigreeModel> findById(Integer id) {
        return pedigreeRepository.findById(id);
    }

    @Override
    public void add(PedigreeModel pedigreeModel, Integer idGenealogy) {
        Optional<GenealogyModel> genealogyModel = genealogyRepository.findById(idGenealogy);
        if(genealogyModel != null) {
            pedigreeModel.setGenealogyModel(genealogyModel.get());
            pedigreeRepository.save(pedigreeModel);
        }else {
            System.out.println("genealogy null");
        }
    }

    @Override
    public void update(PedigreeModel pedigreeModel) {
        if(pedigreeModel != null) {
            pedigreeRepository.save(pedigreeModel);
        }else {
            System.out.println("pedigreeModel null");
        }
    }

    @Override
    public void delete(PedigreeModel pedigreeModel) {
        pedigreeRepository.delete(pedigreeModel);
    }
}
